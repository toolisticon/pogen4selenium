package io.toolisticon.pogen4selenium.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation marks interfaces as PageObjects.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
@Documented
public @interface PageObject {
	/**
	 * Adds a check to the verify method if current page url matches the regular expression if the attribute is set explicitly.
	 * Results in an active wait for url, which either runs in a timeout and throw an exception or successfully validates the url. 
	 * @return the url regular expression to verify
	 */
	String urlRegularExpressionToVerify() default "";
	
	
}
