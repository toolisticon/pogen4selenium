package io.toolisticon.pogen4selenium.api;

import org.openqa.selenium.WebDriver;

public interface RetryCause {

	boolean applies(Exception e, WebDriver driver);
	
}
