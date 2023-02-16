package common.element;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;

/*
 * 测试策略：
 *    测试Timeslot的getter方法和equals方法
 */
public class TimeslotTest {

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	/*
	 *  testing strategy：
	 *  	测试了Timeslot类的各种get方法。
	 */
	@Test
	public void testGetters() {
		LocalDateTime start = LocalDateTime.of(2020, 5, 13, 14, 5);
		LocalDateTime end = LocalDateTime.of(2020, 5, 13, 15, 7);
		Timeslot timeslot1 = new Timeslot(start, end);
		Timeslot timeslot2 = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 15, 7);
		assertEquals(start, timeslot1.getStartTime());
		assertEquals(end, timeslot1.getEndTime());
		assertEquals(start, timeslot2.getStartTime());
		assertEquals(end, timeslot2.getEndTime());
	}

	/*
	 *  testing strategy：
	 *  	测试了Timeslot类的equals方法。
	 */
	@Test
	public void testEquals() {
		LocalDateTime start = LocalDateTime.of(2020, 5, 13, 14, 5);
		LocalDateTime end = LocalDateTime.of(2020, 5, 13, 15, 7);
		Timeslot timeslot1 = new Timeslot(start, end);
		Timeslot timeslot2 = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 15, 7);
		assertEquals(true, timeslot1.equals(timeslot2));
	}
	
}
