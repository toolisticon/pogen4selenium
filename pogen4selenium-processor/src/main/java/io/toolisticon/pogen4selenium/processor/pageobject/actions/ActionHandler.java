package io.toolisticon.pogen4selenium.processor.pageobject.actions;

import java.util.Set;

import javax.lang.model.element.Element;

import io.toolisticon.spiap.api.Spi;


/**
 * Service provider interface to bind implementations for actions.
 */

@Spi
public interface ActionHandler {

	/**
	 * The fully qualified name of the related annotation type.
	 * @return the fqn of the supported annotation type
	 */
	String getSupportedActionAnnotationClassFqn();
	
	/**
	 * Method that is called to generate the code necessary to fulfill the action.
	 * @param element The annotated element
	 * @return The code that must be added to the annotated method or an empty String(must not be null !!!)
	 */
	public String generateCode(Element element);
	
	
	/**
	 * Gets all import needed by the generated code to be compiled and executed correctly
	 * @param element the annotated element
	 * @return a set containing all imports or an empty list (must not be null !!!)
	 */
	public Set<String> getImports(Element element);
	
	
}
