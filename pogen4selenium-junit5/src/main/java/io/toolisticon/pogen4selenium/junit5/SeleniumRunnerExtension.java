package io.toolisticon.pogen4selenium.junit5;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;

import io.toolisticon.pogen4selenium.runtime.tools.ActiveDriverHandler;



public class SeleniumRunnerExtension implements AfterTestExecutionCallback{

	final static Logger LOGGER = LoggerFactory.getLogger(SeleniumRunnerExtension.class);

	@Override
	public void afterTestExecution(ExtensionContext context) throws Exception {
		
        if (context.getExecutionException().isPresent()) {
            WebDriver currentDriver = ActiveDriverHandler.getCurrentDriver();
            LOGGER.error("-------------------------------------------------------------------");
            LOGGER.error("-------------------------------------------------------------------");
            LOGGER.error("-- SELENIUM ERROR INFO");
            LOGGER.error("-------------------------------------------------------------------");
            LOGGER.error("-------------------------------------------------------------------");
            if (currentDriver != null) {
            	
            	LOGGER.error("TEST CLASS   : {}", context.getTestClass().map(e -> e.getCanonicalName()).orElseGet(() -> "<NOT FOUND>"));
            	LOGGER.error("TEST METHOD  : {}", context.getTestMethod().map(e -> e.getName()).orElseGet(() -> "<NOT FOUND>"));
            	LOGGER.error("URL          : {}",  currentDriver.getCurrentUrl());
            	LOGGER.error("PAGE TITLE   : {}", currentDriver.getTitle());
            	LOGGER.error("SCREENSHOT   : {}", getScreenshot(context, currentDriver));
            	LOGGER.error("SOURCE       : {}", getSourceCode(context, currentDriver));
            	LOGGER.error("EXCEPTION    : ", context.getExecutionException());
            	
            	
            } else {
            	LOGGER.error("Unable to provide WebDriver debug data, because Driver isn' configured in {}", ActiveDriverHandler.class.getCanonicalName());
            }
        }
		
	}

	private String getScreenshot(ExtensionContext context, WebDriver driver) {
		try {
			String screenshotPath=
					"target/pogen4selenium-reports/"
					+ context.getTestClass().map(e -> e.getCanonicalName()).orElseGet(() -> "TestClass_UNDEFINED")
					+ "." + context.getTestMethod().map(e -> e.getName()).orElseGet(() -> "method_UNDEFINED")
					+ ".jpg"
					;
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			File targetFile = new File(screenshotPath);
			Files.createParentDirs(targetFile);
			
			Files.copy(scrFile,targetFile);
			
			return screenshotPath;
		} catch (IOException e) {
			return "ERROR : " + e.getMessage();
		}
	}
	
	private String getSourceCode(ExtensionContext context, WebDriver driver) {
		try {
			String sourceFileName=
					"target/pogen4selenium-reports/"
					+ context.getTestClass().map(e -> e.getCanonicalName()).orElseGet(() -> "TestClass_UNDEFINED")
					+ "." + context.getTestMethod().map(e -> e.getName()).orElseGet(() -> "method_UNDEFINED")
					+ ".html"
					;
			
			File targetFile = new File(sourceFileName);
			Files.createParentDirs(targetFile);
			
			Files.write(driver.getPageSource().getBytes(), targetFile);
			
			return sourceFileName;
		} catch (IOException e) {
			return "ERROR : " + e.getMessage();
		}
	}
	
	
}
