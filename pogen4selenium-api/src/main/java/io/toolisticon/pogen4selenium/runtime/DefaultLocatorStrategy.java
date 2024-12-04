package io.toolisticon.pogen4selenium.runtime;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DefaultLocatorStrategy implements ExpectedCondition<WebElement> {

	private final By locator;
	
	public DefaultLocatorStrategy(By locator) {
		this.locator = locator;
	}
	
	@Override
	public WebElement apply(WebDriver input) {
		return ExpectedConditions.elementToBeClickable(locator).apply(input);
	}

}
