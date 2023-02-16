package P3;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class PlayerTest {

	/* Testing strategy
     * 在添加棋子时要注意该棋子是否重复添加
     * 该棋子是否属于该player
     */
	
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}
	
	//covers: the piece is added to the player again.
	//        the piece is not belonging to this player.
	@Test
	public void testAddPieceAndGetPieceSet() {
		String name = "testPlayer";
		String pieceType = "testPiece";
		boolean pieceState = true;
		Set<Piece> set = new HashSet<>();
		Player p = new Player(name);
		Piece p1 = new Piece(name, pieceType, pieceState);
		Piece p2 = new Piece(name, pieceType, pieceState);
		Piece p3 = new Piece(name, pieceType, pieceState);
		Piece p4 = new Piece("notplayer", pieceType, pieceState);
		assertEquals(set, p.getPieceSet());
		set.add(p1);
		assertEquals(false, p.addPiece(p4));
		assertEquals(true, p.addPiece(p1));
		assertEquals(set, p.getPieceSet());
		assertEquals(false, p.addPiece(p1));
		assertEquals(set, p.getPieceSet());
		set.add(p2);
		assertEquals(true, p.addPiece(p2));
		assertEquals(set, p.getPieceSet());
		set.add(p3);
		assertEquals(true, p.addPiece(p3));
		assertEquals(set, p.getPieceSet());
		assertEquals(false, p.addPiece(p2));
		assertEquals(set, p.getPieceSet());
		
	}
	
	@Test
	public void testPieceNumber() {
		String name = "testPlayer";
		String pieceType = "testPiece";
		boolean pieceState = true;
		Player p = new Player(name);
		Piece p1 = new Piece(name, pieceType, pieceState);
		Piece p2 = new Piece(name, pieceType, pieceState);
		Piece p3 = new Piece(name, pieceType, pieceState);
		p.addPiece(p1); p.addPiece(p2); p.addPiece(p3);
		assertEquals(3, p.pieceNumber());
		p1.setState(false);
		assertEquals(2, p.pieceNumber());
	}

	@Test
	public void testGetName() {
		String name = "testPlayer";
		Player p = new Player(name);
		assertEquals(name, p.getName());
	}
	
}
