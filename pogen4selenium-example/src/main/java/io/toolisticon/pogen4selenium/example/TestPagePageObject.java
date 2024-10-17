package io.toolisticon.pogen4selenium.example;

import java.util.List;

import org.openqa.selenium.WebDriver;

import io.toolisticon.pogen4selenium.api.ActionMoveToAndClick;
import io.toolisticon.pogen4selenium.api.By;
import io.toolisticon.pogen4selenium.api.ExtractData;
import io.toolisticon.pogen4selenium.api.PageObject;
import io.toolisticon.pogen4selenium.api.PageObjectElement;
import io.toolisticon.pogen4selenium.api.PageObjectParent;

@PageObject
public interface TestPagePageObject extends PageObjectParent<TestPagePageObject>{

	static final String DATA_EXTRACTION_FROM_TABLE_XPATH = "//table//tr[contains(@class,'data')]";
	
	@PageObjectElement(elementVariableName=TestPagePageObject.INPUT_FIELD_ID, by = By.ID,  value="" )
	static final String INPUT_FIELD_ID = "searchField";
	
	@PageObjectElement(elementVariableName=TestPagePageObject.COUNTER_INCREMENT_BUTTON_ID, by = By.XPATH,  value="//fieldset[@name='counter']/input[@type='button']" )
	static final String COUNTER_INCREMENT_BUTTON_ID = "counterIncrementButton";
	
	@ActionMoveToAndClick(COUNTER_INCREMENT_BUTTON_ID)
	TestPagePageObject clickCounterIncrementButton();
	
	
	@ExtractData(by = io.toolisticon.pogen4selenium.api.By.XPATH, value = DATA_EXTRACTION_FROM_TABLE_XPATH)
	List<TestPageTableEntry> getTableEntries();
	
	@ExtractData(by = io.toolisticon.pogen4selenium.api.By.XPATH, value = DATA_EXTRACTION_FROM_TABLE_XPATH)
	TestPageTableEntry getFirstTableEntry();
	
	default String getCounter() {
		return getDriver().findElement(org.openqa.selenium.By.xpath("//fieldset[@name='counter']/span[@id='counter']")).getText();
	}
	
	
	// Custom entry point for starting your tests
	public static TestPagePageObject init(WebDriver driver) {
		driver.get("http://localhost:9090/start");
		return new TestPagePageObjectImpl(driver);
	}

}
