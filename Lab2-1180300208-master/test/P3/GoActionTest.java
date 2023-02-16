package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class GoActionTest {

	/* Testing strategy
     * 按照游戏操作进行测试即可
     */
	
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}
	
	// covers: 棋子是自己的/棋子是对方的
	//         放置的位置在棋盘范围内/超出了棋盘的范围
	//		     放置的位置没有棋子/已经有棋子占用
	@Test
	public void testPlacePiece() {
		Action action = Action.empyt("go");
		action.createPlayer1("player1");
		action.createPlayer2("player2");
		action.createBoard();
		for(Piece p : action.getPlayer1().getPieceSet()) {
			if(!p.isOnBoard()) {
				assertEquals(true, 
						action.placePiece(action.getPlayer1(), p, 1, 2));
				break;
			}
		}
		for(Piece p : action.getPlayer2().getPieceSet()) {
			if(!p.isOnBoard()) {
				assertEquals(false, 
						action.placePiece(action.getPlayer1(), p, 1, 2));
				break;
			}
		}
		for(Piece p : action.getPlayer1().getPieceSet()) {
			if(!p.isOnBoard()) {
				assertEquals(false, 
						action.placePiece(action.getPlayer1(), p, 20, 2));
				break;
			}
		}
		for(Piece p : action.getPlayer1().getPieceSet()) {
			if(p.isOnBoard()) {
				assertEquals(false, 
						action.placePiece(action.getPlayer1(), p, 1, 2));
				break;
			}
		}
		for(Piece p : action.getPlayer1().getPieceSet()) {
			if(!p.isOnBoard()) {
				assertEquals(false, 
						action.placePiece(action.getPlayer1(), p, 1, 2));
				break;
			}
		}
				
	}

	// covers: 要移去棋子是自己的/棋子是对方的
	//         移除的位置在棋盘范围内/超出了棋盘的范围
	//		     移除的位置没有棋子/已经有棋子占用
	@Test
	public void testRemovePiece() {
		Action action = Action.empyt("go");
		action.createPlayer1("player1");
		action.createPlayer2("player2");
		action.createBoard();
		for(Piece p : action.getPlayer1().getPieceSet()) {
			if(!p.isOnBoard()) {
				action.placePiece(action.getPlayer1(), p, 1, 2);
				break;
			}
		}
		for(Piece p : action.getPlayer2().getPieceSet()) {
			if(!p.isOnBoard()) {
				action.placePiece(action.getPlayer2(), p, 3, 4);
				break;
			}
		}
		assertEquals(true, action.removePiece(action.getPlayer1(), 3, 4));
		assertEquals(false, action.removePiece(action.getPlayer1(), 20, 2));
		assertEquals(false, action.removePiece(action.getPlayer1(), 3, 4));
		assertEquals(false, action.removePiece(action.getPlayer1(), 1, 2));
	}

	// covers: 由于Go没有这一操作，测试结果一定都为false
	@Test
	public void testMovePiece() {
		Action action = Action.empyt("go");
		action.createPlayer1("player1");
		action.createPlayer2("player2");
		action.createBoard();
		assertEquals(false, action.movePiece(action.getPlayer1(), 1, 3, 2, 4));
	}

	// covers: 由于Go没有这一操作，测试结果一定都为false
	@Test
	public void testEatPiece() {
		Action action = Action.empyt("go");
		action.createPlayer1("player1");
		action.createPlayer2("player2");
		action.createBoard();
		assertEquals(false, action.eatPiece(action.getPlayer1(), 1, 3, 2, 4));
	}

	// covers: 要得到棋子信息的位置在棋盘范围内/超出了棋盘的范围
	//         要得到棋子信息放置的位置没有棋子/已经有棋子占用
	@Test
	public void testGetPieceAtXY() {
		Action action = Action.empyt("go");
		action.createPlayer1("player1");
		action.createPlayer2("player2");
		action.createBoard();
		Piece p1 = null, p2 = null;
		for(Piece p : action.getPlayer1().getPieceSet()) {
			if(!p.isOnBoard()) {
				p1 = p;
				assertEquals(true, 
						action.placePiece(action.getPlayer1(), p, 1, 2));
				break;
			}
		}
		for(Piece p : action.getPlayer1().getPieceSet()) {
			if(!p.isOnBoard()) {
				p2 = p;
				assertEquals(true, 
						action.placePiece(action.getPlayer1(), p, 3, 4));
				break;
			}
		}
		assertEquals(null, action.getPieceAtXY(0, 0));
		assertEquals(null, action.getPieceAtXY(20, 20));
		assertEquals(p1, action.getPieceAtXY(1, 2));
		assertEquals(p2, action.getPieceAtXY(3, 4));
	}
}
