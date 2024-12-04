package io.toolisticon.pogen4selenium.processor.common.actions;

import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.Element;

import io.toolisticon.aptk.tools.wrapper.AnnotationMirrorWrapper;
import io.toolisticon.pogen4selenium.api.ActionClick;
import io.toolisticon.pogen4selenium.api._By;
import io.toolisticon.pogen4selenium.processor.pageobject.ActionClickWrapper;
import io.toolisticon.pogen4selenium.processor.pageobject.ActionWrapper;
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
		
		ActionWrapper actionWrapper = ActionWrapper.wrap(AnnotationMirrorWrapper.wrap(wrapper._annotationMirror()).asElement().unwrap());
		
		if (wrapper.by() == _By.ELEMENT) {
			return "new " + actionWrapper.valueAsTypeMirrorWrapper().getSimpleName() + "( getDriver(), new " + wrapper.actionSideConditionAsTypeMirrorWrapper().getSimpleName() + "()).executeAction(" + wrapper.value() + "Element);\n";
		} else {
			return "new " + actionWrapper.valueAsTypeMirrorWrapper().getSimpleName() + "( getDriver(), new " + wrapper.actionSideConditionAsTypeMirrorWrapper().getSimpleName() + "()).executeAction(By." + wrapper.by().getCorrespondingByMethodName() + "(\"" + wrapper.value() +"\"));\n";
		}
		 
	}

	@Override
	public Set<String> getImports(Element element) {
		ActionClickWrapper wrapper = ActionClickWrapper.wrap(element);
		ActionWrapper actionWrapper = ActionWrapper.wrap(AnnotationMirrorWrapper.wrap(wrapper._annotationMirror()).asElement().unwrap());
		
		
		Set<String> imports =  new HashSet<>();
		imports.add(wrapper.actionSideConditionAsTypeMirrorWrapper().getQualifiedName());
		imports.add(actionWrapper.valueAsTypeMirrorWrapper().getQualifiedName());
		
		return imports;
	}

}
