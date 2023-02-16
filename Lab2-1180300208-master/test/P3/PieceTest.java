package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class PieceTest {

	/* Testing strategy
     * 由于对owner, owner, type, pos, state没有特别要求
     * 因此简单测试逻辑即可
     */
	
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}
	
	@Test
	public void testGetOwner() {
		String owner = "testOwner";
		String type = "testPiece";
		boolean state = true;
		Piece p = new Piece(owner, type, state);
		assertEquals(owner, p.getOwner());
		
	}
	
	@Test
	public void testGetType() {
		String owner = "testOwner";
		String type = "testPiece";
		boolean state = true;
		Piece p = new Piece(owner, type, state);
		assertEquals(type, p.getType());
	}
	
	@Test
	public void testSetAndGetPos() {
		String owner = "testOwner";
		String type = "testPiece";
		boolean state = true;
		int x = 1, y = 2;
		Piece p = new Piece(owner, type, state);
		Position pos = new Position(x, y);
		p.setPos(x, y);
		assertEquals(true, p.getPos().equals(pos));
	}
	
	@Test
	public void testIsOnBoard() {
		String owner = "testOwner";
		String type = "testPiece";
		boolean state = true;
		Piece p = new Piece(owner, type, state);
		assertEquals(true, p.isOnBoard());
	}
	
	@Test
	public void testSetState() {
		String owner = "testOwner";
		String type = "testPiece";
		boolean state = true;
		Piece p = new Piece(owner, type, state);
		p.setState(false);
		assertEquals(false, p.isOnBoard());
	}
}
