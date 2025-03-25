package Hangman;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class SB_Frame extends JFrame implements ActionListener, Serializable {

	private JPanel contentPane;
	private JTextArea textAreaPlayers;
	private JTextArea textAreaGP;
	private JTextArea textAreaWins;
	Game_Frame gameFrame = new Game_Frame();
	Login_Frame playerFrame = new Login_Frame();
	File fileName = new File("playersList.txt");
	int lineNum = 0;
	DoublyLinkedList<String> playersList = new DoublyLinkedList<String>();
	DoublyLinkedList<String> winsList = new DoublyLinkedList<String>();
	DoublyLinkedList<String> gamesPlayedList = new DoublyLinkedList<String>();
	boolean sorted;
	int numPlayers;
	int fileLength;
	private SB_Serialize serialize = new SB_Serialize();

	public SB_Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 255, 255));
		contentPane.setBorder(new LineBorder(new Color(224, 255, 255), 1, true));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		ImageIcon icon = new ImageIcon("images\\0lives.png");
		setIconImage(icon.getImage());

		JLabel lblPlayers = new JLabel("PLAYERS");
		lblPlayers.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 18));
		lblPlayers.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayers.setBounds(55, 39, 110, 32);
		contentPane.add(lblPlayers);

		JLabel lblGP = new JLabel("GAMES PLAYED");
		lblGP.setHorizontalAlignment(SwingConstants.CENTER);
		lblGP.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 18));
		lblGP.setBounds(252, 39, 148, 32);
		contentPane.add(lblGP);

		JLabel lblWins = new JLabel("WINS");
		lblWins.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 18));
		lblWins.setHorizontalAlignment(SwingConstants.CENTER);
		lblWins.setBounds(476, 39, 75, 32);
		contentPane.add(lblWins);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(248, 248, 255));
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.DARK_GRAY));
		panel.setBounds(30, 70, 578, 380);
		contentPane.add(panel);
		panel.setLayout(null);

		textAreaPlayers = new JTextArea();
		textAreaPlayers.setFont(new Font("Arial", Font.PLAIN, 24));
		textAreaPlayers.setEditable(false);
		textAreaPlayers.setLineWrap(true);
		textAreaPlayers.setBounds(10, 11, 157, 358);
		panel.add(textAreaPlayers);

		textAreaGP = new JTextArea();
		textAreaGP.setFont(new Font("Arial", Font.PLAIN, 24));
		textAreaGP.setEditable(false);
		textAreaGP.setLineWrap(true);
		textAreaGP.setBounds(218, 11, 157, 358);
		panel.add(textAreaGP);

		textAreaWins = new JTextArea();
		textAreaWins.setFont(new Font("Arial", Font.PLAIN, 24));
		textAreaWins.setEditable(false);
		textAreaWins.setLineWrap(true);
		textAreaWins.setBounds(411, 11, 157, 358);
		panel.add(textAreaWins);
		setTitle("Scoreboard");
		displayStats();
	}//SB_Frame

	public void actionPerformed(ActionEvent e) {
	}//actionPerformed

	public void displayStats() {
		try {
			FileOutputStream oFile = new FileOutputStream(fileName, true);
			Scanner scanner = new Scanner(fileName);
			if (fileName.length() != 0) {
				while (scanner.hasNextLine()) {
					lineNum++;
					String playerNames = scanner.nextLine();
					addPlayer(playerNames);
					gamesPlayedList.addAtEnd(String.valueOf(Game_Frame.getGamesPlayed()));
					winsList.addAtEnd(String.valueOf(gameFrame.getWins()));
				}//while
				textAreaWins.append("\n");
				textAreaGP.append("\n");
				listPlayers(lineNum);
				sortByAlpha();
				for (int i = 0; lineNum > i; i++) {
					textAreaPlayers.append(playersList.getElementAt(i) + "\n");
					numPlayers++;
				}//for
				setNumPlayers(numPlayers);
				for (int i = 0; lineNum - 1 > i; i++) {
					int statWins =  (int)Math.floor(Math.random()*(10-0+1)+0);
					int statGames =  (int)Math.floor(Math.random()*(20-statWins+1)+statWins);
					textAreaWins.append(statWins + "\n");
					textAreaGP.append(statGames + "\n");
				}//for
			}//if
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}//catch
	}//displayStats
	
	public void deserialize() {
		String filename = "savedList.ser";
		try {
			FileInputStream file = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(file);
			DoublyLinkedList<String> sbstats = (DoublyLinkedList<String>) in.readObject();
			in.close();
			file.close();
			System.out.println("Object has been deserialized");
			for (int i = 0; sbstats.getLength() > i; i++) {
				System.out.println(sbstats.getElementAt(i));
			}
		} catch (IOException ex) {
			System.out.println("Object has been deserialized");
		} catch (ClassNotFoundException ex) {
			System.out.println("ClassNotFileException is caught");
		}
	}
	
	public void sortByAlpha() {
		sorted = false;
		int loopend = playersList.getLength() - 1;

		while (loopend > 0 && !sorted) {
			sorted = true;
			for (int j = 0; j < loopend; j++) {
				String index1 = playersList.getElementAt(j);
				String index2 = playersList.getElementAt(j + 1);
				if (index1.compareToIgnoreCase(index2) > 0) {
					switchNames(j, j + 1);
					sorted = false;
				}//if
			}//for
			loopend--;
		}//while
	}//sortByAlpha

	private void switchNames(int j, int k) {
		String player = playersList.getElementAt(j);
		playersList.add(player, k + 1);
		playersList.remove(j);
	} // switchNames(int, int)

	public void setNumPlayers(int NumPlayers) {
		numPlayers = NumPlayers;
	}// setNumPlayers

	public int getNumPlayers() {
		return numPlayers;
	}// getNumPlayers

	public void addPlayer(String player) {
		playersList.addAtEnd(player);
	}// addPlayer

	public boolean listPlayers(int playersLength) {
		if (playersLength == 0) {
			return false;
		} else {
			return true;
		}
	}// listPlayers

	public boolean getNextPlayer(int num) {
		if (playersList.getLength() == num) {
			return true;
		} else if (playersList.getLength() == num) {
			return true;
		} else if (playersList.getLength() == num) {
			return true;
		} else {
			return false;
		} // else if
	}//getNextPlayer

	public boolean gamePlayed(int num) {
		if (num > 0) {
			return true;
		} else if (num == 0) {
			return true;
		} else {
			return false;
		} // else if
	}//gamePlayed

	public boolean totalPlayers(int players) {
		if (players == getNumPlayers()) {
			return true;
		} else {
			return false;
		} // if else
	}// totalPlayers
}//SB_Frame
