package ${ packageName };

!{for import : imports}
import ${import};
!{/for}
import java.util.stream.Collectors;

import io.toolisticon.pogen4selenium.runtime.PageObjectUtilities;

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
!{if pageObject.addUrlCheckToVerify}
		waitUntilUrl("${pageObject.urlRegularExpressionToVerify}");
!{/if}	
!{for element : pageObject.value}!{if element.usedForVerify.name == 'PRESENT'}
		waitForElementToBePresent(${element.locatorConstantName});
!{elseif element.usedForVerify.name == 'CLICKABLE'}
		waitForElementToBeInteractable(${element.locatorConstantName});
!{/if}!{/for}	
		return !{if toImplementHelper.hasTypeParameters}(PAGEOBJECT)!{/if} this;
	}
	
	// implement methods
!{for method : methodsToImplement}
	@Override
	public !{if method.isSynchronized}synchronized!{/if} ${method.methodSignature}{
	
		pause(Duration.ofMillis(${method.beforePause}L));

!{for action : method.actions}
		${action.generateCode}
!{/for}
	
!{if method.getExtractDataValue.isPresent}
		return ${method.getExtractDataValue.get.getFinalMethodCall}
!{elseif method.getExtractData.isPresent}
		return ${method.getExtractData.get.getFinalMethodCall}
!{else}
		return changePageObjectType(${method.getNextImplClassName}.class).pause(Duration.ofMillis(${method.afterPause}L));
!{/if}
	}
!{/for}	

}
