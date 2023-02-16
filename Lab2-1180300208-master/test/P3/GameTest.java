package P3;

import org.junit.Test;

public class GameTest {

	/* Testing strategy
     * game类只是对GoAction和ChessAction的包装，其中各种方法的测试在
     * GoAction和ChessAction中已经测试过，所以在这里不再进行测试。
     */
	
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}

}
