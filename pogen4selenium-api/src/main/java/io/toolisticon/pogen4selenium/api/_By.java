package io.toolisticon.pogen4selenium.api;

/**
 * Defines the type of locator method to use.
 * Sorry about the name, added '_' up front to prevent a name clash with selenium.
 */
public enum _By {
	ID("id"),
	XPATH("xpath"),
	LINK_TEXT("linkText"),
	PARTIAL_LINK_TEXT("partialLinkText"),
	NAME("name"),
	TAG_NAME("tagName"),
	CLASS_NAME("className"),
	CSS_SELECTOR("cssSelector"),
	/** ELEMENT MUST ONLY BE USED TO EXTRACT DATA IN PAGE OBJECTS */
	ELEMENT("");
	
	private final String correspondingByMethodName;
	
	_By(String correspondingByMethodName) {
		this.correspondingByMethodName = correspondingByMethodName;
	}

	public String getCorrespondingByMethodName() {
		return correspondingByMethodName;
	}
	
}
