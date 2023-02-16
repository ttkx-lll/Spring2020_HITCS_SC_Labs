package common.element;

import static org.junit.Assert.*;

import org.junit.Test;

/*
 * 测试策略：
 *    根据状态转移图进行测试。
 */
public class ENDEDTest {

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	/*
	 *  testing strategy：
	 *  	测试了对ENDED使用create方法的结果。
	 */
	@Test
	public void testCreate() {
		EntryState state = ENDED.instance;
		state = state.create();
		assertEquals(ENDED.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对ENDED使用allocate方法的结果。
	 */
	@Test
	public void testAllocate() {
		EntryState state = ENDED.instance;
		state = state.allocate();
		assertEquals(ENDED.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对ENDED使用state方法的结果。
	 */
	@Test
	public void testStart() {
		EntryState state = ENDED.instance;
		state = state.start();
		assertEquals(ENDED.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对ENDED使用cancel方法的结果。
	 */
	@Test
	public void testCancel() {
		EntryState state = ENDED.instance;
		state = state.cancel();
		assertEquals(ENDED.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对ENDED使用end方法的结果。
	 */
	@Test
	public void testEnd() {
		EntryState state = ENDED.instance;
		state = state.end();
		assertEquals(ENDED.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对ENDED使用block方法的结果。
	 */
	@Test
	public void testBlock() {
		EntryState state = ENDED.instance;
		state = state.block();
		assertEquals(ENDED.instance, state);
	}

	/*
	 *  testing strategy：
	 *  	测试了对ENDED使用restart方法的结果。
	 */
	@Test
	public void testRestart() {
		EntryState state = ENDED.instance;
		state = state.restart();
		assertEquals(ENDED.instance, state);
	}

}
