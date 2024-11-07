package io.toolisticon.pogen4selenium.processor.pageobject.actions;

import java.util.Collections;
import java.util.Set;

import javax.lang.model.element.Element;

import io.toolisticon.pogen4selenium.api.ActionWrite;
import io.toolisticon.pogen4selenium.api._By;
import io.toolisticon.pogen4selenium.processor.pageobject.ActionWriteWrapper;
import io.toolisticon.spiap.api.SpiService;

@SpiService(ActionHandler.class)
public class ActionWriteHandler implements ActionHandler {

	@Override
	public String getSupportedActionAnnotationClassFqn() {
		return ActionWrite.class.getCanonicalName();
	}

	@Override
	public String generateCode(Element element) {
		ActionWriteWrapper wrapper = ActionWriteWrapper.wrap(element);
		
		
		if (wrapper.by() == _By.ELEMENT) {
			return "writeToElement((" + wrapper.value() + "Element), " + element.getSimpleName() + ");";
		} else {
			return "writeToElement(waitForElementToBePresent(By." + wrapper.by().getCorrespondingByMethodName() + "(\"" + wrapper.value() + "\")), " + element.getSimpleName() + ");";
		}
		
		
		
	}

	@Override
	public Set<String> getImports(Element element) {
		return Collections.emptySet();
	}

}
