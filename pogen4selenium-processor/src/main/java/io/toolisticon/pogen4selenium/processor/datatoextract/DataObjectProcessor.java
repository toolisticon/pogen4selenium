package io.toolisticon.pogen4selenium.processor.datatoextract;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import io.toolisticon.aptk.compilermessage.api.DeclareCompilerMessage;
import io.toolisticon.aptk.compilermessage.api.DeclareCompilerMessageCodePrefix;
import io.toolisticon.aptk.tools.AbstractAnnotationProcessor;
import io.toolisticon.aptk.tools.FilerUtils;
import io.toolisticon.aptk.tools.InterfaceUtils;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.aptk.tools.fluentvalidator.FluentElementValidator;
import io.toolisticon.aptk.tools.generators.SimpleJavaWriter;
import io.toolisticon.aptk.tools.wrapper.ElementWrapper;
import io.toolisticon.aptk.tools.wrapper.TypeElementWrapper;
import io.toolisticon.pogen4selenium.api.DataObject;
import io.toolisticon.pogen4selenium.processor.common.MethodsToImplementHelper;
import io.toolisticon.pogen4selenium.processor.common.actions.ActionHelper;
import io.toolisticon.pogen4selenium.runtime.DataObjectParentImpl;
import io.toolisticon.spiap.api.SpiService;

/**
 * Annotation Processor for {@link io.toolisticon.pogen4selenium.api.PageObject}.
 *
 * This demo processor does some validations and creates a class.
 */

@SpiService(Processor.class)
@DeclareCompilerMessageCodePrefix("DataObject")
public class DataObjectProcessor extends AbstractAnnotationProcessor {

    private final static Set<String> SUPPORTED_ANNOTATIONS = createSupportedAnnotationSet(DataObject.class);

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return SUPPORTED_ANNOTATIONS;
    }

    @Override
    public boolean processAnnotations(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (!roundEnv.processingOver()) {
            // process Services annotation
            for (Element element : roundEnv.getElementsAnnotatedWith(DataObject.class)) {

                TypeElementWrapper wrappedTypeElement = TypeElementWrapper.wrap((TypeElement) element);
                DataObjectWrapper annotation = DataObjectWrapper.wrap(wrappedTypeElement.unwrap());

                if (validateUsage(wrappedTypeElement, annotation)) {
                    processAnnotation(wrappedTypeElement, annotation);
                }

            }

        } else {

            // ProcessingOver round

        }
        return false;

    }

    void processAnnotation(TypeElementWrapper wrappedTypeElement, DataObjectWrapper annotation) {

        createClass(wrappedTypeElement, annotation);

    }

    boolean validateUsage(TypeElementWrapper wrappedTypeElement, DataObjectWrapper annotation) {

    	boolean result = true;
    	
    	// First make sure that the annotation is placed on interface and interface extends PageObjectParent
    	result = result & wrappedTypeElement.validateWithFluentElementValidator().is(AptkCoreMatchers.IS_INTERFACE)
			//.applyValidator(AptkCoreMatchers.IS_ASSIGNABLE_TO).hasOneOf(PageObjectParent.class)
			.validateAndIssueMessages();
    	
    	// now validate Elements
    	for (ExtractDataValueWrapper dataStorageElementWrapper : annotation.value()) {
    		
    		result = result & FluentElementValidator.createFluentElementValidator(dataStorageElementWrapper._annotatedElement())
    				.applyValidator(AptkCoreMatchers.BY_MODIFIER).hasNoneOf(Modifier.STATIC, Modifier.DEFAULT)
    				.is(AptkCoreMatchers.IS_METHOD)
    				.applyValidator(AptkCoreMatchers.HAS_NO_PARAMETERS)
    				.applyValidator(AptkCoreMatchers.BY_RETURN_TYPE).hasOneOf(String.class)
    				.validateAndIssueMessages();
    		
    	}
    	
    	return result;

    }

    /**
     * Generates a class.
     *
     * Example how to use the templating engine.
     *
     * TODO: remove this
     *
     * @param wrappedTypeElement           The TypeElement representing the annotated class
     * @param annotation The PageObject annotation
     */
    @DeclareCompilerMessage(code = "ERROR_001", enumValueName = "ERROR_COULD_NOT_CREATE_CLASS", message = "Could not create class ${0} : ${1}")
    private void createClass(TypeElementWrapper wrappedTypeElement, DataObjectWrapper annotation) {


        // Now create class
        String packageName = wrappedTypeElement.getPackageName();
        ToImplementHelper toImplementHelper = new ToImplementHelper(wrappedTypeElement);
        
        
        Set<MethodsToImplementHelper> methodsToImplement = InterfaceUtils.getMethodsToImplement(wrappedTypeElement)
        		.stream()
        		// filter out all default methods
        		.filter(e -> !e.isDefault())
        		// Filter out all static  methods
        		.filter(e -> !e.hasModifiers(Modifier.STATIC))
        		// filter out all PageObjectParent methods
        		.filter(e -> !(ElementWrapper.toTypeElement(
        					e.getEnclosingElement().get()
        				).getQualifiedName().equals(DataObjectParentImpl.class.getCanonicalName()))
    			)
        		// just keep methods with action annotations
        		.filter(e -> ActionHelper.hasActions(e))
        		.map(MethodsToImplementHelper::new)
	    		.collect(Collectors.toSet());
        
	    Set<String> imports = new HashSet<>();
	    imports.addAll(toImplementHelper.getImports());
	    imports.addAll(methodsToImplement.stream().map(MethodsToImplementHelper::getImports)
	    		.flatMap(set -> set.stream()).collect(Collectors.toSet()));
        
        // Fill Model
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("imports", imports);
        model.put("toImplementHelper", toImplementHelper);
        model.put("packageName", packageName);
        model.put("dataToExtract", annotation);
        model.put("methodsToImplement",methodsToImplement);
        
        // create the class
        String filePath = packageName + "." + toImplementHelper.getImplementationClassName();
        try {
            SimpleJavaWriter javaWriter = FilerUtils.createSourceFile(filePath, wrappedTypeElement.unwrap());
            javaWriter.writeTemplate("/DataToExtract.tpl", model);
            javaWriter.close();
        } catch (IOException e) {
            wrappedTypeElement.compilerMessage().asError().write(DataObjectProcessorCompilerMessages.ERROR_COULD_NOT_CREATE_CLASS, filePath, e.getMessage());
        }
    }

}
