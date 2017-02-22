package translations.domain;

public class Query {

	private String translationFile;
	private QueryType type;
	private String queryString;

	public Query() {
		type = QueryType.NORMAL;
	}

	public String getType() {
		return type.toString();
	}

	public void setType(String type) {
		this.type = QueryType.valueOf(type);
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String query) {
		this.queryString = query;
	}

	public boolean isRegularLookup() {
		return QueryType.NORMAL == type;
	}

	public String getTranslationFile() {
		return translationFile;
	}

	public void setTranslationFile(String translationFile) {
		this.translationFile = translationFile;
	}

}
