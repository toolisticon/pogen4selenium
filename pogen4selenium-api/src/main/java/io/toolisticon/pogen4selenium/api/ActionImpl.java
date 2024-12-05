package io.toolisticon.pogen4selenium.api;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * The base interface for all actions.
 */
public interface ActionImpl {

	/**
	 * Execute action on passed webElement
	 * @param webElement
	 */
	public void executeAction(WebElement webElement);
	
	/**
	 * Execute action on element located by locator
	 * @param locator
	 */
	public void executeAction(By locator);
	
	
}
