package io.toolisticon.pogen4selenium.api;

import org.openqa.selenium.WebDriver;

import io.toolisticon.pogen4selenium.runtime.PageObjectUtilities;
import io.toolisticon.pogen4selenium.runtime.tools.ActiveDriverHandler;
import io.toolisticon.pogen4selenium.runtime.tools.WebDriverProvider;

public class Pogen4Selenium {
	
	public static <T extends PageObjectParent<T>> T openUrl(Class<T> pageObjectClass, String url, String ... additionalArguments) {
		
		// create driver
		WebDriver webDriver = WebDriverProvider.getDriver(additionalArguments);
		webDriver.get(url);
	
		// focus driver
		ActiveDriverHandler.setCurrentDriver(webDriver);
		
		// create page object instance
		return PageObjectUtilities.getPageObjectInstance(pageObjectClass, webDriver);
		
	}
	
	
}
