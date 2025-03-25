package ConnectN;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class ConnectNInterface {
	static String player1;
	static String player2;
	static int rows;
	static int columns;
	private static char board[][] = new char[rows][columns];
	private static int maxNumTurns;
	Scanner kb = new Scanner(System.in);
	ConnectNGame boardGame = new ConnectNGame();
	static int winRow;
	static int turn;
	private int index = 0;
	private boolean gameContinues = true;

	public void welcome() {
		System.out.println("Welcome to Heritage Connect-N");
	} // welcome

	public void player() {
		Scanner kb = new Scanner(System.in);
		System.out.print("Enter the name of Player 1, yellow: ");
		player1 = kb.nextLine();
		System.out.print("Enter the name of Player 2, red: ");
		player2 = kb.nextLine();
	} // player()

	public void connectN() {
		Scanner kb = new Scanner(System.in);
		System.out.print("Enter the value for N, the number of checkers in a row for a win, From a range from 3-8: ");
		winRow = kb.nextInt();
		while (winRow > 8 || winRow < 3) {
			System.out.print("ConnectN row is out of range. Please enter a number from 3-8: ");
			winRow = kb.nextInt();
		} // while
		while (columns < winRow) {
			System.out.print("Cannot make a winning row with " + columns + " columns and " + winRow
					+ " as winning row. Please enter a new number of checkers\n");
			winRow = kb.nextInt();
		} // while
		while (rows < winRow) {
			System.out.print("Cannot make a winning row with " + rows + " rows and " + winRow
					+ " as winning row. Please enter a new number of checkers\n");
			winRow = kb.nextInt();
		} // while
		boardGame.setCounter(winRow);
	} // connectN()

	public void numRowsColumns() {
		System.out.print("How many rows do you want in your game? From a range from 4-12: ");
		Scanner kb = new Scanner(System.in);
		rows = kb.nextInt();
		while (rows > 12 || rows < 4) {
			System.out.print("Rows are out of range. Please enter number of rows 4-12: ");
			rows = kb.nextInt();
		} // while
		boardGame.setRows(rows);
		System.out.print("How many columns do you want in your game? From a range from 4-12: ");
		columns = kb.nextInt();
		while (columns > 12 || columns < 4) {
			System.out.print("Columns are out of range. Please enter number of columns 4-12: ");
			columns = kb.nextInt();
		} // while
		boardGame.setColumns(columns);
	} // numRowsColumns()

	public void printBoard(ConnectNGame board) {
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumn(); j++) {
				System.out.print(board.getBoard()[i][j] + " ");
			} // for
			System.out.println();
		} // for
		for (int i = 1; i < columns + 1; i++) {
			System.out.print(i + " ");
		}
		System.out.println();
	}// printBoard()

	public void turn() throws IOException {
		ConnectNInterface connectN = new ConnectNInterface();
		maxNumTurns = rows * columns;
		Scanner kb = new Scanner(System.in);
		connectN.printBoard(boardGame);

		while (gameContinues) {
			if (gameContinues == true) {
				if (boardGame.getTurn() == 1) {
					System.out.print(player1 + ", enter the column of your move -> ");
				} else {
					System.out.print(player2 + ", enter the column of your move -> ");
				} // else
				turn = kb.nextInt();
				while (!boardGame.validate(turn)) {
					System.out.println("Invalid column number. Please try again");
					turn = kb.nextInt();
				} // while
				boardGame.play(turn);
				connectN.printBoard(boardGame);
				if (boardGame.checkForWinner()) {
					if (boardGame.getTurn() == 1) {
						System.out.print(player1 + " Won The Game!");
						gameContinues = false;
					} else {
						System.out.print(player2 + " Won The Game!");
						gameContinues = false;
					}
				}
				index++;
				if (index == maxNumTurns) {
					System.out.println("Its a tie!!!");
					System.exit(-1);
				}
				if (gameContinues == true) {
					boardGame.changeTurn();
					turnAction();
				}
			}
		} // while
	} // turn()

	public void turnAction() throws IOException {
		ConnectNInterface connectN = new ConnectNInterface();
		String turnChoice;
		System.out.print(
				"Type C to continue with the game, Q to exit the game, S to save and continue the game or U to undo your last move -> ");
		turnChoice = kb.nextLine();
		while (!(turnChoice.equalsIgnoreCase("C") || turnChoice.equalsIgnoreCase("Q")
				|| turnChoice.equalsIgnoreCase("S") || turnChoice.equalsIgnoreCase("U"))) {
			System.out.print("Please enter a correct letter: ");
			turnChoice = kb.nextLine();
		} // check for correct letter
		if (turnChoice.equalsIgnoreCase("C")) {
			turn();
		} else if (turnChoice.equalsIgnoreCase("Q")) {
			System.exit(-1);
		} else if (index == 0) {
			System.out.println("There have been no moves to save or undo, please select a different option");
			turnAction();
		} else if (turnChoice.equalsIgnoreCase("S")) {
			boardGame.saveGame();
			System.out.println("Saved game successfully");
			turn();
		} else if (turnChoice.equalsIgnoreCase("U")) {
			boardGame.undoMove(turn);
			boardGame.changeTurn();
			connectN.printBoard(boardGame);
		}
	}// turnAction()

	public void gameStatus() {
		String status;
		ConnectNInterface connectN = new ConnectNInterface();
		System.out.print("Please enter N to start a new game or R to resume the game stored in currentGame.txt -> ");
		status = kb.nextLine();
		while (!(status.equalsIgnoreCase("N") || status.equalsIgnoreCase("R"))) {
			System.out.print(
					"Invalid letter. Please enter N to start a new game or R to resume the game stored in currentGame.txt -> ");
			status = kb.nextLine();
		} // while
		if (status.equalsIgnoreCase("N")) {
			player();
			numRowsColumns();
			connectN();
			boardGame.board();
		}
		if (status.equalsIgnoreCase("R")) {
			try {
				boardGame.readGame();
				System.out.println("Player 1: " + player1);
				System.out.println("Player 2: " + player2);
				System.out.println("Number of rows are " + boardGame.getRows());
				System.out.println("Number of columns are " + boardGame.getColumn());
				System.out.println("Number of checkers in a row to win " + winRow);
				if (boardGame.getTurn() == 1) {
					System.out.println("It is " + player1 + "'s turn");
				} else {
					System.out.println("It is " + player2 + "'s turn");
				}
				index = boardGame.getMadeMoves();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			connectN.printBoard(boardGame);
		} // resume game

	}// gameStatus()

	public static void main(String[] args) throws IOException {
		ConnectNInterface connectN = new ConnectNInterface();
		connectN.welcome();
		connectN.gameStatus();
		connectN.turnAction();
//		ConnectNGame.test();
	} // main

} // ConnectNInterface
