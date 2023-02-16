package P3;

public class ChessAction implements Action {

	private Player player1;
	private Player player2;
	private Board board;
	
	// Abstraction function:
	// 	  chess游戏，由两位玩家和一个棋盘构成
	
	// Representation invariant:
	//    两位玩家不能相同，名字也不能为空
	
	// Safety from rep exposure:
	//    不将玩家和棋盘信息暴露出去
	
	// checkRep
	public void checkRep() {
		assert !player1.getName().equals(player2.getName());
		assert !player1.getName().equals("");
		assert !player2.getName().equals("");
	}

	
	@Override
	public void createPlayer1(String player1) {
		this.player1 = new Player(player1);
		this.player1.addPiece(new Piece(player1, "WhiteKing", false));
		this.player1.addPiece(new Piece(player1, "WhiteQueue", false));
		this.player1.addPiece(new Piece(player1, "WhiteRook", false));
		this.player1.addPiece(new Piece(player1, "WhiteRook", false));
		this.player1.addPiece(new Piece(player1, "WhiteBishop", false));
		this.player1.addPiece(new Piece(player1, "WhiteBishop", false));
		this.player1.addPiece(new Piece(player1, "WhiteKnight", false));
		this.player1.addPiece(new Piece(player1, "WhiteKnight", false));
		for(int i = 0; i < 8; i++) {
			this.player1.addPiece(new Piece(player1, "WhitePawn", false));
		}
	}

	@Override
	public void createPlayer2(String player2) {
		this.player2 = new Player(player2);
		this.player2.addPiece(new Piece(player2, "BlackKing", false));
		this.player2.addPiece(new Piece(player2, "BlackQueue", false));
		this.player2.addPiece(new Piece(player2, "BlackRook", false));
		this.player2.addPiece(new Piece(player2, "BlackRook", false));
		this.player2.addPiece(new Piece(player2, "BlackBishop", false));
		this.player2.addPiece(new Piece(player2, "BlackBishop", false));
		this.player2.addPiece(new Piece(player2, "BlackKnight", false));
		this.player2.addPiece(new Piece(player2, "BlackKnight", false));
		for(int i = 0; i < 8; i++) {
			this.player2.addPiece(new Piece(player2, "BlackPawn", false));
		}
		checkRep();
	}

	@Override
	public void createBoard() {
		this.board = new Board(8, false);
		int xPawn = 0;
		for(Piece p : this.player1.getPieceSet()) {
			if(p.getType().equals("WhitePawn") && !p.isOnBoard()) {
				board.setPiece(p, xPawn, 1);
				xPawn++;
			}
			else if(p.getType().equals("WhiteQueue") && !p.isOnBoard()) {
				board.setPiece(p, 3, 0);
			}
			else if(p.getType().equals("WhiteKing") && !p.isOnBoard()) {
				board.setPiece(p, 4, 0);
			}
			else if(p.getType().equals("WhiteBishop") && !p.isOnBoard()) {
				if(!board.xyHasPiece(2, 0)) {
					board.setPiece(p, 2, 0);
				}
				else {
					board.setPiece(p, 5, 0);
				}
			}
			else if(p.getType().equals("WhiteKnight") && !p.isOnBoard()) {
				if(!board.xyHasPiece(1, 0)) {
					board.setPiece(p, 1, 0);
				}
				else {
					board.setPiece(p, 6, 0);
				}
			}
			else if(p.getType().equals("WhiteRook") && !p.isOnBoard()) {
				if(!board.xyHasPiece(0, 0)) {
					board.setPiece(p, 0, 0);
				}
				else {
					board.setPiece(p, 7, 0);
				}
			}
		}
		xPawn = 0;
		for(Piece p : this.player2.getPieceSet()) {
			if(p.getType().equals("BlackPawn") && !p.isOnBoard()) {
				board.setPiece(p, xPawn++, 6);
			}
			else if(p.getType().equals("BlackQueue") && !p.isOnBoard()) {
				board.setPiece(p, 3, 7);
			}
			else if(p.getType().equals("BlackKing") && !p.isOnBoard()) {
				board.setPiece(p, 4, 7);
			}
			else if(p.getType().equals("BlackBishop") && !p.isOnBoard()) {
				if(!board.xyHasPiece(2, 7)) {
					board.setPiece(p, 2, 7);
				}
				else {
					board.setPiece(p, 5, 7);
				}
			}
			else if(p.getType().equals("BlackKnight") && !p.isOnBoard()) {
				if(!board.xyHasPiece(1, 7)) {
					board.setPiece(p, 1, 7);
				}
				else {
					board.setPiece(p, 6, 7);
				}
			}
			else if(p.getType().equals("BlackRook") && !p.isOnBoard()) {
				if(!board.xyHasPiece(0, 7)) {
					board.setPiece(p, 0, 7);
				}
				else {
					board.setPiece(p, 7, 7);
				}
			}
		}
	}

