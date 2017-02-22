package translations.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class TranslationTest {

	private Map<String, Object> jsonMap;
	private Translation t;

	@Before
	public void setUp() {
		jsonMap = new HashMap<String, Object>();
	}

	@Test
	public void testJsonIsSingleString() throws BadJsonException {
		String key = "key";
		String value = "value";
		jsonMap.put(key, value);

		t = new Translation(jsonMap);

		assertEquals(value, t.getMessage(key));
		assertTrue(t.containsMessage(value));
		assertEquals(1, t.getLabels(value).size());
		assertEquals(key, t.getLabels(value).get(0));
		assertEquals(null, t.getMessage("not key"));
	}

	@Test
	public void testGetMessageWithNestedObject() throws BadJsonException {
		String key1 = "key1", key2 = "key2", value2 = "value2", key3 = "key3";
		Map<String, Object> nested = new HashMap<String, Object>();
		jsonMap.put(key1, nested);
		jsonMap.put(key3, value2);
		nested.put(key2, value2);

		t = new Translation(jsonMap);

		assertEquals(value2, t.getMessage(String.join(".", key1, key2)));
	}

	@Test
	public void testGetLabelsReturnsAllValues() throws BadJsonException {
		String key1 = "key1", key2 = "key2", value2 = "value2", key3 = "key3";
		Map<String, Object> nested = new HashMap<String, Object>();
		jsonMap.put(key1, nested);
		jsonMap.put(key3, value2);
		nested.put(key2, value2);

		t = new Translation(jsonMap);

		assertEquals(2, t.getLabels(value2).size());
		assertTrue(t.getLabels(value2).contains(key3));
		assertTrue(t.getLabels(value2).contains(String.join(".", key1, key2)));
	}
	
	@Test(expected=BadJsonException.class)
	public void testInvalidJsonBlowsUp() throws BadJsonException {
		Map<String, Object> map = new HashMap<String, Object>();
		File notAStringOrMap = new File("");
		map.put("key", notAStringOrMap);
		
		t = new Translation(map);
	}

}
