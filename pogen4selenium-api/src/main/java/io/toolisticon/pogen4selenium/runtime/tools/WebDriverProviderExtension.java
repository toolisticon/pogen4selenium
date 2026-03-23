package io.toolisticon.pogen4selenium.runtime.tools;

import org.openqa.selenium.WebDriver;

/**
 * Allows extending of the WebDriverProvider
 */
public interface WebDriverProviderExtension {

	
	void extendDriver(WebDriver webDriver); 
	
}
