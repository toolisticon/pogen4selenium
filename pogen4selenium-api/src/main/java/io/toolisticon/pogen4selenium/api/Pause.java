package io.toolisticon.pogen4selenium.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows to apply pauses at the beginning or end of the generated action.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Pause {
	/**
	 * Applies a pause in ms at the beginning of the action. Defaults to 0.
	 */
	long pauseBefore() default 0;
	
	/** Applies a pause in ms at the end of the action */
	long value();
	
}
