package io.toolisticon.pogen4selenium.runtime;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import io.toolisticon.pogen4selenium.api.PageObjectParent;

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
	public static void waitForPageToHaveMatchingUrl(WebDriver driver, String urlRegex) {
		waitForPageToHaveMatchingUrl(driver, urlRegex, Duration.ofSeconds(25));
	}
	
	/**
	 * Waits for page to have a matching url.
	 * @param driver the web driver
	 * @param urlRegex the regular expression that the url must match
	 * @param timeout the timeout 
	 */
	public static void waitForPageToHaveMatchingUrl(WebDriver driver, String urlRegex, Duration timeout) {
		Wait<WebDriver> wait =
    	        new FluentWait<>(driver)
    	            .withTimeout(timeout)
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
 	
    	return waitForElementToBeInteractable(driver, by, Duration.ofSeconds(15));
	}
	
	/**
	 * Wait for an element to exist and to be interactable.	
	 * @param driver the web driver
	 * @param by the locator for the element
	 * @return the web element
	 */
	public static WebElement waitForElementToBeInteractable(WebDriver driver, By by, Duration timeout) {
		Wait<WebDriver> wait =
    	        new FluentWait<>(driver)
    	            .withTimeout(timeout)
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
    	return waitForElementToBeInteractable(driver, element, Duration.ofSeconds(15));
	}
	
	/**
	 * Wait if passed element is interactable.
	 * @param driver the web driver
	 * @param element the element to wait for the condition
	 * @param timeout the timeout to use
	 * @return the web element
	 */
	protected static WebElement waitForElementToBeInteractable(WebDriver driver, WebElement element, Duration timeout) {
		if(element == null) {
			return null;
		}
		
		Wait<WebDriver> wait =
    	        new FluentWait<>(driver)
    	            .withTimeout(timeout)
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
    	return waitForElementToBePresent(driver, by, Duration.ofSeconds(15));
	}
	
	/**
	 * Wait for element to be present.
	 * @param driver web driver
	 * @param by the locator to use
	 * @param timeout the timeout to use
	 * @return the web element
	 */
	public static WebElement waitForElementToBePresent(WebDriver driver, By by, Duration timeout) {
		Wait<WebDriver> wait =
    	        new FluentWait<>(driver)
    	            .withTimeout(timeout)
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
    	waitForElementToBeAbsent(driver, element, Duration.ofSeconds(15));
	}

	/**
	 * Wait for element to be absent
	 * @param driver the web driver
	 * @param element the web element to check
	 * @param timeout the timeout to use
	 */
	public static void waitForElementToBeAbsent(WebDriver driver, WebElement element, Duration timeout) {
		Wait<WebDriver> wait =
    	        new FluentWait<>(driver)
    	            .withTimeout(timeout)
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
	
	/**
	 * Wait until page source contains.
	 * @param driver the web driver
	 * @param text the test to search
	 * @param timeout the timeout to ise
	 */
	public static void waitUntilPageSourceContains(WebDriver driver, String text, Duration timeout) {
		
		waitForElementToBePresent(driver, By.xpath("//*[text()[contains(.,'" + text + "')]]"), timeout);	
		
	}

	
	public static void doPageRefresh(WebDriver driver) {
		driver.navigate().refresh();
	}
	
	
	/**
	 * Allows the creation of page object instances while using the fluent interface
	 * @param <T>
	 * @param pageObjectInterfaceType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends PageObjectParent<T>> T getPageObjectInstance(Class<T> pageObjectInterfaceType, WebDriver driver) {
		
		try {
			
			Class<?> clazz = Class.forName(pageObjectInterfaceType.getPackageName() + "." + pageObjectInterfaceType.getSimpleName() + "Impl");
			return (T) clazz.getConstructor(WebDriver.class).newInstance(driver);
			
		} catch (ClassCastException |ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new IllegalArgumentException("Couldn't create instance for PageObject '" + pageObjectInterfaceType.getCanonicalName() + "'", e);
		}
		
	}
	
	
}
