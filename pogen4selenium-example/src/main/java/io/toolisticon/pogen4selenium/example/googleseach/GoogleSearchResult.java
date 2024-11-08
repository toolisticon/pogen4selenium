package io.toolisticon.pogen4selenium.example.googleseach;

import io.toolisticon.pogen4selenium.api.ActionClick;
import io.toolisticon.pogen4selenium.api.DataObject;
import io.toolisticon.pogen4selenium.api.ExtractDataValue;
import io.toolisticon.pogen4selenium.api.ExtractDataValue.Kind;
import io.toolisticon.pogen4selenium.api._By;

@DataObject
public interface GoogleSearchResult {

	@ExtractDataValue(by=_By.XPATH, value = ".//a[@jsname='UWckNb']//h3", kind = Kind.TEXT)
	String getTitle();
	
	@ExtractDataValue(by=_By.XPATH, value= ".//div[contains(@class,'VwiC3b')]", kind = Kind.TEXT)
	String getDescription();
	
	@ExtractDataValue(by = _By.XPATH, value = ".//a[@jsname='UWckNb']", kind = Kind.ATTRIBUTE, name="href")
	String getLink();
	
	@ActionClick(by = _By.XPATH, value = ".//a[@jsname='UWckNb']")
	ExternalPage clickLink();
	
}
