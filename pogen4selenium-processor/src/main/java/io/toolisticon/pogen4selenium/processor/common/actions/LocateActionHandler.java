package io.toolisticon.pogen4selenium.processor.common.actions;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.lang.model.element.Element;

import io.toolisticon.pogen4selenium.processor.pageobject.PageObjectProcessor;

public class LocateActionHandler {

	private final String annotationTypeFqn;
	private final Element annotatedElement;
	
	private final ActionHandler actionHandler;
	
	static {
		// enforce classloader of processor
		ActionHandlerServiceLocator.setClassLoaderToUse(PageObjectProcessor.class.getClassLoader());
	}
	
	public LocateActionHandler(String annotationTypeFqn, Element annotatedElement) {
		super();
		
		this.annotationTypeFqn = annotationTypeFqn;
		this.annotatedElement = annotatedElement;
		
		this.actionHandler = locateActionHandler(annotationTypeFqn);
		
	}
	
	static ActionHandler locateActionHandler (String annotationTypeFqn) {
			
		List<ActionHandler> matchingHandlers = ActionHandlerServiceLocator.locateAll().stream().filter(e -> annotationTypeFqn.equals(e.getSupportedActionAnnotationClassFqn())).collect(Collectors.toList());
		
		return !matchingHandlers.isEmpty() ? matchingHandlers.get(0) : new DefaultActionHandler();
		
	}
	

	public String generateCode() {
		return actionHandler.generateCode(annotatedElement);
	}
	
	public Set<String> getImports() {
		return actionHandler.getImports(annotatedElement);
	}
	
	
	
}
