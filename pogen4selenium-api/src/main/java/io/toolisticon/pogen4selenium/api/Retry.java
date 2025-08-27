package io.toolisticon.pogen4selenium.api;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PACKAGE})
public @interface Retry {

	// Must be >= 0
	int nrOfRetries();
	
	// Must be >= 0
	int retryIntervallInMs();
	
	Class<? extends RetryCause>[] retryCauses() default {};
	
}
