package io.toolisticon.pogen4selenium.example.googleseach;

import io.toolisticon.pogen4selenium.api.ActionMoveToAndClick;
import io.toolisticon.pogen4selenium.api.ActionWrite;
import io.toolisticon.pogen4selenium.api.PageObject;
import io.toolisticon.pogen4selenium.api.PageObjectParent;
import io.toolisticon.pogen4selenium.api._By;

@PageObject
public interface GoogleSearchPage extends PageObjectParent<GoogleSearchPage>{

	public GoogleSearchPage writeSearchTerm(@ActionWrite(by=_By.ID, value="APjFqb") String searchTerm);
	
	@ActionMoveToAndClick(by=_By.XPATH, value="(//input[@name='btnK'])[2]")
	public GoogleSearchResultPage clickSearchButton();
	
}
