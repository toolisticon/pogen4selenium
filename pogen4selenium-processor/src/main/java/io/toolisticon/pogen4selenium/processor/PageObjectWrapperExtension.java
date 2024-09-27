package io.toolisticon.pogen4selenium.processor;

import java.util.List;
import java.util.stream.Collectors;

import io.toolisticon.aptk.annotationwrapper.api.CustomCodeMethod;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.aptk.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.pogen4selenium.api.PageObject;
import io.toolisticon.pogen4selenium.api.PageObjectElement;

public class PageObjectWrapperExtension {

	@CustomCodeMethod(PageObject.class)
	public static List<PageObjectElementWrapper> value(PageObjectWrapper pageObjectWrapper) {
		
		return FluentElementFilter.createFluentElementFilter(pageObjectWrapper._annotatedElement().getEnclosedElements())
			.applyFilter(AptkCoreMatchers.BY_ANNOTATION).filterByAllOf(PageObjectElement.class).getResult().stream().map(e -> PageObjectElementWrapper.wrap(e)).collect(Collectors.toList());
		
	}
	
}
