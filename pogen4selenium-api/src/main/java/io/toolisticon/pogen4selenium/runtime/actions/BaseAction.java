package io.toolisticon.pogen4selenium.runtime.actions;

import java.time.Duration;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import io.toolisticon.pogen4selenium.api.ActionImpl;
import io.toolisticon.pogen4selenium.runtime.LocatorCondition;

public abstract class BaseAction  implements LocatorCondition, ActionImpl{
	
	
	protected final WebDriver driver;
	protected final SearchContext searchContext;
	private final LocatorCondition sideCondition;
	
	protected BaseAction(WebDriver driver, SearchContext searchContext, LocatorCondition sideCondition) {
		this.driver = driver;
		this.searchContext = searchContext;
		this.sideCondition = sideCondition;
	}
	
	
	protected abstract void applyAction(WebElement webElement);
	
	public void executeAction(WebElement webElement) {
		Wait<WebDriver> wait =
    	        new FluentWait<>(driver)
    	            .withTimeout(Duration.ofSeconds(15))
    	            .pollingEvery(Duration.ofMillis(300))
    	            .withMessage("Element based action '" + this.getClass().getCanonicalName() + "' with side condition '" + this.sideCondition.getClass().getCanonicalName() + "'")
    	            .ignoreAll(getExceptionsToIgnore());
    	
    	applyAction(wait.until(new OnElementCondition(webElement)));
	}
	
	private Collection<Class<? extends Throwable>> getExceptionsToIgnore() {
		return Stream.concat(  sideCondition.exceptionsToIgnore().stream(), this.exceptionsToIgnore().stream()).collect(Collectors.toSet());
	}
	
	public void executeAction(By locator) {
		
		
		Wait<WebDriver> wait =
    	        new FluentWait<>(driver)
    	            .withTimeout(Duration.ofSeconds(15))
    	            .pollingEvery(Duration.ofMillis(300))
    	            .withMessage("Locator '" + locator.toString() + "'  based action '" + this.getClass().getCanonicalName() + "' with side condition '" + this.sideCondition.getClass().getCanonicalName() + "'")
    	            .ignoreAll(getExceptionsToIgnore());
    	
    	applyAction(wait.until(new WithLocatorCondition(this.searchContext, locator)));
	
	}
	
	protected String getLocatorMessage() {
		return "Wasn't able to locate with locator of type '" + this.getClass().getCanonicalName() + "' and side condition '" + this.sideCondition.getClass().getCanonicalName() + "'";
	}
	
	
	class OnElementCondition implements ExpectedCondition<WebElement> {

		private final WebElement webElement;
		
		private OnElementCondition(
				WebElement webElement) {
			
				this.webElement = webElement;
			
		}
		
		@Override
		public WebElement apply(WebDriver input) {
			try {
				return checkCondition(input, webElement) && sideCondition.checkCondition(input,webElement) ? webElement : null;
			} catch (StaleElementReferenceException e) {
				return null;
			}
		}
		
	}
	
	class WithLocatorCondition implements ExpectedCondition<WebElement> {
		
		private final SearchContext searchContext;
		private final By locator;
		
		private WithLocatorCondition(SearchContext searchContext, By locator) {
			
			this.searchContext = searchContext;
			this.locator = locator;
			
		}
		
		@Override
		public WebElement apply(WebDriver input) {
			
			try {
				
				WebElement element = searchContext.findElement(locator);
				return new OnElementCondition(element).apply(input);
				
			} catch (NoSuchElementException | StaleElementReferenceException e) {
				// can be ignored
			}
			
			return null;
		}
		
	}
	
}
