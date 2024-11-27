package io.toolisticon.pogen4selenium.example.googlesearch;

import java.time.Duration;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import io.toolisticon.pogen4selenium.example.googleseach.GoogleInitialPage;
import io.toolisticon.pogen4selenium.example.googleseach.GoogleSearchResult;

public class GoogleSearchTest {

	private WebDriver webDriver;
	
	@Before
	public void init() throws Exception{
		
		webDriver = new EdgeDriver();
		webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	
	@After
	public void cleanup() throws Exception{
		webDriver.quit();
	}
	
	
	@Test
	@Ignore
	public void testGoogleSearch() {
		
		GoogleInitialPage.init(webDriver)
		.acceptPrivacyTerms()
		.writeSearchTerm("github toolisticon aptk\t")
		.clickSearchButton()
		.doAssertions(e -> {
			List<GoogleSearchResult> searchResults = e.getSearchResults();
			
			MatcherAssert.assertThat(searchResults.get(0).getLink(), Matchers.is("https://github.com/toolisticon/aptk"));
			
		})
		.execute(e -> {
			return e.getSearchResults().get(0).clickLink();
		})
		.doAssertions(e -> MatcherAssert.assertThat(e.getDriver().getCurrentUrl(), Matchers.is("https://github.com/toolisticon/aptk")))
		
		;
		
	}
	
}
