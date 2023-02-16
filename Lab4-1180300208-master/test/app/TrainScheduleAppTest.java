package app;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import common.element.Location;
import common.element.Timeslot;
import common.operation.PlanningEntry;
import resource.Carriage;

public class TrainScheduleAppTest {

	private Carriage c1 = new Carriage("123", "asdf", 100, 2012);
	private Carriage c2 = new Carriage("234", "sdfg", 80, 2011);
	private Carriage c3 = new Carriage("345", "dfgh", 60, 2013);
	private Location l1 = new Location("asdf", false);
	private Location l2 = new Location("sdfg", false);
	private Location l3 = new Location("dfgh", false);
	private Location l4 = new Location("fghj", false);
	private Timeslot time1 = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 15, 7);
	private Timeslot time2 = new Timeslot(2020, 5, 13, 14, 20, 2020, 5, 13, 14, 30);
	private Timeslot time3 = new Timeslot(2020, 5, 13, 14, 40, 2020, 5, 13, 14, 50);
	private Timeslot time4 = new Timeslot(2020, 5, 13, 14, 51, 2020, 5, 13, 15, 7);
	
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
		TrainScheduleApp tsa = new TrainScheduleApp();
		assertEquals(true, tsa.addResource(c1));
		assertEquals(false, tsa.addResource(c1));
		
	}
	
	/*
	 *  testing strategy：
	 *  	测试了资源set中已经存在该资源和不存在该资源的情况。
	 */
	@Test
	public void testDelResource() {
		TrainScheduleApp tsa = new TrainScheduleApp();
		assertEquals(false, tsa.delResource(c1));
		tsa.addResource(c1);
		assertEquals(true, tsa.delResource(c1));
	}

	/*
	 *  testing strategy：
	 *  	测试了位置set中已经存在该位置和不存在该位置的情况。
	 */
	@Test
	public void testAddLocation() {
		TrainScheduleApp tsa = new TrainScheduleApp();
		assertEquals(true, tsa.addLocation(l1));
		assertEquals(false, tsa.addLocation(l1));
	}

	/*
	 *  testing strategy：
	 *  	测试了位置set中已经存在该位置和不存在该位置的情况。
	 */
	@Test
	public void testDelLocation() {
		TrainScheduleApp tsa = new TrainScheduleApp();
		assertEquals(false, tsa.delLocation(l1));
		tsa.addLocation(l1);
		assertEquals(true, tsa.delLocation(l1));
	}

	/*
	 *  testing strategy：
	 *  	测试了有/没有位置、同一航班号时间相同/不相同的情况
	 */
	@Test
	public void testAddPlan() {
		TrainScheduleApp tsa = new TrainScheduleApp();
		tsa.addResource(c1); tsa.addResource(c2); tsa.addResource(c3);
		tsa.addLocation(l1); tsa.addLocation(l2); tsa.addLocation(l3); tsa.addLocation(l4);
		List<Timeslot> timelist = new ArrayList<Timeslot>();
		timelist.add(time2); timelist.add(time3);
		List<Location> loclist = new ArrayList<Location>();
		loclist.add(l2); loclist.add(l3);
		assertEquals(true, tsa.addPlan("K90", l1, l4, loclist, time1, timelist));
		assertEquals(false, tsa.addPlan("K90", l1, l2, loclist, time1, timelist));
		assertEquals(true, tsa.addPlan("K91", l4, l1, loclist, time1, timelist));
		assertEquals(true, tsa.addPlan("K92", l1, l4, loclist, time1, timelist));
	}

	/*
	 *  testing strategy：
	 *  	测试了存在/不存在资源、存在/不存在计划项的情况。
	 */
	@Test
	public void testDistributeResource() {
		TrainScheduleApp tsa = new TrainScheduleApp();
		tsa.addResource(c1); tsa.addResource(c2);
		tsa.addLocation(l1); tsa.addLocation(l2); tsa.addLocation(l3); tsa.addLocation(l4);
		List<Timeslot> timelist = new ArrayList<Timeslot>();
		timelist.add(time2); timelist.add(time3);
		List<Location> loclist = new ArrayList<Location>();
		loclist.add(l2); loclist.add(l3);
		tsa.addPlan("K90", l1, l4, loclist, time1, timelist);
		assertEquals(true, tsa.distributeResource("K90", c1));
		assertEquals(false, tsa.distributeResource("K90", c3));
		assertEquals(false, tsa.distributeResource("K92", c3));
	}

	/*
	 *  testing strategy：
	 *  	测试了各种冲突情况，由于当添加时如果有冲突会抛出异常，因此测试结果应该永远为false
	 */
	@Test
	public void testCheckLocationAndResourseConflict() {
		TrainScheduleApp tsa = new TrainScheduleApp();
		tsa.addResource(c1); tsa.addResource(c2); tsa.addResource(c3);
		tsa.addLocation(l1); tsa.addLocation(l2); tsa.addLocation(l3); tsa.addLocation(l4);
		List<Timeslot> timelist = new ArrayList<Timeslot>();
		timelist.add(time2); timelist.add(time3);
		List<Location> loclist = new ArrayList<Location>();
		loclist.add(l2); loclist.add(l3);
		tsa.addPlan("K90", l1, l4, loclist, time1, timelist);
		tsa.addPlan("K91", l1, l4, loclist, time2, timelist);
		tsa.addPlan("K92", l1, l4, loclist, time3, timelist);
		tsa.distributeResource("K90", c1);
		tsa.distributeResource("K91", c2);
		tsa.distributeResource("K92", c3);
		assertEquals(false, tsa.checkLocationAndResourseConflict());
		tsa.distributeResource("K91", c1);
		assertEquals(false, tsa.checkLocationAndResourseConflict());
	}

	/*
	 *  testing strategy：
	 *  	测试了资源占用相同资源的计划项列表。
	 */
	@Test
	public void testGetAllPlanWithResource() {
		TrainScheduleApp tsa = new TrainScheduleApp();
		tsa.addResource(c1); tsa.addResource(c2); tsa.addResource(c3);
		tsa.addLocation(l1); tsa.addLocation(l2); tsa.addLocation(l3); tsa.addLocation(l4);
		List<Timeslot> timelist = new ArrayList<Timeslot>();
		timelist.add(time2); timelist.add(time3);
		List<Location> loclist = new ArrayList<Location>();
		loclist.add(l2); loclist.add(l3);
		tsa.addPlan("K90", l1, l4, loclist, time4, timelist);
		tsa.addPlan("K91", l1, l4, loclist, time2, timelist);
		tsa.addPlan("K92", l1, l4, loclist, time3, timelist);
		tsa.distributeResource("K90", c1);
		tsa.distributeResource("K91", c2);
		tsa.distributeResource("K92", c3);
		tsa.distributeResource("K91", c1);
		tsa.distributeResource("K92", c1);
		List<PlanningEntry<Carriage>> list = new ArrayList<PlanningEntry<Carriage>>();
		list.add(tsa.getPlan("K91"));
		list.add(tsa.getPlan("K92"));
		list.add(tsa.getPlan("K90"));
		assertEquals(list, tsa.getAllPlanWithResource(c1));
	}
}
