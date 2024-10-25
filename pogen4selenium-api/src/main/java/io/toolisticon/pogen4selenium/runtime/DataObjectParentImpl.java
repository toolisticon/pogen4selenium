package io.toolisticon.pogen4selenium.runtime;

import org.openqa.selenium.WebElement;

import io.toolisticon.pogen4selenium.api._By;
import io.toolisticon.pogen4selenium.api.ExtractDataValue;

public class DataObjectParentImpl {
	
	private final WebElement relativeParentWebElement;
	
	protected DataObjectParentImpl(WebElement relativeParentWebElement) {
		this.relativeParentWebElement = relativeParentWebElement;
	}

	protected WebElement getRelativeParentWebElement() {
		return this.relativeParentWebElement;
	}
	
	protected WebElement getValueWebElement(_By by, String locatorString) {
		
		org.openqa.selenium.By relativeLocator = null;
		switch(by) {
			case ID : 
			{
				relativeLocator = org.openqa.selenium.By.id(locatorString);
				break;
			}
			case XPATH : 
			{
				relativeLocator = org.openqa.selenium.By.xpath(locatorString);
				break;
			}
		}
		
		if (relativeLocator != null) {
			return relativeParentWebElement.findElement(relativeLocator);
		}
		
		return null;
	}
	
	protected String getValue(_By by, String locatorString, ExtractDataValue.Kind kind, String name) {		
		WebElement  valueWebElement = this.getValueWebElement(by, locatorString);
		
		if (valueWebElement != null) {
			
			switch (kind) {
				case ATTRIBUTE: {
					return valueWebElement.getAttribute(name);
				}
				case CSS_VALUE: {
					return valueWebElement.getCssValue(name);
				}
				case TAG_NAME: {
					return valueWebElement.getTagName();
				}
				case ACCESSIBLE_NAME: {
					return valueWebElement.getAccessibleName();
				}		
				case TEXT:
				default:
					return valueWebElement.getText();
			}
	
		
		}
		
		return null;
	}
	
}
