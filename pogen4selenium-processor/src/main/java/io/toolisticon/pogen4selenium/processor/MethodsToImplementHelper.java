package io.toolisticon.pogen4selenium.processor;

import java.lang.annotation.Annotation;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.interactions.Actions;

import io.toolisticon.pogen4selenium.api.ActionClick;
import io.toolisticon.pogen4selenium.api.ActionMoveToAndClick;
import io.toolisticon.pogen4selenium.api.ActionWrite;

import io.toolisticon.aptk.tools.wrapper.ExecutableElementWrapper;

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
		imports.add(Actions.class.getCanonicalName());
		imports.add(Duration.class.getCanonicalName());
		
		return imports;
	}
	
	public List<ElementsToWrite> getElementsToWriteStrings() {
		return this.executableElementWrapper.getParameters().stream()
				.filter(e -> e.hasAnnotation((Class<? extends Annotation>) ActionWrite.class))
				.map(e -> new ElementsToWrite(ActionWriteWrapper.wrap(e.unwrap()).value(), e.getSimpleName()))
				.collect(Collectors.toList());
	}
	
	public Optional<String> getElementToClick() {
		return this.executableElementWrapper.hasAnnotation(ActionClick.class) ? 
				Optional.of(ActionClickWrapper.wrap(this.executableElementWrapper.unwrap()).value())
				: Optional.empty();
	}
	
	public Optional<String> getElementToMoveToAndClick() {
		return this.executableElementWrapper.hasAnnotation(ActionMoveToAndClick.class) ? 
				Optional.of(ActionMoveToAndClickWrapper.wrap(this.executableElementWrapper.unwrap()).value())
				: Optional.empty();
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
	
		


}
