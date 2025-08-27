package io.toolisticon.pogen4selenium.processor.common.actions;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import javax.lang.model.element.Element;


import io.toolisticon.aptk.tools.ElementUtils;
import io.toolisticon.aptk.tools.TypeMirrorWrapper;
import io.toolisticon.aptk.tools.wrapper.AnnotationMirrorWrapper;
import io.toolisticon.aptk.tools.wrapper.AnnotationValueWrapper;
import io.toolisticon.aptk.tools.wrapper.ElementWrapper;
import io.toolisticon.aptk.tools.wrapper.PackageElementWrapper;
import io.toolisticon.pogen4selenium.api.LocatorBy;
import io.toolisticon.pogen4selenium.api.LocatorSideCondition;
import io.toolisticon.pogen4selenium.api.LocatorValue;
import io.toolisticon.pogen4selenium.api.Retry;
import io.toolisticon.pogen4selenium.api.RetryConfig;
import io.toolisticon.pogen4selenium.api._By;
import io.toolisticon.pogen4selenium.processor.pageobject.ActionWrapper;
import io.toolisticon.pogen4selenium.runtime.RetryImpl.RetryConfigType;

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
		
		String attributeString = actionWrapper.getAttributeValuesString(annotationMirrorWrapper);
				
		if (by == _By.ELEMENT) {
			return "new " + actionWrapper.valueAsTypeMirrorWrapper().getSimpleName() + "( getDriver(), getSearchContext(), new " + sideConditionTmw.getSimpleName() + "()" + (elementWrapper.isMethodParameter() ? ", " + element.getSimpleName() : "") + (attributeString.isEmpty() ? "" : "," + attributeString) +  ").executeAction(" + annotationValue + "Element);\n";
		} else {
			return "new " + actionWrapper.valueAsTypeMirrorWrapper().getSimpleName() + "( getDriver(), getSearchContext(), new " + sideConditionTmw.getSimpleName() + "()" + (elementWrapper.isMethodParameter() ? ", " + element.getSimpleName() : "")  + (attributeString.isEmpty() ? "" : "," + attributeString) + ").executeAction(By." + by.getCorrespondingByMethodName() + "(\"" + annotationValue +"\"));\n";
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
		imports.addAll(getRetryConfig(element).getImports());
		return imports;
	}

	@Override
	public String getSupportedActionAnnotationClassFqn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RetryHandler getRetryConfig(Element element) {
		
		RetryWrapper retryConfigViaAnnotation = getManualDefaults(element);
		
		try {
			
			AnnotationMirrorWrapper annotationMirrorWrapper = AnnotationMirrorWrapper.get(element, annotation).get();
			
			Optional<AnnotationValueWrapper> avWrapper = annotationMirrorWrapper.getAttributeByAnnotation(RetryConfig.class);
			
			RetryWrapper retryConfig = null;
			
			if (!avWrapper.isEmpty()) {
				retryConfig = RetryWrapper.wrap(avWrapper.get().getAnnotationValue().unwrap());
				return new RetryHandlerImpl(RetryConfigType.EXPLICIT, retryConfig.nrOfRetries(), retryConfig.retryIntervallInMs(), Arrays.asList(retryConfig.retryCausesAsTypeMirrorWrapper()));
				
			} else {
				
				// default - check if Retry annotation could be found at enclosing elements 
				if (retryConfigViaAnnotation != null) {
					retryConfig = retryConfigViaAnnotation;
				} else {
					retryConfig = RetryWrapper.wrap(annotationMirrorWrapper.getAttributeWithDefaultByAnnotation(RetryConfig.class).getAnnotationValue().unwrap());
				}
				return new RetryHandlerImpl(RetryConfigType.DEFAULT, retryConfig.nrOfRetries(), retryConfig.retryIntervallInMs(), Arrays.asList(retryConfig.retryCausesAsTypeMirrorWrapper()));
			}
			
			
		} catch (IllegalArgumentException e) {
			
			// no value could be found
			if (retryConfigViaAnnotation != null) {
				return new RetryHandlerImpl(RetryConfigType.DEFAULT, retryConfigViaAnnotation.nrOfRetries(), retryConfigViaAnnotation.retryIntervallInMs(), Arrays.asList(retryConfigViaAnnotation.retryCausesAsTypeMirrorWrapper()));
			} else {
				return new RetryHandlerImpl(RetryConfigType.UNDEFINED, 0, 0, Collections.emptyList());
			}
		}
		
		
	}


	
	RetryWrapper getManualDefaults (Element element) {
		
		ElementWrapper<?> elementWrapper = ElementWrapper.wrap(element);
		
		for (ElementWrapper<Element> ew : elementWrapper.getAllEnclosingElements(true)) {
			if (ew.hasAnnotation(Retry.class)) {
				return RetryWrapper.wrap(ew);
			}
		}
		
		// check parent packages if it wasn't found so far
		PackageElementWrapper packageElementWrapper = elementWrapper.getPackage();
		Optional<PackageElementWrapper> parentPackage = packageElementWrapper.getParentPackage();
		while (parentPackage.isPresent()) {
			
			if (parentPackage.get().hasAnnotation(Retry.class)) {
				return RetryWrapper.wrap(parentPackage.get());
			}
			
			parentPackage = parentPackage.get().getParentPackage();
		}
		
		
		
		return null;
	}
	

}
