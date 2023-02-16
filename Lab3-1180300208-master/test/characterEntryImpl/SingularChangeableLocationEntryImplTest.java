package characterEntryImpl;

import static org.junit.Assert.*;

import org.junit.Test;

import common.element.Location;

/*
 * 测试策略：
 *    测试有关单一可变地点计划项的地点的各种方法。
 */
public class SingularChangeableLocationEntryImplTest {

	private Location loc = new Location("loc", true);
	
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}
	
	@Test
	public void testGetLocation() {
		SingularChangeableLocationEntryImpl scle = new SingularChangeableLocationEntryImpl(loc);
		Location l = new Location("loc", true);
		assertEquals(l, scle.getLocation());
	}
	
	@Test
	public void testChangeLocation() {
		SingularChangeableLocationEntryImpl scle = new SingularChangeableLocationEntryImpl(loc);
		Location l = new Location("loc2", true);
		scle.changeLocation(l);
		assertEquals(l, scle.getLocation());
	}
	
}
