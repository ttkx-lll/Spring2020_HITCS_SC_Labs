package app;

/*
 * 测试策略：
 * 	  仅测试了在FlightsScheduleOperation类中独特出现的方法，为使代码简洁，委派给其他对象的方法已经在原对象类中进行过测试。
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
import source.Plane;

public class FlightScheduleOperationTest {

	private Plane p1 = new Plane("123", "asdf", 200, (float) 2.5);
	private Plane p2 = new Plane("234", "sdfg", 201, (float) 3.5);
	private Plane p3 = new Plane("345", "dfgh", 202, (float) 4.5);
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
		FlightScheduleOperation fso = new FlightScheduleOperation();
		assertEquals(true, fso.addResource(p1));
		assertEquals(false, fso.addResource(p1));
	}
	
	@Test
	public void testDelResource() {
		FlightScheduleOperation fso = new FlightScheduleOperation();
		assertEquals(false, fso.delResource(p1));
		fso.addResource(p1);
		assertEquals(true, fso.delResource(p1));
	}
	
	@Test
	public void testGetResourceSet() {
		FlightScheduleOperation fso = new FlightScheduleOperation();
		fso.addResource(p1); fso.addResource(p2); fso.addResource(p3);
		Set<Plane> set = new HashSet<Plane>();
		set.add(p1); set.add(p2); set.add(p3);
		assertEquals(set, fso.getResourceSet());
	}
	
	@Test
	public void testAddLocation() {
		FlightScheduleOperation fso = new FlightScheduleOperation();
		assertEquals(true, fso.addLocation(l1));
		assertEquals(false, fso.addLocation(l1));
	}
	
	@Test
	public void testDelLocation() {
		FlightScheduleOperation fso = new FlightScheduleOperation();
		assertEquals(false, fso.delLocation(l1));
		fso.addLocation(l1);
		assertEquals(true, fso.delLocation(l1));
	}
	
	@Test
	public void testGetLocationSet() {
		FlightScheduleOperation fso = new FlightScheduleOperation();
		fso.addLocation(l1); fso.addLocation(l2); fso.addLocation(l3);
		Set<Location> set = new HashSet<Location>();
		set.add(l1); set.add(l2); set.add(l3);
		assertEquals(set, fso.getLocationSet());
	}
	
	@Test
	public void testCheckIlleagalPlan() {
		FlightScheduleOperation fso = new FlightScheduleOperation();
		Timeslot t1 = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 14, 10);
		Timeslot t2 = new Timeslot(2020, 5, 14, 14, 5, 2020, 5, 14, 14, 10);
		Timeslot t3 = new Timeslot(2020, 5, 13, 14, 40, 2020, 5, 13, 14, 50);
		fso.addLocation(l1); fso.addLocation(l2); fso.addLocation(l3);
		fso.addPlan("AS120", l1, l2, t1);
		assertEquals(false, fso.checkIlleagalPlan("AS130", l2, l3, t2));
		assertEquals(false, fso.checkIlleagalPlan("AS130", l1, l2, t1));
		assertEquals(false, fso.checkIlleagalPlan("AS0120", l1, l2, t2));
		assertEquals(false, fso.checkIlleagalPlan("AS120", l1, l2, t2));
		assertEquals(true, fso.checkIlleagalPlan("asdf", l1, l2, t2));
		assertEquals(true, fso.checkIlleagalPlan("AS120", l1, l2, t1));
		assertEquals(true, fso.checkIlleagalPlan("AS120", l1, l3, t2));
		assertEquals(true, fso.checkIlleagalPlan("AS120", l1, l2, t3));
	}
	
	@Test
	public void testAddPlan() {
		FlightScheduleOperation fso = new FlightScheduleOperation();
		Timeslot t1 = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 14, 10);
		Timeslot t2 = new Timeslot(2020, 5, 14, 14, 5, 2020, 5, 14, 14, 10);
		Timeslot t3 = new Timeslot(2020, 5, 13, 14, 40, 2020, 5, 13, 14, 50);
		fso.addLocation(l1); fso.addLocation(l2);
		assertEquals(false, fso.addPlan("AS120", l1, l3, t1));
		assertEquals(false, fso.addPlan("AS120", l3, l1, t1));
		assertEquals(true, fso.addPlan("AS120", l1, l2, t1));
		assertEquals(true, fso.addPlan("AS0120", l1, l2, t2));
		assertEquals(false, fso.addPlan("AS0120", l1, l2, t1));
		assertEquals(false, fso.addPlan("AS0120", l1, l2, t3));
	}
	
	@Test
	public void testDistributeResource() {
		FlightScheduleOperation fso = new FlightScheduleOperation();
		Timeslot t1 = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 14, 10);
		Timeslot t2 = new Timeslot(2020, 5, 14, 14, 5, 2020, 5, 14, 14, 10);
		Timeslot t3 = new Timeslot(2020, 5, 13, 14, 40, 2020, 5, 13, 14, 50);
		fso.addLocation(l1); fso.addLocation(l2);
		fso.addPlan("AS120", l1, l2, t1);
		fso.addPlan("AS120", l1, l2, t2);
		fso.addResource(p1); fso.addResource(p2);
		assertEquals(false, fso.distributeResource("AS120", t1, p3));
		assertEquals(true, fso.distributeResource("AS120", t1, p2));
		assertEquals(false, fso.distributeResource("1230", t1, p3));
		assertEquals(false, fso.distributeResource("AS120", t3, p3));
		
	}
	
	@Test
	public void testCheckLocationAndResourseConflict() {
		FlightScheduleOperation fso = new FlightScheduleOperation();
		fso.addLocation(l1); fso.addLocation(l2); fso.addLocation(l3);
		fso.addResource(p1); fso.addResource(p2); fso.addResource(p3);
		fso.addPlan("AS001", l1, l2, time1);
		fso.addPlan("AS002", l2, l3, time2);
		fso.addPlan("AS003", l1, l3, time3);
		fso.distributeResource("AS001", time1, p1);
		fso.distributeResource("AS002", time2, p2);
		fso.distributeResource("AS003", time3, p3);
		assertEquals(false, fso.checkLocationAndResourseConflict());
		fso.distributeResource("AS002", time2, p1);
		assertEquals(true, fso.checkLocationAndResourseConflict());
	}
	
	@Test
	public void testGetAllPlanWithResource() {
		FlightScheduleOperation fso = new FlightScheduleOperation();
		fso.addLocation(l1); fso.addLocation(l2); fso.addLocation(l3);
		fso.addResource(p1); fso.addResource(p2); fso.addResource(p3);
		fso.addPlan("AS001", l1, l2, time1);
		fso.addPlan("AS002", l2, l3, time2);
		fso.addPlan("AS003", l1, l3, time3);
		fso.distributeResource("AS001", time1, p1);
		fso.distributeResource("AS002", time2, p1);
		fso.distributeResource("AS003", time3, p3);
		List<PlanningEntry<Plane>> list = new ArrayList<>();
		list.add(fso.getPlan("AS001", time1));
		list.add(fso.getPlan("AS002", time2));
		assertEquals(list, fso.getAllPlanWithResource(p1));
	}
}
