package common.element;

import static org.junit.Assert.*;

import org.junit.Test;

/*
 * 测试策略：
 *    根据状态转移图进行测试。
 */
public class CANCELLEDTest {

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testCreate() {
		EntryState state = CANCELLED.instance;
		state = state.create();
		assertEquals(CANCELLED.instance, state);
	}

	@Test
	public void testAllocate() {
		EntryState state = CANCELLED.instance;
		state = state.allocate();
		assertEquals(CANCELLED.instance, state);
	}

	@Test
	public void testStart() {
		EntryState state = CANCELLED.instance;
		state = state.start();
		assertEquals(CANCELLED.instance, state);
	}

	@Test
	public void testCancel() {
		EntryState state = CANCELLED.instance;
		state = state.cancel();
		assertEquals(CANCELLED.instance, state);
	}

	@Test
	public void testEnd() {
		EntryState state = CANCELLED.instance;
		state = state.end();
		assertEquals(CANCELLED.instance, state);
	}

	@Test
	public void testBlock() {
		EntryState state = CANCELLED.instance;
		state = state.block();
		assertEquals(CANCELLED.instance, state);
	}

	@Test
	public void testRestart() {
		EntryState state = CANCELLED.instance;
		state = state.restart();
		assertEquals(CANCELLED.instance, state);
	}

}
