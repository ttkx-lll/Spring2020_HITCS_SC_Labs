package source;

import static org.junit.Assert.*;

import org.junit.Test;

/*
 * 测试策略：
 *    测试Carriage的getter方法和equals方法
 */
public class CarriageTest {

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testGetters() {
		Carriage carriage = new Carriage("001", "餐车", 65, 2012);
		assertEquals("001", carriage.getCarriageID());
		assertEquals("餐车", carriage.getCarriageType());
		assertEquals(65, carriage.getSeatNumber());
		assertEquals(2012, carriage.getProductYear());
	}
	
	@Test
	public void testEquals() {
		Carriage carriage1 = new Carriage("001", "餐车", 65, 2012);
		Carriage carriage2 = new Carriage("001", "餐车", 65, 2012);
		Carriage carriage3 = new Carriage("002", "餐车", 65, 2012);
		Carriage carriage4 = new Carriage("001", "客车", 65, 2012);
		Carriage carriage5 = new Carriage("001", "餐车", 70, 2012);
		Carriage carriage6 = new Carriage("001", "餐车", 65, 2010);
		assertEquals(true, carriage1.equals(carriage2));
		assertEquals(false, carriage1.equals(carriage3));
		assertEquals(false, carriage1.equals(carriage4));
		assertEquals(false, carriage1.equals(carriage5));
		assertEquals(false, carriage1.equals(carriage6));
	}
}
