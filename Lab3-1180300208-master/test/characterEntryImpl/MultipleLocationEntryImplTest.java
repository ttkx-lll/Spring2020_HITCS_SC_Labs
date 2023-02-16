package characterEntryImpl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import common.element.Location;

/*
 * 测试策略：
 *    测试有关多地点计划项的地点的各种方法。
 */
public class MultipleLocationEntryImplTest {

	private static Location l1 = new Location("1", true);
	private static Location l2 = new Location("2", true);
	private static Location l3 = new Location("3", true);
	private static Location l4 = new Location("4", true);
	private static List<Location> list = new ArrayList<Location>();
	
	@BeforeClass
	public static void before() {
		list.add(l2); list.add(l3);
	}
	
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}
	
	@Test
	public void testGetMidLocations() {
		MultipleLocationEntryImpl mle = new MultipleLocationEntryImpl(l1, l4, list);
		List<Location> locs = new ArrayList<Location>();
		Location loc1 = new Location("2", true);
		Location loc2 = new Location("3", true);
		locs.add(loc1); locs.add(loc2);
		assertEquals(locs, mle.getMidLocations());
	}
	
	@Test
	public void testGetStartLoc() {
		MultipleLocationEntryImpl mle = new MultipleLocationEntryImpl(l1, l4, list);
		Location s = new Location("1", true);
		assertEquals(s, mle.getStartLoc());
	}
	
	@Test
	public void testGetEndLoc() {
		MultipleLocationEntryImpl mle = new MultipleLocationEntryImpl(l1, l4, list);
		Location e = new Location("4", true);
		assertEquals(e, mle.getEndLoc());
	}
	
}
