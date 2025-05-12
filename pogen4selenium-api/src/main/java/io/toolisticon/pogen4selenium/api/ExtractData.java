package io.toolisticon.pogen4selenium.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to extract data relative to passed element located by the annotation.
 * This annotation must be used inside a {@link PageObject} annotated interface.
 * Method must have a non void return type annotated with {@link DataObject} annotation or a list of it.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtractData {
	@LocatorBy
	_By by() default _By.XPATH;
	
	@LocatorValue
	String value();
}
