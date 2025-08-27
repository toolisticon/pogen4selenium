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

import io.toolisticon.pogen4selenium.runtime.RetryImpl;
import io.toolisticon.pogen4selenium.runtime.RetryImpl.RetryConfigType;

/**
 * An empty class.
 */
public class ${ toImplementHelper.implementationClassName }  extends DataObjectParentImpl implements ${toImplementHelper.interfaceName}{
	
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
		return getValue(_By.${dataToExtractValue.by}, "${dataToExtractValue.value}", ExtractDataValue.Kind.${dataToExtractValue.kind}, "${dataToExtractValue.name}");
	}
!{/for}	

!{for method : methodsToImplement}
	@Override
	public ${method.methodSignature}{
	
		pause(Duration.ofMillis(${method.beforePause}L));

!{for action : method.actions}
		RetryImpl.prepareRetry(getDriver(),RetryConfigType.${action.retryConfig.retryConfigType}, ${action.retryConfig.nrOfTries}, ${action.retryConfig.intervalInMs} ${action.retryConfig.getRetryCauseClassNames}).execute( () -> {
		${action.generateCode}
		});
!{/for}
!{if method.returnsPageObject}	
		return new ${method.getNextImplClassName}(getDriver()).pause(Duration.ofMillis(${method.afterPause}L));
!{else}
		return this;	
!{/if}
	}
!{/for}	

}
