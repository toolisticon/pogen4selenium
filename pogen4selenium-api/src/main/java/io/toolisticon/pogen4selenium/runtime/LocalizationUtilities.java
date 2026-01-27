package io.toolisticon.pogen4selenium.runtime;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.LoggerFactory;

/**
 * This tool is used to localize checks for text strings.
 * Internally it uses a thread local resource bundle to retrieve localized strings.
 */
public class LocalizationUtilities {

	private final static org.slf4j.Logger Logger = LoggerFactory.getLogger(LocalizationUtilities.class);
	
	private final static Pattern LOCALIZED_TEXT_PATTERN = Pattern.compile("^[$][\\{]([a-zA-Z0-9.]+?)\\}$");
	
	private static String resourceName = "po4s";
	private static final ThreadLocal<ResourceBundle> CURRENT_RB = new ThreadLocal<ResourceBundle>();
	
	static {
		try {
			CURRENT_RB.set(ResourceBundle.getBundle(resourceName));
		} catch (MissingResourceException e) {
			Logger.warn("Wasn't able to load resource bundle '{}'", resourceName);
		}
	}
	
	/**
	 * This method is used to configure a nonstandard resource bundle name
	 * @param resourceName the name of the resource bundle
	 */
	public static void init(String resourceName) {
		init(resourceName, null);
	}
	
	
	/**
	 * This method is used to configure a nonstandard resource bundle name
	 * @param resourceName the name of the resource bundle
	 * @param locale the locale to use, will default to default resource bundle if locale is null
	 */
	public static void init(String resourceName, Locale locale) {
		if (resourceName == null || resourceName.isBlank()) {
			throw new IllegalArgumentException("Passed resourceName must neither be empty or null");
		} 
		LocalizationUtilities.resourceName = resourceName;
		setLocale(locale);
	}
	
	/**
	 * Either return a static or localized text.
	 * @param textToLocalize returns localized text if textToLocalize matches "${key}", otherwise return textToLocalize
	 * @return either the localized string or textToLocalize
	 */
	public static String getLocalizedText(String textToLocalize) {
		
		// a String can be
		Matcher matcher = LOCALIZED_TEXT_PATTERN.matcher(textToLocalize);
		if (matcher.matches()) {
			
			// get the key
			String key = matcher.group(1);
			
			
			ResourceBundle rb = CURRENT_RB.get();
			if (rb != null) {
				return CURRENT_RB.get().getString(key);
			} else {
				throw new IllegalStateException("Tried to localize text but no resource bundle " + resourceName + " is present" );
			}
			
			
		} else {
			// got static text just return it
			return textToLocalize;
		}
		
	}
	
	
	
	public static void setLocale(Locale locale) {
		try {
			if (locale != null) {
				CURRENT_RB.set(ResourceBundle.getBundle(resourceName, locale));
			} else {
				CURRENT_RB.set(ResourceBundle.getBundle(resourceName));
			}
		} catch (MissingResourceException e) {
			Logger.warn("Wasn't able to load resource bundle '{}' with locale {}!", resourceName, locale);
			CURRENT_RB.remove();
		}
	}
	

}
