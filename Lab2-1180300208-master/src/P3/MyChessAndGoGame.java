package P3;

import java.util.Scanner;

/**
 * Run the main method in this class.
 * 
 * @author 梁书育
 *
 */
public class MyChessAndGoGame {

	/**
	 * Generate go's actions in this method.
	 * 
	 * @param game the new game
	 */
	public void Go(Game game) {
		Scanner in = new Scanner(System.in);
		String commandLine = "";
		String[] splitcmd;
		Player player;
		boolean turn = true;
		
		
		while(!commandLine.equals("end")) {
			if(turn) {
				player = game.getPlayer1();
				turn = !turn;
			}
			else {
				player = game.getPlayer2();
				turn = !turn;
			}
			System.out.println("操作提示：\n"
					+ "落子：set 要落子位置横坐标 要落子位置纵坐标(以上各项用空格隔开)\n"
					+ "提子：remove 要提子位置横坐标 要提子位置纵坐标(以上各项用空格隔开)\n"
					+ "结束游戏：end");
			System.out.print("\n轮到" + player.getName() + "进行操作：");
			
			commandLine = in.nextLine();
			splitcmd = commandLine.split(" ");
			
			try {
				if(splitcmd[0].toLowerCase().equals("set")) {
					int x = Integer.valueOf(splitcmd[1]);
					int y = Integer.valueOf(splitcmd[2]);
					for(Piece p : player.getPieceSet()) {
						if(!p.isOnBoard()) {
							game.placePiece(player, p, x, y);
							break;
						}
					}
				
				}
				else if(splitcmd[0].toLowerCase().equals("remove")) {
					int x = Integer.valueOf(splitcmd[1]);
					int y = Integer.valueOf(splitcmd[2]);
					game.removePiece(player, x, y);
				}
				else if(splitcmd[0].toLowerCase().equals("inquiry")) {
					int x = Integer.valueOf(splitcmd[1]);
					int y = Integer.valueOf(splitcmd[2]);
					if(x < 0 || x > 18 || y < 0 || y > 18) {
						System.out.println("该位置超出了棋盘的范围！");
					}
					else {
						Piece piece = game.getPiece(x, y);
						if(piece.equals(null)) {
							System.out.println("(" + x + ", " + y + ")处没有棋子!");
						}
						else {
							System.out.println("(" + x + ", " + y + ")处被" + piece.getOwner()
							 + "的" + piece.getType() + "占用!");
						}
					}
					
				}
				else if(splitcmd[0].toLowerCase().equals("piecenumber")) {
					System.out.println(player.getName() + "在棋盘上的棋子总数为" + player.pieceNumber());
				}
			} catch(Exception e) {
				System.out.println("输入格式不符合规范，操作失败！");
			}
			
			System.out.println("操作后棋盘状态如下：");
			game.printBoard();
		}
		
		System.out.println("\n是否查询走棋历史？若需要查询请输入 “yes” 或 “y” 进行查询。");
		commandLine = in.nextLine();
		splitcmd = commandLine.split(" ");
		
		try {
			if (splitcmd[0].toLowerCase().equals("yes") || splitcmd[0].toLowerCase().equals("y")) {
				System.out.println(game.getPlayer1().getName() + "的走棋历史为：");
				game.printHistory(game.getPlayer1());
				System.out.println(game.getPlayer2().getName() + "的走棋历史为：");
				game.printHistory(game.getPlayer2());
			}
		} catch(Exception e) {
			System.out.println("输入格式不符合规范，操作失败！");
		}
		
		in.close();
	}

