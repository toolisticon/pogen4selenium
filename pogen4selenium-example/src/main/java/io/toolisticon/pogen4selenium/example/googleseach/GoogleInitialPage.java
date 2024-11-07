package io.toolisticon.pogen4selenium.example.googleseach;

import org.openqa.selenium.WebDriver;

import io.toolisticon.pogen4selenium.api.ActionMoveToAndClick;
import io.toolisticon.pogen4selenium.api.PageObject;
import io.toolisticon.pogen4selenium.api.PageObjectParent;
import io.toolisticon.pogen4selenium.api._By;

@PageObject
public interface GoogleInitialPage extends PageObjectParent<GoogleInitialPage>{


	@ActionMoveToAndClick(by=_By.ID, value="L2AGLb")
	public GoogleSearchPage acceptPrivacyTerms();
	
	
	// Custom entry point for starting your tests
	public static GoogleInitialPage init(WebDriver driver) {
		driver.get("http://www.google.de/");
		return new GoogleInitialPageImpl(driver);
	}
	
}
