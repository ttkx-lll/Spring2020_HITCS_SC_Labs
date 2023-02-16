package applicationEntry;

import static org.junit.Assert.*;

import org.junit.Test;

import common.element.Location;
import common.element.Timeslot;
import source.Plane;

/*
 * 测试策略：
 * 	  仅测试了在FlightEntry类中独特出现的方法，其他委派给其他对象的方法已经在原对象类中进行过测试。
 */
public class FlightEntryTest {

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testGetPresentStateName() {
		String name = "AS150";
		Plane plane = new Plane("s175", "c919", 201, (float) 2.5);
		Location l1 = new Location("1", true);
		Location l2 = new Location("2", true);
		Timeslot timeslot = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 15, 7);
		FlightEntry fe = new FlightFactory().getEntry(name, l1, l2, timeslot);
		assertEquals("未分配飞机", fe.getPresentStateName());
		fe.setResource(plane);
		assertEquals("已分配飞机", fe.getPresentStateName());
		fe.start();
		assertEquals("飞行中", fe.getPresentStateName());
		fe.end();
		assertEquals("已降落", fe.getPresentStateName());
		fe = new FlightFactory().getEntry(name, l1, l2, timeslot);
		fe.cancel();
		assertEquals("已取消", fe.getPresentStateName());
	}
	
	@Test
	public void testGetPlanningName() {
		String name = "AS150";
		Location l1 = new Location("1", true);
		Location l2 = new Location("2", true);
		Timeslot timeslot = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 15, 7);
		FlightEntry fe = new FlightFactory().getEntry(name, l1, l2, timeslot);
		assertEquals(name, fe.getPlanningName());
	}
	
	@Test
	public void testCompareTo() {
		String name = "AS150";
		Location l1 = new Location("1", true);
		Location l2 = new Location("2", true);
		Timeslot timeslot1 = new Timeslot(2020, 5, 13, 12, 5, 2020, 5, 13, 15, 7);
		Timeslot timeslot2 = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 15, 7);
		FlightEntry fe1 = new FlightFactory().getEntry(name, l1, l2, timeslot1);
		FlightEntry fe2 = new FlightFactory().getEntry(name, l1, l2, timeslot2);
		assertEquals(-1, fe1.compareTo(fe2));
	}
}
