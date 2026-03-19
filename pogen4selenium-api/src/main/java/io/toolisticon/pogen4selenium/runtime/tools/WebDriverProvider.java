package io.toolisticon.pogen4selenium.runtime.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;


public class WebDriverProvider {

	
	private final static String PROPERTY_NAME_BROWSER = "selenium.browser";
	private final static String PROPERTY_NAME_BROWSER_LOCATION = "selenium.browserLocation";
	final static String PROPERTY_NAME_HEADLESS = "selenium.headless";
	final static String PROPERTY_NAME_SUPPRESS_AUTO_FOCUS = "selenium.autoFocusWIndows";
	
	final static List<WebDriver> createdWebDrivers = new ArrayList<>();
	
	static {
		
		// init a shutdown hook to ensure that all drivers will be quit automatically
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println("[ShutdownHook] JVM is shutting down...");
            
            killAllBrowsers();
            
            System.out.println("[ShutdownHook] Final cleanup complete.");
        }));
		
	}
	
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
		
		WebDriver webDriver = null;
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
				
				webDriver = new EdgeDriver(options);
				
				break;
				
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
				
				
				
				
				webDriver = new ChromeDriver(chromeOptions); 
				
				break;
			}
		
		}
		
		ActiveDriverHandler.setCurrentDriver(webDriver);
		
		if (webDriver != null) {
			createdWebDrivers.add(webDriver);
		}
		
		return webDriver;
		
	}
	
	
	public static void killAllBrowsers() {
		for (WebDriver webDriverToQuit : createdWebDrivers) {
        	try {
        		if(!ActiveDriverHandler.hasQuit(webDriverToQuit)) {
        			webDriverToQuit.quit();
        		}
        	} catch (RuntimeException e) {
        		// just ignore everything
        	}
        }
	}
	
	
}
