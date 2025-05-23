package io.toolisticon.pogen4selenium.api;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import io.toolisticon.pogen4selenium.runtime.DefaultSideCondition;
import io.toolisticon.pogen4selenium.runtime.LocatorCondition;

/**
 * This annotation can be used inside interfaces annotated with either {@link PageObject} or {@link DataObject}.
 * It allows extraction of single data values as String.
 * So annotated method must have return type of String.
 */

@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface ExtractDataValue {

	/** The locator type to use. */
	@LocatorBy
	_By by() default _By.XPATH;
	
	/** The locator string used together with locator configured in by. Be sure to use './/', if your relative xpath locator string starts with '//', otherwise the whole document will be scanned. */
	@LocatorValue
	String value();
	
	/**
	 * The locator strategy to use, will just be taken into account if by attribute is not set to ELEMENT.
	 * @return the Locator strategy, defaults to DefaultLocatorStrategy
	 */
	@LocatorSideCondition
	Class<? extends LocatorCondition> locatorSideCondition() default DefaultSideCondition.class;
	
	/** The kind of data to extract from element. */
	Kind kind() default Kind.TEXT;
	
	/** attribute or property name. */
	String name() default "";
	

	enum Kind{
		TEXT("getText"),
		ATTRIBUTE("getAttribute"),
		CSS_VALUE("getCssValue"),
		TAG_NAME("getTagName"),
		ACCESSIBLE_NAME("getAccessibleName");
		
		private final String elementMethodName; 
		
		private Kind(String elementMethodName) {
			this.elementMethodName = elementMethodName;
		}
		
		public String getElementMethodName() {
			return this.elementMethodName;
		}
	}
	
}
