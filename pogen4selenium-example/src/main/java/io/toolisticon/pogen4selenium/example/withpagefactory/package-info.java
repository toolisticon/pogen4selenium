@Retry(nrOfRetries = 20, retryIntervallInMs = 500, retryCauses = {ElementClickedInterceptedExceptionCause.class})
package io.toolisticon.pogen4selenium.example.withpagefactory;

import io.toolisticon.pogen4selenium.api.Retry;
import io.toolisticon.pogen4selenium.runtime.ElementClickedInterceptedExceptionCause;
