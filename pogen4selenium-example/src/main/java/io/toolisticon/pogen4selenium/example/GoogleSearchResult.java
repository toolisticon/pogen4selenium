package io.toolisticon.pogen4selenium.example;

import io.toolisticon.pogen4selenium.api.By;
import io.toolisticon.pogen4selenium.api.DataObject;
import io.toolisticon.pogen4selenium.api.ExtractDataValue;
import io.toolisticon.pogen4selenium.api.ExtractDataValue.Kind;

@DataObject
public interface GoogleSearchResult {

	@ExtractDataValue(by = By.XPATH, value = "./div//a//h3")
	String title();
	
	@ExtractDataValue(by = By.XPATH, value = "./div//a", kind=Kind.ATTRIBUTE, name="href")
	String href();
	
}
