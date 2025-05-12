package io.toolisticon.pogen4selenium.runtime.actions;

import java.util.Arrays;
import java.util.Collection;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.toolisticon.pogen4selenium.runtime.LocatorCondition;

public class ActionWriteImpl extends BaseAction {

	
	private final String toSet;
	
	public ActionWriteImpl(WebDriver driver, SearchContext searchContext, LocatorCondition locatorCondition, String toSet) {
		super(driver, searchContext, locatorCondition);
		
		this.toSet = toSet;
	}

	@Override
	public boolean checkCondition(WebDriver driver, WebElement element) {
		return element.isDisplayed() && element.isEnabled();
	}

	@Override
	public Collection<Class<? extends Throwable>> exceptionsToIgnore() {
		return Arrays.asList(NoSuchElementException.class);
	}

	@Override
	protected void applyAction(WebElement webElement) {
		
		webElement.click();
		webElement.sendKeys(Keys.CONTROL + "a");
		webElement.sendKeys(Keys.DELETE);
		webElement.sendKeys(toSet);
		
	}

}