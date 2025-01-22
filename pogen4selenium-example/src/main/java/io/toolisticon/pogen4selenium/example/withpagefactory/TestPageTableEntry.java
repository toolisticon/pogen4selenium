package io.toolisticon.pogen4selenium.example.withpagefactory;

import io.toolisticon.pogen4selenium.api._By;
import io.toolisticon.pogen4selenium.api.ActionMoveToAndClick;
import io.toolisticon.pogen4selenium.api.ActionWrite;
import io.toolisticon.pogen4selenium.api.DataObject;
import io.toolisticon.pogen4selenium.api.ExtractDataValue;
import io.toolisticon.pogen4selenium.api.ExtractDataValue.Kind;

@DataObject
public interface TestPageTableEntry {

	@ExtractDataValue(by = _By.XPATH, value = "./td[1]")
	String name();
	
	@ExtractDataValue(by = _By.XPATH, value = "./td[2]")
	String age();
	
	@ExtractDataValue(by = _By.XPATH, value = "./td[3]/a",  kind = Kind.ATTRIBUTE, name = "href")
	String link();
	
	@ActionMoveToAndClick(by = _By.XPATH, value = "./td[3]/a")
	ExternalPage clickLink();
	
	@ExtractDataValue(by = _By.XPATH, value = "./td[3]/a", kind = Kind.TEXT)
	String linkText();
	
	@ExtractDataValue(by = _By.XPATH, value = "./td[4]/input", kind = Kind.ATTRIBUTE, name = "value")
	String inputField();
	
	TestPageTableEntry writeToInputField(@ActionWrite(by = _By.XPATH, value = "./td[4]/input") String text);
	
	
}
