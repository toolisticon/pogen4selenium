package io.toolisticon.pogen4selenium.example;

import java.time.Duration;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class GoogleSearchTest {
	
	
	private WebDriver webDriver;
	private JettyServer jettyServer;
	
	@Before
	public void init() throws Exception{
		
		jettyServer = new JettyServer();
		jettyServer.start();
		
		webDriver = new EdgeDriver();
		webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	
	@After
	public void cleanup() throws Exception{
		webDriver.quit();
		jettyServer.stop();
	}
	
	@Test
	//@Ignore
	public void testGoogleSearch() {
		GoogleHomepage.init(webDriver)
			.acceptL2Agl()
			.enterSearchString("aptk toolisticon \n")
			//.clickSearchButton()
			.doAssertions(e -> {
				
				// Do assertions here
				List<GoogleSearchResult> results = e.getResults();
				
				MatcherAssert.assertThat(results.getFirst().title(), Matchers.containsString("toolisticon/aptk"));
				MatcherAssert.assertThat(results.getFirst().href(), Matchers.is("https://github.com/toolisticon/aptk"));

			})
			;
	}
	
	@Test
	//@Ignore
	public void testGoogleSearch_firstMatch() {
		GoogleHomepage.init(webDriver)
			.acceptL2Agl()
			.enterSearchString("aptk toolisticon \n")
			//.clickSearchButton()
			.doAssertions(e -> {
				
				// Do assertions here
				GoogleSearchResult results = e.getFirstResult();
				
				MatcherAssert.assertThat(results.title(), Matchers.containsString("toolisticon/aptk"));
				MatcherAssert.assertThat(results.href(), Matchers.is("https://github.com/toolisticon/aptk"));

			})
			;
	}

}
