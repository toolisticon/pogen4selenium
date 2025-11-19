package ${ packageName };

!{for import : imports}
import ${import};
!{/for}
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import io.toolisticon.pogen4selenium.api._By;
import io.toolisticon.pogen4selenium.api.ExtractDataValue;
import io.toolisticon.pogen4selenium.runtime.DataObjectParentImpl;
import io.toolisticon.pogen4selenium.runtime.PageObjectUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An empty class.
 */
public class ${ toImplementHelper.implementationClassName }  extends DataObjectParentImpl implements ${toImplementHelper.interfaceName}{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(${toImplementHelper.interfaceName}.class);
	
	
	/** 
	 * Constructor
	 */
	public ${ toImplementHelper.implementationClassName } (WebDriver driver, WebElement relativeContentRoot) {
		super(driver, relativeContentRoot);    	
	}
		
	// implement methods
!{for dataToExtractValue : dataToExtract.value}
	@Override
	public String ${dataToExtractValue.methodName}(){
		
		String value = getValue(_By.${dataToExtractValue.by}, "${dataToExtractValue.value}", ExtractDataValue.Kind.${dataToExtractValue.kind}, "${dataToExtractValue.name}");
		LOGGER.info("${toImplementHelper.simpleInterfaceName}.${dataToExtractValue.methodName}() = '{}'", value);
		return value;
	
	}
!{/for}	

!{for method : methodsToImplement}
	@Override
	public !{if method.isSynchronized}synchronized!{/if} ${method.methodSignature}{
	
		LOGGER.info(${method.getMethodLogStatement});
	
		pause(Duration.ofMillis(${method.beforePause}L));

!{for action : method.actions}
		${action.generateCode}
!{/for}
!{if method.returnsPageObject}	
		return changePageObjectType(${method.getNextImplClassName}.class).pause(Duration.ofMillis(${method.afterPause}L));
!{else}
		return this;	
!{/if}
	}
!{/for}	

}
