package P3;

/**
 * Actions needed by different games.
 * 
 * @author 梁书育
 *
 */
public interface Action {

	/**
	 * Create a required game.
	 * 
	 * @param game the type of the game
	 * @return actions a required type of game needs
	 */
	public static Action empyt(String game) {
		if(game.toLowerCase().equals("go")) {
			return new GoAction();
		}
		else if(game.toLowerCase().equals("chess")) {
			return new ChessAction();
		}
		else {
			System.out.println("Don't have this type of game!");
			return null;
		}
	}
	
	/**
	 * Initialize the player1.
	 * 
	 * @param player1 the name of player1
	 */
	public void createPlayer1(String player1);
	
	/**
	 * Initialize the player2.
	 * 
	 * @param player2 the name of player2
	 */
	public void createPlayer2(String player2);
	
	/**
	 * Initialize a new board.
	 * 
	 */
	public void createBoard();
	
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
	public boolean placePiece(Player player, Piece piece, int x, int y);
	
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
	public boolean removePiece(Player player, int x, int y);
	
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
	public boolean movePiece(Player player, int x1, int y1, int x2, int y2);
	
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
	public boolean eatPiece(Player player, int x1, int y1, int x2, int y2);
	
	/**
	 * Print the board.
	 */
	public void printBoard();
	
	/**
	 * Get player1.
	 * 
	 * @return player1
	 */
	public Player getPlayer1();
	
	/**
	 * Get player2.
	 * 
	 * @return player2
	 */
	public Player getPlayer2();
	
	/**
	 * Get the piece at (x, y).
	 * 
	 * @param x x coordinate on the board
	 * @param y y coordinate on the board
	 * @return the piece at (x, y)
	 */
	public Piece getPieceAtXY(int x, int y);
	
	/**
	 * Print a player's action history.
	 * 
	 * @param player
	 */
	public void printHistory(Player player);
}
