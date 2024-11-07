package io.toolisticon.pogen4selenium.processor.pageobject.actions;

import java.util.Collections;
import java.util.Set;

import javax.lang.model.element.Element;

public class DefaultActionHandler implements ActionHandler {

	@Override
	public String getSupportedActionAnnotationClassFqn() {
		// not needed...
		return null;
	}

	@Override
	public String generateCode(Element element) {
		return "";
	}

	@Override
	public Set<String> getImports(Element element) {
		return Collections.emptySet();
	}

}
