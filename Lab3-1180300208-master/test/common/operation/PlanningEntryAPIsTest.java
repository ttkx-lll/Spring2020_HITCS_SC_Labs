package common.operation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import applicationEntry.CourseEntry;
import applicationEntry.CourseFactory;
import applicationEntry.FlightEntry;
import applicationEntry.FlightFactory;
import applicationEntry.TrainEntry;
import applicationEntry.TrainFactory;
import common.element.Location;
import common.element.Timeslot;
import source.Carriage;
import source.Plane;
import source.Teacher;

/*
 * 测试策略：
 * 	  分为course、flight和train三种类别分别测试三种apis方法
 */
public class PlanningEntryAPIsTest {

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testCourseHelpers() {
		Teacher teacher1 = new Teacher("123", "lala", "male", "teacher");
		Teacher teacher2 = new Teacher("456", "haha", "female", "teacher");
		String name1 = "English1";
		String name2 = "English2";
		String name3 = "English3";
		Location loc1 = new Location("正心231", false);
		Location loc2 = new Location("正心233", false);
		Location loc3 = new Location("正心233", false);
		Timeslot timeslot1 = new Timeslot(2020, 5, 14, 14, 30, 2020, 5, 14, 15, 0);
		Timeslot timeslot2 = new Timeslot(2020, 5, 14, 15, 0, 2020, 5, 14, 16, 30);
		Timeslot timeslot3 = new Timeslot(2020, 5, 14, 15, 30, 2020, 5, 14, 17, 0);
		CourseEntry ce1 = new CourseFactory().getEntry(name1, loc1, timeslot1);
		CourseEntry ce2 = new CourseFactory().getEntry(name2, loc2, timeslot2);
		CourseEntry ce3 = new CourseFactory().getEntry(name3, loc3, timeslot3);
		ce1.setResource(teacher1); ce2.setResource(teacher2); ce3.setResource(teacher1);
		List<PlanningEntry<Teacher>> list = new ArrayList<>();
		PlanningEntryAPIs<Teacher> help1 = new PlanningEntryAPIs<Teacher>();
		assertEquals(false, help1.checkLocationConflict(new FirstStrategy<Teacher>(list)));
		assertEquals(false, help1.checkLocationConflict(new SecondStrategy<Teacher>(list)));
		assertEquals(false, help1.checkLocationConflict(list));
		assertEquals(false, help1.checkResourceExclusiveConflict(list));
		list.add(ce1); list.add(ce2); list.add(ce3);
		assertEquals(true, help1.checkLocationConflict(new FirstStrategy<Teacher>(list)));
		assertEquals(true, help1.checkLocationConflict(new SecondStrategy<Teacher>(list)));
		assertEquals(true, help1.checkLocationConflict(list));
		assertEquals(false, help1.checkResourceExclusiveConflict(list));
		assertEquals(ce1, help1.findPreEntryPerResource(teacher1, ce3, list));
		ce2.setResource(teacher1);
		assertEquals(true, help1.checkResourceExclusiveConflict(list));
		
	}
	
	@Test
	public void testFlightHelpers() {
		String name1 = "AS1501";
		String name2 = "AS1502";
		String name3 = "AS1503";
		Location l1 = new Location("1", true);
		Location l2 = new Location("2", true);
		Timeslot t1 = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 15, 7);
		Timeslot t2 = new Timeslot(2020, 5, 13, 14, 20, 2020, 5, 13, 14, 30);
		Timeslot t3 = new Timeslot(2020, 5, 13, 14, 40, 2020, 5, 13, 14, 50);
		FlightEntry fe1 = new FlightFactory().getEntry(name1, l1, l2, t1);
		FlightEntry fe2 = new FlightFactory().getEntry(name2, l1, l2, t2);
		FlightEntry fe3 = new FlightFactory().getEntry(name3, l1, l2, t3);
		List<PlanningEntry<Plane>> list = new ArrayList<>();
		list.add(fe1); list.add(fe2); list.add(fe3);
		PlanningEntryAPIs<Plane> help2 = new PlanningEntryAPIs<Plane>();
		assertEquals(false, help2.checkLocationConflict(new FirstStrategy<Plane>(list)));
		assertEquals(false, help2.checkLocationConflict(new SecondStrategy<Plane>(list)));
		assertEquals(false, help2.checkLocationConflict(list));
		Plane p1 = new Plane("123", "adsf", 120, (float) 2.5);
		Plane p2 = new Plane("456", "qwer", 45, (float) 1.5);
		fe1.setResource(p1); fe2.setResource(p2); fe3.setResource(p1);
		assertEquals(true, help2.checkResourceExclusiveConflict(list));
		assertEquals(null, help2.findPreEntryPerResource(p1, fe3, list));
		fe2.setResource(p1);
		assertEquals(fe2, help2.findPreEntryPerResource(p1, fe3, list));
	}
	
	@Test
	public void testTrainHelpers() {
		String name1 = "K90";
		String name2 = "K91";
		String name3 = "K92";
		Timeslot t1 = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 15, 7);
		Timeslot t2 = new Timeslot(2020, 5, 13, 14, 20, 2020, 5, 13, 14, 30);
		Timeslot t3 = new Timeslot(2020, 5, 13, 14, 40, 2020, 5, 13, 14, 50);
		List<Timeslot> timelist = new ArrayList<Timeslot>();
		timelist.add(t2); timelist.add(t3);
		Location l1 = new Location("1", true);
		Location l2 = new Location("2", true);
		Location l3 = new Location("3", true);
		Location l4 = new Location("4", true);
		List<Location> loclist = new ArrayList<Location>();
		loclist.add(l2); loclist.add(l3);
		TrainEntry te1 = new TrainFactory().getEntry(name1, l1, l4, loclist, t1, timelist);
		TrainEntry te2 = new TrainFactory().getEntry(name2, l1, l4, loclist, t2, timelist);
		TrainEntry te3 = new TrainFactory().getEntry(name3, l1, l4, loclist, t3, timelist);
		List<PlanningEntry<Carriage>> list = new ArrayList<>();
		list.add(te1); list.add(te2); list.add(te3);
		PlanningEntryAPIs<Carriage> help3 = new PlanningEntryAPIs<>();
		assertEquals(false, help3.checkLocationConflict(new FirstStrategy<Carriage>(list)));
		assertEquals(false, help3.checkLocationConflict(new SecondStrategy<Carriage>(list)));
		assertEquals(false, help3.checkLocationConflict(list));
		Carriage c1 = new Carriage("123", "adsf", 120, 2012);
		Carriage c2 = new Carriage("456", "sdfg", 120, 2012);
		Carriage c3 = new Carriage("789", "dfgh", 120, 2012);
		Carriage c4 = new Carriage("012", "fghj", 120, 2012);
		te1.addResource(c1); te1.addResource(c2);
		te2.addResource(c2); te2.addResource(c3);
		te3.addResource(c3); te3.addResource(c4);
		assertEquals(true, help3.checkResourceExclusiveConflict(list));
		assertEquals(te2, help3.findPreEntryPerResource(c3, te3, list));
		assertEquals(null, help3.findPreEntryPerResource(c4, te3, list));
	}
}
