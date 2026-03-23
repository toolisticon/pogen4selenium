package io.toolisticon.pogen4selenium.api;

import java.io.File;

import org.openqa.selenium.WebDriver;

import io.toolisticon.pogen4selenium.runtime.PageObjectUtilities;
import io.toolisticon.pogen4selenium.runtime.tools.ActiveDriverHandler;
import io.toolisticon.pogen4selenium.runtime.tools.WebDriverProvider;
import io.toolisticon.pogen4selenium.runtime.tools.WebDriverProviderExtension;

public class Pogen4Selenium {
	
	public static <T extends PageObjectParent<T>> T openUrl(Class<T> pageObjectClass, String url, String ... additionalArguments) {
		
		// create driver
		WebDriver webDriver = WebDriverProvider.getDriver(additionalArguments);
		return Pogen4Selenium.openUrl(webDriver, pageObjectClass, url);
		
	}
	
	
	public static <T extends PageObjectParent<T>> T openUrl(WebDriverProviderExtension webDriverExtension,Class<T> pageObjectClass, String url, String ... additionalArguments) {
		
		// create driver
		WebDriver webDriver = WebDriverProvider.getDriverWithExtension(webDriverExtension, additionalArguments);
		return Pogen4Selenium.openUrl(webDriver, pageObjectClass, url);
		
	}
	
	public static <T extends PageObjectParent<T>> T openUrl(Class<T> pageObjectClass, File tempDir, String url, String ... additionalArguments) {
		
		// create driver
		WebDriver webDriver = WebDriverProvider.getDriver(tempDir, additionalArguments);
		return Pogen4Selenium.openUrl(webDriver, pageObjectClass, url);
		
	}
	
	
	public static <T extends PageObjectParent<T>> T openUrl(WebDriverProviderExtension webDriverExtension, File tempDir,Class<T> pageObjectClass, String url, String ... additionalArguments) {
		
		// create driver
		WebDriver webDriver = WebDriverProvider.getDriverWithExtension(webDriverExtension, tempDir, additionalArguments);
		return Pogen4Selenium.openUrl(webDriver, pageObjectClass, url);
		
	}
	
	private static <T extends PageObjectParent<T>> T openUrl(WebDriver webDriver, Class<T> pageObjectClass, String url) {
		
		webDriver.get(url);
		
		// focus driver
		ActiveDriverHandler.setCurrentDriver(webDriver);
		
		// create page object instance
		return PageObjectUtilities.getPageObjectInstance(pageObjectClass, webDriver);
		
	}
	
	
}
