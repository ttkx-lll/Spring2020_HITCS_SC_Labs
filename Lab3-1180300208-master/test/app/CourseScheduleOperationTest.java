package app;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import common.element.Location;
import common.element.Timeslot;
import common.operation.PlanningEntry;
import source.Teacher;

/*
 * 测试策略：
 * 	  仅测试了在CourseScheduleOperation类中独特出现的方法，为使代码简洁，委派给其他对象的方法已经在原对象类中进行过测试。
 */
public class CourseScheduleOperationTest {

	private Teacher t1 = new Teacher("123", "Tom", "male", "professor");
	private Teacher t2 = new Teacher("234", "Bob", "male", "professor");
	private Teacher t3 = new Teacher("345", "Kitty", "female", "professor");
	private Location l1 = new Location("asdf", false);
	private Location l2 = new Location("sdfg", false);
	private Location l3 = new Location("dfgh", false);
	private Timeslot time1 = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 15, 7);
	private Timeslot time2 = new Timeslot(2020, 5, 13, 14, 20, 2020, 5, 13, 14, 30);
	private Timeslot time3 = new Timeslot(2020, 5, 13, 14, 40, 2020, 5, 13, 14, 50);
	
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testAddResource() {
		CourseScheduleOperation cso = new CourseScheduleOperation();
		assertEquals(true, cso.addResource(t1));
		assertEquals(false, cso.addResource(t1));
	}
	
	@Test
	public void testDelResource() {
		CourseScheduleOperation cso = new CourseScheduleOperation();
		assertEquals(false, cso.delResource(t1));
		cso.addResource(t1);
		assertEquals(true, cso.delResource(t1));
	}
	
	@Test
	public void testGetResourceSet() {
		CourseScheduleOperation cso = new CourseScheduleOperation();
		cso.addResource(t1); cso.addResource(t2); cso.addResource(t3);
		Set<Teacher> set = new HashSet<Teacher>();
		set.add(t1); set.add(t2); set.add(t3);
		assertEquals(set, cso.getResourceSet());
	}
	
	@Test
	public void testAddLocation() {
		CourseScheduleOperation cso = new CourseScheduleOperation();
		assertEquals(true, cso.addLocation(l1));
		assertEquals(false, cso.addLocation(l1));
	}
	
	@Test
	public void testDelLocation() {
		CourseScheduleOperation cso = new CourseScheduleOperation();
		assertEquals(false, cso.delLocation(l1));
		cso.addLocation(l1);
		assertEquals(true, cso.delLocation(l1));
	}
	
	@Test
	public void testGetLocationSet() {
		CourseScheduleOperation cso = new CourseScheduleOperation();
		cso.addLocation(l1); cso.addLocation(l2); cso.addLocation(l3);
		Set<Location> set = new HashSet<Location>();
		set.add(l1); set.add(l2); set.add(l3);
		assertEquals(set, cso.getLocationSet());
	}
	
	@Test
	public void testChangeLocation() {
		CourseScheduleOperation cso = new CourseScheduleOperation();
		cso.addResource(t1); cso.addResource(t2);
		cso.addLocation(l1); cso.addLocation(l2);
		cso.addPlan("1", l1, time1);
		cso.addPlan("2", l2, time2);
		assertEquals(false, cso.changeLocation("1", l3));
		assertEquals(true, cso.changeLocation("1", l2));
		assertEquals(l2, cso.getPlan("1").getLocation());
		assertEquals(false, cso.changeLocation("4", l2));
	}
	
	@Test
	public void testAddPlan() {
		CourseScheduleOperation cso = new CourseScheduleOperation();
		cso.addResource(t1); cso.addResource(t2);
		cso.addLocation(l1); cso.addLocation(l2);
		assertEquals(false, cso.addPlan("1", l3, time1));
		assertEquals(true, cso.addPlan("1", l1, time1));
		assertEquals(false, cso.addPlan("1", l2, time1));
	}
	
	@Test
	public void testDistributeResource() {
		CourseScheduleOperation cso = new CourseScheduleOperation();
		cso.addResource(t1); cso.addResource(t2);
		cso.addLocation(l1); cso.addLocation(l2);
		cso.addPlan("1", l1, time1);
		cso.addPlan("2", l2, time2);
		assertEquals(false, cso.distributeResource("3", t1));
		assertEquals(false, cso.distributeResource("1", t3));
		assertEquals(true, cso.distributeResource("1", t1));
	}
	
	@Test
	public void testCheckLocationAndResourseConflict() {
		CourseScheduleOperation cso = new CourseScheduleOperation();
		cso.addResource(t1); cso.addResource(t2); cso.addResource(t3);
		cso.addLocation(l1); cso.addLocation(l2); cso.addLocation(l3);
		cso.addPlan("1", l1, time1);
		cso.addPlan("2", l2, time2);
		cso.addPlan("3", l3, time3);
		cso.distributeResource("1", t1);
		cso.distributeResource("2", t2);
		cso.distributeResource("3", t3);
		assertEquals(false, cso.checkLocationAndResourseConflict());
		cso.distributeResource("2", t1);
		assertEquals(true, cso.checkLocationAndResourseConflict());
	}
	
	@Test
	public void testGetAllPlanWithResource() {
		CourseScheduleOperation cso = new CourseScheduleOperation();
		cso.addResource(t1); cso.addResource(t2); cso.addResource(t3);
		cso.addLocation(l1); cso.addLocation(l2); cso.addLocation(l3);
		cso.addPlan("1", l1, time1);
		cso.addPlan("2", l2, time2);
		cso.addPlan("3", l3, time3);
		cso.distributeResource("1", t1);
		cso.distributeResource("2", t1);
		cso.distributeResource("3", t3);
		List<PlanningEntry<Teacher>> list = new ArrayList<>();
		list.add(cso.getPlan("1"));
		list.add(cso.getPlan("2"));
		assertEquals(list, cso.getAllPlanWithResource(t1));
	}
}
