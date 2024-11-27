package io.toolisticon.pogen4selenium.runtime;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * These are the locators which should be used by actions, that are both available in {@link DataObjectParentImpl} and {@link PageObjectParentImpl}.
 */
public interface CommonByLocators {

	 WebElement waitForElementToBeInteractable(By by);
	
	 WebElement waitForElementToBePresent(By by);	
	
}
