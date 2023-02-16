package common.element;

import static org.junit.Assert.*;

import org.junit.Test;

/*
 * 测试策略：
 *    根据状态转移图进行测试。
 */
public class RUNNINGTest {

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	/*
	 *  testing strategy：
	 *  	测试了对RUNNING使用create方法的结果。
	 */
	@Test
	public void testCreate() {
		EntryState state = RUNNING.instance;
		state = state.create();
		assertEquals(RUNNING.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对RUNNING使用allocate方法的结果。
	 */
	@Test
	public void testAllocate() {
		EntryState state = RUNNING.instance;
		state = state.allocate();
		assertEquals(RUNNING.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对RUNNING使用start方法的结果。
	 */
	@Test
	public void testStart() {
		EntryState state = RUNNING.instance;
		state = state.start();
		assertEquals(RUNNING.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对RUNNING使用cancel方法的结果。
	 */
	@Test
	public void testCancel() {
		EntryState state = RUNNING.instance;
		state = state.cancel();
		assertEquals(RUNNING.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对RUNNING使用end方法的结果。
	 */
	@Test
	public void testEnd() {
		EntryState state = RUNNING.instance;
		state = state.end();
		assertEquals(ENDED.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对RUNNING使用block方法的结果。
	 */
	@Test
	public void testBlock() {
		EntryState state = RUNNING.instance;
		state = state.block();
		assertEquals(BLOCKED.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对RUNNING使用restart方法的结果。
	 */
	@Test
	public void testRestart() {
		EntryState state = RUNNING.instance;
		state = state.restart();
		assertEquals(RUNNING.instance, state);
	}

}
