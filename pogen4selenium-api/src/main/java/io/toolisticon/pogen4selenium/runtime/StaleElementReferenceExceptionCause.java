package io.toolisticon.pogen4selenium.runtime;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.toolisticon.pogen4selenium.api.RetryCause;

public class StaleElementReferenceExceptionCause implements RetryCause {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(StaleElementReferenceExceptionCause.class);
	
	@Override
	public boolean applies(Exception e, WebDriver driver) {
		LOGGER.info("Do retry check for cause : " + StaleElementReferenceExceptionCause.class.getName());
		return e != null && StaleElementReferenceException.class.equals(e.getClass());
	}

}
