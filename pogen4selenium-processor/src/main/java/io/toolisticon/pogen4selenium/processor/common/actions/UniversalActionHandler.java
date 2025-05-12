package io.toolisticon.pogen4selenium.processor.common.actions;

import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.Element;

import io.toolisticon.aptk.tools.TypeMirrorWrapper;
import io.toolisticon.aptk.tools.wrapper.AnnotationMirrorWrapper;
import io.toolisticon.aptk.tools.wrapper.ElementWrapper;
import io.toolisticon.pogen4selenium.api.LocatorBy;
import io.toolisticon.pogen4selenium.api.LocatorSideCondition;
import io.toolisticon.pogen4selenium.api.LocatorValue;
import io.toolisticon.pogen4selenium.api._By;
import io.toolisticon.pogen4selenium.processor.pageobject.ActionWrapper;

public class UniversalActionHandler implements ActionHandler {

	private final String annotation;
	
	public UniversalActionHandler(String annotation) {
		this.annotation = annotation;
	}
	
	@Override
	public String generateCode(Element element) {
		
		ElementWrapper<?> elementWrapper = ElementWrapper.wrap(element);
		
		AnnotationMirrorWrapper annotationMirrorWrapper = AnnotationMirrorWrapper.get(element, annotation).get();
		TypeMirrorWrapper sideConditionTmw = annotationMirrorWrapper.getAttributeWithDefaultByAnnotation(LocatorSideCondition.class).getClassValue();
		_By by = annotationMirrorWrapper.getAttributeWithDefaultByAnnotation(LocatorBy.class).getEnumValue(_By.class);
		String annotationValue = annotationMirrorWrapper.getAttributeWithDefaultByAnnotation(LocatorValue.class).getStringValue();
		
		ActionWrapper actionWrapper = ActionWrapper.wrap(annotationMirrorWrapper.asElement().unwrap());
		
		
		
		if (by == _By.ELEMENT) {
			return "new " + actionWrapper.valueAsTypeMirrorWrapper().getSimpleName() + "( getDriver(), getSearchContext(), new " + sideConditionTmw.getSimpleName() + "()" + (elementWrapper.isMethodParameter() ? ", " + element.getSimpleName() : "") + ").executeAction(" + annotationValue + "Element);\n";
		} else {
			return "new " + actionWrapper.valueAsTypeMirrorWrapper().getSimpleName() + "( getDriver(), getSearchContext(), new " + sideConditionTmw.getSimpleName() + "()" + (elementWrapper.isMethodParameter() ? ", " + element.getSimpleName() : "") + ").executeAction(By." + by.getCorrespondingByMethodName() + "(\"" + annotationValue +"\"));\n";
		}
		 
	}

	@Override
	public Set<String> getImports(Element element) {
		AnnotationMirrorWrapper annotationMirrorWrapper = AnnotationMirrorWrapper.get(element, annotation).get();
		TypeMirrorWrapper sideConditionTmw = annotationMirrorWrapper.getAttributeWithDefaultByAnnotation(LocatorSideCondition.class).getClassValue();
		
		ActionWrapper actionWrapper = ActionWrapper.wrap(annotationMirrorWrapper.asElement().unwrap());
		
		
		Set<String> imports =  new HashSet<>();
		imports.add(sideConditionTmw.getQualifiedName());
		imports.add(actionWrapper.valueAsTypeMirrorWrapper().getQualifiedName());
		
		return imports;
	}

	@Override
	public String getSupportedActionAnnotationClassFqn() {
		// TODO Auto-generated method stub
		return null;
	}




}
