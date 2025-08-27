package io.toolisticon.pogen4selenium.processor.common.actions;

import java.util.Set;

public interface RetryHandler {

	String getRetryConfigType();
	
	int getNrOfTries();
	
	int getIntervalInMs();
	
	String getRetryCauseClassNames();
	
	/**
	 * Gets all import needed by the generated code to be compiled and executed correctly
	 * @return a set containing all imports or an empty list (must not be null !!!)
	 */
	Set<String> getImports();
	
}
