package io.toolisticon.pogen4selenium.example;

import io.toolisticon.pogen4selenium.api.By;
import io.toolisticon.pogen4selenium.api.DataObject;
import io.toolisticon.pogen4selenium.api.ExtractDataValue;
import io.toolisticon.pogen4selenium.api.ExtractDataValue.Kind;

@DataObject
public interface TestPageTableEntry {

	@ExtractDataValue(by = By.XPATH, value = "./td[1]")
	String name();
	
	@ExtractDataValue(by = By.XPATH, value = "./td[2]")
	String age();
	
	@ExtractDataValue(by = By.XPATH, value = "./td[3]/a",  kind = Kind.ATTRIBUTE, name = "href")
	String link();
	
	@ExtractDataValue(by = By.XPATH, value = "./td[3]/a", kind = Kind.TEXT)
	String linkText();
	
}
