package io.toolisticon.pogen4selenium.processor.tests;

import io.toolisticon.cute.PassIn;
import io.toolisticon.pogen4selenium.api.ActionClick;
import io.toolisticon.pogen4selenium.api.ActionWrite;
import io.toolisticon.pogen4selenium.api._By;
import io.toolisticon.pogen4selenium.api.ExtractDataValue;
import io.toolisticon.pogen4selenium.api.PageObject;
import io.toolisticon.pogen4selenium.api.PageObjectElement;
import io.toolisticon.pogen4selenium.api.PageObjectParent;
import io.toolisticon.pogen4selenium.api.Synchronized;


public class TestcaseValidUsage {
	
	@PageObject	
	public interface LoginPage extends PageObjectParent<LoginPage>{ 
		
		
		@ActionClick(by = _By.XPATH, value = "//button[@type='submit']")
		@Synchronized
		LoginPage clickSubmitButton();
		
	}
	
}