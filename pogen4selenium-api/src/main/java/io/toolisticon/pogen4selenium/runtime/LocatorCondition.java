package io.toolisticon.pogen4selenium.runtime;

import java.util.Collection;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface LocatorCondition{
	
	boolean checkCondition(WebDriver driver, WebElement element);
	
	Collection<Class<? extends Throwable>> exceptionsToIgnore();
	
}
