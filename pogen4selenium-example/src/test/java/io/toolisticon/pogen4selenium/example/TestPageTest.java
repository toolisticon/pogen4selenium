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

public class TestPageTest {


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
	public void extractDatasetsTest() {
		TestPagePageObject.init(webDriver)
		.doAssertions(e -> {
				
				// Do assertions here
				List<TestPageTableEntry> results = e.getTableEntries();
				
				MatcherAssert.assertThat(results, Matchers.hasSize(2));
				
				MatcherAssert.assertThat(results.get(0).name(), Matchers.is("Max"));
				MatcherAssert.assertThat(results.get(0).age(), Matchers.is("9"));
				MatcherAssert.assertThat(results.get(0).link(), Matchers.is("https://de.wikipedia.org/wiki/Max_und_Moritz"));
				MatcherAssert.assertThat(results.get(0).linkText(), Matchers.is("Max und Moritz Wikipedia"));
				
				
				MatcherAssert.assertThat(results.get(1).name(), Matchers.is("Moritz"));
				MatcherAssert.assertThat(results.get(1).age(), Matchers.is("10"));
				MatcherAssert.assertThat(results.get(1).link(), Matchers.is("https://de.wikipedia.org/wiki/Wilhelm_Busch"));
				MatcherAssert.assertThat(results.get(1).linkText(), Matchers.is("Wilhelm Busch Wikipedia"));
				

			})
			;
	}
	
	@Test
	public void extractFirstDatasetTest() {
		TestPagePageObject.init(webDriver)
		.doAssertions(e -> {
				
				// Do assertions here
				TestPageTableEntry result = e.getFirstTableEntry();
				
				
				MatcherAssert.assertThat(result.name(), Matchers.is("Max"));
				MatcherAssert.assertThat(result.age(), Matchers.is("9"));
				MatcherAssert.assertThat(result.link(), Matchers.is("https://de.wikipedia.org/wiki/Max_und_Moritz"));
				MatcherAssert.assertThat(result.linkText(), Matchers.is("Max und Moritz Wikipedia"));
				
		

			})
			;
	}
	
	@Test
	public void incrementCounterTest() {
		TestPagePageObject.init(webDriver)
		.doAssertions(e -> {
				MatcherAssert.assertThat(e.getCounter(), Matchers.is("1"));
			}).clickCounterIncrementButton()
		.doAssertions(e -> {
			MatcherAssert.assertThat(e.getCounter(), Matchers.is("2"));
		})
		.clickCounterIncrementButton()
		.clickCounterIncrementButton()
		.doAssertions(e -> {
			MatcherAssert.assertThat(e.getCounter(), Matchers.is("4"));
		})
			;
	}
	
	
}
