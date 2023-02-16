package P3;

public class GoAction implements Action {

	private Player player1;
	private Player player2;
	private Board board;
	
	// Abstraction function:
	// 	  go游戏，由两位玩家和一个棋盘构成
	
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
		for(int i = 0; i < 361; i++) {
			this.player1.addPiece(new Piece(player1, "whitePiece", false));
		}
	}
	
	@Override
	public void createPlayer2(String player2) {
		this.player2 = new Player(player2);
		for(int i = 0; i < 361; i++) {
			this.player2.addPiece(new Piece(player2, "blackPiece", false));
		}
		checkRep();
	}

	@Override
	public void createBoard() {
		board = new Board(18, true);
	}

	@Override
	public boolean placePiece(Player player, Piece piece, int x, int y) {
		if(!piece.getOwner().equals(player.getName())) {
			System.out.println("该棋子为对方棋子，不可放置！");
			return false;
		}
		else if(x < 0 || x > 18 || y < 0 || y > 18) {
			System.out.println("该位置超出了棋盘的范围！");
			return false;
		}
		else if(piece.isOnBoard()) {
			System.out.println("该棋子已经在棋盘上，无法移动！");
			return false;
		}
		else if(board.xyHasPiece(x, y)) {
			System.out.println("该位置已经有棋子，无法放置！");
			return false;
		}
		else {
			board.setPiece(piece, x, y);
			System.out.println("操作成功！");
			String history = player.getName() + " places a " + piece.getType() + " at (" + x + "," + y + ")";
			player.addHistory(history);
			return true;
		}
	}
	
	@Override
	public boolean removePiece(Player player, int x, int y) {
		Piece piece = null;
		
		if(!(x < 0 || x > 18 || y < 0 || y > 18)) {
			piece = board.getPiece(x, y);
		}
		if(x < 0 || x > 18 || y < 0 || y > 18) {
			System.out.println("该位置超出了棋盘的范围！");
			return false;
		}
		else if(!board.xyHasPiece(x, y)) {
			System.out.println("该位置没有棋子！");
			return false;
		}
		else if(piece.getOwner().equals(player.getName())) {
			System.out.println("该棋子不是对方棋子！");
			return false;
		}
		else {
			board.removePiece(x, y);
			System.out.println("操作成功！");
			String history = player.getName() + " removes " + piece.getOwner() +"'s " + piece.getType() + " at (" + x + "," + y + ")";
			player.addHistory(history);
			return true;
		}

	}

	@Override
	public boolean movePiece(Player player, int x1, int y1, int x2, int y2) {
		System.out.println("Go中无此操作！");
		return false;
	}

	@Override
	public boolean eatPiece(Player player, int x1, int y1, int x2, int y2) {
		System.out.println("Go中无此操作！");
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
		if(x < 0 || x > 18 || y < 0 || y > 18) {
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
