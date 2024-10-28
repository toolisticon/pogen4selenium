package io.toolisticon.pogen4selenium.processor.pageobject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import io.toolisticon.aptk.annotationwrapper.api.CustomCodeMethod;
import io.toolisticon.aptk.tools.ElementUtils;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.aptk.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.pogen4selenium.api.PageObject;
import io.toolisticon.pogen4selenium.api.PageObjectElement;

public class PageObjectWrapperExtension {

	@CustomCodeMethod(PageObject.class)
	public static List<PageObjectElementWrapper> value(PageObjectWrapper pageObjectWrapper) {
		
		// Must be Sure to collect all annotated fields of this Page Object and all extended interfaces
		return getPageObjectElementsRecursively((TypeElement)pageObjectWrapper._annotatedElement());
		
	}
	
	@CustomCodeMethod(PageObject.class)
	public static boolean addUrlCheckToVerify(PageObjectWrapper pageObjectWrapper) {
		
		return !pageObjectWrapper.urlRegularExpressionToVerifyIsDefaultValue();
		
	}
	
	private static List<PageObjectElementWrapper> getPageObjectElementsRecursively(TypeElement element){
		
		List<PageObjectElementWrapper> result = new ArrayList<PageObjectElementWrapper>();
		
		// first get the PageObjectElements of this element
		result.addAll(FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
			.applyFilter(AptkCoreMatchers.BY_ANNOTATION)
			.filterByAllOf(PageObjectElement.class)
			.getResult().stream()
			.map(e -> PageObjectElementWrapper.wrap(e))
			.collect(Collectors.toList()));
		
		for (TypeElement extendedInterface : ElementUtils.AccessTypeHierarchy.getDirectSuperTypeElementsOfKindInterface(element)) {
			result.addAll(getPageObjectElementsRecursively(extendedInterface));
		}
		
		return result;
		
		
	}
}


