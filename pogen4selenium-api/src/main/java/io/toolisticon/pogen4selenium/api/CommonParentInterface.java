package io.toolisticon.pogen4selenium.api;

import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.WebDriver;

import io.toolisticon.pogen4selenium.runtime.PageObjectUtilities;

public interface CommonParentInterface {
	
	/**
	 * Gets the seleium driver.
	 * @return the selenium driver currently used
	 */
	WebDriver getDriver();
	
	/**
	 * This method can be used to change the page object type.
	 * This will be helpful if you encounter expected situations that differ from "happy path" like i.e. having a failing form validation.
	 * @param <APO> the alternative page object type
	 */
	default <APO extends PageObjectParent<APO>> APO changePageObjectType(Class<APO> targetPageObjectType) {
		
		return PageObjectUtilities.getPageObjectInstance(targetPageObjectType, getDriver());
		
	}
	
	
	
	
	
	
}
