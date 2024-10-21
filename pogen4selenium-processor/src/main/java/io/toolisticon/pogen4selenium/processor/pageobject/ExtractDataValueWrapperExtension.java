package io.toolisticon.pogen4selenium.processor.pageobject;

import io.toolisticon.aptk.annotationwrapper.api.CustomCodeMethod;
import io.toolisticon.pogen4selenium.api.By;
import io.toolisticon.pogen4selenium.api.ExtractDataValue;

public class ExtractDataValueWrapperExtension {
	
	@CustomCodeMethod(ExtractDataValue.class)
	public static String getFinalMethodCall(ExtractDataValueWrapper extractDataValueWrapper) {
		
		String command = (
					extractDataValueWrapper.by() == By.ELEMENT ? 
						extractDataValueWrapper.value() + "Element"
						: "getDriver().findElement(By." + extractDataValueWrapper.by().getCorrespondingByMethodName() + "(\"" + extractDataValueWrapper.value() + "\"))"
				)
				+ ".";
		
		switch (extractDataValueWrapper.kind()) {
			case ATTRIBUTE:
			case CSS_VALUE: {
				
				return command + extractDataValueWrapper.kind().getElementMethodName() + "(\"" + extractDataValueWrapper.name() + "\");";
			}
			default: {
				
				return command + extractDataValueWrapper.kind().getElementMethodName() + "();";
			}
		}
		
		
	}

}
