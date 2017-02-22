package translations.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Translation {

	private static final String SEPARATOR = ".";

	private Map<String, String> map;
	private Map<String, List<String>> reverseMap;

	public Translation(Map<String, Object> jsonMap) throws BadJsonException {
		map = new HashMap<String, String>();
		reverseMap = new HashMap<String, List<String>>();

		for (Map.Entry<String, Object> entry : jsonMap.entrySet()) {
			add(entry.getKey(), entry);
		}
	}

	private void add(String label, Map.Entry<String, Object> entry) throws BadJsonException {
		if (entry.getValue() instanceof String) {
			map.put(label, (String) entry.getValue());
			addReverse(label, (String) entry.getValue());

		} else if (entry.getValue() instanceof Map<?, ?>) {
			Map<String, Object> subMap = (Map<String, Object>) entry.getValue();

			for (Map.Entry<String, Object> subEntry : subMap.entrySet()) {
				String nestedLabel = label + SEPARATOR + subEntry.getKey();
				add(nestedLabel, subEntry);
			}
		} else {
			throw new BadJsonException("JSON file contains value of bad type: " + entry.getClass().getSimpleName());
		}
	}

	private void addReverse(String label, String message) {
		if (!reverseMap.containsKey(message)) {
			reverseMap.put(message, new ArrayList<String>());
		}
		reverseMap.get(message).add(label);
	}

	public String getMessage(String label) {
		if (map.containsKey(label)) {
			return map.get(label);
		}
		return null;
	}

	public boolean containsMessage(String message) {
		return reverseMap.containsKey(message);
	}

	public List<String> getLabels(String message) {
		if (reverseMap.containsKey(message)) {
			return reverseMap.get(message);
		}
		return null;
	}

}
