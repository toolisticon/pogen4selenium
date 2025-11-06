/**
 * 
 */
package io.toolisticon.pogen4selenium.api;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
/**
 * This annotation is used to enforce page objects method to be synchronized
 */
public @interface Synchronized {

}
