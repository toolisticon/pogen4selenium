package io.toolisticon.pogen4selenium.processor.common.actions;

import java.util.Set;

import javax.lang.model.element.Element;

/**
 * TODO : This was once realized as an SPI solution and need to be removed.
 */
public class LocateActionHandler {

	private final Element annotatedElement;
	
	private final ActionHandler actionHandler;
	
	
	public LocateActionHandler(String annotationTypeFqn, Element annotatedElement) {
		super();
		
		this.annotatedElement = annotatedElement;
		
		this.actionHandler = locateActionHandler(annotationTypeFqn);
		
	}
	
	static ActionHandler locateActionHandler (String annotationTypeFqn) {
		return new UniversalActionHandler(annotationTypeFqn);
	}
	

	public String generateCode() {
		return actionHandler.generateCode(annotatedElement);
	}
	
	public Set<String> getImports() {
		return actionHandler.getImports(annotatedElement);
	}
	
	
	
}
