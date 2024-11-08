package io.toolisticon.pogen4selenium.example.googleseach;

import java.util.List;

import io.toolisticon.pogen4selenium.api.ExtractData;
import io.toolisticon.pogen4selenium.api.PageObject;
import io.toolisticon.pogen4selenium.api.PageObjectParent;
import io.toolisticon.pogen4selenium.api._By;

@PageObject
public interface GoogleSearchResultPage extends PageObjectParent<GoogleSearchResultPage>{

	@ExtractData(by = _By.XPATH, value= "//div[@id='rso']/div/div")
	public List<GoogleSearchResult> getSearchResults();
	
	
}
