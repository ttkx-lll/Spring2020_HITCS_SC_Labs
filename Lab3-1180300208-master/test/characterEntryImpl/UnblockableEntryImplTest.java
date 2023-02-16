package characterEntryImpl;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;

import common.element.Timeslot;

/*
 * 测试策略：
 *    测试有关可不阻塞计划项的时间的各种方法。
 */
public class UnblockableEntryImplTest {

	private static Timeslot timeslot = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 15, 7);
	
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}
	
	@Test
	public void testGetStartTime() {
		LocalDateTime start = LocalDateTime.of(2020, 5, 13, 14, 5);
		UnblockableEntryImpl ue = new UnblockableEntryImpl(timeslot);
		assertEquals(start, ue.getStartTime());
	}
	
	@Test
	public void testGetEndTime() {
		LocalDateTime end = LocalDateTime.of(2020, 5, 13, 15, 7);
		UnblockableEntryImpl ue = new UnblockableEntryImpl(timeslot);
		assertEquals(end, ue.getEndTime());
	}
}
