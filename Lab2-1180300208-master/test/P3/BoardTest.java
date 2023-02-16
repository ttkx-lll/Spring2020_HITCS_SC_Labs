package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class BoardTest {

	/* Testing strategy
     * 
     * Partition the inputs as follows:
     * size: > 0 (因为是内部引用所以只测试大于0的情况)
     * type: true, false
     */
	
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}
	
	//covers getSize, getActualSize, getType
	//		 type: true, false
	@Test
	public void testGeneralBoard() {
		Board board1 = new Board(5, true);
		Board board2 = new Board(5, false);
		assertEquals(5, board1.getSize());
		assertEquals(6, board1.getActualSize());
		assertEquals(5, board2.getSize());
		assertEquals(5, board2.getActualSize());
		assertEquals(true, board1.getType());
		assertEquals(false, board2.getType());
	}
	
	// 由于此方法只提供对合法的位置进行查询，对其是否合法由
	// 其调用者进行判断，因此此方法中不进行非法状况的测试
	@Test
	public void testGetAndSetPiece() {
		Board board1 = new Board(5, true);
		Board board2 = new Board(5, false);
		Piece p1 = new Piece("lalala", "testPiece", true);
		Piece p2 = new Piece("lalala", "testPiece", true);
		Piece p3 = new Piece("lalala", "testPiece", true);
		Piece p4 = new Piece("lalala", "testPiece", true);
		board1.setPiece(p1, 4, 4);
		board1.setPiece(p2, 5, 5);
		board2.setPiece(p3, 4, 4);
		board2.setPiece(p4, 2, 4);
		assertEquals(p1, board1.getPiece(4, 4));
		assertEquals(null, board1.getPiece(3, 3));
		assertEquals(p2, board1.getPiece(5, 5));
		assertEquals(p3, board2.getPiece(4, 4));
		assertEquals(p4, board2.getPiece(2, 4));
		assertEquals(null, board2.getPiece(2, 3));
	}
	
	@Test
	public void testXYHasPiece() {
		Board board1 = new Board(5, true);
		Board board2 = new Board(5, false);
		Piece p1 = new Piece("lalala", "testPiece", true);
		Piece p2 = new Piece("lalala", "testPiece", true);
		Piece p3 = new Piece("lalala", "testPiece", true);
		Piece p4 = new Piece("lalala", "testPiece", true);
		board1.setPiece(p1, 4, 4);
		board1.setPiece(p2, 5, 5);
		board2.setPiece(p3, 4, 4);
		board2.setPiece(p4, 2, 4);
		assertEquals(true, board1.xyHasPiece(4, 4));
		assertEquals(true, board1.xyHasPiece(5, 5));
		assertEquals(false, board1.xyHasPiece(3, 4));
		assertEquals(true, board2.xyHasPiece(4, 4));
		assertEquals(true, board2.xyHasPiece(2, 4));
		assertEquals(false, board2.xyHasPiece(3, 3));
	}
	
	@Test
	public void testRemovePiece() {
		Board board1 = new Board(5, true);
		Board board2 = new Board(5, false);
		Piece p1 = new Piece("lalala", "testPiece", true);
		Piece p2 = new Piece("lalala", "testPiece", true);
		Piece p3 = new Piece("lalala", "testPiece", true);
		Piece p4 = new Piece("lalala", "testPiece", true);
		board1.setPiece(p1, 4, 4);
		board1.setPiece(p2, 5, 5);
		board2.setPiece(p3, 4, 4);
		board2.setPiece(p4, 2, 4);
		board1.removePiece(4, 4);
		board2.removePiece(2, 4);
		assertEquals(false, board1.xyHasPiece(4, 4));
		assertEquals(false, board2.xyHasPiece(2, 4));
		assertEquals(null, board1.getPiece(4, 4));
		assertEquals(null, board2.getPiece(2, 4));
	}
	
	@Test
	public void testChangePosition() {
		Board board1 = new Board(5, true);
		Board board2 = new Board(5, false);
		Piece p1 = new Piece("lalala", "testPiece", true);
		Piece p3 = new Piece("lalala", "testPiece", true);
		board1.setPiece(p1, 4, 4);
		assertEquals(true, board1.xyHasPiece(4, 4));
		assertEquals(p1, board1.getPiece(4, 4));
		board1.changePosition(4, 4, 5, 5);
		assertEquals(false, board1.xyHasPiece(4, 4));
		assertEquals(null, board1.getPiece(4, 4));
		assertEquals(true, board1.xyHasPiece(5, 5));
		assertEquals(p1, board1.getPiece(5, 5));
		board2.setPiece(p3, 4, 4);
		assertEquals(true, board2.xyHasPiece(4, 4));
		assertEquals(p3, board2.getPiece(4, 4));
		board2.changePosition(4, 4, 2, 4);
		assertEquals(false, board2.xyHasPiece(4, 4));
		assertEquals(null, board2.getPiece(4, 4));
		assertEquals(true, board2.xyHasPiece(2, 4));
		assertEquals(p3, board2.getPiece(2, 4));
	}

}
