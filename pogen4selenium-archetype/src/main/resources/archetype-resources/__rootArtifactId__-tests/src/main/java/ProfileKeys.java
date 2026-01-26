
package ${package};

import io.toolisticon.pogen4selenium.runtime.profile.ProfileKey;

public enum ProfileKeys implements ProfileKey{
    
    URL("url");

    final String profileKey;

    ProfileKeys (String profileKey){
        this.profileKey = profileKey;
    }

    @Override
    public String getProfileKey(){
        return profileKey;
    }

}
