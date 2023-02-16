package characterEntryImpl;

import static org.junit.Assert.*;

import org.junit.Test;

import common.element.Location;

/*
 * 测试策略：
 *    测试有关双地点计划项的地点的各种方法。
 */
public class DoubleLocationEntryImplTest {

	private Location start = new Location("123", true);
	private Location end = new Location("456", true);
	
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}
	
	/*
	 *  testing strategy：
	 *  	测试了取得起始位置的方法。
	 */
	@Test
	public void testGetStartLoc() {
		DoubleLocationEntryImpl dle = new DoubleLocationEntryImpl(start, end);
		Location s = new Location("123", true);
		assertEquals(s, dle.getStartLoc());
	}
	
	/*
	 *  testing strategy：
	 *  	测试了取得终点位置的方法。
	 */
	@Test
	public void testGetEndLoc() {
		DoubleLocationEntryImpl dle = new DoubleLocationEntryImpl(start, end);
		Location e = new Location("456", true);
		assertEquals(e, dle.getEndLoc());
	}
}
