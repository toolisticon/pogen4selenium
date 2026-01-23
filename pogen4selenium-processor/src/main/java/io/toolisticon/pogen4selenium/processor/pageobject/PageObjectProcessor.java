package io.toolisticon.pogen4selenium.processor.pageobject;

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
import io.toolisticon.pogen4selenium.api.CommonParentInterface;
import io.toolisticon.pogen4selenium.api.PageObject;
import io.toolisticon.pogen4selenium.api.PageObjectParent;
import io.toolisticon.pogen4selenium.processor.common.MethodsToImplementHelper;
import io.toolisticon.spiap.api.SpiService;

/**
 * Annotation Processor for {@link io.toolisticon.pogen4selenium.api.PageObject}.
 *
 * This demo processor does some validations and creates a class.
 */

@SpiService(Processor.class)
@DeclareCompilerMessageCodePrefix("PageObject")
public class PageObjectProcessor extends AbstractAnnotationProcessor {

    private final static Set<String> SUPPORTED_ANNOTATIONS = createSupportedAnnotationSet(PageObject.class);

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return SUPPORTED_ANNOTATIONS;
    }

    @Override
    public boolean processAnnotations(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (!roundEnv.processingOver()) {
            // process Services annotation
            for (Element element : roundEnv.getElementsAnnotatedWith(PageObject.class)) {

                TypeElementWrapper wrappedTypeElement = TypeElementWrapper.wrap((TypeElement) element);
                PageObjectWrapper annotation = PageObjectWrapper.wrap(wrappedTypeElement.unwrap());

                if (validateUsage(wrappedTypeElement, annotation)) {
                    processAnnotation(wrappedTypeElement, annotation);
                }

            }

        } else {

            // ProcessingOver round

        }
        return false;

    }

    void processAnnotation(TypeElementWrapper wrappedTypeElement, PageObjectWrapper annotation) {

        createClass(wrappedTypeElement, annotation);

    }

    @DeclareCompilerMessage(code = "ERROR_011", enumValueName = "ERROR_COULD_INTERFACE_MUST_NOT_HAVE_TYPEPARAMETERS", message = "Interface '${0}' annotated with '${1}' must not have type parameters. Please use an interface that extends this interface to resolve type parameters to concrete types")
    boolean validateUsage(TypeElementWrapper wrappedTypeElement, PageObjectWrapper annotation) {

    	boolean result = true;
    	
    	// First make sure that the annotation is placed on interface and interface extends PageObjectParent
    	result = result 
    			& wrappedTypeElement.validateWithFluentElementValidator()
    				.is(AptkCoreMatchers.IS_INTERFACE)
    				.validateAndIssueMessages();
    	
    	if (wrappedTypeElement.hasTypeParameters()) {
    		wrappedTypeElement.compilerMessage().asError().write(PageObjectProcessorCompilerMessages.ERROR_COULD_INTERFACE_MUST_NOT_HAVE_TYPEPARAMETERS, wrappedTypeElement.getSimpleName(), PageObject.class.getSimpleName());
    		result = false;
    	}
    	
    			
    	
    	// now validate Elements
    	for (PageObjectElementWrapper pageObjectElementWrapper : annotation.value()) {
    		
    		result = result & FluentElementValidator.createFluentElementValidator(pageObjectElementWrapper._annotatedElement())
    				.applyValidator(AptkCoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
    				.is(AptkCoreMatchers.IS_FIELD)
    				.applyValidator(AptkCoreMatchers.BY_REGEX_NAME).hasOneOf(".*_ID")
    				.applyValidator(AptkCoreMatchers.BY_RAW_TYPE).hasOneOf(String.class)
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
    private void createClass(TypeElementWrapper wrappedTypeElement, PageObjectWrapper annotation) {


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
        				).getQualifiedName().equals(PageObjectParent.class.getCanonicalName()))
    			)
        		// filter out all CommonParentInterface methods
        		.filter(e -> !(ElementWrapper.toTypeElement(
        					e.getEnclosingElement().get()
        				).getQualifiedName().equals(CommonParentInterface.class.getCanonicalName()))
    			)
        		.map(MethodsToImplementHelper::new)
	    		.collect(Collectors.toSet());
        
        Set<MethodsToImplementHelper> defaultMethods = InterfaceUtils.getMethodsToImplement(wrappedTypeElement)
        		.stream()
        		// filter out all default methods
        		.filter(e -> e.isDefault())
        		// Filter out all static  methods
        		.filter(e -> !e.hasModifiers(Modifier.STATIC))
        		// filter out all PageObjectParent methods
        		.filter(e -> !(ElementWrapper.toTypeElement(
        					e.getEnclosingElement().get()
        				).getQualifiedName().equals(PageObjectParent.class.getCanonicalName()))
    			)
        		// filter out all CommonParentInterface methods
        		.filter(e -> !(ElementWrapper.toTypeElement(
        					e.getEnclosingElement().get()
        				).getQualifiedName().equals(CommonParentInterface.class.getCanonicalName()))
    			)
        		.map(MethodsToImplementHelper::new)
	    		.collect(Collectors.toSet());
        
	    Set<String> imports = new HashSet<>();
	    imports.addAll(toImplementHelper.getImports());
	    imports.addAll(methodsToImplement.stream().map(MethodsToImplementHelper::getImports)
	    		.flatMap(set -> set.stream()).collect(Collectors.toSet()));
	    imports.addAll(defaultMethods.stream().map(MethodsToImplementHelper::getImports)
	    		.flatMap(set -> set.stream()).collect(Collectors.toSet()));
        
        // Fill Model
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("imports", imports);
        model.put("toImplementHelper", toImplementHelper);
        model.put("packageName", packageName);
        model.put("pageObject", annotation);
        model.put("methodsToImplement",methodsToImplement);
        model.put("defaultMethods", defaultMethods);
        
        // create the class
        String filePath = packageName + "." + toImplementHelper.getImplementationClassName();
        try {
            SimpleJavaWriter javaWriter = FilerUtils.createSourceFile(filePath, wrappedTypeElement.unwrap());
            javaWriter.writeTemplate("/PageObject.tpl", model);
            javaWriter.close();
        } catch (IOException e) {
            wrappedTypeElement.compilerMessage().asError().write(PageObjectProcessorCompilerMessages.ERROR_COULD_NOT_CREATE_CLASS, filePath, e.getMessage());
        }
    }

}
