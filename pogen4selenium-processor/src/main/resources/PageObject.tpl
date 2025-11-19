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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An empty class.
 */
public class ${ toImplementHelper.implementationClassName } ${toImplementHelper.typeVarString} extends ${toImplementHelper.extendsString} implements ${toImplementHelper.interfaceName}${toImplementHelper.getInterfaceTypeVarString}{

	private final static Logger LOGGER = LoggerFactory.getLogger(${toImplementHelper.interfaceName}.class);
	

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
	
	// default methods
!{for defaultMethod : defaultMethods}
	@Override
	public ${defaultMethod.methodSignature}{
		LOGGER.info(${defaultMethod.getMethodLogStatement});
		return ${toImplementHelper.interfaceName}.super.${defaultMethod.getDefaultMethodCall};
	}
!{/for}
	
	// implement methods
!{for method : methodsToImplement}
	@Override
	public ${method.methodSignature}{
	
		LOGGER.info(${method.getMethodLogStatement});
	
		!{if method.isSynchronized}synchronized(${ toImplementHelper.implementationClassName }.class){!{/if}
		pause(Duration.ofMillis(${method.beforePause}L));

!{for action : method.actions}
		${action.generateCode}
!{/for}
	
!{if method.getExtractDataValue.isPresent}
		String value = ${method.getExtractDataValue.get.getFinalMethodCall}
		LOGGER.info("Extracted Data Value : '{}'", value);
		return value;
!{elseif method.getExtractData.isPresent}
		return ${method.getExtractData.get.getFinalMethodCall}
!{else}
		return changePageObjectType(${method.getNextImplClassName}.class).pause(Duration.ofMillis(${method.afterPause}L));
!{/if}
		!{if method.isSynchronized}}!{/if}
	}
!{/for}	

}
