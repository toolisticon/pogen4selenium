package io.toolisticon.pogen4selenium.runtime.profile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * This class helps to handle properties for different kinds of applications and environments.
 * This can be used for example to access the environments base url.
 */
public class Profile {

	private static final Map<String, Profile> profileMap = new HashMap<>();
	public final static String PROFILE_PARAM_NAME = "test.profile";
	public final static String DEFAULT_PROFILE = "dev";
	
	private final Properties profileProperties = new Properties();
	private final Properties commonProfileProperties = new Properties();
	
	private Profile (String profileKind) {
		
		final String PROFILE_PATH = "/profiles/" + profileKind + "/" + System.getProperty(PROFILE_PARAM_NAME, DEFAULT_PROFILE) + ".properties";
		final String COMMON_PROFILE_PATH = "/profiles/" + profileKind + "/common.properties";
		
		try {
			profileProperties.load(Profile.class.getResourceAsStream( PROFILE_PATH ));
			
		} catch (IOException|NullPointerException e) {
			throw new IllegalStateException("Can't read profile properties : " + PROFILE_PATH);
		}
		
		try {
			commonProfileProperties.load(Profile.class.getResourceAsStream( COMMON_PROFILE_PATH ));
			
		} catch (IOException|NullPointerException e) {
			// Just ignore - common file is optional
		}
		
		
	}
	
	

	private String readValue (ProfileKey profileKey) {
		
		// first try environment specific properties
		String value = this.profileProperties.getProperty(profileKey.getProfileKey());
		
		// then use common properties as fallback, if value isn't found
		if (value == null) {
			value = this.commonProfileProperties.getProperty(profileKey.getProfileKey());
		}
		
		return value != null ? value.trim() : null;
		
	}
	

	/**
	 * Gets a property from a specific profile
	 * @param profileKind the profile kind
	 * @param profileKey the key to read from profile
	 * @return The value of for the key or null if it can't be found
	 */
	public static synchronized String getProfileValue(ProfileKind profileKind, ProfileKey profileKey) {
		return getProfileValue(profileKind.getProfileKind(), profileKey);
	}
	
	/**
	 * Gets a property from 'default' profile
	 * @param profileKey the key to read from profile
	 * @return The value of for the key or null if it can't be found
	 */
	public static String getProfileValue(ProfileKey profileKey) {
		
		return getProfileValue("default", profileKey);
		
	}
	
	private static synchronized String getProfileValue(String profileKind, ProfileKey profileKey) {
		Profile profile = profileMap.computeIfAbsent(profileKind, (e1) -> {
			return new Profile(e1);
		});
		
		return profile.readValue(profileKey);
	}
	
	/**
	 * Resets, the loaded profiles.
	 * Normally, there shouldn't been any need to use this.
	 */
	public static void reset() {
		profileMap.clear();
	}
	
}
