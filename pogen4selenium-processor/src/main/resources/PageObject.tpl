package ${ packageName };

!{for import : imports}
import ${import};
!{/for}
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * An empty class.
 */
public class ${ toImplementHelper.implementationClassName } ${toImplementHelper.typeVarString} extends ${toImplementHelper.extendsString} implements ${toImplementHelper.interfaceName}${toImplementHelper.getInterfaceTypeVarString}{

	// constants
!{for element : pageObject.value}
	protected final static String ${element.locatorConstantName} = "${element.value}";
!{/for}	
	
	// bindings
!{for element : pageObject.value}
	@FindBy(how=How.${element.by.name}, using=${element.locatorConstantName})
	protected WebElement ${element.elementVariableName}Element;
!{/for}

	/** 
	 * Constructor
	 */
	public ${ toImplementHelper.implementationClassName } (WebDriver driver) {
		super(driver);    	
	}
	

	public !{if !toImplementHelper.hasTypeParameters}${toImplementHelper.interfaceName}!{else}PAGEOBJECT!{/if} verify() {
!{for element : pageObject.value}!{if element.usedForVerify.name == 'PRESENT'}
		waitForElementToBePresent(${element.locatorConstantName});
!{elseif element.usedForVerify.name == 'CLICKABLE'}
		waitForElementToBeClickable(${element.locatorConstantName});
!{/if}!{/for}	
		return !{if toImplementHelper.hasTypeParameters}(PAGEOBJECT)!{/if} this;
	}
	
	// implement methods
!{for method : methodsToImplement}
	@Override
	public ${method.methodSignature}{
	
		pause(Duration.ofMillis(${method.beforePause}L));
	
		// Elements to write to
!{for toWrite : method.elementsToWriteStrings}
		writeToElement(${toWrite.elementVarName}Element, ${toWrite.toWriteParameterName});
!{/for}
	
!{if method.getElementToClick.isPresent}
		// Button to click
		${method.getElementToClick.get}Element.click();
!{/if}
!{if method.getElementToMoveToAndClick.isPresent}
		// Move to Element and click
		new Actions(getDriver()).moveToElement(${method.getElementToMoveToAndClick.get}Element).pause(300).click().build().perform();		
!{/if}
!{if method.getExtractDataValue.isPresent}
		return ${method.getExtractDataValue.get.getFinalMethodCall}
!{elseif method.getExtractData.isPresent}
!{if method.getExtractData.get.isList}
		return getDriver().findElements(By.${method.getExtractData.get.by.correspondingByMethodName}("${method.getExtractData.get.value}")).stream().map( ${method.getExtractData.get.extractedDataImplName}::new).collect(Collectors.toList());
!{else}
		return new ${method.getExtractData.get.extractedDataImplName}(getDriver().findElement(By.${method.getExtractData.get.by.correspondingByMethodName}("${method.getExtractData.get.value}")));
!{/if}
!{else}
		return new ${method.getNextImplClassName}(getDriver()).pause(Duration.ofMillis(${method.afterPause}L));
!{/if}
	}
!{/for}	

}
