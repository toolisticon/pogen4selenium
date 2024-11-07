package io.toolisticon.pogen4selenium.processor.pageobject.actions;

import java.util.Collections;
import java.util.Set;

import javax.lang.model.element.Element;

import io.toolisticon.pogen4selenium.api.ActionMoveToAndClick;
import io.toolisticon.pogen4selenium.api._By;
import io.toolisticon.pogen4selenium.processor.pageobject.ActionClickWrapper;
import io.toolisticon.pogen4selenium.processor.pageobject.ActionMoveToAndClickWrapper;
import io.toolisticon.spiap.api.SpiService;

@SpiService(ActionHandler.class)
public class ActionMoveAndClickHandler implements ActionHandler {

	@Override
	public String getSupportedActionAnnotationClassFqn() {
		return ActionMoveToAndClick.class.getCanonicalName();
	}

	@Override
	public String generateCode(Element element) {
		ActionMoveToAndClickWrapper wrapper = ActionMoveToAndClickWrapper.wrap(element);
		
		if (wrapper.by() == _By.ELEMENT) {
			return "new Actions(getDriver()).moveToElement(waitForElementToBeInteractable( " + wrapper.value() + "Element)).pause(300).click().build().perform();\n";
		} else {
			return "new Actions(getDriver()).moveToElement(waitForElementToBeInteractable( By." + wrapper.by().getCorrespondingByMethodName() + "(\"" + wrapper.value() +"\"))).pause(300).click().build().perform();\n";
		}
		
	}

	@Override
	public Set<String> getImports(Element element) {
		return Collections.emptySet();
	}

}
