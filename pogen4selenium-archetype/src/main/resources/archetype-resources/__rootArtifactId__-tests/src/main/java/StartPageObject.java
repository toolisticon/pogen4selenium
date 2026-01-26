#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.openqa.selenium.WebDriver;

import io.toolisticon.pogen4selenium.api.PageObject;
import io.toolisticon.pogen4selenium.api.PageObjectParent;
import io.toolisticon.pogen4selenium.runtime.profile.Profile;


@PageObject
public interface StartPageObject extends PageObjectParent<StartPageObject>{

    
    static StartPageObject init(WebDriver driver) {
        driver.get(Profile.getProfileValue(ProfileKeys.URL));
		return new StartPageObjectImpl(driver);
    }

}
