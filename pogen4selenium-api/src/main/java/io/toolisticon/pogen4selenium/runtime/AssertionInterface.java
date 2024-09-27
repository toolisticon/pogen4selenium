package io.toolisticon.pogen4selenium.runtime;

import io.toolisticon.pogen4selenium.api.PageObjectParent;

public interface AssertionInterface <PAGEOBJECT extends PageObjectParent<PAGEOBJECT>>{

	void doAssertion(PAGEOBJECT pageObject);
	
}