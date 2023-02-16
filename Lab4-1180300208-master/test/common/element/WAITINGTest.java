package common.element;

import static org.junit.Assert.*;

import org.junit.Test;

/*
 * 测试策略：
 *    根据状态转移图进行测试。
 */
public class WAITINGTest {

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	/*
	 *  testing strategy：
	 *  	测试了对WAITING使用create方法的结果。
	 */
	@Test
	public void testCreate() {
		EntryState state = WAITING.instance;
		state = state.create();
		assertEquals(WAITING.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对WAITING使用allocate方法的结果。
	 */
	@Test
	public void testAllocate() {
		EntryState state = WAITING.instance;
		state = state.allocate();
		assertEquals(ALLOCATED.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对WAITING使用start方法的结果。
	 */
	@Test
	public void testStart() {
		EntryState state = WAITING.instance;
		state.start();
		assertEquals(WAITING.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对WAITING使用cancel方法的结果。
	 */
	@Test
	public void testCancel() {
		EntryState state = WAITING.instance;
		state = state.cancel();
		assertEquals(CANCELLED.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对WAITING使用end方法的结果。
	 */
	@Test
	public void testEnd() {
		EntryState state = WAITING.instance;
		state = state.end();
		assertEquals(WAITING.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对WAITING使用block方法的结果。
	 */
	@Test
	public void testBlock() {
		EntryState state = WAITING.instance;
		state = state.block();
		assertEquals(WAITING.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对WAITING使用restart方法的结果。
	 */
	@Test
	public void testRestart() {
		EntryState state = WAITING.instance;
		state = state.restart();
		assertEquals(WAITING.instance, state);
	}
	
}
