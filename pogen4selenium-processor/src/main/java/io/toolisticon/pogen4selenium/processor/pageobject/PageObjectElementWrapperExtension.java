package io.toolisticon.pogen4selenium.processor.pageobject;

import javax.lang.model.element.VariableElement;

import io.toolisticon.aptk.annotationwrapper.api.CustomCodeMethod;
import io.toolisticon.aptk.tools.wrapper.VariableElementWrapper;
import io.toolisticon.pogen4selenium.api.PageObjectElement;

public class PageObjectElementWrapperExtension {

	@CustomCodeMethod(PageObjectElement.class)
	public static String locatorConstantName(PageObjectElementWrapper pageObjectElementWrapper) {
		
		return VariableElementWrapper.wrap((VariableElement)pageObjectElementWrapper._annotatedElement()).getSimpleName().replaceAll("_ID", "_LOCATION_" + pageObjectElementWrapper.by().name());
		
	}
	
}