	/**
	 * Generate chess's actions in this method.
	 * 
	 * @param game the new game
	 */
	public void Chess(Game game) {
		Scanner in = new Scanner(System.in);
		String commandLine = "";
		String[] splitcmd;
		Player player;
		boolean turn = true;

		while (!commandLine.equals("end")) {
			if (turn) {
				player = game.getPlayer1();
				turn = !turn;
			} else {
				player = game.getPlayer2();
				turn = !turn;
			}
			System.out.println("操作提示：\n"
					+ "移子：move 要移动棋子位置横坐标 要移动棋子位置纵坐标 移动目标位置横坐标 移动目标位置纵坐标(以上各项用空格隔开)\n"
					+ "吃子：eat 己方要移动棋子位置横坐标 己方要移动棋子位置纵坐标 对方被吃棋子横坐标 对方被吃棋子纵坐标(以上各项用空格隔开)\n"
					+ "结束游戏：end");
			System.out.print("\n轮到" + player.getName() + "进行操作：");

			commandLine = in.nextLine();
			splitcmd = commandLine.split(" ");

			try {
				if (splitcmd[0].toLowerCase().equals("move")) {
					int x1 = Integer.valueOf(splitcmd[1]);
					int y1 = Integer.valueOf(splitcmd[2]);
					int x2 = Integer.valueOf(splitcmd[3]);
					int y2 = Integer.valueOf(splitcmd[4]);
					game.movePiece(player, x1, y1, x2, y2);

				} else if (splitcmd[0].toLowerCase().equals("eat")) {
					int x1 = Integer.valueOf(splitcmd[1]);
					int y1 = Integer.valueOf(splitcmd[2]);
					int x2 = Integer.valueOf(splitcmd[3]);
					int y2 = Integer.valueOf(splitcmd[4]);
					game.eatPiece(player, x1, y1, x2, y2);
				} else if (splitcmd[0].toLowerCase().equals("inquiry")) {
					int x = Integer.valueOf(splitcmd[1]);
					int y = Integer.valueOf(splitcmd[2]);
					if (x < 0 || x > 7 || y < 0 || y > 7) {
						System.out.println("该位置超出了棋盘的范围！");
					} else {
						Piece piece = game.getPiece(x, y);
						if (piece == null) {
							System.out.println("(" + x + ", " + y + ")处没有棋子!");
						} else {
							System.out
									.println("(" + x + ", " + y + ")处被" + piece.getOwner() + "的" + piece.getType() + "占用!");
						}
					}

				} else if (splitcmd[0].toLowerCase().equals("piecenumber")) {
					System.out.println(player.getName() + "在棋盘上的棋子总数为" + player.pieceNumber());
				}
			} catch(Exception e) {
				System.out.println("输入格式不符合规范，操作失败！");
			}
			
			
			System.out.println("操作后棋盘状态如下：");
			game.printBoard();
		}

		System.out.println("\n是否查询走棋历史？若需要查询请输入 “yes” 或 “y” 进行查询。");
		commandLine = in.nextLine();
		splitcmd = commandLine.split(" ");
		
		try {
			if (splitcmd[0].toLowerCase().equals("yes") || splitcmd[0].toLowerCase().equals("y")) {
				System.out.println(game.getPlayer1().getName() + "的走棋历史为：");
				game.printHistory(game.getPlayer1());
				System.out.println(game.getPlayer2().getName() + "的走棋历史为：");
				game.printHistory(game.getPlayer2());
			}
		} catch(Exception e) {
			System.out.println("输入格式不符合规范，操作失败！");
		}
		
		in.close();
	}

	public static void main(String[] args) {
		MyChessAndGoGame game = new MyChessAndGoGame();
		String gameType;
		String player1;
		String player2;
		Scanner in = new Scanner(System.in);

		while (true) {
			System.out.println("请输入要选择的棋类(Chess or Go):");
			gameType = in.nextLine();
			if (gameType.toLowerCase().equals("chess") || gameType.toLowerCase().equals("go")) {
				break;
			} else {
				System.out.println("输入不合法，请重新选择! ");
			}

		}

		while(true) {
			System.out.println("请输入玩家1的名字:");
			player1 = in.nextLine();
			System.out.println("请输入玩家2的名字:");
			player2 = in.nextLine();
			if(player1.equals("")) {
				System.out.println("玩家一名字为空，重新输入！");
			}
			else if(player2.equals("")) {
				System.out.println("玩家二名字为空，重新输入！");
			}
			else if(player1.equals(player2)) {
				System.out.println("两玩家名字相同，请重新输入！");
			}
			else {
				break;
			}
		}
		
		Game newGame = new Game(gameType, player1, player2);
		
		System.out.println();
		if (gameType.toLowerCase().equals("chess")) {
			game.Chess(newGame);
		} else if (gameType.toLowerCase().equals("go")) {
			game.Go(newGame);
		}
		in.close();
	}

}
