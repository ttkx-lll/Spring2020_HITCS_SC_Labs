package source;

import static org.junit.Assert.*;

import org.junit.Test;

/*
 * 测试策略：
 *    测试Teacher的getter方法和equals方法
 */
public class TeacherTest {

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testGetters() {
		Teacher teacher = new Teacher("123456789", "Tom", "male", "professor");
		assertEquals("123456789", teacher.getTeacherID());
		assertEquals("Tom", teacher.getName());
		assertEquals("male", teacher.getGender());
		assertEquals("professor", teacher.getPosition());
	}
	
	@Test
	public void testEquals() {
		Teacher teacher1 = new Teacher("123456789", "Tom", "male", "professor");
		Teacher teacher2 = new Teacher("123456789", "Tom", "male", "professor");
		Teacher teacher3 = new Teacher("1256789", "Tom", "male", "professor");
		Teacher teacher4 = new Teacher("123456789", "Bob", "male", "professor");
		Teacher teacher5 = new Teacher("123456789", "Tom", "female", "professor");
		Teacher teacher6 = new Teacher("123456789", "Tom", "male", "lecturer");
		assertEquals(true, teacher1.equals(teacher2));
		assertEquals(false, teacher1.equals(teacher3));
		assertEquals(false, teacher1.equals(teacher4));
		assertEquals(false, teacher1.equals(teacher5));
		assertEquals(false, teacher1.equals(teacher6));
	}
}
