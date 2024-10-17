package io.toolisticon.pogen4selenium.api;

public enum By {
	ID("id"),
	XPATH("xpath");
	
	private final String correspondingByMethodName;
	
	By(String correspondingByMethodName) {
		this.correspondingByMethodName = correspondingByMethodName;
	}

	public String getCorrespondingByMethodName() {
		return correspondingByMethodName;
	}
	
}
