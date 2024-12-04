package io.toolisticon.pogen4selenium.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import io.toolisticon.pogen4selenium.runtime.ActionMoveToAndClickImpl;
import io.toolisticon.pogen4selenium.runtime.DefaultLocatorStrategy;
import io.toolisticon.pogen4selenium.runtime.DefaultSideCondition;
import io.toolisticon.pogen4selenium.runtime.LocatorCondition;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Action(ActionMoveToAndClickImpl.class)
public @interface ActionMoveToAndClick {
	
	/**
	 * The locator type to use. Can be ELEMENT for using a generated element or any kind of locator provided by selenium.
	 * @return the locator to use.
	 */
	_By by() default _By.ELEMENT;
	
	/** The locator string to use. */
	String value();
	
	/**
	 * The locator strategy to use, will just be taken into account if by attribute is not set to ELEMENT.
	 * @return the Locator strategy, defaults to DefaultLocatorStrategy
	 */
	@ActionSideCondition
	Class<? extends LocatorCondition> actionSideCondition() default DefaultSideCondition.class;
}
