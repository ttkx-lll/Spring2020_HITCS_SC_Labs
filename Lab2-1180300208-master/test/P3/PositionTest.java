package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class PositionTest {

	/* Testing strategy
     * 对x和y没有特别的要求，只要测试逻辑正确即可
     */
	
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}
	
	@Test
	public void testSetPosition() {
		int x = 1, y = 2;
		Position p = new Position(x, y);
		assertEquals(x, p.getX());
		assertEquals(y, p.getY());
		p.setPosition(3, 4);
		assertEquals(3, p.getX());
		assertEquals(4, p.getY());
		
	}

	@Test
	public void testGetXY() {
		int x = 1, y = 2;
		Position p = new Position(x, y);
		assertEquals(x, p.getX());
		assertEquals(y, p.getY());
	}

	@Test
	public void testGetPosition() {
		int x = 1, y = 2;
		Position p = new Position(x, y);
		assertEquals(true, p.equals(p.getPosition()));
	}

	@Test
	public void testEquals() {
		int x = 1, y = 2, z = 3;
		Position p1 = new Position(x, y);
		Position p2 = new Position(x, y);
		Position p3 = new Position(y, z);
		assertEquals(true, p1.equals(p2));
		assertEquals(false, p1.equals(p3));
	}
	
}
