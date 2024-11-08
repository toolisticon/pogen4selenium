package io.toolisticon.pogen4selenium.processor.pageobject;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import org.openqa.selenium.interactions.Actions;

import io.toolisticon.aptk.tools.wrapper.ElementWrapper;
import io.toolisticon.aptk.tools.wrapper.ExecutableElementWrapper;
import io.toolisticon.aptk.tools.wrapper.TypeElementWrapper;
import io.toolisticon.aptk.tools.wrapper.VariableElementWrapper;
import io.toolisticon.pogen4selenium.api.Action;
import io.toolisticon.pogen4selenium.api.PageObject;
import io.toolisticon.pogen4selenium.processor.pageobject.actions.ActionWrapper;

public class MethodsToImplementHelper {
	
	private final ExecutableElementWrapper executableElementWrapper;

	public MethodsToImplementHelper(ExecutableElementWrapper executableElementWrapper) {
		super();
		this.executableElementWrapper = executableElementWrapper;
	}
	

	
	public String getMethodSignature() {
		return this.executableElementWrapper.getMethodSignature();
	}
	
	public Set<String> getImports() {
		Set<String> imports = new HashSet<>();
		imports.addAll(this.executableElementWrapper.getImports());
		
		Optional<TypeElementWrapper> tew = this.executableElementWrapper.getReturnType().getTypeElement();
		if( tew.isPresent() && tew.get().hasAnnotation(PageObject.class)) {
			
			// must also import the impl class for other Page Objects
			imports.add(this.executableElementWrapper.getReturnType().getPackage() + "." + this.executableElementWrapper.getReturnType().getSimpleName() + "Impl");
		
		}		
		
		imports.add(Actions.class.getCanonicalName());
		imports.add(Duration.class.getCanonicalName());
		
		// get the imports needed by all related actions
		imports.addAll( getActions().stream().map(e -> e.getImports()).flatMap(Set::stream).collect(Collectors.toSet()) );
		
		
		return imports;
	}
	
	public List<ActionWrapper> getActions() {
		
		// Must get annotations by Meta annotation Action
		List<ActionWrapper> actions = getActionsForElement(this.executableElementWrapper);
		
		List<VariableElementWrapper> annotatedParameters = this.executableElementWrapper.getParameters();
		
		for (VariableElementWrapper annotatedParameter : annotatedParameters) {
			actions.addAll(getActionsForElement(annotatedParameter));
		}
		
		return actions;
	}
	
	private static List<ActionWrapper> getActionsForElement(ElementWrapper<? extends Element> element) {
		return element.getAnnotations().stream()
				.filter(e -> e.asElement().hasAnnotation(Action.class))
				.<ActionWrapper>map(e -> {
					
					return new ActionWrapper(TypeElementWrapper.wrap((TypeElement)(e.getAnnotationType().asElement())).getQualifiedName(), element.unwrap());
				})
				.collect(Collectors.toList());
	}
	
	public Optional<ExtractDataWrapper> getExtractData() {
		return Optional.ofNullable(ExtractDataWrapper.wrap(this.executableElementWrapper.unwrap()));
	}
	
	public Optional<ExtractDataValueWrapper> getExtractDataValue() {
		return Optional.ofNullable(ExtractDataValueWrapper.wrap(this.executableElementWrapper.unwrap()));
	}
	
	
	public String getNextImplClassName() {
		return this.executableElementWrapper.getReturnType().getSimpleName() + "Impl";
	}
	
	public long getBeforePause() {
		return PauseWrapper.isAnnotated(this.executableElementWrapper.unwrap()) ? PauseWrapper.wrap(this.executableElementWrapper.unwrap()).pauseBefore(): 0L;
	}
	
	public long getAfterPause() {
		return PauseWrapper.isAnnotated(this.executableElementWrapper.unwrap()) ? PauseWrapper.wrap(this.executableElementWrapper.unwrap()).value(): 0L;
	}
	
	public record ElementsToWrite( String elementVarName,String toWriteParameterName) {};
	
	
	public boolean validate() {
		
		// default implementations must be ignored at validation
		if (executableElementWrapper.isDefault()) {
			return true;
		}
		
		boolean validationResult = true;
		
		// validate methods related with data extraction
		if(ExtractDataWrapper.isAnnotated(executableElementWrapper.unwrap())) {
			validationResult = validationResult & ExtractDataWrapper.wrap(executableElementWrapper.unwrap()).validate();
		}
		
		return validationResult;
		
	}
	


}
