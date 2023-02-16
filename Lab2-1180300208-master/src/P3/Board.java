package P3;

/**
 * Specification Mutable. Create a board for a chess game.
 * 
 * @author 梁书育
 *
 */
public class Board {

	private final boolean isCrossPlace;
	private final int size;
	private final int actualSize;
	private Piece[][] pieces;
	private boolean[][] hasPiece;
	
	// Abstraction function:
	// 	  一个n*n的棋盘，棋子放在格中或者格点处
	
	// Representation invariant:
	//    如果是格点处放置棋子，应该有(n+1)*(n+1)个放置位置; 
	//	    如果是格中放置棋子，应该有n*n个放置位置
	
	// Safety from rep exposure:
	//    使用了final标签
	//    不直接返回board而是直接打印，所以RI一旦确定讲不会被改变，无需对representation进行检查
	
	/**
	 * Create a board;
	 * 
	 * @param size
	 * @param isCrossPlce true if piece should place on the cross;
	 * 			   		  false if piece should place in the blank.
	 */
	public Board(int size, boolean isCrossPlace) {
		this.size = size;
		this.isCrossPlace = isCrossPlace;
		
		if(isCrossPlace) actualSize = size + 1;
		else actualSize = size;
		
		pieces = new Piece[actualSize][actualSize];
		hasPiece = new boolean[actualSize][actualSize];
		
		for(int i = 0; i < actualSize; i++) {
			for(int j = 0; j < actualSize; j++) {
				hasPiece[i][j] = false;
			}
		}
		
		for(int i = 0; i < actualSize; i++) {
			for(int j = 0; j < actualSize; j++) {
				pieces[i][j] = null;
			}
		}
	}
	
	/**
	 * Get the piece at (x,y).
	 * @param x x coordinate on the board
	 * @param y y coordinate on the board
	 * @return the piece at (x, y)
	 */
	public Piece getPiece(int x, int y) {
		return pieces[x][y];
	}
	
	/**
	 * 
	 * @param x x coordinate on the board
	 * @param y y coordinate on the board
	 * @return true if (x, y) has piece;
	 * 		   false if (x, y) doesn't have piece
	 */
	public boolean xyHasPiece(int x, int y) {
		return hasPiece[x][y];
	}
	
	/**
	 * Place a piece at (x, y)
	 * @param the piece want to place
	 * @param x x coordinate on the board
	 * @param y y coordinate on the board
	 */
	public void setPiece(Piece piece, int x, int y) {
		piece.setPos(x, y);
		piece.setState(true);
		pieces[x][y] = piece;
		hasPiece[x][y] = true;
	}
	
	/**
	 * Remove the piece at (x, y)
	 * 
	 * @param x x coordinate on the board
	 * @param y y coordinate on the board
	 */
	public void removePiece(int x, int y) {
		pieces[x][y].setState(false);
		pieces[x][y] = null;
		hasPiece[x][y] = false;
	}
	/**
	 * Change a piece from (x1, y1) to (x2, y2).
	 * 
	 * @param x1 initial x coordinate on the board
	 * @param y1 initial y coordinate on the board
	 * @param x2 target x coordinate on the board
	 * @param y2 target y coordinate on the board
	 */
	public void changePosition(int x1, int y1, int x2, int y2) {
		Piece piece = this.getPiece(x1, y1);
		piece.setPos(x2, y2);
		pieces[x1][y1] = null;
		hasPiece[x1][y1] = false;
		pieces[x2][y2] = piece;
		hasPiece[x2][y2] = true;
	}
	
	/**
	 * Get the size of the board.
	 * 
	 * @return board's actual size
	 */
	public int getActualSize() {
		return actualSize;
	}
	
	/**
	 * Get the size of the board.
	 * 
	 * @return board's size
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Get the type of the board.
	 * 
	 * @return true if piece should place on the cross
	 *         false if piece should place in the blank
	 */
	public boolean getType() {
		return isCrossPlace;
	}
	
	/**
	 * Print the board.
	 */
	public void printBoard() {
		for(int i = 0; i < actualSize; i++) {
			for(int j = 0; j < actualSize; j++) {
				if(pieces[i][j] == null)
					System.out.print("nullPiece\t");
				else
					System.out.print(pieces[i][j].getType() + "\t");
			}
			System.out.println();
		}
	}
}
