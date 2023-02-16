package characterEntryImpl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import resource.Carriage;

/*
 * 测试策略：
 *    测试有关多个可排序资源计划项的资源的各种方法。
 */
public class MultipleSortedResourceEntryImplTest {

	private static Carriage c1 = new Carriage("001", "餐车", 65, 2012);
	private static Carriage c2 = new Carriage("002", "客车", 60, 2011);
	private static Carriage c3 = new Carriage("003", "客车", 60, 2011);
	private static Carriage c4 = new Carriage("004", "货车", 10, 2013);
	private static Carriage c5 = new Carriage("005", "货车", 10, 2013);
	private static List<Carriage> list = new ArrayList<>();
	
	@BeforeClass
	public static void before() {
		list.add(c1); list.add(c2); list.add(c3); list.add(c4); list.add(c5);
	}
	
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}
	
	/*
	 *  testing strategy：
	 *  	测试了取得资源列表的方法。
	 */
	@Test
	public void testGetResources() {
		MultipleSortedResourceEntryImpl<Carriage> msre = new MultipleSortedResourceEntryImpl<Carriage>(list);
		assertEquals(list, msre.getResources());
	}

	/*
	 *  testing strategy：
	 *  	测试了添加资源的方法。
	 */
	@Test
	public void testAddResource() {
		MultipleSortedResourceEntryImpl<Carriage> msre = new MultipleSortedResourceEntryImpl<Carriage>();
		msre.addResource(c1);
		msre.addResource(c2);
		msre.addResource(c3);
		msre.addResource(c4);
		msre.addResource(c5);
		assertEquals(list, msre.getResources());
		
	}

	/*
	 *  testing strategy：
	 *  	测试了删除资源的方法。
	 */
	@Test
	public void testDeleteResource() {
		MultipleSortedResourceEntryImpl<Carriage> msre = new MultipleSortedResourceEntryImpl<Carriage>(list);
		msre.deleteResource(c3);
		list.remove(c3);
		assertEquals(list, msre.getResources());
	}
	
}
