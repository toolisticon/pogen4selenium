package io.toolisticon.pogen4selenium.processor.datatoextract;

import javax.lang.model.element.VariableElement;

import io.toolisticon.aptk.annotationwrapper.api.CustomCodeMethod;
import io.toolisticon.aptk.tools.wrapper.ExecutableElementWrapper;
import io.toolisticon.aptk.tools.wrapper.VariableElementWrapper;
import io.toolisticon.pogen4selenium.api.ExtractDataValue;

public class ExtractDataValueWrapperExtension {

	@CustomCodeMethod(ExtractDataValue.class)
	public static String locatorConstantName(ExtractDataValueWrapper extractDataValueWrapper) {
		
		return VariableElementWrapper.wrap((VariableElement)extractDataValueWrapper._annotatedElement()).getSimpleName().replaceAll("_ID", "_LOCATION_" + extractDataValueWrapper.by().name());
		
	}
	
	@CustomCodeMethod(ExtractDataValue.class)
	public static String methodName(ExtractDataValueWrapper extractDataValueWrapper) {
		
		return ExecutableElementWrapper.wrap(extractDataValueWrapper._annotatedElement()).getSimpleName();
		
	}
	
	
}
