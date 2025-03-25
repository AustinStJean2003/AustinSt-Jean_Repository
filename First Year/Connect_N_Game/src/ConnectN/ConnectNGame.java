package ConnectN;

import java.util.Scanner;
import java.io.*;

public class ConnectNGame {
	private int rows;
	private int columns;
	private int whosTurn = 1;
	private char board[][];
	private int counter;
	private int lastRow;
	private int lastCol;
	private int madeMoves = 0;
	private char boardFrame[][];
	
	public void changeTurn() {
		if (whosTurn == 1) {
			whosTurn = 2;
		} else {
			whosTurn = 1;
		} // else
	}// changeTurn()

	public int getTurn() {
		return whosTurn;
	}// getTurn()

	public void board() {
		board = new char[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				board[i][j] = '_';
			} // for
		} // for
	} // board()
	


	public int getMadeMoves() {
		return madeMoves;
	}//getMadeMoves()
	
	public void setMadeMoves(int mademove) {
		madeMoves = mademove;
	}//setMadeMoves()
	
	public char[][] getBoard() {
		return board;
	}// getBoard()


	
	public void setRows(int row) {
		rows = row;
	}// setRows

	public int getRows() {

		return rows;
	}// setRows

	public void setColumns(int column) {
		columns = column;
	}// setRows

	public void setCounter(int count) {
		counter = count;
	}// setCounter

	public int getCounter() {
		return counter;
	}// getCounter

	public int getColumn() {

		return columns;
	}// getColumns

	public void undoMove(int turn) {
		boolean found = false;
		if (board[lastRow][lastCol] == 'X' || board[lastRow][lastCol] == 'O') {
			board[lastRow][lastCol] = '_';
			found = true;
		} // if
	}// undo

	public boolean checkForWinner(int column) {
		boolean winner = false;
		return winner;
	}// checkForWinner()

	public boolean validate(int turn) {
		if (turn < 0 || turn > columns) {
			return false;
		} // if
		if (board[0][turn - 1] != '_') {
			return false;
		} // if
		return true;
	}// validate

	public void play(int turn) {
		boolean found = false;
		for (int j = rows - 1; j >= 0 && !found; j--) {
			if (board[j][turn - 1] == '_') {
				if (whosTurn == 1) {
					board[j][turn - 1] = 'X';
					lastRow = j;
					lastCol = turn - 1;
					found = true;
				} else {
					board[j][turn - 1] = 'O';
					lastRow = j;
					lastCol = turn - 1;
					found = true;
				} // else
			} // if
		} // for
	}// play()

	public void readGame() throws FileNotFoundException, IOException {
		ConnectNInterface interf = new ConnectNInterface();
		File gameFile = new File("currentGame.txt");
		Scanner gameReader = new Scanner(gameFile);
		gameReader.useDelimiter("~");
		interf.player1 = gameReader.nextLine();
		interf.player2 = gameReader.nextLine();
		rows = Integer.parseInt(gameReader.nextLine());
		columns = Integer.parseInt(gameReader.nextLine());
		counter = Integer.parseInt(gameReader.nextLine());
		whosTurn = Integer.parseInt(gameReader.nextLine());
		board = new char[rows][columns];
		for (int j = 0; j < board.length; j++) {
			for (int k = 0; k < board[0].length; k++) {
				board[j][k] = gameReader.next().charAt(0);
			}
		}
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (board[i][j] != '_') {
					madeMoves++;
				}
			} // for
		} // for
	}// readGame()

	public void saveGame() throws IOException {
		File saveGame = new File("currentGame.txt");
		FileWriter gameWriter = new FileWriter(saveGame);
		ConnectNGame connectNG = new ConnectNGame();
		ConnectNInterface interf = new ConnectNInterface();
		try {
			gameWriter.write(interf.player1 + "\n");
			gameWriter.write(interf.player2 + "\n");
			gameWriter.write(rows + "\n");
			gameWriter.write(columns + "\n");
			gameWriter.write(interf.winRow + "\n");
			gameWriter.write(whosTurn + "\n");
			for (int j = 0; j < board.length; j++) {
				for (int k = 0; k < board[0].length; k++) {
					gameWriter.write("~" + board[j][k]);
				}
				gameWriter.write("\n");
			}
			gameWriter.close();
		} // try
		catch (IOException e) {
			System.out.println("ERROR: Could not write to " + saveGame + ": " + e.getMessage());
		} // catch
	}// saveGame()

	public boolean checkForWinner() {
		boolean winner = false;
		int count = 0;
		boolean tooMuch = false;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				tooMuch = false;
				count = 0;
				for (int h = 0; h < counter && !tooMuch; h++) {
					if (r + h < rows) {
						if (board[r + h][c] == 'X' && whosTurn == 1) {
							count++;
							if (count >= counter) {
								winner = true;
								return winner;
							}
						} else if (board[r + h][c] == 'O' && whosTurn == 2) {
							count++;
							if (count >= counter) {
								winner = true;
								return winner;
							}
						}
					} else {
						tooMuch = true;
					}
				}
			}
		} // Vertical

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				tooMuch = false;
				count = 0;
				for (int h = 0; h < counter && !tooMuch; h++) {
					if (c + h < columns) {
						if (board[r][c + h] == 'X' && whosTurn == 1) {
							count++;
							if (count >= counter) {
								winner = true;
								return winner;
							}
						} else if (board[r][c + h] == 'O' && whosTurn == 2) {
							count++;
							if (count >= counter) {
								winner = true;
								return winner;
							}
						}
					} else {
						tooMuch = true;
					}
				}
			}
		} // Horizontal

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				tooMuch = false;
				count = 0;
				for (int h = 0; h < counter && !tooMuch; h++) {
					if (c + h < columns && r + h < rows) {
						if (board[r + h][c + h] == 'X' && whosTurn == 1) {
							count++;
							if (count >= counter) {
								winner = true;
								return winner;
							}
						} else if (board[r + h][c + h] == 'O' && whosTurn == 2) {
							count++;
							if (count >= counter) {
								winner = true;
								return winner;
							}
						}
					} else {
						tooMuch = true;
					}
				}
			}
		} // Top-left to Bottom-right

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				tooMuch = false;
				count = 0;
				for (int h = 0; h < counter && !tooMuch; h++) {
					if (c - h >= 0 && r + h < rows) {
						if (board[r + h][c - h] == 'X' && whosTurn == 1) {
							count++;
							if (count >= counter) {
								winner = true;
								return winner;
							}
						} else if (board[r + h][c - h] == 'O' && whosTurn == 2) {
							count++;
							if (count >= counter) {
								winner = true;
								return winner;
							}
						}
					} else {
						tooMuch = true;
					}
				}
			}
		} // Bottom-left to top-right
		return winner;

	}// checkForWinner()
} // class ConnectNGame
