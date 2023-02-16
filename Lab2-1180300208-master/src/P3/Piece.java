package P3;

/**
 * Specification Mutable.
 * message of a piece.
 * 
 * @author 梁书育
 *
 */
public class Piece {
	
	private final String owner;
	private final String type;
	private Position pos;
	private boolean state;
	
	// Abstraction function:
	// 	  owner的一个类型为type的棋子，位置为pos，如果在棋盘上则状态为true，不在棋盘上状态为false
	
	// Representation invariant:
	//    对owner, type, pos, state没有特别要求
	
	// Safety from rep exposure:
	//    使用了final关键字
	
	/**
	 * Ensure a piece's owner and type.
	 * 
	 * @param owner player owning this piece
	 * @param type this piece's type
	 * @param state true if piece on the board, false if piece not on the board
	 */
	public Piece(String owner, String type, boolean state) {
		this.owner = owner;
		this.type = type;
		this.state = state;
		this.pos = new Position(0, 0);
	}

	/**
	 * Get the piece's owner.
	 * 
	 * @return the piece's owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Get the piece's type.
	 * 
	 * @return the piece's type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Get the piece's position.
	 * 
	 * @return the piece's position
	 */
	public Position getPos() {
		return pos.getPosition();
	}

	/**
	 * Get the state of the piece.
	 * <p>true->on the board; false->not on the board.
	 * 
	 * @return state
	 */
	public boolean isOnBoard() {
		return state;
	}
	
	/**
	 * Set the piece's position.
	 * 
	 * @param x the abscissa to change
	 * @param y the ordinate to change
	 * @return true if the change is successful;false if the 
	 * 		   position to change is equals to the former one.
	 */
	public boolean setPos(int x, int y) {
		return pos.setPosition(x, y);
	}
	
	/**
	 * Set the state of the piece.
	 * 
	 * @param state true if the piece is on the board;
	 * 				false if the piece is not on the board
	 */
	public void setState(boolean state) {
		this.state = state;
	}

}
