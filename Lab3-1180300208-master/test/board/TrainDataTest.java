package board;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import applicationEntry.TrainEntry;
import applicationEntry.TrainFactory;
import common.element.Location;
import common.element.Timeslot;

/*
 * 测试策略：
 *     测试了对train计划项列表的各种操作。
 */
public class TrainDataTest {

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testDataOperation() {
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
		TrainEntry te2 = new TrainFactory().getEntry(name2, l1, l4, loclist, t1, timelist);
		TrainEntry te3 = new TrainFactory().getEntry(name3, l1, l4, loclist, t1, timelist);
		TrainData td = new TrainData();
		List<TrainEntry> list = new ArrayList<TrainEntry>();
		list.add(te1); list.add(te2); list.add(te3);
		td.addData(te1); td.addData(te2); td.addData(te3);
		assertEquals(list, td.getDataList());
		list.remove(te1);
		td.deleteData(te1);
		assertEquals(list, td.getDataList());
	}
	
	@Test
	public void testIterator() {
		String name1 = "K90";
		String name2 = "K91";
		String name3 = "K92";
		Timeslot t1 = new Timeslot(2020, 5, 13, 14, 40, 2020, 5, 13, 15, 7);
		Timeslot t2 = new Timeslot(2020, 5, 13, 14, 20, 2020, 5, 13, 14, 30);
		Timeslot t3 = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 14, 50);
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
		TrainData td = new TrainData();
		td.addData(te1); td.addData(te2); td.addData(te3);
		Iterator<TrainEntry> iterator = td.iterator();
		if(iterator.hasNext()) {
			assertEquals(te3, iterator.next());
		}
		if(iterator.hasNext()) {
			assertEquals(te2, iterator.next());
		}
	}
	
	@Test
	public void testGetPlan() {
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
		TrainEntry te2 = new TrainFactory().getEntry(name2, l1, l4, loclist, t1, timelist);
		TrainEntry te3 = new TrainFactory().getEntry(name3, l1, l4, loclist, t1, timelist);
		TrainData td = new TrainData();
		td.addData(te1); td.addData(te2); td.addData(te3);
		assertEquals(te1, td.getPlan(name1));
		assertEquals(null, td.getPlan("lalala"));
	}
}
