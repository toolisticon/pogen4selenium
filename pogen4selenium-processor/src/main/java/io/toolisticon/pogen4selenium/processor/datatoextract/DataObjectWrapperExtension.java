package io.toolisticon.pogen4selenium.processor.datatoextract;

import java.util.List;
import java.util.stream.Collectors;

import io.toolisticon.aptk.annotationwrapper.api.CustomCodeMethod;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.aptk.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.pogen4selenium.api.DataObject;
import io.toolisticon.pogen4selenium.api.ExtractDataValue;

public class DataObjectWrapperExtension {

	@CustomCodeMethod(DataObject.class)
	public static List<ExtractDataValueWrapper> value(DataObjectWrapper dataObjectWrapper) {
		
		return FluentElementFilter.createFluentElementFilter(dataObjectWrapper._annotatedElement().getEnclosedElements())
			.applyFilter(AptkCoreMatchers.BY_ANNOTATION).filterByAllOf(ExtractDataValue.class).getResult().stream().map(e -> ExtractDataValueWrapper.wrap(e)).collect(Collectors.toList());
		
	}
	
	
	
}
