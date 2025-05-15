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
import io.toolisticon.pogen4selenium.api._By;
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
    	applyAction(locateWithCondition(webElement));
	}
	
	private Collection<Class<? extends Throwable>> getExceptionsToIgnore() {
		return Stream.concat(  sideCondition.exceptionsToIgnore().stream(), this.exceptionsToIgnore().stream()).collect(Collectors.toSet());
	}
	
	public void executeAction(By locator) {
		
    	applyAction(locateWithCondition(locator));
	
	}
	
	protected String getLocatorMessage() {
		return "Wasn't able to locate with locator of type '" + this.getClass().getCanonicalName() + "' and side condition '" + this.sideCondition.getClass().getCanonicalName() + "'";
	}
	
	WebElement locate(_By locator, String locatorString) {

		By seleniumLocator = null;
		
		switch (locator) {
			case ID : {
				seleniumLocator = By.id(locatorString);
				break;
			}
			case XPATH : {
				seleniumLocator = By.xpath(locatorString);
				break;
			}
			case CLASS_NAME : {
				seleniumLocator = By.className(locatorString);
				break;
			}
			case CSS_SELECTOR : {
				seleniumLocator = By.cssSelector(locatorString);
				break;
			}
			case LINK_TEXT : {
				seleniumLocator = By.linkText(locatorString);
				break;
			}
			case NAME : {
				seleniumLocator = By.name(locatorString);
				break;
			}
			case PARTIAL_LINK_TEXT : {
				seleniumLocator = By.partialLinkText(locatorString);
				break;
			}
			case TAG_NAME : {
				seleniumLocator = By.tagName(locatorString);
				break;
			}
			
			
			default: {
				// this shouldn't be used!
				throw new IllegalStateException("Annotation uses locator strategy '" + locator.name() + "' , which cannot be used in actions");
			}
			
		}
		
		if (seleniumLocator == null) {
			throw new IllegalStateException("Annotation uses locator strategy ELEMENT, which cannot be used in actions");
		}
		
		return locateWithCondition(seleniumLocator);
		
		
	}
	
	WebElement locateWithCondition(WebElement element) {
		Wait<WebDriver> wait =
    	        new FluentWait<>(driver)
    	            .withTimeout(Duration.ofSeconds(15))
    	            .pollingEvery(Duration.ofMillis(300))
    	            .withMessage("Element based action '" + this.getClass().getCanonicalName() + "' with side condition '" + this.sideCondition.getClass().getCanonicalName() + "'")
    	            .ignoreAll(getExceptionsToIgnore());
    	
    	return wait.until(new OnElementCondition(element));
	}
	
	WebElement locateWithCondition(By locator) {
		Wait<WebDriver> wait =
    	        new FluentWait<>(driver)
    	            .withTimeout(Duration.ofSeconds(15))
    	            .pollingEvery(Duration.ofMillis(300))
    	            .withMessage("Locator '" + locator.toString() + "'  based action '" + this.getClass().getCanonicalName() + "' with side condition '" + this.sideCondition.getClass().getCanonicalName() + "'")
    	            .ignoreAll(getExceptionsToIgnore());
    	return wait.until(new WithLocatorCondition(this.searchContext, locator));
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
