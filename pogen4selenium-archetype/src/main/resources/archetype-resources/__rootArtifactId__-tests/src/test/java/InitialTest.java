#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.time.Duration;
import java.util.List;


import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;

import io.toolisticon.pogen4selenium.junit5.SeleniumRunnerExtension;
import io.toolisticon.pogen4selenium.runtime.tools.WebDriverProvider;

public class InitialTest {

	private WebDriver webDriver;
	
	@BeforeEach
	public void init() throws Exception{
		webDriver = WebDriverProvider.getDriver();
	}
	
	@AfterEach
	public void cleanup() throws Exception{
		webDriver.quit();
	}
	
	
	@Test
	public void testOpenPages() {
		
		StartPageObject.init(webDriver).pause(Duration.ofMillis(10000L));
		


	}
	
}
