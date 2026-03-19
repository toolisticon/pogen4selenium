package io.toolisticon.pogen4selenium.api;

import java.time.Duration;
import java.util.Locale;

import io.toolisticon.pogen4selenium.runtime.AssertionInterface;
import io.toolisticon.pogen4selenium.runtime.ExecuteBlock;
import io.toolisticon.pogen4selenium.runtime.tools.WebDriverProvider;

/**
 * The page object parent interface. Must be extended by all interfaces annotated with {@link PageObject}
 * @param <PAGEOBJECT> The page object type
 */
public interface PageObjectParent<PAGEOBJECT extends PageObjectParent<PAGEOBJECT>> extends CommonParentInterface {

	/**
	 * Verifies all elements marked via {@link PageObjectElement} annotated fields according to the configuration in the annotation.
	 * @return The next fluent interface
	 */
	PAGEOBJECT verify();

	/**
	 * Applies a fixed time pause.
	 * @param duration the duration to pause
	 * @return next fluent interface
	 */
	PAGEOBJECT pause(Duration duration);

	/**
	 * Wait as long as text is displayed
	 * @param text the text to search, might be a static string or a localized String referenced by key "${key}"
	 * @return next fluent interface
	 */
	PAGEOBJECT waitForPageToContainText(String text);
	
	/**
	 * Wait as long as text is displayed
	 * @param text the text to search, might be a static string or a localized String referenced by key "${key}"
	 * @param timeout the timout to use
	 * @return next fluent interface
	 */
	PAGEOBJECT waitForPageToContainText(String text, Duration timeout);
	
	/**
	 * Allows to do inline assertions done with your favourite test tools without the need to create local variables to keep state.
	 * @param function a function containing the custom assertions
	 * @return next fluent interface
	 */
	PAGEOBJECT doAssertions(AssertionInterface<PAGEOBJECT> function);
	
	/**
	 * Changes the locale for localized text checks like done in waitForPageToContainText.
	 * @return next fluent interface
	 */
	PAGEOBJECT changeLocale(Locale locale);

	/**
	 * Allows to do inline execution of custom code. 
	 * This is pretty helpful to for example do actions based on the data currently displayed on current page object.
	 * @param <OPO> The function allows to return a custom page object type instance
	 * @param function a function containing the custom code to be executed
	 * @return the next fluent interface
	 */
	<OPO extends PageObjectParent<OPO>> OPO execute(ExecuteBlock<PAGEOBJECT, OPO> function);
	
	
	/**
	 * Immediately quits browser and frees it's resources. 
	 * Nevertheless, there will always be a shutdown hook that tries to kill all open browser when the program stops regularly, but only if those browsers are opened via {@link Pogen4Selenium} or {@link WebDriverProvider}.
	 * Unfortunately, there might be a resource leak - open web driver and browser processes - if JVM is terminated by force.
	 */
	void closeBrowser();
	
	/**
	 * Put focus back on the browser/driver used by this page object. This must be done to be able 
	 * @return next fluent interface
	 */
	PAGEOBJECT focusBrowser();
	
	
}
