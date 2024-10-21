package io.toolisticon.pogen4selenium.api;

public enum By {
	ID("id"),
	XPATH("xpath"),
	/** ELEMENT MUST ONLY BE USED TO EXTRACT DATA IN PAGE OBJECTS */
	ELEMENT("");
	
	private final String correspondingByMethodName;
	
	By(String correspondingByMethodName) {
		this.correspondingByMethodName = correspondingByMethodName;
	}

	public String getCorrespondingByMethodName() {
		return correspondingByMethodName;
	}
	
}
