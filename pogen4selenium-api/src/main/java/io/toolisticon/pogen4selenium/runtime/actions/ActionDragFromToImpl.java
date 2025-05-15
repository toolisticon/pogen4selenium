package io.toolisticon.pogen4selenium.runtime.actions;

import java.util.Arrays;
import java.util.Collection;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import io.toolisticon.pogen4selenium.api._By;
import io.toolisticon.pogen4selenium.runtime.LocatorCondition;

public class ActionDragFromToImpl extends BaseAction {

	private final String dynamicPart;
	private final _By fromBy;
	private final String fromValue;
	
	public ActionDragFromToImpl(WebDriver driver, SearchContext searchContext, LocatorCondition locatorCondition, String dynamicPart, _By fromBy, String fromValue) {
		super(driver, searchContext, locatorCondition);
		
		this.dynamicPart = dynamicPart;
		this.fromBy = fromBy;
		this.fromValue = fromValue;

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
		
		new Actions(driver).dragAndDrop(locate(fromBy, fromValue.replace("${}", dynamicPart)), webElement).build().perform();
		
	}

}
