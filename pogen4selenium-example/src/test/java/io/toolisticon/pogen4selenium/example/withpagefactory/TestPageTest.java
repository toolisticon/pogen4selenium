package io.toolisticon.pogen4selenium.example.withpagefactory;

import java.time.Duration;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import io.toolisticon.pogen4selenium.example.JettyServer;
import io.toolisticon.pogen4selenium.runtime.WebDriverProvider;

public class TestPageTest {


	private WebDriver webDriver;
	private JettyServer jettyServer;
	
	@Before
	public void init() throws Exception{
		
		jettyServer = new JettyServer();
		jettyServer.start();
		
		webDriver = WebDriverProvider.getDriver();
		
	}
	
	@After
	public void cleanup() throws Exception{
		jettyServer.stop();
		webDriver.quit();
	}
	
	@Test
	public void extractDatasetsTest() {
		TestPagePageObject.init(webDriver)
		.doAssertions(e -> {
				
				// Do assertions here
				List<TestPageTableEntry> results = e.getTableEntries();
				
				MatcherAssert.assertThat(results, Matchers.hasSize(1));
				
				MatcherAssert.assertThat(results.get(0).name(), Matchers.is("Max"));
				MatcherAssert.assertThat(results.get(0).age(), Matchers.is("9"));
				MatcherAssert.assertThat(results.get(0).link(), Matchers.is("http://localhost:9090/linkA"));
				MatcherAssert.assertThat(results.get(0).linkText(), Matchers.is("Link A"));
				

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
				MatcherAssert.assertThat(result.link(), Matchers.is("http://localhost:9090/linkA"));
				MatcherAssert.assertThat(result.linkText(), Matchers.is("Link A"));
				
		

			})
			;
	}
	
	@Test
	public void mixedDataObjectWithActionsAndPageObjectTraversialTest() {
		TestPagePageObject.init(webDriver)
		.doAssertions(e -> {
				
				// Do assertions here
				TestPageTableEntry result = e.getFirstTableEntry();
				
				MatcherAssert.assertThat(result.writeToInputField("TEST!").inputField(), Matchers.is("TEST!"));
				MatcherAssert.assertThat(result.clickLink().getDriver().getCurrentUrl(),Matchers.is("http://localhost:9090/linkA"));
		
				System.out.println("");
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
	
	@Test
	public void writeToAndReadFromInputField() {
		
		TestPagePageObject.init(webDriver)
		.writeToInputField("TEST!!!")
		.pause(Duration.ofMillis(200L))
		.doAssertions(e -> {
			MatcherAssert.assertThat(e.readInputFieldValue(), Matchers.is("TEST!!!"));
		});
		
	}
	
	
}
