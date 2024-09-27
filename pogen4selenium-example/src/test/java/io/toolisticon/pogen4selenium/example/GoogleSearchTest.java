package io.toolisticon.pogen4selenium.example;

import java.time.Duration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class GoogleSearchTest {
	
	
	private WebDriver webDriver;
	
	
	@Before
	public void init() {
		webDriver = new ChromeDriver();
		webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	
	@After
	public void cleanup() {
		webDriver.close();
	}
	
	@Test
	public void testGoogleSearch() {
		GoogleHomepage.init(webDriver)
			.acceptL2Agl()
			.enterSearchString("toolisticon aptk")
			.clickSearchButton()
			.doAssertions(e -> {
				
				// Do assertions here
				
			})
			;
	}

}
