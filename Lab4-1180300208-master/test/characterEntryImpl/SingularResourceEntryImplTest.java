package characterEntryImpl;

import static org.junit.Assert.*;

import org.junit.Test;

import resource.Teacher;

/*
 * 测试策略：
 *    测试有关单一资源计划项的资源的各种方法。
 */
public class SingularResourceEntryImplTest {

	private Teacher teacher = new Teacher("123456789", "Tom", "male", "professor");
	
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	/*
	 *  testing strategy：
	 *  	测试了取得该单一资源的方法。
	 */
	@Test
	public void testGetResource() {
		SingularResourceEntryImpl<Teacher> sre = new SingularResourceEntryImpl<Teacher>(teacher);
		Teacher t = new Teacher("123456789", "Tom", "male", "professor");
		assertEquals(t, sre.getResource());
	}

	/*
	 *  testing strategy：
	 *  	测试了设置单一资源的方法。
	 */
	@Test
	public void testSetResource() {
		SingularResourceEntryImpl<Teacher> sre = new SingularResourceEntryImpl<Teacher>();
		Teacher t = new Teacher("123456789", "Tom", "male", "professor");
		sre.setResource(teacher);
		assertEquals(t, sre.getResource());
	}
	
}
