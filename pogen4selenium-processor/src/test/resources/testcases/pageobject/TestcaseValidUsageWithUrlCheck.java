package io.toolisticon.pogen4selenium.processor.tests;

import io.toolisticon.pogen4selenium.api.ActionClick;
import io.toolisticon.pogen4selenium.api.ActionWrite;
import io.toolisticon.pogen4selenium.api.ExtractDataValue;
import io.toolisticon.pogen4selenium.api.PageObject;
import io.toolisticon.pogen4selenium.api.PageObjectElement;
import io.toolisticon.pogen4selenium.api.PageObjectParent;
import io.toolisticon.pogen4selenium.api._By;

public class TestcaseValidUsageWithUrlCheck {
	
	
	@PageObject(urlRegularExpressionToVerify=".*/home")
	public interface LoginPage extends PageObjectParent<LoginPage>{ 
	
		@PageObjectElement(elementVariableName=LoginPage.USERNAME_ID,  value="//input[@formcontrolname='username' and @type='text']" )
		static final String USERNAME_ID = "username";
		
		@PageObjectElement(elementVariableName=LoginPage.PASSWORD_ID, value="//input[@formcontrolname='password' and @type='text']" )
		static final String PASSWORD_ID = "password";
		
		@PageObjectElement(elementVariableName=LoginPage.SUBMIT_BUTTON_ID, value="//button[@type='submit']" )
		static final String SUBMIT_BUTTON_ID = "submitButton";
		
		LoginPage writeUserName(@ActionWrite(USERNAME_ID) String username);
		LoginPage writePassword(@ActionWrite(PASSWORD_ID) String password);
		
		@ExtractDataValue(by=_By.XPATH, value="/div", kind=ExtractDataValue.Kind.ATTRIBUTE, name="href")
		String getLinkHref();
		
		@ActionClick(SUBMIT_BUTTON_ID)
		LoginPage clickSubmitButton();
		
		
	}
}