package io.toolisticon.pogen4selenium.api;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import io.toolisticon.pogen4selenium.runtime.BaseAction;
import io.toolisticon.pogen4selenium.runtime.DefaultSideCondition;
import io.toolisticon.pogen4selenium.runtime.LocatorCondition;

/**
 * Meta-Annotation to allow custom annotations.
 * 
 */

@Documented
@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
public @interface Action {
	
	/**
	 * The implementation class.
	 * @return
	 */
	Class<? extends ActionImpl> value();

	
}
