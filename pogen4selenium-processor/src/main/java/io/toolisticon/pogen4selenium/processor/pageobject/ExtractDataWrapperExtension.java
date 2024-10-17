package io.toolisticon.pogen4selenium.processor.pageobject;

import java.util.List;
import java.util.Optional;

import javax.lang.model.element.ExecutableElement;

import io.toolisticon.aptk.annotationwrapper.api.CustomCodeMethod;
import io.toolisticon.aptk.compilermessage.api.DeclareCompilerMessage;
import io.toolisticon.aptk.tools.TypeMirrorWrapper;
import io.toolisticon.aptk.tools.wrapper.ExecutableElementWrapper;
import io.toolisticon.aptk.tools.wrapper.TypeElementWrapper;
import io.toolisticon.pogen4selenium.api.DataObject;
import io.toolisticon.pogen4selenium.api.ExtractData;

public class ExtractDataWrapperExtension {
	
	@CustomCodeMethod(ExtractData.class)
	public static boolean isList(ExtractDataWrapper dataToExtractWrapper) {
		TypeMirrorWrapper returnTypeMirror = ExecutableElementWrapper.wrap((ExecutableElement)dataToExtractWrapper._annotatedElement()).getReturnType();
		return returnTypeMirror.erasure().isAssignableTo(List.class);
	}
	
	@CustomCodeMethod(ExtractData.class)
	public static boolean isString(ExtractDataWrapper dataToExtractWrapper) {
		TypeMirrorWrapper returnTypeMirror = ExecutableElementWrapper.wrap((ExecutableElement)dataToExtractWrapper._annotatedElement()).getReturnType();
		return returnTypeMirror.erasure().isAssignableTo(String.class);
	}

	@CustomCodeMethod(ExtractData.class)
	public static String getExtractedDataImplName(ExtractDataWrapper dataToExtractWrapper) {
		
		return getReturnExtractDataTypeMirror(dataToExtractWrapper).getTypeDeclaration() + "Impl";
		
	}
	
	static TypeMirrorWrapper getReturnExtractDataTypeMirror(ExtractDataWrapper dataToExtractWrapper) {
		
		TypeMirrorWrapper returnTypeMirror = ExecutableElementWrapper.wrap((ExecutableElement)dataToExtractWrapper._annotatedElement()).getReturnType();
		
		if (isList(dataToExtractWrapper)) {
			return returnTypeMirror.getWrappedComponentType();
		} else {
			return returnTypeMirror;
		}
		
	}
	
	@CustomCodeMethod(ExtractData.class)
	@DeclareCompilerMessage(code = "ERROR_002", enumValueName = "ERROR_RETURN_TYPE_MUST_BE_ANNOTATED_WITH_DATATOEXTRACT", message = "Return type of method annotated with {0} must be annotated with {1} or a List with component type annotated with {1}", processorClass = PageObjectProcessor.class)
	public static boolean validate(ExtractDataWrapper dataToExtractWrapper) {
		
		Optional<TypeElementWrapper> typeElementWrapper = getReturnExtractDataTypeMirror(dataToExtractWrapper).getTypeElement();
		
		if (!typeElementWrapper.isPresent() || typeElementWrapper.get().hasAnnotation(DataObject.class) ) {
			dataToExtractWrapper.compilerMessage().asError().write(PageObjectProcessorCompilerMessages.ERROR_RETURN_TYPE_MUST_BE_ANNOTATED_WITH_DATATOEXTRACT, ExtractData.class.getSimpleName(), DataObject.class.getSimpleName());
			return false;
		}
		
		
		return true;
	}
	
	
}
