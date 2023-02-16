package debug;

import static org.junit.Assert.*;

import org.junit.Test;

public class EventManagerTest {

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testBook() {
		
		EventManager em = new EventManager();
		
		assertEquals(1, em.book(1, 10, 20));
		assertEquals(1, em.book(1, 1, 7));
		assertEquals(2, em.book(1, 10, 22));
		assertEquals(3, em.book(1, 5, 15));
		assertEquals(4, em.book(1, 5, 12));
		assertEquals(4, em.book(1, 7, 10));

		assertEquals(1, em.book(2, 10, 20));
		assertEquals(1, em.book(2, 1, 7));
		assertEquals(2, em.book(2, 10, 22));
		assertEquals(3, em.book(2, 5, 15));
		assertEquals(4, em.book(2, 5, 12));
		assertEquals(4, em.book(2, 7, 10));
		assertEquals(5, em.book(2, 7, 15));
	}

}
