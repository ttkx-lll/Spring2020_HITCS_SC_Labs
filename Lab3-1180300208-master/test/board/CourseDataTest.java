package board;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import applicationEntry.CourseEntry;
import applicationEntry.CourseFactory;
import common.element.Location;
import common.element.Timeslot;

/*
 * 测试策略：
 *     测试了对course计划项列表的各种操作。
 */
public class CourseDataTest {

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testDataOperation() {
		String name1 = "English1";
		String name2 = "English2";
		String name3 = "English3";
		Location loc1 = new Location("正心231", false);
		Location loc2 = new Location("正心232", false);
		Location loc3 = new Location("正心233", false);
		Timeslot timeslot1 = new Timeslot(2020, 5, 15, 14, 5, 2020, 5, 15, 15, 7);
		Timeslot timeslot2 = new Timeslot(2020, 5, 14, 14, 5, 2020, 5, 14, 15, 7);
		Timeslot timeslot3 = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 15, 7);
		CourseEntry ce1 = new CourseFactory().getEntry(name1, loc1, timeslot1);
		CourseEntry ce2 = new CourseFactory().getEntry(name2, loc2, timeslot2);
		CourseEntry ce3 = new CourseFactory().getEntry(name3, loc3, timeslot3);
		List<CourseEntry> list = new ArrayList<CourseEntry>();
		list.add(ce1); list.add(ce2); list.add(ce3);
		CourseData cd = new CourseData();
		cd.addData(ce1); cd.addData(ce2); cd.addData(ce3);
		assertEquals(list, cd.getDataList());
		cd.deleteData(ce3);
		list.remove(ce3);
		assertEquals(list, cd.getDataList());
	}
	
	@Test
	public void testIterator() {
		String name1 = "English1";
		String name2 = "English2";
		String name3 = "English3";
		Location loc1 = new Location("正心231", false);
		Location loc2 = new Location("正心232", false);
		Location loc3 = new Location("正心233", false);
		Timeslot timeslot1 = new Timeslot(2020, 5, 15, 14, 5, 2020, 5, 15, 15, 7);
		Timeslot timeslot2 = new Timeslot(2020, 5, 14, 14, 5, 2020, 5, 14, 15, 7);
		Timeslot timeslot3 = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 15, 7);
		CourseEntry ce1 = new CourseFactory().getEntry(name1, loc1, timeslot1);
		CourseEntry ce2 = new CourseFactory().getEntry(name2, loc2, timeslot2);
		CourseEntry ce3 = new CourseFactory().getEntry(name3, loc3, timeslot3);
		CourseData cd = new CourseData();
		cd.addData(ce1); cd.addData(ce2); cd.addData(ce3);
		Iterator<CourseEntry> iterator = cd.iterator();
		if(iterator.hasNext()) {
			assertEquals(ce3, iterator.next());
		}
		if(iterator.hasNext()) {
			assertEquals(ce2, iterator.next());
		}
	}
	
	@Test
	public void testGetPlan() {
		String name1 = "English1";
		String name2 = "English2";
		String name3 = "English3";
		Location loc1 = new Location("正心231", false);
		Location loc2 = new Location("正心232", false);
		Location loc3 = new Location("正心233", false);
		Timeslot timeslot1 = new Timeslot(2020, 5, 15, 14, 5, 2020, 5, 15, 15, 7);
		Timeslot timeslot2 = new Timeslot(2020, 5, 14, 14, 5, 2020, 5, 14, 15, 7);
		Timeslot timeslot3 = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 15, 7);
		CourseEntry ce1 = new CourseFactory().getEntry(name1, loc1, timeslot1);
		CourseEntry ce2 = new CourseFactory().getEntry(name2, loc2, timeslot2);
		CourseEntry ce3 = new CourseFactory().getEntry(name3, loc3, timeslot3);
		CourseData cd = new CourseData();
		cd.addData(ce1); cd.addData(ce2); cd.addData(ce3);
		assertEquals(ce1, cd.getPlan(name1));
		assertEquals(null, cd.getPlan("lalala"));
		
	}
}
