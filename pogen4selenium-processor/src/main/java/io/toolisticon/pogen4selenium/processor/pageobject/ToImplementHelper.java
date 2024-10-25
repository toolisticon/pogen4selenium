package io.toolisticon.pogen4selenium.processor.pageobject;

import java.util.HashSet;
import java.util.Set;

import io.toolisticon.aptk.tools.TypeMirrorWrapper;
import io.toolisticon.aptk.tools.TypeUtils;
import io.toolisticon.aptk.tools.wrapper.TypeElementWrapper;
import io.toolisticon.pogen4selenium.api.PageObjectParent;
import io.toolisticon.pogen4selenium.runtime.PageObjectParentImpl;

public class ToImplementHelper {
	
	private final TypeElementWrapper element;

	public ToImplementHelper(TypeElementWrapper element) {
		super();
		this.element = element;
	}
	
	public String getExtendsString() {
		return PageObjectParentImpl.class.getSimpleName() + "<" + this.element.getQualifiedName() + ">";
		/*-
		return !hasTypeParameters() ? 
				element.getInterfaces().get(0).getSimpleName() + "Impl <" + this.element.getQualifiedName() + ">"
						: element.getInterfaces().get(0).getSimpleName() + "Impl <PAGEOBJECT>";
		*/
	}
	
	public String  getInterfaceTypeVarString() {
		return hasTypeParameters() ? "<PAGEOBJECT>": "";
	}
	
	public String getTypeVarString() {
		return hasTypeParameters() ? "<PAGEOBJECT extends PageObjectParent<PAGEOBJECT>>": "";
	}
	
	public boolean hasTypeParameters() {
		return element.hasTypeParameters();
	}
	
	public String getInterfaceName() {
		return this.element.getQualifiedName();
	}
	
	public String getImplementationClassName() {
		return element.getSimpleName() + "Impl";
	}
	
	public Set<String> getImports() {
		Set<String> result = new HashSet<String>();
		result.add(PageObjectParent.class.getCanonicalName());
		result.add(PageObjectParentImpl.class.getCanonicalName());
		result.add(this.element.getQualifiedName());
		result.add(element.getInterfaces().get(0).getQualifiedName());
		return result;
	}
	

	
	boolean validate() {
		
		return this.element.getInterfaces().size() == 1
				&& (
						TypeUtils.TypeComparison.isErasedTypeEqual(this.element.getInterfaces().get(0).unwrap(),TypeMirrorWrapper.wrap(PageObjectParent.class).unwrap())
						|| this.element.getInterfaces().get(0).erasure().isAssignableTo(TypeMirrorWrapper.wrap(PageObjectParent.class))
					);
	}
	
	
	
	
	
	

}
