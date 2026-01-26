package io.toolisticon.pogen4selenium.runtime.tools;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * This class can be used to in multi-driver test session to pull the active driver window to front and maximize it.
 * It eases to look at the what is happening during execution a lot.
 */
public class ActiveDriverHandler {

	private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();
	
	private static final boolean suppressAutoFocusWindows = Boolean.parseBoolean(System.getProperty(WebDriverProvider.PROPERTY_NAME_SUPPRESS_AUTO_FOCUS, "false"));
	
	public static WebDriver getCurrentDriver() {
		return ActiveDriverHandler.threadLocalDriver.get();
	}
	
	public static void setCurrentDriver(WebDriver driver) {
		
		WebDriver previousDriver = getCurrentDriver();
		if (!suppressAutoFocusWindows && previousDriver != null && !hasQuit(previousDriver)) {
			minimize(previousDriver);
		}
		
		threadLocalDriver.set(driver);
		
		if (!suppressAutoFocusWindows) {
			driver.manage().window().setPosition(new Point(0, 0));
			driver.manage().window().maximize();
		}
	}
	
	public static void clear() {
		threadLocalDriver.remove();
	}

	
	private static void minimize(WebDriver driver) { 
		Window window = driver.manage().window(); 
		window.minimize(); 
		
	}
	
	public static boolean hasQuit(WebDriver driver) {
	    return ((RemoteWebDriver)driver).getSessionId() == null;
	}
	
	
}
