package io.toolisticon.pogen4selenium.processor.datatoextract;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import org.openqa.selenium.interactions.Actions;

import io.toolisticon.aptk.tools.wrapper.ExecutableElementWrapper;

public class MethodsToImplementHelper {
	
	private final ExecutableElementWrapper executableElementWrapper;

	public MethodsToImplementHelper(ExecutableElementWrapper executableElementWrapper) {
		super();
		this.executableElementWrapper = executableElementWrapper;
	}
	
	public String getMethodName() {
		return this.executableElementWrapper.getSimpleName();
	}
	
	public String getByLocator() {
		ExtractDataValueWrapper annotation = ExtractDataValueWrapper.wrap(this.executableElementWrapper.unwrap());
		return "By." + annotation.by().name().toLowerCase() + "(\"" + annotation.value() + "\")";
	}
	
	public Set<String> getImports() {
		Set<String> imports = new HashSet<>();
		imports.addAll(this.executableElementWrapper.getImports());
		imports.add(Actions.class.getCanonicalName());
		imports.add(Duration.class.getCanonicalName());
		
		return imports;
	}
	
	
	


}
