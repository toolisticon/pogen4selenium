package io.toolisticon.pogen4selenium.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a page object element.
 * 
 * This annotation must be placed on a final static String constant inside an interface.
 * The surrounding interface must not be annotated with a {@link PageObject}, if the interface is used as a parent interface of another page object.
 * Otherwise the surrounding interface must be annotated with {@link PageObject}.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PageObjectElement {

	/**
	 * The element variable name. Must refer to the String based constant that's annotated with this annotation.
	 * Sorry for doing so. This is the only way to read the constants value inside the annotation.
	 * @return the value of the annotated constant
	 */
	String elementVariableName();
	
	/**
	 * Defines how the element should be located.
	 * @return The locator method to be used. Defaults to XPATH
	 */
	@LocatorBy
	_By by() default _By.XPATH;
	
	/**
	 * Defines the locator string used by the locator method defined in by.
	 * @return the locator string
	 */
	@LocatorValue
	String value();
	
	/**
	 * Defines if and how the element should be verified in verify method.
	 * @return the verification type used in verify.
	 */
	VerifyType usedForVerify() default VerifyType.PRESENT;

	/**
	 * The verify types.
	 */
	enum VerifyType {
		/** Skip verification */
		NONE,
		/** Check if element is present */
		PRESENT,
		/** Check if element is clickable */
		CLICKABLE
	}
}
