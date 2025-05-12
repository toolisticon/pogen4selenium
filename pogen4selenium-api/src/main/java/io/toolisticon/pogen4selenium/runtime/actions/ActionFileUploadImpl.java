package io.toolisticon.pogen4selenium.runtime.actions;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.toolisticon.pogen4selenium.runtime.LocatorCondition;

public class ActionFileUploadImpl extends BaseAction {

	private final File file;
	
	public ActionFileUploadImpl(WebDriver driver, SearchContext searchContext, LocatorCondition locatorCondition, File file) {
		super(driver, searchContext, locatorCondition);
		this.file = file;
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
		webElement.sendKeys(file.getAbsolutePath());
	}
	
}
