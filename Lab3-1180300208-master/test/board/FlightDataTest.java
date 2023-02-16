package board;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import applicationEntry.FlightEntry;
import applicationEntry.FlightFactory;
import common.element.Location;
import common.element.Timeslot;

/*
 * 测试策略：
 *     测试了对flight计划项列表的各种操作。
 */
public class FlightDataTest {

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testDataOperation() {
		String name1 = "AS1501";
		String name2 = "AS1502";
		String name3 = "AS1503";
		Location l1 = new Location("1", true);
		Location l2 = new Location("2", true);
		Timeslot timeslot1 = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 15, 7);
		Timeslot timeslot2 = new Timeslot(2020, 5, 14, 14, 5, 2020, 5, 14, 15, 7);
		Timeslot timeslot3 = new Timeslot(2020, 5, 15, 14, 5, 2020, 5, 15, 15, 7);
		FlightEntry fe1 = new FlightFactory().getEntry(name1, l1, l2, timeslot1);
		FlightEntry fe2 = new FlightFactory().getEntry(name2, l1, l2, timeslot2);
		FlightEntry fe3 = new FlightFactory().getEntry(name3, l1, l2, timeslot3);
		List<FlightEntry> list = new ArrayList<FlightEntry>();
		list.add(fe1); list.add(fe2); list.add(fe3);
		FlightData fd = new FlightData();
		fd.addData(fe1); fd.addData(fe2); fd.addData(fe3);
		assertEquals(list, fd.getDataList());
		list.remove(fe2);
		fd.deleteData(fe2);
		assertEquals(list, fd.getDataList());
	}
	
	@Test
	public void testIterator() {
		String name1 = "AS1501";
		String name2 = "AS1502";
		String name3 = "AS1503";
		Location l1 = new Location("1", true);
		Location l2 = new Location("2", true);
		Timeslot timeslot1 = new Timeslot(2020, 5, 15, 14, 5, 2020, 5, 15, 15, 7);
		Timeslot timeslot2 = new Timeslot(2020, 5, 14, 14, 5, 2020, 5, 14, 15, 7);
		Timeslot timeslot3 = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 15, 7);
		FlightEntry fe1 = new FlightFactory().getEntry(name1, l1, l2, timeslot1);
		FlightEntry fe2 = new FlightFactory().getEntry(name2, l1, l2, timeslot2);
		FlightEntry fe3 = new FlightFactory().getEntry(name3, l1, l2, timeslot3);
		FlightData fd = new FlightData();
		fd.addData(fe1); fd.addData(fe2); fd.addData(fe3);
		Iterator<FlightEntry> iterator = fd.iterator();
		if(iterator.hasNext()) {
			assertEquals(fe3, iterator.next());
		}
		if(iterator.hasNext()) {
			assertEquals(fe2, iterator.next());
		}
		
	}
	
	@Test
	public void testGetPlan() {
		String name1 = "AS1501";
		String name2 = "AS1502";
		String name3 = "AS1503";
		Location l1 = new Location("1", true);
		Location l2 = new Location("2", true);
		Timeslot timeslot1 = new Timeslot(2020, 5, 15, 14, 5, 2020, 5, 15, 15, 7);
		Timeslot timeslot2 = new Timeslot(2020, 5, 14, 14, 5, 2020, 5, 14, 15, 7);
		Timeslot timeslot3 = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 15, 7);
		FlightEntry fe1 = new FlightFactory().getEntry(name1, l1, l2, timeslot1);
		FlightEntry fe2 = new FlightFactory().getEntry(name2, l1, l2, timeslot2);
		FlightEntry fe3 = new FlightFactory().getEntry(name3, l1, l2, timeslot3);
		FlightData fd = new FlightData();
		fd.addData(fe1); fd.addData(fe2); fd.addData(fe3);
		assertEquals(fe1, fd.getPlan(name1, timeslot1));
		assertEquals(null, fd.getPlan("123", timeslot1));
		assertEquals(null, fd.getPlan(name1, timeslot2));

	}
}
