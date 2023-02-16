package common.element;

import static org.junit.Assert.*;

import org.junit.Test;

/*
 * 测试策略：
 *    根据状态转移图进行测试。
 */
public class BLOCKEDTest {

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	/*
	 *  testing strategy：
	 *  	测试了对BLOCKED使用create方法的结果。
	 */
	@Test
	public void testCreate() {
		EntryState state = BLOCKED.instance;
		state = state.create();
		assertEquals(BLOCKED.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对BLOCKED使用allocate方法的结果。
	 */
	@Test
	public void testAllocate() {
		EntryState state = BLOCKED.instance;
		state = state.allocate();
		assertEquals(BLOCKED.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对BLOCKED使用start方法的结果。
	 */
	@Test
	public void testStart() {
		EntryState state = BLOCKED.instance;
		state = state.start();
		assertEquals(BLOCKED.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对BLOCKED使用cancel方法的结果。
	 */
	@Test
	public void testCancel() {
		EntryState state = BLOCKED.instance;
		state = state.cancel();
		assertEquals(CANCELLED.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对BLOCKED使用end方法的结果。
	 */
	@Test
	public void testEnd() {
		EntryState state = BLOCKED.instance;
		state = state.end();
		assertEquals(BLOCKED.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对BLOCKED使用block方法的结果。
	 */
	@Test
	public void testBlock() {
		EntryState state = BLOCKED.instance;
		state = state.block();
		assertEquals(BLOCKED.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对BLOCKED使用restart方法的结果。
	 */
	@Test
	public void testRestart() {
		EntryState state = BLOCKED.instance;
		state = state.restart();
		assertEquals(RUNNING.instance, state);
	}

}
