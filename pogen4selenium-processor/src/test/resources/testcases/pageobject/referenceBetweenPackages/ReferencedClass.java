package io.toolisticon.pogen4selenium.processor.tests.package2;

import io.toolisticon.cute.PassIn;
import io.toolisticon.pogen4selenium.api.ActionClick;
import io.toolisticon.pogen4selenium.api.ActionWrite;
import io.toolisticon.pogen4selenium.api._By;
import io.toolisticon.pogen4selenium.api.ExtractDataValue;
import io.toolisticon.pogen4selenium.api.PageObject;
import io.toolisticon.pogen4selenium.api.PageObjectElement;
import io.toolisticon.pogen4selenium.api.PageObjectParent;

@PageObject	
public interface ReferencedClass  extends PageObjectParent<ReferencedClass>{ 
	
	
		@PageObjectElement(elementVariableName=ReferencedClass.SUBMIT_BUTTON_ID, value="//button[@type='submit']" )
		static final String SUBMIT_BUTTON_ID = "submitButton";
		
		
		@ActionClick(SUBMIT_BUTTON_ID)
		ReferencedClass clickSubmitButton();
		
		

}