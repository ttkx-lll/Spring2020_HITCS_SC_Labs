package common.element;

import static org.junit.Assert.*;

import org.junit.Test;

/*
 * 测试策略：
 *    测试Location的getter方法和equals方法
 */
public class LocationTest {

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	/*
	 *  testing strategy：
	 *  	测试了Location类的各种get方法。
	 */
	@Test
	public void testGetters() {
		Location location1 = new Location("正心23", false);
		Location location2 = new Location(56, 78, "shanghai", true);
		assertEquals("正心23", location1.getLocName());
		assertEquals(0, location1.getLongitude(), 0.01);
		assertEquals(0, location1.getLatitude(), 0.01);
		assertEquals(false, location1.isShareable());
		assertEquals("shanghai", location2.getLocName());
		assertEquals(56, location2.getLongitude(), 0.01);
		assertEquals(78, location2.getLatitude(), 0.01);
		assertEquals(true, location2.isShareable());
	}

	/*
	 *  testing strategy：
	 *  	测试了Location类的Equals方法。
	 */
	@Test
	public void testEquals() {
		Location location1 = new Location("正心23", false);
		Location location2 = new Location("正心23", false);
		Location location3 = new Location("正心22", false);
		Location location4 = new Location("正心23", true);
		assertEquals(true, location1.equals(location2));
		assertEquals(false, location1.equals(location3));
		assertEquals(false, location1.equals(location4));
		Location location5 = new Location(56, 78, "shanghai", true);
		Location location6 = new Location(56, 78, "shanghai", true);
		Location location7 = new Location(89, 78, "shanghai", true);
		Location location8 = new Location(56, 45, "shanghai", true);
		assertEquals(true, location5.equals(location6));
		assertEquals(false, location5.equals(location7));
		assertEquals(false, location5.equals(location8));
	}
}
