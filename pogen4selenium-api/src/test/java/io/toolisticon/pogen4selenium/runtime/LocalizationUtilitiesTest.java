package io.toolisticon.pogen4selenium.runtime;

import java.util.Locale;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test class for {@link LocalizationUtilities.}
 */
public class LocalizationUtilitiesTest {
	
	
	@Test
	public void accessNonexistingResourceBundleTest() {
		LocalizationUtilities.init("nonexisting");
		MatcherAssert.assertThat(LocalizationUtilities.getLocalizedText("abc"),org.hamcrest.Matchers.equalTo("abc"));
	}

	
	@Test(expected = IllegalStateException.class)
	public void accessNonexistingResourceBundleToGetLocalizedTextTest() {
		LocalizationUtilities.init("nonexisting");
		LocalizationUtilities.getLocalizedText("${abc}");
	}
	
	@Test
	public void accessExistingResourceBundleTest() {
		Locale.setDefault(Locale.ENGLISH);
		
		LocalizationUtilities.init("po4s");
		MatcherAssert.assertThat(LocalizationUtilities.getLocalizedText("abc"),org.hamcrest.Matchers.equalTo("abc"));
		MatcherAssert.assertThat(LocalizationUtilities.getLocalizedText("${abc}"),org.hamcrest.Matchers.equalTo("YES!"));
	}

	
	@Test
	public void accessExistingResourceBundleToGetLocalizedTextTest() {
		
		LocalizationUtilities.init("po4s");
		LocalizationUtilities.setLocale(Locale.GERMAN);
		
		MatcherAssert.assertThat(LocalizationUtilities.getLocalizedText("${abc}"),Matchers.equalTo("JA!"));
		
		// Back to default
		LocalizationUtilities.setLocale(null);
		MatcherAssert.assertThat(LocalizationUtilities.getLocalizedText("${abc}"),Matchers.equalTo("YES!"));
	}
}
