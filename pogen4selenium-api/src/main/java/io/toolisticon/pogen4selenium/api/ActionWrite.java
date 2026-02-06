package io.toolisticon.pogen4selenium.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.toolisticon.pogen4selenium.runtime.DefaultSideCondition;
import io.toolisticon.pogen4selenium.runtime.LocatorCondition;
import io.toolisticon.pogen4selenium.runtime.actions.ActionWriteImpl;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Action(value= ActionWriteImpl.class, attributeNameToConstructorMapping = {"clickElementBeforeWrite"} )
public @interface ActionWrite {
	
	/**
	 * The locator type to use. Can be ELEMENT for using a generated element or any kind of locator provided by Selenium.
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
	
	/**
	 * Per default the Action will click the element first. In some cases this click could be intercepted for example by a label.
	 * So this attribute can be used to suppress the first click
	 * @return should click the element before write
	 */
	boolean clickElementBeforeWrite() default true;
}
