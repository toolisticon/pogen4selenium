package io.toolisticon.pogen4selenium.processor.common;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.interactions.Actions;

import io.toolisticon.aptk.tools.wrapper.ExecutableElementWrapper;
import io.toolisticon.aptk.tools.wrapper.TypeElementWrapper;
import io.toolisticon.pogen4selenium.api.PageObject;
import io.toolisticon.pogen4selenium.processor.common.actions.ActionHelper;
import io.toolisticon.pogen4selenium.processor.common.actions.ActionWrapper;
import io.toolisticon.pogen4selenium.processor.pageobject.ExtractDataValueWrapper;
import io.toolisticon.pogen4selenium.processor.pageobject.ExtractDataWrapper;
import io.toolisticon.pogen4selenium.processor.pageobject.PauseWrapper;

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
		return ActionHelper.getActions(executableElementWrapper);
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