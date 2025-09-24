package io.toolisticon.pogen4selenium.api;

import java.time.Duration;

import io.toolisticon.pogen4selenium.runtime.AssertionInterface;
import io.toolisticon.pogen4selenium.runtime.ExecuteBlock;

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
	 * @param text the text to search
	 * @return next fluent interface
	 */
	PAGEOBJECT waitForPageToContainText(String text);
	
	
	/**
	 * Allows to do inline assertions done with your favourite test tools without the need to create local variables to keep state.
	 * @param function a function containing the custom assertions
	 * @return next fluent interface
	 */
	PAGEOBJECT doAssertions(AssertionInterface<PAGEOBJECT> function);

	/**
	 * Allows to do inline execution of custom code. 
	 * This is pretty helpful to for example do actions based on the data currently displayed on current page object.
	 * @param <OPO> The function allows to return a custom page object type instance
	 * @param function a function containing the custom code to be executed
	 * @return the next fluent interface
	 */
	<OPO extends PageObjectParent<OPO>> OPO execute(ExecuteBlock<PAGEOBJECT, OPO> function);
	
	
}
