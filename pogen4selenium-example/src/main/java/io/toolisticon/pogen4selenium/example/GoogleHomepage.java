package io.toolisticon.pogen4selenium.example;

import java.util.List;

import org.openqa.selenium.WebDriver;

import io.toolisticon.pogen4selenium.api.ActionClick;
import io.toolisticon.pogen4selenium.api.ActionMoveToAndClick;
import io.toolisticon.pogen4selenium.api.ActionWrite;
import io.toolisticon.pogen4selenium.api.ExtractData;
import io.toolisticon.pogen4selenium.api.PageObject;
import io.toolisticon.pogen4selenium.api.PageObjectElement;
import io.toolisticon.pogen4selenium.api.PageObjectParent;

@PageObject
public interface GoogleHomepage extends PageObjectParent<GoogleHomepage>{

	static final String RESULTS_XPATH = "//div[@id='search']//div[@class='MjjYud']/div";
	
	@PageObjectElement(elementVariableName=GoogleHomepage.SEARCH_FIELD_ID,  value="//textarea[@name='q']" )
	static final String SEARCH_FIELD_ID = "searchField";
	
	@PageObjectElement(elementVariableName=GoogleHomepage.SEARCH_BUTTON_ID,  value="//input[@name='btnK']" )
	static final String SEARCH_BUTTON_ID = "searchButton";
	
	@PageObjectElement(elementVariableName=GoogleHomepage.ACCEPT_L2AGL_BUTTON_ID,  value="//button[@id='L2AGLb']" )
    static final String ACCEPT_L2AGL_BUTTON_ID = "acceptL2aglButton";
	
	
	
	@ActionMoveToAndClick(ACCEPT_L2AGL_BUTTON_ID)
	GoogleHomepage acceptL2Agl();
	
	GoogleHomepage enterSearchString(@ActionWrite(SEARCH_FIELD_ID) String searchString);
	
	@ActionClick(SEARCH_BUTTON_ID)
	GoogleHomepage clickSearchButton();
	
	@ExtractData(by = io.toolisticon.pogen4selenium.api.By.XPATH, value = RESULTS_XPATH)
	List<GoogleSearchResult> getResults();
	
	@ExtractData(by = io.toolisticon.pogen4selenium.api.By.XPATH, value = RESULTS_XPATH)
	GoogleSearchResult getFirstResult();
	
	
	public static GoogleHomepage init(WebDriver driver) {
		driver.get("https://www.google.de");
		return new GoogleHomepageImpl(driver);
	}
	
	
}
