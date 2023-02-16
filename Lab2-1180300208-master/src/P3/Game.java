package P3;

/**
 * Generate different actions to a Class for convenience.
 * 
 * @author 梁书育
 *
 */
public class Game {

	private final Action action;
	
	// Abstraction function:
	// 	  作为ChessAction和GoAction的包装供MyChessAndGoGame调用
	
	// Representation invariant:
	//    一经创建就不会被修改
	
	// Safety from rep exposure:
	//    使用final关键字
	
	/**
	 * Create a new game.
	 * 
	 * @param game the name of the game
	 * @param player1 the first player of this game
	 * @param player2 the second player of this game
	 */
	public Game(String game, String player1, String player2) {
		action = Action.empyt(game);
		action.createPlayer1(player1);
		action.createPlayer2(player2);
		action.createBoard();
		System.out.println(game + "游戏创建成功！");
		System.out.println("棋盘如下所示：");
		action.printBoard();
	}
	
	/**
	 * Get the first player of this game.
	 * 
	 * @return the first player
	 */
	public Player getPlayer1() {
		return action.getPlayer1();
	}
	
	/**
	 * Get the second player of this game.
	 * 
	 * @return the second player
	 */
	public Player getPlayer2() {
		return action.getPlayer2();
	}
	
	/**
	 * Get the piece at (x, y).
	 * 
	 * @param x x coordinate on the board
	 * @param y y coordinate on the board
	 * @return the piece at (x, y)
	 */
	public Piece getPiece(int x, int y) {
		return action.getPieceAtXY(x, y);
	}
	
	/**
	 * Place a Piece of a player to (x, y).
	 * This method is used in Go game!
	 * 
	 * @param player the certain player
	 * @param piece the certain piece
	 * @param x x coordinate on the board
	 * @param y y coordinate on the board
	 * @return true if place successfully;
	 * 		   false if this action is illegal.
	 */
	public boolean placePiece(Player player, Piece piece, int x, int y) {
		return action.placePiece(player, piece, x, y);
	}
	
	/**
	 * Remove a player's certain piece.
	 * This method is used in Go game!
	 * 
	 * @param player whose piece will be removed
	 * @param x x coordinate on the board
	 * @param y y coordinate on the board
	 * @return true if this action is successful;
	 * 		   false if this action is illegal.
	 */
	public boolean removePiece(Player player, int x, int y) {
		return action.removePiece(player, x, y);
	}
	
	/**
	 * Move a piece from (x1, y1) to (x2, y2).
	 * This method is used in Chess game!
	 * 
	 * @param player whose piece will be moved
	 * @param x1 x1 coordinate on the board
	 * @param y1 y1 coordinate on the board
	 * @param x2 x2 coordinate on the board
	 * @param y2 y2 coordinate on the board
	 * @return true if this action is successful;
	 * 		   false if this action is illegal.
	 */
	public boolean movePiece(Player player, int x1, int y1, int x2, int y2) {
		return action.movePiece(player, x1, y1, x2, y2);
	}
	
	/**
	 * A piece at (x1, y1) replaces the piece at (x2, y2).
	 * This method is used in Chess game!
	 * 
	 * @param player
	 * @param x1 x1 coordinate on the board
	 * @param y1 y1 coordinate on the board
	 * @param x2 x2 coordinate on the board
	 * @param y2 y2 coordinate on the board
	 * @return true if this action is successful;
	 * 		   false if this action is illegal.
	 */
	public boolean eatPiece(Player player, int x1, int y1, int x2, int y2) {
		return action.eatPiece(player, x1, y1, x2, y2);
	}
	
	/**
	 * Print the board.
	 */
	public void printBoard() {
		action.printBoard();
	}
	
	/**
	 * Print a player's action history.
	 * 
	 * @param player
	 */
	public void printHistory(Player player) {
		action.printHistory(player);
	}
}
