package io.toolisticon.pogen4selenium.runtime.profile;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

public class ProfileTest {

	enum TestProfileKind implements ProfileKind {
		
		APP1("app1"),
		APP2("app2"),
		NONEXISTING("nonexisting");
		
		final String profileKind;

		private TestProfileKind(String profileKind) {
			this.profileKind = profileKind;
		}

		@Override
		public String getProfileKind() {
			return profileKind;
		}
		
		
	} 
	
	enum TestProfileKey implements ProfileKey {
		NAME("name"),
		OVERWRITTEN("overwritten"),
		COMMON("common");
		
		final String profileKey;
		
		private TestProfileKey(String profileKey) {
			this.profileKey = profileKey;
		}

		@Override
		public String getProfileKey() {
			return profileKey;
		}
		
	}
	
	
	@Test
	public void readValueTest_default() {
		
		Profile.reset();
		testEnv("DEV");
		
	}
	
	@Test
	public void readValueTest_dev() {
		
		Profile.reset();
		
		System.setProperty(Profile.PROFILE_PARAM_NAME, "dev");
		testEnv("DEV");
		
	}
	
	@Test
	public void readValueTest_int() {
		
		Profile.reset();
		
		System.setProperty(Profile.PROFILE_PARAM_NAME, "int");
		testEnv("INT");
		
	}
	
	private void testEnv(String env) {
		
		
		MatcherAssert.assertThat(Profile.getProfileValue(TestProfileKey.NAME), Matchers.equalTo("DEF-" + env +"-Name"));
		MatcherAssert.assertThat(Profile.getProfileValue(TestProfileKind.APP1, TestProfileKey.NAME), Matchers.equalTo("APP1-" + env + "-Name"));
		MatcherAssert.assertThat(Profile.getProfileValue(TestProfileKind.APP2, TestProfileKey.NAME), Matchers.equalTo("APP2-" + env + "-Name"));
		
		
	}
	
	@Test(expected = IllegalStateException.class)
	public void readNonexceistingProfile() {
		Profile.getProfileValue(TestProfileKind.NONEXISTING, TestProfileKey.NAME);
	}
	
	@Test
	public void readValueTest_common() {
		
		Profile.reset();
		
		System.setProperty(Profile.PROFILE_PARAM_NAME, "dev");
		
		// Test overwritten property
		MatcherAssert.assertThat(Profile.getProfileValue(TestProfileKey.OVERWRITTEN), Matchers.equalTo("OVERWRITTEN"));
		
		// Test common only property
		MatcherAssert.assertThat(Profile.getProfileValue(TestProfileKey.COMMON), Matchers.equalTo("COMMON"));
		
	}
	
}
