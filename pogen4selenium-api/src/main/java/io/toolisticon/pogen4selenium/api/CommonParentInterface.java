package io.toolisticon.pogen4selenium.api;

import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.WebDriver;

public interface CommonParentInterface {
	
	/**
	 * Gets the seleium driver.
	 * @return the selenium driver currently used
	 */
	WebDriver getDriver();
	
	/**
	 * Allows the creation of page object instances while using the fluent interface
	 * @param <T>
	 * @param pageObjectInterfaceType
	 * @return
	 */
	default <T extends PageObjectParent<T>> T getPageObjectInstance(Class<T> pageObjectInterfaceType) {
		
		return getPageObjectInstance(pageObjectInterfaceType, getDriver());
		
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
