package io.toolisticon.pogen4selenium.runtime;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class PageObjectUtilities {
	
	/**
	 * Hidden constructor
	 */
	private PageObjectUtilities(){
		
	}

	
	/**
	 * Waits for page to have a matching url.
	 * @param driver the web driver
	 * @param urlRegex the regular expression that the url must match
	 */
	public static void waitForiPageToHaveMatchingUrl(WebDriver driver, String urlRegex) {
		Wait<WebDriver> wait =
    	        new FluentWait<>(driver)
    	            .withTimeout(Duration.ofSeconds(25))
    	            .pollingEvery(Duration.ofMillis(300));
    	
    	wait.until(ExpectedConditions.urlMatches(urlRegex));
	}
	

	
	/**
	 * Wait for an element to exist and to be interactable.	
	 * @param driver the web driver
	 * @param by the locator for the element
	 * @return the web element
	 */
	public static WebElement waitForElementToBeInteractable(WebDriver driver, By by) {
		Wait<WebDriver> wait =
    	        new FluentWait<>(driver)
    	            .withTimeout(Duration.ofSeconds(15))
    	            .pollingEvery(Duration.ofMillis(300))
    	            .ignoring(NoSuchElementException.class,ElementNotInteractableException.class);
    	
    	return wait.until(ExpectedConditions.elementToBeClickable(by));
	}
	
	
	/**
	 * Wait if passed element is interactable.
	 * @param driver the web driver
	 * @param element the element to wait for the condition
	 * @return the web element
	 */
	protected static WebElement waitForElementToBeInteractable(WebDriver driver, WebElement element) {
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
	

	/**
	 * Wait for element to be present.
	 * @param driver web driver
	 * @param by the locator to use
	 * @return the web element
	 */
	public static WebElement waitForElementToBePresent(WebDriver driver, By by) {
		Wait<WebDriver> wait =
    	        new FluentWait<>(driver)
    	            .withTimeout(Duration.ofSeconds(15))
    	            .pollingEvery(Duration.ofMillis(300))
    	            .ignoring(NoSuchElementException.class);
    	
    	return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    	
	}

	/**
	 * Wait for element to be absent
	 * @param driver the web driver
	 * @param element the web element to check
	 */
	public static void waitForElementToBeAbsent(WebDriver driver, WebElement element) {
		Wait<WebDriver> wait =
    	        new FluentWait<>(driver)
    	            .withTimeout(Duration.ofSeconds(15))
    	            .pollingEvery(Duration.ofMillis(300));
    	
    	wait.until(ExpectedConditions.stalenessOf(element));
	}
	
	
	/**
	 * Wait until page source contains.
	 * @param driver the web driver
	 * @param text the test to search
	 */
	public static void waitUntilPageSourceContains(WebDriver driver, String text) {
		
		waitForElementToBePresent(driver, By.xpath("//*[text()[contains(.,'" + text + "')]]"));	
		
	}
	


	
	public static void doPageRefresh(WebDriver driver) {
		driver.navigate().refresh();
	}
	
	
}
