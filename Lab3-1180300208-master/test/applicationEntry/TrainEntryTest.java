package applicationEntry;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import common.element.Location;
import common.element.Timeslot;
import source.Carriage;

/*
 * 测试策略：
 * 	  仅测试了在TrainEntry类中独特出现的方法，其他委派给其他对象的方法已经在原对象类中进行过测试。
 */
public class TrainEntryTest {

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testGetPresentStateName() {
		String name = "K90";
		Carriage c = new Carriage("1234", "货车", 5, 2020);
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
		TrainEntry te = new TrainFactory().getEntry(name, l1, l4, loclist, t1, timelist);
		assertEquals("未分配车厢", te.getPresentStateName());
		te.addResource(c);
		assertEquals("已分配车厢", te.getPresentStateName());
		te.start();
		assertEquals("列车运行中", te.getPresentStateName());
		te.block();
		assertEquals("停靠中间车站", te.getPresentStateName());
		te.restart();
		assertEquals("列车运行中", te.getPresentStateName());
		te.end();
		assertEquals("抵达终点站", te.getPresentStateName());
		te = new TrainFactory().getEntry(name, l1, l4, loclist, t1, timelist);
		te.cancel();
		assertEquals("已取消", te.getPresentStateName());
	}
	
	@Test
	public void testGetPlanningName() {
		String name = "K90";
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
		TrainEntry te = new TrainFactory().getEntry(name, l1, l4, loclist, t1, timelist);
		assertEquals(name, te.getPlanningName());
	}
	
	@Test
	public void testCompareTo() {
		String name = "K90";
		Timeslot t1 = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 15, 7);
		Timeslot t2 = new Timeslot(2020, 5, 13, 14, 20, 2020, 5, 13, 14, 30);
		Timeslot t3 = new Timeslot(2020, 5, 13, 14, 40, 2020, 5, 13, 14, 50);
		Timeslot t4 = new Timeslot(2020, 5, 13, 13, 5, 2020, 5, 13, 15, 7);
		List<Timeslot> timelist = new ArrayList<Timeslot>();
		timelist.add(t2); timelist.add(t3);
		Location l1 = new Location("1", true);
		Location l2 = new Location("2", true);
		Location l3 = new Location("3", true);
		Location l4 = new Location("4", true);
		List<Location> loclist = new ArrayList<Location>();
		loclist.add(l2); loclist.add(l3);
		TrainEntry te = new TrainFactory().getEntry(name, l1, l4, loclist, t1, timelist);
		TrainEntry te2 = new TrainFactory().getEntry(name, l1, l4, loclist, t4, timelist);
		assertEquals(1, te.compareTo(te2));
		
	}
}
