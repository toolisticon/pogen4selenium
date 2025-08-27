package io.toolisticon.pogen4selenium.example.withoutpagefactory;

import java.util.List;

import org.openqa.selenium.WebDriver;

import io.toolisticon.pogen4selenium.api.ActionDragFromTo;
import io.toolisticon.pogen4selenium.api.ActionMoveToAndClick;
import io.toolisticon.pogen4selenium.api.ActionWrite;
import io.toolisticon.pogen4selenium.api.ExtractData;
import io.toolisticon.pogen4selenium.api.ExtractDataValue;
import io.toolisticon.pogen4selenium.api.ExtractDataValue.Kind;
import io.toolisticon.pogen4selenium.api.PageObject;
import io.toolisticon.pogen4selenium.api.PageObjectParent;
import io.toolisticon.pogen4selenium.api.Pause;
import io.toolisticon.pogen4selenium.api._By;

@PageObject
public interface TestPagePageObject extends PageObjectParent<TestPagePageObject>{

	static final String DATA_EXTRACTION_FROM_TABLE_XPATH = "//table//tr[contains(@class,'data')]";
	
	
	TestPagePageObject writeToInputField(@ActionWrite(by=_By.ID, value="input_field") String value);
	
	@ExtractDataValue(by=_By.ID, value = "input_field", kind=Kind.ATTRIBUTE, name="value")
	String readInputFieldValue();
	
	@ActionMoveToAndClick(by=_By.XPATH, value = "//fieldset[@name='counter']/input[@type='button']")
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
		
	TestPagePageObject dragDivToTargetArea(@ActionDragFromTo(fromBy = _By.ID,  toBy = _By.ID, toValue = "div1") String value);	
	
	@ActionMoveToAndClick(by = _By.ID, value = "spinnerbutton")
	TestPagePageObject clickDisplaySpinnerButton();
	
	
	// Custom entry point for starting your tests
	public static TestPagePageObject init(WebDriver driver) {
		driver.get("http://localhost:9090/start");
		return new TestPagePageObjectImpl(driver);
	}

}
