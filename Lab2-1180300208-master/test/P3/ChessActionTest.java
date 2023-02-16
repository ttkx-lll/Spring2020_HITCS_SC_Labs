package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class ChessActionTest {
	
	/* Testing strategy
     * 按照游戏操作进行测试即可
     */
	
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}
	
	// covers: 由于Chess没有这一操作，测试结果一定都为false
	@Test
	public void testPlacePiece() {
		Action action = Action.empyt("chess");
		action.createPlayer1("player1");
		action.createPlayer2("player2");
		action.createBoard();
		action.eatPiece(action.getPlayer2(), 7, 7, 0, 0);
		for(Piece p : action.getPlayer1().getPieceSet()) {
			if(!p.isOnBoard()) {
				assertEquals(false, 
						action.placePiece(action.getPlayer1(), p, 1, 2));
				break;
			}
		}
	}

	// covers: 由于Chess没有这一操作，测试结果一定都为false
	@Test
	public void testRemovePiece() {
		Action action = Action.empyt("chess");
		action.createPlayer1("player1");
		action.createPlayer2("player2");
		action.createBoard();
		assertEquals(false, action.removePiece(action.getPlayer1(), 2, 2));
	}
	
	// covers: 起始位置在棋盘范围内/超出棋盘范围
	//         落子位置在棋盘范围内/超出棋盘范围
	//         起始位置有棋子/没有棋子
	//         落子位置有棋子/没有棋子
	//         移动棋子是本方棋子/是对方棋子
	//         起始位置和落子位置不是同一位置/是同一位置
	@Test
	public void testMovePiece() {
		Action action = Action.empyt("chess");
		action.createPlayer1("player1");
		action.createPlayer2("player2");
		action.createBoard();
		assertEquals(true, action.movePiece(action.getPlayer1(), 0, 0, 4, 4));
		assertEquals(false, action.movePiece(action.getPlayer1(), 9, 9, 4, 4));
		assertEquals(false, action.movePiece(action.getPlayer1(), 1, 0, 9, 9));
		assertEquals(false, action.movePiece(action.getPlayer1(), 3, 4, 4, 5));
		assertEquals(false, action.movePiece(action.getPlayer1(), 1, 0, 7, 7));
		assertEquals(false, action.movePiece(action.getPlayer1(), 7, 7, 5, 5));
		assertEquals(false, action.movePiece(action.getPlayer1(), 1, 0, 1, 0));
	}
	
	// covers: 起始位置在棋盘范围内/超出棋盘范围
	//         落子位置在棋盘范围内/超出棋盘范围
	//         起始位置有棋子/没有棋子
	//         落子位置有棋子/没有棋子
	//         移动棋子是本方棋子/是对方棋子
	//         起始位置和落子位置不是同一位置/是同一位置
	@Test
	public void testEatPiece() {
		Action action = Action.empyt("chess");
		action.createPlayer1("player1");
		action.createPlayer2("player2");
		action.createBoard();
		assertEquals(true, action.eatPiece(action.getPlayer1(), 0, 0, 7, 7));
		assertEquals(false, action.eatPiece(action.getPlayer1(), 9, 9, 7, 6));
		assertEquals(false, action.eatPiece(action.getPlayer1(), 1, 0, 9, 9));
		assertEquals(false, action.eatPiece(action.getPlayer1(), 3, 4, 7, 5));
		assertEquals(false, action.eatPiece(action.getPlayer1(), 1, 0, 4, 5));
		assertEquals(false, action.eatPiece(action.getPlayer1(), 5, 7, 6, 7));
		assertEquals(false, action.eatPiece(action.getPlayer1(), 1, 0, 0, 1));
		assertEquals(false, action.eatPiece(action.getPlayer1(), 1, 0, 1, 0));
	}
	
	// covers: 要得到棋子信息的位置在棋盘范围内/超出了棋盘的范围
	//         要得到棋子信息放置的位置没有棋子/已经有棋子占用
	@Test
	public void testGetPieceAtXY() {
		Action action = Action.empyt("chess");
		action.createPlayer1("player1");
		action.createPlayer2("player2");
		action.createBoard();
		Piece p1 = null;
		for(Piece p : action.getPlayer1().getPieceSet()) {
			if(p.getPos().getX() == 0 && p.getPos().getY() == 0) {
				p1 = p;
				break;
			}
		}
		assertEquals(p1, action.getPieceAtXY(0, 0));
		assertEquals(null, action.getPieceAtXY(9, 9));
		assertEquals(null, action.getPieceAtXY(4, 5));
		
	}

}
