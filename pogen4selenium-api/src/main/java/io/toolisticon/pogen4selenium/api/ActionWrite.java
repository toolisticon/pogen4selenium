package io.toolisticon.pogen4selenium.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Action
public @interface ActionWrite {
	
	/**
	 * The locator type to use. Can be ELEMENT for using a generated element or any kind of locator provided by selenium.
	 * @return the locator to use.
	 */
	_By by() default _By.ELEMENT;
	
	/** The locator string to use. */
	String value();
	
}
