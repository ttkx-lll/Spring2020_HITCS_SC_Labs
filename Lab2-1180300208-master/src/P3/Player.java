package P3;

import java.util.*;

/**
 * Specification Mutable.
 * Message of a player, piece's he owned and his history action record.
 * 
 * @author 梁书育
 *
 */
public class Player {
	
	private final String name;
	private final Set<Piece> pieceSet = new HashSet<>();
	private final List<String> history = new ArrayList<>();
	
	// Abstraction function:
	// 	  一个名字为name的玩家，其棋子集合为pieceSet，走棋历史为history
	
	// Representation invariant:
	//    名字name不能为空
	
	// Safety from rep exposure:
	//    使用了final关键字

	public void checkRep() {
		assert !name.equals("");
	}

	
	/**
	 * Create a player.
	 * 
	 * @param name is this player's name
	 */
	public Player(String name) {
		this.name = name;
	}

	/**
	 * Add a piece to this player.
	 * 
	 * @param piece is willing to add to this player.
	 * @return false if this player has owned this piece; 
	 *         true if successfully add this piece to the player 
	 */
	public boolean addPiece(Piece piece) {
		if(pieceSet.contains(piece)) {
			return false;
		}
		else if(!piece.getOwner().equals(name)) {
			return false;
		}
		else {
			pieceSet.add(piece);
			return true;
		}
	}
	
	/**
	 * Calculate the number of pieces on the board.
	 * 
	 * @return the number of pieces on the board
	 */
	public int pieceNumber() {
		int number = 0;
		for(Piece p : pieceSet) {
			if(p.isOnBoard())
				number++;
		}
		return number;
	}
	
	/**
	 * Add action message to history record.
	 * 
	 * @param history is an action message
	 */
	public void addHistory(String history) {
		this.history.add(history);
	}
	
	/**
	 * Get the name of the player.
	 * 
	 * @return the player's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the piece set of the player.
	 * 
	 * @return piece set of the player
	 */
	public Set<Piece> getPieceSet() {
		return pieceSet;
	}

	/**
	 * Get the history record of this player.
	 * 
	 * @return history list
	 */
	public List<String> getHistory() {
		List<String> list = new ArrayList<String>();
		list.addAll(history);
		return list;
	}
	
	
}
