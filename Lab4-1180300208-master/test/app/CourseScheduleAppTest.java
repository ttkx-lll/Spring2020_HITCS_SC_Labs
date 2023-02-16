package app;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import common.element.Location;
import common.element.Timeslot;
import common.operation.PlanningEntry;
import resource.Teacher;

public class CourseScheduleAppTest {
	private Teacher t1 = new Teacher("123", "Tom", "male", "professor");
	private Teacher t2 = new Teacher("234", "Bob", "male", "professor");
	private Teacher t3 = new Teacher("345", "Kitty", "female", "professor");
	private Location l1 = new Location("asdf", false);
	private Location l2 = new Location("sdfg", false);
	private Location l3 = new Location("dfgh", false);
	private Timeslot time1 = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 14, 6);
	private Timeslot time2 = new Timeslot(2020, 5, 13, 14, 20, 2020, 5, 13, 14, 30);
	private Timeslot time3 = new Timeslot(2020, 5, 13, 14, 40, 2020, 5, 13, 14, 50);
	private Timeslot time4 = new Timeslot(2020, 5, 13, 14, 4, 2020, 5, 13, 15, 6);
	
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	/*
	 *  testing strategy:
	 *  	测试了资源set中已经存在该资源和不存在该资源的情况。
	 */
	@Test
	public void testAddResource() {
		CourseScheduleApp csa = new CourseScheduleApp();
		assertEquals(true, csa.addResource(t1));
		assertEquals(false, csa.addResource(t1));
	}
	
	/*
	 *  testing strategy:
	 *  	测试了资源set中已经存在该资源和不存在该资源的情况。
	 */
	@Test
	public void testDelResource() {
		CourseScheduleApp csa = new CourseScheduleApp();
		assertEquals(false, csa.delResource(t1));
		csa.addResource(t1);
		assertEquals(true, csa.delResource(t1));
	}

	/*
	 *  testing strategy：
	 *  	测试了位置set中已经存在该位置和不存在该位置的情况。
	 */
	@Test
	public void testAddLocation() {
		CourseScheduleApp csa = new CourseScheduleApp();
		assertEquals(true, csa.addLocation(l1));
		assertEquals(false, csa.addLocation(l1));
	}

	/*
	 *  testing strategy：
	 *  	测试了位置set中已经存在该位置和不存在该位置的情况。
	 */
	@Test
	public void testDelLocation() {
		CourseScheduleApp csa = new CourseScheduleApp();
		assertEquals(false, csa.delLocation(l1));
		csa.addLocation(l1);
		assertEquals(true, csa.delLocation(l1));
	}
	
	/*
	 *  testing strategy：
	 *  	考虑了要改变的位置在/不在位置列表中，计划项存在/不存在的情况
	 */
	@Test
	public void testChangeLocation() {
		CourseScheduleApp csa = new CourseScheduleApp();
		csa.addResource(t1); csa.addResource(t2);
		csa.addLocation(l1); csa.addLocation(l2);
		csa.addPlan("1", l1, time1);
		csa.addPlan("2", l2, time2);
		assertEquals(false, csa.changeLocation("1", l3));
		assertEquals(true, csa.changeLocation("1", l2));
		assertEquals(l2, csa.getPlan("1").getLocation());
		assertEquals(false, csa.changeLocation("4", l2));
	}
	
	/*
	 *  testing strategy：
	 *  	测试了要添加的计划项要用到的位置存在/不存在，名称重复/不重复的情况。
	 */
	@Test
	public void testAddPlan() {
		CourseScheduleApp csa = new CourseScheduleApp();
		csa.addResource(t1); csa.addResource(t2);
		csa.addLocation(l1); csa.addLocation(l2);
		assertEquals(false, csa.addPlan("1", l3, time1));
		assertEquals(true, csa.addPlan("1", l1, time1));
		assertEquals(false, csa.addPlan("1", l2, time1));
	}
	
	/*
	 *  testing strategy：
	 *  	测试了资源存在/不存在和计划项存在/不存在的情况
	 */
	@Test
	public void testDistributeResource() {
		CourseScheduleApp csa = new CourseScheduleApp();
		csa.addResource(t1); csa.addResource(t2);
		csa.addLocation(l1); csa.addLocation(l2);
		csa.addPlan("1", l1, time1);
		csa.addPlan("2", l2, time2);
		assertEquals(false, csa.distributeResource("3", t1));
		assertEquals(false, csa.distributeResource("1", t3));
		assertEquals(true, csa.distributeResource("1", t1));
	}
	
	/*
	 *  testing strategy：
	 *  	测试了各种冲突情况，由于当添加时如果有冲突会抛出异常，因此测试结果应该永远为false
	 */
	@Test
	public void testCheckLocationAndResourseConflict() {
		CourseScheduleApp csa = new CourseScheduleApp();
		csa.addResource(t1); csa.addResource(t2); csa.addResource(t3);
		csa.addLocation(l1); csa.addLocation(l2); csa.addLocation(l3);
		csa.addPlan("1", l1, time1);
		csa.addPlan("2", l2, time2);
		csa.addPlan("3", l3, time4);
		csa.distributeResource("1", t1);
		csa.distributeResource("2", t2);
		csa.distributeResource("3", t3);
		assertEquals(false, csa.checkLocationAndResourseConflict());
		csa.distributeResource("3", t1);
		assertEquals(false, csa.checkLocationAndResourseConflict());
	}
	
	/*
	 *  testing strategy：
	 *  	测试了资源占用相同资源的计划项列表。
	 */
	@Test
	public void testGetAllPlanWithResource() {
		CourseScheduleApp csa = new CourseScheduleApp();
		csa.addResource(t1); csa.addResource(t2); csa.addResource(t3);
		csa.addLocation(l1); csa.addLocation(l2); csa.addLocation(l3);
		csa.addPlan("1", l1, time1);
		csa.addPlan("2", l2, time2);
		csa.addPlan("3", l3, time3);
		csa.distributeResource("1", t1);
		csa.distributeResource("2", t1);
		csa.distributeResource("3", t3);
		List<PlanningEntry<Teacher>> list = new ArrayList<>();
		list.add(csa.getPlan("1"));
		list.add(csa.getPlan("2"));
		assertEquals(list, csa.getAllPlanWithResource(t1));
	}

}
