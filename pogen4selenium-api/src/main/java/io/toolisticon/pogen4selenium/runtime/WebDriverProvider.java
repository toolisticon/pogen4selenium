package io.toolisticon.pogen4selenium.runtime;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;


public class WebDriverProvider {

	private final static String PROPERTY_NAME_BROWSER = "selenium.browser";
	private final static String PROPERTY_NAME_BROWSER_LOCATION = "selenium.browserLocation";
	private final static String PROPERTY_NAME_HEADLESS = "selenium.headless";
	
	public enum Browser {
		EDGE,
		CHROME;		
	}
	
		
	public static WebDriver getDriver(String ... additionalArguments) {
		return getDriver(null, additionalArguments);
	}
	
	public static WebDriver getDriver(File tempDir, String ... additionalArguments) {
		
		
		Browser browser = Browser.valueOf(System.getProperty(PROPERTY_NAME_BROWSER, Browser.CHROME.name()).toUpperCase());
		String browserLocation = System.getProperty(PROPERTY_NAME_BROWSER_LOCATION);
		String headless = System.getProperty(PROPERTY_NAME_HEADLESS, "false");
		
		switch(browser) {
		
			
			case EDGE: {
				
				
				EdgeOptions options = new EdgeOptions();
				
				// set temporary download directory
				if (tempDir != null) {
					Map<String, Object> prefs = new HashMap<String, Object>();
				    try {
				    	prefs.put("download.default_directory",  tempDir.getCanonicalPath());
					} catch (IOException e) {
						e.printStackTrace();
					}
				    options.setExperimentalOption("prefs", prefs);
				}
				
				
				options.addArguments("--window-size=1024,768");
				
				options.addArguments(additionalArguments);
				
				if(browserLocation != null) {
					options.setBinary(browserLocation);
				}
				if(Boolean.valueOf(headless)) {
					options.addArguments("headless");
				}
				
				// de language
				
				return new EdgeDriver(options);
				
			}
			
			case CHROME:
			default: {
				
				
				ChromeOptions chromeOptions = new ChromeOptions();
			
				chromeOptions.addArguments("--no-sandbox");
				chromeOptions.addArguments("--disable-dev-shm-usage");
				chromeOptions.addArguments("--accept-lang=de-DE");
				chromeOptions.addArguments("--window-size=1024,768");
				
				
				chromeOptions.addArguments(additionalArguments);
				
				// set temporary download directory
				if (tempDir != null) {
					Map<String, Object> prefs = new HashMap<String, Object>();
				    try {
				    	prefs.put("download.default_directory", tempDir.getCanonicalPath());
					} catch (IOException e) {
						e.printStackTrace();
					}
					chromeOptions.setExperimentalOption("prefs", prefs);
				}
				
				
				if(browserLocation != null) {
					chromeOptions.setBinary(browserLocation);
				}
				
				if(Boolean.valueOf(headless)) {
					chromeOptions.addArguments("--headless=new");
				}
				
				
				
				
				return new ChromeDriver(chromeOptions); 
				
			}
		
		}
		
	}
	
	
}
