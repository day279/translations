package translations.domain;

public enum QueryType {
	NORMAL("Normal"), REVERSE("Reverse");
	
	private String displayText;
	
	QueryType(String displayText) {
		this.displayText = displayText;
	}
	
	public String getDisplayText() {
		return this.displayText;
	}
}
