package io.toolisticon.pogen4selenium.processor.tests;

import io.toolisticon.cute.PassIn;
import io.toolisticon.pogen4selenium.api.ActionWrite;
import io.toolisticon.pogen4selenium.api.DataObject;
import io.toolisticon.pogen4selenium.api.ExtractDataValue;
import io.toolisticon.pogen4selenium.api._By;

@DataObject
interface TestcaseValidUsage {
	
	@ExtractDataValue("//input[@name='firstName']")
	String firstName();
	
	@ExtractDataValue("//input[@name='lastName']")
	String lastName();
	
	// Linked Action - self referenced
	TestcaseValidUsage linkedAction(@ActionWrite(by = _By.XPATH, value = "//input[@formcontrolname='username' and @type='text']" ) String username);
	
	// Linked Action - transfer to PageObject
	SimplePageObject transferToPageObject(@ActionWrite(by = _By.XPATH, value = "//input[@formcontrolname='username' and @type='text']" ) String username);
	
}