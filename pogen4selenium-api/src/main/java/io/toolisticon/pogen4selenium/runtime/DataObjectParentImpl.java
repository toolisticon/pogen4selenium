package io.toolisticon.pogen4selenium.runtime;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import io.toolisticon.pogen4selenium.api._By;
import io.toolisticon.pogen4selenium.api.ExtractDataValue;

public class DataObjectParentImpl implements CommonByLocators{
	
	protected final WebDriver driver;
	private final WebElement relativeParentWebElement;
	
	protected DataObjectParentImpl(WebDriver driver, WebElement relativeParentWebElement) {
		this.driver = driver;
		this.relativeParentWebElement = relativeParentWebElement;
	}

	public WebDriver getDriver() {
		return this.driver;
	}
	
	protected WebElement getRelativeParentWebElement() {
		return this.relativeParentWebElement;
	}
	
	protected WebElement getValueWebElement(_By by, String locatorString) {
		
		org.openqa.selenium.By relativeLocator = null;
		switch(by) {
			case ID : 
			{
				relativeLocator = org.openqa.selenium.By.id(locatorString);
				break;
			}
			case XPATH : 
			{
				relativeLocator = org.openqa.selenium.By.xpath(locatorString);
				break;
			}
		}
		
		if (relativeLocator != null) {
			return relativeParentWebElement.findElement(relativeLocator);
		}
		
		return null;
	}
	
	protected String getValue(_By by, String locatorString, ExtractDataValue.Kind kind, String name) {		
		WebElement  valueWebElement = this.getValueWebElement(by, locatorString);
		
		if (valueWebElement != null) {
			
			switch (kind) {
				case ATTRIBUTE: {
					return valueWebElement.getAttribute(name);
				}
				case CSS_VALUE: {
					return valueWebElement.getCssValue(name);
				}
				case TAG_NAME: {
					return valueWebElement.getTagName();
				}
				case ACCESSIBLE_NAME: {
					return valueWebElement.getAccessibleName();
				}		
				case TEXT:
				default:
					return valueWebElement.getText();
			}
	
		
		}
		
		return null;
	}
	
	
	@Override
	public WebElement waitForElementToBeInteractable(By by) {
		Wait<WebDriver> wait =
    	        new FluentWait<>(driver)
    	            .withTimeout(Duration.ofSeconds(15))
    	            .pollingEvery(Duration.ofMillis(300))
    	            .ignoring(ElementNotInteractableException.class);
    	
    	return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(relativeParentWebElement, by));
	}
	
	
	@Override
	public WebElement waitForElementToBePresent(By by) {
		Wait<WebDriver> wait =
    	        new FluentWait<>(driver)
    	            .withTimeout(Duration.ofSeconds(15))
    	            .pollingEvery(Duration.ofMillis(300))
    	            .ignoring(NoSuchElementException.class);
    	
    	return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(relativeParentWebElement, by));
    	
	}
	
	public void pause(Duration duration) {
		new Actions(driver).pause(duration).perform();
	}
	
}
