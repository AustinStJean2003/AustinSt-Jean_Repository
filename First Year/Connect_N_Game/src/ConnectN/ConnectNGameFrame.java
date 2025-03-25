package ConnectN;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ConnectNGameFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JMenu helpMenu = new JMenu("Help");
	private JMenuItem helpItem = new JMenuItem("Help");
	private JMenu aboutMenu = new JMenu("About");
	private JMenuItem aboutMenuItem = new JMenuItem("About");
	private JMenuBar menuBar = new JMenuBar();
	private final JButton btnUndo = new JButton("UNDO");
	private final JButton btnSave = new JButton("SAVE");
	private final JButton btnQuit = new JButton("QUIT");
	private final JButton btnNewButton = new JButton("NEW");
	private JTextArea gameArea = new JTextArea();
	private JButton connectNButton[][];
	private JPanel gameNPanel;
	ConnectNGame boardGame = new ConnectNGame();
	private int rows = 6;
	private int columns = 7;
	private JTextArea txtGame;
	private char boardFrame[][];
	private JTextField textMove;
	private final JLabel lblMove = new JLabel("Enter the column of your move:\r\n");
	private int whosTurn = 1;

	public ConnectNGameFrame() {
//		ConnectNGameFrame boardGameFrame = new ConnectNGameFrame(rows, columns, player1, player2, checker);
//int rows, int columns, String player1, String player2, int checker		
		gameNPanel = new JPanel();
		gameNPanel.setBounds(2, 2, 0, 0);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 550, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 240));
		contentPane.setForeground(new Color(210, 105, 30));
		contentPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		setContentPane(contentPane);
		btnUndo.setBounds(210, 13, 89, 41);
		btnUndo.setFont(new Font("Gill Sans MT", Font.PLAIN, 11));
		btnUndo.setForeground(Color.BLUE);
		btnSave.setBounds(111, 13, 89, 41);
		btnSave.setForeground(new Color(75, 0, 130));
		btnSave.setFont(new Font("Gill Sans MT", Font.PLAIN, 11));
		btnQuit.setBounds(309, 13, 89, 41);
		btnQuit.setForeground(new Color(165, 42, 42));
		btnQuit.setFont(new Font("Gill Sans MT", Font.PLAIN, 11));
		btnNewButton.setBounds(12, 13, 89, 41);

		btnNewButton.setForeground(new Color(47, 79, 79));
		btnNewButton.setFont(new Font("Gill Sans MT", Font.PLAIN, 11));

		gameArea = new JTextArea(5, 5);
		gameArea.setBounds(2, 2, 0, 0);
		setTitle("ConnectN");
		setJMenuBar(menuBar);
		menuBar.add(helpMenu);
		menuBar.add(aboutMenu);
		helpMenu.add(helpItem);
		aboutMenu.add(aboutMenuItem);
		aboutMenuItem.addActionListener(this);
		helpItem.addActionListener(this);
		btnQuit.addActionListener(this);
		btnNewButton.addActionListener(this);

		txtGame = new JTextArea();
		txtGame.setBounds(12, 80, 386, 215);
		contentPane.setLayout(null);
		contentPane.add(btnUndo);
		contentPane.add(btnSave);
		contentPane.add(btnQuit);
		contentPane.add(btnNewButton);
		contentPane.add(gameArea);
		contentPane.add(gameNPanel);
		contentPane.add(txtGame);
		
		textMove = new JTextField();
		textMove.setBounds(312, 309, 86, 20);
		contentPane.add(textMove);
		textMove.setColumns(10);
		lblMove.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMove.setBounds(54, 312, 245, 14);
		
		contentPane.add(lblMove);
	}
	
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

	public void printBoard(ConnectNGame boardFrame) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				txtGame.append(getBoardFrame()[i][j] + " ");
			} // for
			txtGame.append("\n");
		} // for
	}// printBoard()

	public void boardFrame() {
		boardFrame = new char[6][7];
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				boardFrame[i][j] = '_';
			} // for
		} // for
	} // board()

	public char[][] getBoardFrame() {
		return boardFrame;
	}// getBoard()

	public void actionPerformed(ActionEvent e) {
		ConnectNGameFrame frame = new ConnectNGameFrame();
		if (e.getSource() == aboutMenuItem) {
			JOptionPane.showMessageDialog(this, new ConnectN_AboutPanel(), "About", JOptionPane.PLAIN_MESSAGE);
		} else if (e.getSource() == helpItem) {
			JOptionPane.showMessageDialog(this, new ConnectN_HelpPanel(), "Help", JOptionPane.PLAIN_MESSAGE);
		} else if (e.getSource() == btnQuit) {
			dispose();
		} else if (e.getSource() == btnSave) {
			try {
				frame.saveGame();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == btnUndo) {

		} else if (e.getSource() == btnNewButton) {
			frame.setVisible(true);
			frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			dispose();
		}
	}
	
	public void saveGame() throws IOException {
		File saveGame = new File("currentGame.txt");
		FileWriter gameWriter = new FileWriter(saveGame);
		ConnectNInterface interf = new ConnectNInterface();
		try {
			gameWriter.write(interf.player1 + "\n");
			gameWriter.write(interf.player2 + "\n");
			gameWriter.write(rows + "\n");
			gameWriter.write(columns + "\n");
			gameWriter.write(interf.winRow + "\n");
			gameWriter.write(whosTurn + "\n");
			for (int j = 0; j < boardFrame.length; j++) {
				for (int k = 0; k < boardFrame[0].length; k++) {
					gameWriter.write("~" + boardFrame[j][k]);
				}
				gameWriter.write("\n");
			}
			gameWriter.close();
		} // try
		catch (IOException e) {
			System.out.println("ERROR: Could not write to " + saveGame + ": " + e.getMessage());
		} // catch
	}// saveGame()

	public static void main(String[] args) {

		ConnectNGame boardGame = new ConnectNGame();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectNGameFrame frame = new ConnectNGameFrame();
					frame.setVisible(true);
					frame.boardFrame();
					frame.printBoard(boardGame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
