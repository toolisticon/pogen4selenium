package io.toolisticon.pogen4selenium.processor.common.actions;

import java.util.Collections;
import java.util.Set;

import javax.lang.model.element.Element;

import io.toolisticon.pogen4selenium.api.ActionClick;
import io.toolisticon.pogen4selenium.api._By;
import io.toolisticon.pogen4selenium.processor.pageobject.ActionClickWrapper;
import io.toolisticon.spiap.api.SpiService;

@SpiService(ActionHandler.class)
public class ActionClickHandler implements ActionHandler {

	@Override
	public String getSupportedActionAnnotationClassFqn() {
		return ActionClick.class.getCanonicalName();
	}

	@Override
	public String generateCode(Element element) {
		ActionClickWrapper wrapper = ActionClickWrapper.wrap(element);
		
		if (wrapper.by() == _By.ELEMENT) {
			return "waitForElementToBeInteractable( " + wrapper.value() + "Element).click();\n";
		} else {
			return "waitForElementToBeInteractable( By." + wrapper.by().getCorrespondingByMethodName() + "(\"" + wrapper.value() +"\")).click();\n";
		}
		
	}

	@Override
	public Set<String> getImports(Element element) {
		return Collections.emptySet();
	}

}
