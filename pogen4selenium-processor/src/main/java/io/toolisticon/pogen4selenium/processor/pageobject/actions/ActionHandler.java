package io.toolisticon.pogen4selenium.processor.pageobject.actions;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.lang.model.element.Element;

import io.toolisticon.spiap.api.Spi;


/**
 * Service provider interface to bind implementations for actions.
 */

@Spi
public interface ActionHandler {

	String getSupportedActionAnnotationClassFqn();
	
	public String generateCode(Element element);
	
	public Set<String> getImports(Element element);
	
	
}
