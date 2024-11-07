package io.toolisticon.pogen4selenium.runtime;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import io.toolisticon.pogen4selenium.api.PageObjectParent;

@SuppressWarnings("unchecked")
public abstract class PageObjectParentImpl <PAGEOBJECT extends PageObjectParent<PAGEOBJECT>> implements PageObjectParent<PAGEOBJECT>{

	protected WebDriver driver;
	
	public PageObjectParentImpl(WebDriver driver){
		this.driver = driver;
		
		PageFactory.initElements(driver, this);
	}
	
	
	
	@Override
	public WebDriver getDriver() {
		return this.driver;
	}

	
	@Override
	public PAGEOBJECT pause(Duration duration) {
		new Actions(driver).pause(duration).perform();
		return (PAGEOBJECT) this;
	}
	
	@Override
	public PAGEOBJECT doAssertions(AssertionInterface<PAGEOBJECT> function) {
		
		if (function != null) {
			function.doAssertion((PAGEOBJECT)this);
		}
		
		return (PAGEOBJECT) this;
	}
	
	@Override
	public <OPO extends PageObjectParent<OPO>> OPO execute(ExecuteBlock<PAGEOBJECT,OPO> function) {
		
		if (function == null) {
			throw new IllegalArgumentException("PASSED EXECUTION BLOCK MUST NOT BE NULL!!!");
		}
		
		return (OPO) function.execute((PAGEOBJECT)this);
	}
		
	@Override
	public <APO extends PageObjectParent<APO>> APO changePageObjectType(Class<APO> targetPageObjectType) {
		try {
			return targetPageObjectType.getConstructor(WebDriver.class).newInstance(this.getDriver());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("Unable to instantate the passed page object class : " + targetPageObjectType.getCanonicalName(), e);
		}
		
	}



	protected void waitUntilUrl(String urlRegex) {
		Wait<WebDriver> wait =
    	        new FluentWait<>(driver)
    	            .withTimeout(Duration.ofSeconds(25))
    	            .pollingEvery(Duration.ofMillis(300))
    	            .ignoring(ElementNotInteractableException.class);
    	
    	wait.until(ExpectedConditions.urlMatches(urlRegex));
	}
	
	
	protected void waitForElementToBeInteractable(String xpath) {
		waitForElementToBeInteractable(By.xpath(xpath));
	}
	
	protected WebElement waitForElementToBeInteractable(By by) {
		Wait<WebDriver> wait =
    	        new FluentWait<>(driver)
    	            .withTimeout(Duration.ofSeconds(15))
    	            .pollingEvery(Duration.ofMillis(300))
    	            .ignoring(ElementNotInteractableException.class);
    	
    	return wait.until(ExpectedConditions.elementToBeClickable(by));
	}
	
	protected WebElement waitForElementToBeInteractable(WebElement element) {
		if(element == null) {
			return null;
		}
		
		Wait<WebDriver> wait =
    	        new FluentWait<>(driver)
    	            .withTimeout(Duration.ofSeconds(15))
    	            .pollingEvery(Duration.ofMillis(300))
    	            .ignoring(ElementNotInteractableException.class);
    	
    	return wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	protected void waitForElementToBePresent(String xpath) {
		waitForElementToBePresent(By.xpath(xpath));
	}
	
	protected WebElement waitForElementToBePresent(By by) {
		Wait<WebDriver> wait =
    	        new FluentWait<>(driver)
    	            .withTimeout(Duration.ofSeconds(15))
    	            .pollingEvery(Duration.ofMillis(300))
    	            .ignoring(NoSuchElementException.class);
    	
    	return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    	
	}
	
	protected void waitForElementToBeAbsent(String xpath) {
		Wait<WebDriver> wait =
    	        new FluentWait<>(driver)
    	            .withTimeout(Duration.ofSeconds(15))
    	            .pollingEvery(Duration.ofMillis(300))
    	            .ignoring(NoSuchElementException.class);
    	
    	wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
	}
	
	protected void waitForElementToBeAbsent(WebElement element) {
		Wait<WebDriver> wait =
    	        new FluentWait<>(driver)
    	            .withTimeout(Duration.ofSeconds(15))
    	            .pollingEvery(Duration.ofMillis(300));
    	
    	wait.until(ExpectedConditions.stalenessOf(element));
	}
	
	protected void waitForMessage(String message) {
		
		waitForElementToBePresent("//*[text()[contains(.,'" + message+ "')]]");	
		
	}
	
	protected void waitForMessageToVanish(String ... messages) {
			
		String criterias = Arrays.stream(messages).map(e -> "contains(.,'" + e + "')").collect(Collectors.joining(" or "));
		
		String xpath = "//*[text()[" + criterias + "]]";
		waitForElementToBePresent(xpath);	
		WebElement element = driver.findElement(By.xpath(xpath));
		waitForElementToBeAbsent(element);
				
	}
	
	
	protected void writeToElement(WebElement webElement, String toSet) {
		webElement.click();
		webElement.sendKeys(Keys.CONTROL + "a");
		webElement.sendKeys(Keys.DELETE);
		webElement.sendKeys(toSet);
	}

	
	protected void doPageRefresh() {
		driver.navigate().refresh();
	}
	
	
}
