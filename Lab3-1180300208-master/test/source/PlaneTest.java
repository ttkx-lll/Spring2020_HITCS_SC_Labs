package source;

import static org.junit.Assert.*;

import org.junit.Test;

/*
 * 测试策略：
 *    测试Plane的getter方法和equals方法
 */
public class PlaneTest {

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testGetters() {
		Plane plane = new Plane("dc123", "c919", 301, (float) 20.5);
		assertEquals("dc123", plane.getPlaneID());
		assertEquals("c919", plane.getPlaneType());
		assertEquals(301, plane.getSeatNumber());
		assertEquals((float) 20.5, plane.getPlaneAge(), 0.01);
	}
	
	@Test
	public void testEquals() {
		Plane plane1 = new Plane("dc123", "c919", 301, (float) 20.5);
		Plane plane2 = new Plane("dc123", "c919", 301, (float) 20.5);
		Plane plane3 = new Plane("dc456", "c919", 301, (float) 20.5);
		Plane plane4 = new Plane("dc123", "波音737", 301, (float) 20.5);
		Plane plane5 = new Plane("dc123", "c919", 321, (float) 20.5);
		Plane plane6 = new Plane("dc123", "c919", 301, (float) 19.5);
		assertEquals(true, plane1.equals(plane2));
		assertEquals(false, plane1.equals(plane3));
		assertEquals(false, plane1.equals(plane4));
		assertEquals(false, plane1.equals(plane5));
		assertEquals(false, plane1.equals(plane6));
	}
}
