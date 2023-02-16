package P3;


/**
 * Specification Mutable. This class represent 
 * a piece's position on the board.
 * 
 * @author 梁书育
 */
public class Position {
	
	private int x;
	private int y;
	
	// Abstraction function:
	// 	   坐标为(x, y)的位置
	
	// Representation invariant:
	//    对x和y没有要求
	
	// Safety from rep exposure:
	//    返回时使用了防御性拷贝
	
	/**
	 * Create a new position for a piece.
	 * 
	 * @param x the abscissa of that position
	 * @param y the vertical coordinate of the position
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Change the message of the position.
	 * 
	 * @param x the abscissa to change
	 * @param y the ordinate to change
	 * @return true if the change is successful;false if the 
	 * 		   position to change is equals to the former one.
	 */
	public boolean setPosition(int x, int y) {
		if(this.x == x && this.y == y) {
			return false;
		}
		else {
			this.x = x;
			this.y = y;
			return true;
		}
	}

	/**
	 * Get the abscissa.
	 * 
	 * @return the value of abscissa
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Get the ordinate.
	 * 
	 * @return the value of ordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Get the position.
	 * 
	 * @return a copy of this position
	 */
	public Position getPosition() {
		return new Position(x, y);
	}
	
	/**
	 * Determine if two positions are equal.
	 * 
	 * @param that is another position
	 * @return true if equal; false if not equal
	 */
	@Override
	public boolean equals(Object that) {
		if(!(that instanceof Position)) return false;
		Position thatPos = (Position) that;
		if(x == thatPos.getX() && y == thatPos.getY()) {
			return true;
		}
		return false;
	}
	
	
}
