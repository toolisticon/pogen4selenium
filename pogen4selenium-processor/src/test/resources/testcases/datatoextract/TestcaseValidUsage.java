package io.toolisticon.pogen4selenium.processor.tests;

import io.toolisticon.cute.PassIn;
import io.toolisticon.pogen4selenium.api.DataObject;
import io.toolisticon.pogen4selenium.api.ExtractDataValue;

@DataObject
interface TestcaseValidUsage {
	
	@ExtractDataValue("//input[@name='firstName']")
	String firstName();
	
	@ExtractDataValue("//input[@name='lastName']")
	String lastName();
	
}