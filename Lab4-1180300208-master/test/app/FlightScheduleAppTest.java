package app;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import common.element.Location;
import common.element.Timeslot;
import common.operation.PlanningEntry;
import resource.Plane;

public class FlightScheduleAppTest {
	
	private Plane p1 = new Plane("123", "asdf", 200, (float) 2.5);
	private Plane p2 = new Plane("234", "sdfg", 201, (float) 3.5);
	private Plane p3 = new Plane("345", "dfgh", 202, (float) 4.5);
	private Location l1 = new Location("asdf", false);
	private Location l2 = new Location("sdfg", false);
	private Location l3 = new Location("dfgh", false);
	private Timeslot time1 = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 15, 7);
	private Timeslot time2 = new Timeslot(2020, 5, 13, 14, 20, 2020, 5, 13, 14, 30);
	private Timeslot time3 = new Timeslot(2020, 5, 13, 14, 40, 2020, 5, 13, 14, 50);
	private Timeslot time4 = new Timeslot(2020, 5, 13, 14, 55, 2020, 5, 13, 15, 50);
	
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	/*
	 *  testing strategy：
	 *  	测试了资源set中已经存在该资源和不存在该资源的情况。
	 */
	@Test
	public void testAddResource() {
		FlightScheduleApp fsa = new FlightScheduleApp();
		assertEquals(true, fsa.addResource(p1));
		assertEquals(false, fsa.addResource(p1));
	}
	
	/*
	 *  testing strategy：
	 *  	测试了资源set中已经存在该资源和不存在该资源的情况。
	 */
	@Test
	public void testDelResource() {
		FlightScheduleApp fsa = new FlightScheduleApp();
		assertEquals(false, fsa.delResource(p1));
		fsa.addResource(p1);
		assertEquals(true, fsa.delResource(p1));
	}
	
	/*
	 *  testing strategy：
	 *  	测试了位置set中已经存在该位置和不存在该位置的情况。
	 */
	@Test
	public void testAddLocation() {
		FlightScheduleApp fsa = new FlightScheduleApp();
		assertEquals(true, fsa.addLocation(l1));
		assertEquals(false, fsa.addLocation(l1));
	}
	
	/*
	 *  testing strategy：
	 *  	测试了位置set中已经存在该位置和不存在该位置的情况。
	 */
	@Test
	public void testDelLocation() {
		FlightScheduleApp fsa = new FlightScheduleApp();
		assertEquals(false, fsa.delLocation(l1));
		fsa.addLocation(l1);
		assertEquals(true, fsa.delLocation(l1));
	}
	
	/*
	 *  testing strategy：
	 *  	测试了有/没有位置、同一航班号时间相同、不相同的情况
	 */
	@Test
	public void testAddPlan() {
		FlightScheduleApp fsa = new FlightScheduleApp();
		Timeslot t1 = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 14, 10);
		Timeslot t2 = new Timeslot(2020, 5, 14, 14, 5, 2020, 5, 14, 14, 10);
		Timeslot t3 = new Timeslot(2020, 5, 13, 14, 40, 2020, 5, 13, 14, 50);
		fsa.addLocation(l1); fsa.addLocation(l2);
		assertEquals(false, fsa.addPlan("AS120", l1, l3, t1));
		assertEquals(false, fsa.addPlan("AS120", l3, l1, t1));
		assertEquals(true, fsa.addPlan("AS120", l1, l2, t1));
		assertEquals(true, fsa.addPlan("AS0120", l1, l2, t2));
		assertEquals(false, fsa.addPlan("AS0120", l1, l2, t1));
		assertEquals(false, fsa.addPlan("AS0120", l1, l2, t3));
	}
	
	/*
	 *  testing strategy：
	 *  	测试了存在/不存在资源、存在/不存在计划项的情况。
	 */
	@Test
	public void testDistributeResource() {
		FlightScheduleApp fsa = new FlightScheduleApp();
		Timeslot t1 = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 14, 10);
		Timeslot t2 = new Timeslot(2020, 5, 14, 14, 5, 2020, 5, 14, 14, 10);
		Timeslot t3 = new Timeslot(2020, 5, 13, 14, 40, 2020, 5, 13, 14, 50);
		fsa.addLocation(l1); fsa.addLocation(l2);
		fsa.addPlan("AS120", l1, l2, t1);
		fsa.addPlan("AS120", l1, l2, t2);
		fsa.addResource(p1); fsa.addResource(p2);
		assertEquals(false, fsa.distributeResource("AS120", t1, p3));
		assertEquals(true, fsa.distributeResource("AS120", t1, p2));
		assertEquals(false, fsa.distributeResource("1230", t1, p3));
		assertEquals(false, fsa.distributeResource("AS120", t3, p3));
	}
	
	/*
	 *  testing strategy：
	 *  	测试了各种冲突情况，由于当添加时如果有冲突会抛出异常，因此测试结果应该永远为false
	 */
	@Test
	public void testCheckLocationAndResourseConflict() {
		FlightScheduleApp fsa = new FlightScheduleApp();
		fsa.addLocation(l1); fsa.addLocation(l2); fsa.addLocation(l3);
		fsa.addResource(p1); fsa.addResource(p2); fsa.addResource(p3);
		fsa.addPlan("AS001", l1, l2, time1);
		fsa.addPlan("AS002", l2, l3, time2);
		fsa.addPlan("AS003", l1, l3, time3);
		fsa.distributeResource("AS001", time1, p1);
		fsa.distributeResource("AS002", time2, p2);
		fsa.distributeResource("AS003", time3, p3);
		assertEquals(false, fsa.checkLocationAndResourseConflict());
		fsa.distributeResource("AS002", time2, p1);
		assertEquals(false, fsa.checkLocationAndResourseConflict());
	}
	
	/*
	 *  testing strategy：
	 *  	测试了资源占用相同资源的计划项列表。
	 */
	@Test
	public void testGetAllPlanWithResource() {
		FlightScheduleApp fsa = new FlightScheduleApp();
		fsa.addLocation(l1); fsa.addLocation(l2); fsa.addLocation(l3);
		fsa.addResource(p1); fsa.addResource(p2); fsa.addResource(p3);
		fsa.addPlan("AS001", l1, l2, time4);
		fsa.addPlan("AS002", l2, l3, time2);
		fsa.addPlan("AS003", l1, l3, time3);
		fsa.distributeResource("AS001", time4, p1);
		fsa.distributeResource("AS002", time2, p1);
		fsa.distributeResource("AS003", time3, p3);
		List<PlanningEntry<Plane>> list = new ArrayList<>();
		list.add(fsa.getPlan("AS002", time2));
		list.add(fsa.getPlan("AS001", time4));
		assertEquals(list, fsa.getAllPlanWithResource(p1));
	}
}
