package app;

/*
 * 测试策略：
 * 	  仅测试了在TrainScheduleOperation类中独特出现的方法，为使代码简洁，委派给其他对象的方法已经在原对象类中进行过测试。
 */
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import common.element.Location;
import common.element.Timeslot;
import common.operation.PlanningEntry;
import exception.LocationConflictException;
import exception.ResourceConflictException;
import resource.Carriage;

public class TrainScheduleOperationTest {

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
		TrainScheduleOperation tso = new TrainScheduleOperation();
		assertEquals(true, tso.addResource(c1));
		assertEquals(false, tso.addResource(c1));
		
	}

	/*
	 *  testing strategy：
	 *  	测试了资源set中已经存在该资源和不存在该资源的情况。
	 */
	@Test
	public void testDelResource() {
		TrainScheduleOperation tso = new TrainScheduleOperation();
		try {
			assertEquals(false, tso.delResource(c1));
		} catch (ResourceConflictException e) {
			e.printStackTrace();
		}
		tso.addResource(c1);
		try {
			assertEquals(true, tso.delResource(c1));
		} catch (ResourceConflictException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testGetResourceSet() {
		TrainScheduleOperation tso = new TrainScheduleOperation();
		tso.addResource(c1); tso.addResource(c2); tso.addResource(c3);
		Set<Carriage> set = new HashSet<Carriage>();
		set.add(c1); set.add(c2); set.add(c3);
		assertEquals(set, tso.getResourceSet());
	}

	/*
	 *  testing strategy：
	 *  	测试了位置set中已经存在该位置和不存在该位置的情况。
	 */
	@Test
	public void testAddLocation() {
		TrainScheduleOperation tso = new TrainScheduleOperation();
		assertEquals(true, tso.addLocation(l1));
		assertEquals(false, tso.addLocation(l1));
	}

	/*
	 *  testing strategy：
	 *  	测试了位置set中已经存在该位置和不存在该位置的情况。
	 */
	@Test
	public void testDelLocation() {
		TrainScheduleOperation tso = new TrainScheduleOperation();
		try {
			assertEquals(false, tso.delLocation(l1));
		} catch (LocationConflictException e) {
			e.printStackTrace();
		}
		tso.addLocation(l1);
		try {
			assertEquals(true, tso.delLocation(l1));
		} catch (LocationConflictException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetLocationSet() {
		TrainScheduleOperation tso = new TrainScheduleOperation();
		tso.addLocation(l1); tso.addLocation(l2); tso.addLocation(l3);
		Set<Location> set = new HashSet<Location>();
		set.add(l1); set.add(l2); set.add(l3);
		assertEquals(set, tso.getLocationSet());
	}

	/*
	 *  testing strategy：
	 *  	测试了有/没有位置、同一航班号时间相同/不相同的情况
	 */
	@Test
	public void testAddPlan() {
		TrainScheduleOperation tso = new TrainScheduleOperation();
		tso.addResource(c1); tso.addResource(c2); tso.addResource(c3);
		tso.addLocation(l1); tso.addLocation(l2); tso.addLocation(l3); tso.addLocation(l4);
		List<Timeslot> timelist = new ArrayList<Timeslot>();
		timelist.add(time2); timelist.add(time3);
		List<Location> loclist = new ArrayList<Location>();
		loclist.add(l2); loclist.add(l3);
		assertEquals(true, tso.addPlan("K90", l1, l4, loclist, time1, timelist));
		assertEquals(false, tso.addPlan("K90", l1, l2, loclist, time1, timelist));
		assertEquals(true, tso.addPlan("K91", l4, l1, loclist, time1, timelist));
		assertEquals(true, tso.addPlan("K92", l1, l4, loclist, time1, timelist));
//		loclist.add(l4);
//		assertEquals(false, tso.addPlan("K93", l1, l2, loclist, time1, timelist));
	}

	/*
	 *  testing strategy：
	 *  	测试了存在/不存在资源、存在/不存在计划项的情况。
	 */
	@Test
	public void testDistributeResource() {
		TrainScheduleOperation tso = new TrainScheduleOperation();
		tso.addResource(c1); tso.addResource(c2);
		tso.addLocation(l1); tso.addLocation(l2); tso.addLocation(l3); tso.addLocation(l4);
		List<Timeslot> timelist = new ArrayList<Timeslot>();
		timelist.add(time2); timelist.add(time3);
		List<Location> loclist = new ArrayList<Location>();
		loclist.add(l2); loclist.add(l3);
		tso.addPlan("K90", l1, l4, loclist, time1, timelist);
		try {
			assertEquals(true, tso.distributeResource("K90", c1));
		} catch (ResourceConflictException e) {
			e.printStackTrace();
		}
		try {
			assertEquals(false, tso.distributeResource("K90", c3));
		} catch (ResourceConflictException e) {
			e.printStackTrace();
		}
		try {
			assertEquals(false, tso.distributeResource("K92", c3));
		} catch (ResourceConflictException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 *  testing strategy：
	 *  	测试了各种冲突情况，由于当添加时如果有冲突会抛出异常，因此测试结果应该永远为false
	 */
	@Test
	public void testCheckLocationAndResourseConflict() {
		TrainScheduleOperation tso = new TrainScheduleOperation();
		tso.addResource(c1); tso.addResource(c2); tso.addResource(c3);
		tso.addLocation(l1); tso.addLocation(l2); tso.addLocation(l3); tso.addLocation(l4);
		List<Timeslot> timelist = new ArrayList<Timeslot>();
		timelist.add(time2); timelist.add(time3);
		List<Location> loclist = new ArrayList<Location>();
		loclist.add(l2); loclist.add(l3);
		tso.addPlan("K90", l1, l4, loclist, time1, timelist);
		tso.addPlan("K91", l1, l4, loclist, time2, timelist);
		tso.addPlan("K92", l1, l4, loclist, time3, timelist);
		try {
			tso.distributeResource("K90", c1);
		} catch (ResourceConflictException e2) {
			e2.printStackTrace();
		}
		try {
			tso.distributeResource("K91", c2);
		} catch (ResourceConflictException e) {
			e.printStackTrace();
		}
		try {
			tso.distributeResource("K92", c3);
		} catch (ResourceConflictException e1) {
			e1.printStackTrace();
		}
		assertEquals(false, tso.checkLocationAndResourseConflict());
		try {
			tso.distributeResource("K91", c1);
		} catch (ResourceConflictException e) {
			e.printStackTrace();
		}
		assertEquals(false, tso.checkLocationAndResourseConflict());
	}
	
	/*
	 *  testing strategy：
	 *  	测试了资源占用相同资源的计划项列表。
	 */
	@Test
	public void testGetAllPlanWithResource() {
		TrainScheduleOperation tso = new TrainScheduleOperation();
		tso.addResource(c1); tso.addResource(c2); tso.addResource(c3);
		tso.addLocation(l1); tso.addLocation(l2); tso.addLocation(l3); tso.addLocation(l4);
		List<Timeslot> timelist = new ArrayList<Timeslot>();
		timelist.add(time2); timelist.add(time3);
		List<Location> loclist = new ArrayList<Location>();
		loclist.add(l2); loclist.add(l3);
		tso.addPlan("K90", l1, l4, loclist, time4, timelist);
		tso.addPlan("K91", l1, l4, loclist, time2, timelist);
		tso.addPlan("K92", l1, l4, loclist, time3, timelist);
		try {
			tso.distributeResource("K90", c1);
		} catch (ResourceConflictException e4) {
			e4.printStackTrace();
		}
		try {
			tso.distributeResource("K91", c2);
		} catch (ResourceConflictException e3) {
			e3.printStackTrace();
		}
		try {
			tso.distributeResource("K92", c3);
		} catch (ResourceConflictException e2) {
			e2.printStackTrace();
		}
		try {
			tso.distributeResource("K91", c1);
		} catch (ResourceConflictException e1) {
			e1.printStackTrace();
		}
		try {
			tso.distributeResource("K92", c1);
		} catch (ResourceConflictException e) {
			e.printStackTrace();
		}
		List<PlanningEntry<Carriage>> list = new ArrayList<PlanningEntry<Carriage>>();
		list.add(tso.getPlan("K91"));
		list.add(tso.getPlan("K92"));
		list.add(tso.getPlan("K90"));
		assertEquals(list, tso.getAllPlanWithResource(c1));
	}
}
