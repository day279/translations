package translations.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class QueryTest {

	@Test
	public void testRegularQuery() {
		String lookup = "query string";
		Query q = new Query();
		q.setQueryString(lookup);
		q.setType("NORMAL");
		
		assertTrue(q.isRegularLookup());
		assertEquals(lookup, q.getQueryString());
	}

	@Test
	public void testReverseQuery() {
		String lookup = "query string";
		Query q = new Query();
		q.setQueryString(lookup);
		q.setType("REVERSE");
		
		assertFalse(q.isRegularLookup());
		assertEquals(lookup, q.getQueryString());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testInvalidQueryType() {
		String lookup = "query string";
		Query q = new Query();
		q.setQueryString(lookup);
		q.setType("WRONG");
	}

}
