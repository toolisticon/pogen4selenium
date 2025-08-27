package io.toolisticon.pogen4selenium.runtime;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.toolisticon.pogen4selenium.api.RetryCause;

public class ElementClickedInterceptedExceptionCause implements RetryCause {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ElementClickedInterceptedExceptionCause.class);
	
	@Override
	public boolean applies(Exception e, WebDriver driver) {
		LOGGER.info("Do retry check for cause : " + ElementClickedInterceptedExceptionCause.class.getName());
		return e != null && ElementClickInterceptedException.class.equals(e.getClass());
	}

}
