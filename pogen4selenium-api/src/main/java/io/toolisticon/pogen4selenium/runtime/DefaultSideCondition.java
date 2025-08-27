package io.toolisticon.pogen4selenium.runtime;

import java.util.Arrays;
import java.util.Collection;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DefaultSideCondition implements LocatorCondition {

	@Override
	public boolean checkCondition(WebDriver driver, WebElement element) {
		return true;
	}

	@Override
	public Collection<Class<? extends Throwable>> exceptionsToIgnore() {
		return Arrays.asList(NoSuchElementException.class);
	}

}
