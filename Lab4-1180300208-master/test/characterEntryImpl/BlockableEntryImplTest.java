package characterEntryImpl;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import common.element.Timeslot;

/*
 * 测试策略：
 *    测试有关可阻塞计划项的时间的各种方法。
 */
public class BlockableEntryImplTest {

	private static Timeslot t1 = new Timeslot(2020, 5, 13, 14, 5, 2020, 5, 13, 15, 7);
	private static Timeslot t2 = new Timeslot(2020, 5, 13, 14, 20, 2020, 5, 13, 14, 30);
	private static Timeslot t3 = new Timeslot(2020, 5, 13, 14, 40, 2020, 5, 13, 14, 50);
	private static Timeslot t4 = new Timeslot(2020, 5, 13, 14, 55, 2020, 5, 13, 15, 5);
	private static List<Timeslot> list = new ArrayList<Timeslot>();
	
	@BeforeClass
	public static void before() {
		list.add(t2);
		list.add(t3);
		list.add(t4);
	}
	
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	/*
	 *  testing strategy：
	 *  	测试了取得总体起始时间的方法。
	 */
	@Test
	public void testGetTotalStartTime() {
		LocalDateTime start = LocalDateTime.of(2020, 5, 13, 14, 5);
		BlockableEntryImpl be = new BlockableEntryImpl(t1, list);
		assertEquals(start, be.getTotalStartTime());
	}
	
	/*
	 *  testing strategy：
	 *  	测试了取得总体结束时间的方法。
	 */
	@Test
	public void testGetTotalEndTime() {
		LocalDateTime end = LocalDateTime.of(2020, 5, 13, 15, 7);
		BlockableEntryImpl be = new BlockableEntryImpl(t1, list);
		assertEquals(end, be.getTotalEndTime());
	}
	
	/*
	 *  testing strategy：
	 *  	测试了取得中间阻塞时间的方法。
	 */
	@Test
	public void testGetBlockTimeslotList() {
		BlockableEntryImpl be = new BlockableEntryImpl(t1, list);
		assertEquals(list, be.getBlockTimeslotList());
	}
	
}
