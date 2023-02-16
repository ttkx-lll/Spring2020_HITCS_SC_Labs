package applicationEntry;

import static org.junit.Assert.*;


import org.junit.Test;

import common.element.Location;
import common.element.Timeslot;
import source.Teacher;

/*
 * 测试策略：
 * 	  仅测试了在ConrseEntry类中独特出现的方法，其他委派给其他对象的方法已经在原对象类中进行过测试。
 */
public class CourseEntryTest {

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testGetPresentStateName() {
		String name = "English";
		Location loc = new Location("正心23", false);
		Timeslot timeslot = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 15, 7);
		CourseEntry ce = new CourseFactory().getEntry(name, loc, timeslot);
		Teacher teacher = new Teacher("123456", "张三", "男", "副教授");
		assertEquals("未安排教师", ce.getPresentStateName());
		ce.setResource(teacher);
		assertEquals("已安排教师", ce.getPresentStateName());
		ce.start();
		assertEquals("上课中", ce.getPresentStateName());
		ce.end();
		assertEquals("已下课", ce.getPresentStateName());
		ce = new CourseFactory().getEntry(name, loc, timeslot);
		ce.cancel();
		assertEquals("已取消", ce.getPresentStateName());
	}
	
	@Test
	public void testGetPlanningName() {
		String name = "English";
		Location loc = new Location("正心23", false);
		Timeslot timeslot = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 15, 7);
		CourseEntry ce = new CourseFactory().getEntry(name, loc, timeslot);
		assertEquals(name, ce.getPlanningName());
	}
	
	@Test
	public void testCompareTo() {
		String name = "English";
		String name2 = "Chinese";
		Location loc = new Location("正心23", false);
		Location loc2 = new Location("正心21", false);
		Timeslot timeslot = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 15, 7);
		Timeslot timeslot2 = new Timeslot(2020, 5, 13, 15, 5, 2020, 5, 13, 15, 7);
		CourseEntry ce = new CourseFactory().getEntry(name, loc, timeslot);
		CourseEntry ce2 = new CourseFactory().getEntry(name2, loc2, timeslot2);
		assertEquals(-1, ce.compareTo(ce2));
	}
}
