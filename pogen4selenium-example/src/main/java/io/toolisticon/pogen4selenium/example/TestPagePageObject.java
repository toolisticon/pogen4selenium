package io.toolisticon.pogen4selenium.example;

import java.util.List;

import org.openqa.selenium.WebDriver;

import io.toolisticon.pogen4selenium.api.ActionMoveToAndClick;
import io.toolisticon.pogen4selenium.api.ActionWrite;
import io.toolisticon.pogen4selenium.api._By;
import io.toolisticon.pogen4selenium.api.ExtractData;
import io.toolisticon.pogen4selenium.api.ExtractDataValue;
import io.toolisticon.pogen4selenium.api.ExtractDataValue.Kind;
import io.toolisticon.pogen4selenium.api.PageObject;
import io.toolisticon.pogen4selenium.api.PageObjectElement;
import io.toolisticon.pogen4selenium.api.PageObjectParent;
import io.toolisticon.pogen4selenium.api.Pause;

@PageObject
public interface TestPagePageObject extends PageObjectParent<TestPagePageObject>{

	static final String DATA_EXTRACTION_FROM_TABLE_XPATH = "//table//tr[contains(@class,'data')]";
	
	@PageObjectElement(elementVariableName=TestPagePageObject.INPUT_FIELD_ID, by = _By.ID,  value="input_field" )
	static final String INPUT_FIELD_ID = "inputField";
	
	@PageObjectElement(elementVariableName=TestPagePageObject.COUNTER_INCREMENT_BUTTON_ID, by = _By.XPATH,  value="//fieldset[@name='counter']/input[@type='button']" )
	static final String COUNTER_INCREMENT_BUTTON_ID = "counterIncrementButton";
	
	
	TestPagePageObject writeToInputField(@ActionWrite(INPUT_FIELD_ID) String value);
	
	@ExtractDataValue(by=_By.ELEMENT, value = INPUT_FIELD_ID, kind=Kind.ATTRIBUTE, name="value")
	String readInputFieldValue();
	
	@ActionMoveToAndClick(COUNTER_INCREMENT_BUTTON_ID)
	@Pause(value = 500L)
	TestPagePageObject clickCounterIncrementButton();	
	
	@ExtractData(by = io.toolisticon.pogen4selenium.api._By.XPATH, value = DATA_EXTRACTION_FROM_TABLE_XPATH)
	List<TestPageTableEntry> getTableEntries();
	
	@ExtractData(by = io.toolisticon.pogen4selenium.api._By.XPATH, value = DATA_EXTRACTION_FROM_TABLE_XPATH)
	TestPageTableEntry getFirstTableEntry();
	
	@ExtractDataValue(by = _By.XPATH, value="//fieldset[@name='counter']/span[@id='counter']")
	String getCounter();
	
	// you can always provide your own methods and logic
	default String providedGetCounter() {
		return getDriver().findElement(org.openqa.selenium.By.xpath("//fieldset[@name='counter']/span[@id='counter']")).getText();
	}
	
	// Custom entry point for starting your tests
	public static TestPagePageObject init(WebDriver driver) {
		driver.get("http://localhost:9090/start");
		return new TestPagePageObjectImpl(driver);
	}

}
