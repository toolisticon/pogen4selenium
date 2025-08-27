package io.toolisticon.pogen4selenium.runtime.actions;

import java.util.Arrays;
import java.util.Collection;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import io.toolisticon.pogen4selenium.runtime.LocatorCondition;

public class ActionMoveToAndClickImpl extends BaseAction {

	
	public ActionMoveToAndClickImpl(WebDriver driver, SearchContext searchContext, LocatorCondition locatorCondition) {
		super(driver, searchContext, locatorCondition);
	}

	@Override
	public boolean checkCondition(WebDriver driver, WebElement element) {
		return 
				// ExpectedConditions.elementToBeClickable(element).apply( driver) != null && 
				element.isDisplayed() && element.isEnabled();
	}

	@Override
	public Collection<Class<? extends Throwable>> exceptionsToIgnore() {
		return Arrays.asList(NoSuchElementException.class);
	}

	@Override
	protected void applyAction(WebElement webElement) {
		new Actions(driver).moveToElement(webElement).pause(300).click().perform();
	}

}