package common.element;

import static org.junit.Assert.*;

import org.junit.Test;

/*
 * 测试策略：
 *    根据状态转移图进行测试。
 */
public class ALLOCATEDTest {

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	/*
	 *  testing strategy：
	 *  	测试了对ALLOCATED使用create方法的结果。
	 */
	@Test
	public void testCreate() {
		EntryState state = ALLOCATED.instance;
		state = state.create();
		assertEquals(ALLOCATED.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对ALLOCATED使用allocate方法的结果。
	 */
	@Test
	public void testAllocate() {
		EntryState state = ALLOCATED.instance;
		state = state.allocate();
		assertEquals(ALLOCATED.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对ALLOCATED使用start方法的结果。
	 */
	@Test
	public void testStart() {
		EntryState state = ALLOCATED.instance;
		state = state.start();
		assertEquals(RUNNING.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对ALLOCATED使用cancel方法的结果。
	 */
	@Test
	public void testCancel() {
		EntryState state = ALLOCATED.instance;
		state = state.cancel();
		assertEquals(CANCELLED.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对ALLOCATED使用end方法的结果。
	 */
	@Test
	public void testEnd() {
		EntryState state = ALLOCATED.instance;
		state = state.end();
		assertEquals(ALLOCATED.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对ALLOCATED使用block方法的结果。
	 */
	@Test
	public void testBlock() {
		EntryState state = ALLOCATED.instance;
		state = state.block();
		assertEquals(ALLOCATED.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对ALLOCATED使用restart方法的结果。
	 */
	@Test
	public void testRestart() {
		EntryState state = ALLOCATED.instance;
		state = state.restart();
		assertEquals(ALLOCATED.instance, state);
	}

}