	@Override
	public boolean movePiece(Player player, int x1, int y1, int x2, int y2) {
		
		if(x1 < 0 || x1 > 7 || y1 < 0 || y1 > 7) {
			System.out.println("起始位置超出了棋盘的范围，没有棋子！");
			return false;
		}
		else if(x2 < 0 || x2 > 7 || y2 < 0 || y2 > 7) {
			System.out.println("要落子的位置超出了棋盘的范围，移动失败！");
			return false;
		}
		else if(!board.xyHasPiece(x1, y1)) {
			System.out.println("该位置没有棋子！");
			return false;
		}
		else if(x1 == x2 && y1 == y2) {
			System.out.println("起始目的位置相同！");
			return false;
		}
		else if(!this.board.getPiece(x1, y1).getOwner().equals(player.getName())) {
			System.out.println("该棋子非己方棋子！");
			return false;
		}
		else if(board.xyHasPiece(x2, y2)) {
			System.out.println("目的地已有棋子！");
			return false;
		}
		else {
			String history = player.getName() + " removes a " + board.getPiece(x1, y1).getType()
					+ " from (" + x1 + "," + y1 + ") to " + "(" + x2 + "," + y2 + ")";
			player.addHistory(history);
			board.changePosition(x1, y1, x2, y2);
			System.out.println("操作成功！");
			return true;
		}
	}
	
	@Override
	public boolean eatPiece(Player player, int x1, int y1, int x2, int y2) {
		
		if(x1 < 0 || x1 > 7|| y1 < 0 || y1 > 7) {
			System.out.println("起始位置超出了棋盘的范围，没有棋子！");
			return false;
		}
		else if(x2 < 0 || x2 > 7 || y2 < 0 || y2 > 7) {
			System.out.println("要吃子的位置超出了棋盘的范围，移动失败！");
			return false;
		}
		else if(!board.xyHasPiece(x1, y1)) {
			System.out.println("起始位置没有棋子！");
			return false;
		}
		else if(!board.xyHasPiece(x2, y2)) {
			System.out.println("目的位置没有棋子！");
			return false;
		}
		else if(x1 == x2 && y1 == y2) {
			System.out.println("起始目的位置相同！");
			return false;
		}
		else if(!this.board.getPiece(x1, y1).getOwner().equals(player.getName())) {
			System.out.println("起始位置不是自己的棋子！");
			return false;
		}
		else if(this.board.getPiece(x2, y2).getOwner().equals(player.getName())) {
			System.out.println("目的位置不是对方的棋子！");
			return false;
		}
		else {
			String history = player.getName() + " uses a " + board.getPiece(x1, y1).getType() + " at (" + x1 + "," + y1 + ")"
					+ " to eat the " + board.getPiece(x2, y2).getOwner() + "'s " + board.getPiece(x2, y2).getType() + " at (" + x2 + "," + y2 + ")";
			player.addHistory(history);
			board.removePiece(x2, y2);
			board.changePosition(x1, y1, x2, y2);
			System.out.println("操作成功！");
			return true;
		}
	}

	@Override
	public boolean placePiece(Player player, Piece piece, int x, int y) {
		System.out.println("Chess中无此操作！");
		return false;
	}

	@Override
	public boolean removePiece(Player player, int x, int y) {
		System.out.println("Chess中无此操作！");
		return false;
	}
	
	@Override
	public void printBoard() {
		board.printBoard();
	}
	
	@Override
	public Player getPlayer1() {
		return player1;
	}

	@Override
	public Player getPlayer2() {
		return player2;
	}
	
	@Override
	public Piece getPieceAtXY(int x, int y) {
		if(x < 0 || x > 7 || y < 0 || y > 7) {
			System.out.println("该位置超出了棋盘的范围！");
			return null;
		}
		else {
			return board.getPiece(x, y);
		}
	}

	@Override
	public void printHistory(Player player) {
		for(String str : player.getHistory())
			System.out.println(str);
	}
}
