package io.toolisticon.pogen4selenium.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.toolisticon.pogen4selenium.runtime.DefaultSideCondition;
import io.toolisticon.pogen4selenium.runtime.ElementClickedInterceptedExceptionCause;
import io.toolisticon.pogen4selenium.runtime.LocatorCondition;
import io.toolisticon.pogen4selenium.runtime.actions.ActionMoveToAndClickImpl;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Action(ActionMoveToAndClickImpl.class)
public @interface ActionMoveToAndClick {
	
	/**
	 * The locator type to use. Can be ELEMENT for using a generated element or any kind of locator provided by selenium.
	 * @return the locator to use.
	 */
	@LocatorBy
	_By by() default _By.ELEMENT;
	
	/** The locator string to use. */
	@LocatorValue
	String value();
	
	/**
	 * The locator strategy to use, will just be taken into account if by attribute is not set to ELEMENT.
	 * @return the Locator strategy, defaults to DefaultLocatorStrategy
	 */
	@LocatorSideCondition
	Class<? extends LocatorCondition> locatorSideCondition() default DefaultSideCondition.class;
	
	
	/*-
	 * A retry logic to handle things like spinner overlays in a better way than with side conditions
	 * @return The retry configuration
	 */
	/*-
	@RetryConfig
	Retry retry() default @Retry(nrOfRetries = 6, retryIntervallInMs = 300, retryCauses = {ElementClickedInterceptedExceptionCause.class});
	*/
}
