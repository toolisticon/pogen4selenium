package io.toolisticon.pogen4selenium.processor.common.actions;

import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import io.toolisticon.aptk.tools.wrapper.ElementWrapper;
import io.toolisticon.aptk.tools.wrapper.ExecutableElementWrapper;
import io.toolisticon.aptk.tools.wrapper.TypeElementWrapper;
import io.toolisticon.aptk.tools.wrapper.VariableElementWrapper;
import io.toolisticon.pogen4selenium.api.Action;

public class ActionHelper {

	private ActionHelper() {
		
	}
	
	public static boolean hasActions(ExecutableElementWrapper executableElementWrapper) {
		return !getActions(executableElementWrapper).isEmpty();
	}
	
	public static  List<LocateActionHandler> getActions(ExecutableElementWrapper executableElementWrapper) {
		
		// Must get annotations by Meta annotation Action
		List<LocateActionHandler> actions = getActionsForElement(executableElementWrapper);
		
		List<VariableElementWrapper> annotatedParameters = executableElementWrapper.getParameters();
		
		for (VariableElementWrapper annotatedParameter : annotatedParameters) {
			actions.addAll(getActionsForElement(annotatedParameter));
		}
		
		return actions;
	}
	
	private static List<LocateActionHandler> getActionsForElement(ElementWrapper<? extends Element> element) {
		return element.getAnnotations().stream()
				.filter(e -> e.asElement().hasAnnotation(Action.class))
				.<LocateActionHandler>map(e -> {
					
					return new LocateActionHandler(TypeElementWrapper.wrap((TypeElement)(e.getAnnotationType().asElement())).getQualifiedName(), element.unwrap());
				})
				.collect(Collectors.toList());
	}

}