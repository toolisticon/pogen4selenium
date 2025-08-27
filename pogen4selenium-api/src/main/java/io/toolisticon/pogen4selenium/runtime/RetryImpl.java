package io.toolisticon.pogen4selenium.runtime;

import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.toolisticon.pogen4selenium.api.RetryCause;

public class RetryImpl {
 
	private final static Logger LOGGER = LoggerFactory.getLogger(RetryImpl.class);
	
	
	static class RetryFailedException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		RetryFailedException(Exception e) {
			super("Retry failed", e);
		}
	}
	
	public enum RetryConfigType {
		UNDEFINED,
		DEFAULT,
		EXPLICIT
	}
	
	
	private final WebDriver driver;
	private final RetryConfigType retryConfigType;
	private final int nrOfRetries;
	private final int intervalInMs;
	private final Class<? extends RetryCause>[] retryCauses;
	
	private RetryImpl(WebDriver driver, RetryConfigType retryConfigType, int nrOfRetries, int intervalInMs, Class<? extends RetryCause>[] retryCauses) {
		this.driver = driver;
		this.retryConfigType = retryConfigType;
	
		this.nrOfRetries = nrOfRetries;
		this.intervalInMs = intervalInMs;
		this.retryCauses = retryCauses;
		
	}
	
	
	public void execute(Runnable runnable) {
		
		RuntimeException lastException = null;
		
		// must be executed at least once
		for (int i=0 ; i < this.nrOfRetries + 1; i++) {
			
			try {
				
				runnable.run();
				return;
				
			} catch (RuntimeException e) {
				
				try {
					if (!checkCauses(e)) {
						// rethrow e in case no retry cause has been found
						throw e;
					}
				
					// have found RetryCause - must wait
					this.driver.wait(intervalInMs);
				
				} catch (InterruptedException e1) {
					// ignore and return
					return;
				}
				
				lastException = e;
				
				if (i + 1 < this.nrOfRetries) {
					LOGGER.info("Do retry " + (i+1));
				}
			}
				
			
		}
		
		// rethrow last exception to stop test
		LOGGER.info("Max retries reached => rethrow exception '" + lastException.getMessage() + "'");
		throw lastException;
	}
	
	private boolean checkCauses(Exception e) {
		
		for (Class<? extends RetryCause> retryCauseClass : retryCauses) {
			
			try {
				if (retryCauseClass.getConstructor().newInstance().applies(e, driver)) {
					return true;
				}
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e1) {
				// ignore the cause
				LOGGER.error("Some error happened");
			}
			
		}
		
		return false;
	}
	
	@SafeVarargs
	public static RetryImpl prepareRetry(WebDriver driver, RetryConfigType retryConfigType, int nrOfRetries, int intervalInMs, Class<? extends RetryCause>... retryCauses) {
		return new RetryImpl(driver, retryConfigType, nrOfRetries, intervalInMs, retryCauses);
	}
	
}
