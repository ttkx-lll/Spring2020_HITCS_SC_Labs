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
import source.Carriage;

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
	
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testAddResource() {
		TrainScheduleOperation tso = new TrainScheduleOperation();
		assertEquals(true, tso.addResource(c1));
		assertEquals(false, tso.addResource(c1));
		
	}
	
	@Test
	public void testDelResource() {
		TrainScheduleOperation tso = new TrainScheduleOperation();
		assertEquals(false, tso.delResource(c1));
		tso.addResource(c1);
		assertEquals(true, tso.delResource(c1));
		
	}
	
	@Test
	public void testGetResourceSet() {
		TrainScheduleOperation tso = new TrainScheduleOperation();
		tso.addResource(c1); tso.addResource(c2); tso.addResource(c3);
		Set<Carriage> set = new HashSet<Carriage>();
		set.add(c1); set.add(c2); set.add(c3);
		assertEquals(set, tso.getResourceSet());
	}
	
	@Test
	public void testAddLocation() {
		TrainScheduleOperation tso = new TrainScheduleOperation();
		assertEquals(true, tso.addLocation(l1));
		assertEquals(false, tso.addLocation(l1));
	}
	
	@Test
	public void testDelLocation() {
		TrainScheduleOperation tso = new TrainScheduleOperation();
		assertEquals(false, tso.delLocation(l1));
		tso.addLocation(l1);
		assertEquals(true, tso.delLocation(l1));
	}
	
	@Test
	public void testGetLocationSet() {
		TrainScheduleOperation tso = new TrainScheduleOperation();
		tso.addLocation(l1); tso.addLocation(l2); tso.addLocation(l3);
		Set<Location> set = new HashSet<Location>();
		set.add(l1); set.add(l2); set.add(l3);
		assertEquals(set, tso.getLocationSet());
	}

	@Test
	public void testAddPlan() {
		TrainScheduleOperation tso = new TrainScheduleOperation();
		tso.addResource(c1); tso.addResource(c2); tso.addResource(c3);
		tso.addLocation(l1); tso.addLocation(l2); tso.addLocation(l3);
		List<Timeslot> timelist = new ArrayList<Timeslot>();
		timelist.add(time2); timelist.add(time3);
		List<Location> loclist = new ArrayList<Location>();
		loclist.add(l2); loclist.add(l3);
		assertEquals(true, tso.addPlan("K90", l1, l2, loclist, time1, timelist));
		assertEquals(false, tso.addPlan("K90", l1, l2, loclist, time1, timelist));
		assertEquals(false, tso.addPlan("K91", l4, l2, loclist, time1, timelist));
		assertEquals(false, tso.addPlan("K92", l1, l4, loclist, time1, timelist));
		loclist.add(l4);
		assertEquals(false, tso.addPlan("K93", l1, l2, loclist, time1, timelist));
	}

	@Test
	public void testDistributeResource() {
		TrainScheduleOperation tso = new TrainScheduleOperation();
		tso.addResource(c1); tso.addResource(c2);
		tso.addLocation(l1); tso.addLocation(l2); tso.addLocation(l3);
		List<Timeslot> timelist = new ArrayList<Timeslot>();
		timelist.add(time2); timelist.add(time3);
		List<Location> loclist = new ArrayList<Location>();
		loclist.add(l2); loclist.add(l3);
		tso.addPlan("K90", l1, l2, loclist, time1, timelist);
		assertEquals(true, tso.distributeResource("K90", c1));
		assertEquals(false, tso.distributeResource("K90", c3));
		assertEquals(false, tso.distributeResource("K92", c3));
	}
	
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
		tso.distributeResource("K90", c1);
		tso.distributeResource("K91", c2);
		tso.distributeResource("K92", c3);
		assertEquals(false, tso.checkLocationAndResourseConflict());
		tso.distributeResource("K91", c1);
		assertEquals(true, tso.checkLocationAndResourseConflict());
	}
	
	@Test
	public void testGetAllPlanWithResource() {
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
		tso.distributeResource("K90", c1);
		tso.distributeResource("K91", c2);
		tso.distributeResource("K92", c3);
		tso.distributeResource("K91", c1);
		tso.distributeResource("K92", c1);
		List<PlanningEntry<Carriage>> list = new ArrayList<PlanningEntry<Carriage>>();
		list.add(tso.getPlan("K90"));
		list.add(tso.getPlan("K91"));
		list.add(tso.getPlan("K92"));
		assertEquals(list, tso.getAllPlanWithResource(c1));
	}
}
