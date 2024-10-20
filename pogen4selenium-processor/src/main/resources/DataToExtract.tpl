package ${ packageName };

!{for import : imports}
import ${import};
!{/for}
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import io.toolisticon.pogen4selenium.api.By;
import io.toolisticon.pogen4selenium.api.ExtractDataValue;
import io.toolisticon.pogen4selenium.runtime.DataObjectParentImpl;

/**
 * An empty class.
 */
public class ${ toImplementHelper.implementationClassName }  extends DataObjectParentImpl implements ${toImplementHelper.interfaceName}{
	
	/** 
	 * Constructor
	 */
	public ${ toImplementHelper.implementationClassName } (WebElement relativeContentRoot) {
		super(relativeContentRoot);    	
	}
		
	// implement methods
!{for dataToExtractValue : dataToExtract.value}
	@Override
	public String ${dataToExtractValue.methodName}(){
		return getValue(By.${dataToExtractValue.by}, "${dataToExtractValue.value}", ExtractDataValue.Kind.${dataToExtractValue.kind}, "${dataToExtractValue.name}");
	}
!{/for}	

}
