package Hangman;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

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
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;

public class Game_Frame extends JFrame implements ActionListener, Serializable {

	private static JPanel contentPane;
	private JMenu newGameMenu = new JMenu("Game");
	private JMenuItem newGameItem = new JMenuItem("New Game");
	private JMenuItem quitGameItem = new JMenuItem("Quit Game");
	private JMenu sbMenu = new JMenu("Scoreboard");
	private JMenuItem sbMenuItem = new JMenuItem("View Scoreboard");
	private JMenu ruleMenu = new JMenu("Rules");
	private JMenuItem ruleMenuItem = new JMenuItem("View Rules");
	private JMenu rulesMenu = new JMenu("Hint");
	private JMenuItem hintItem = new JMenuItem("Get a Hint");
	private JMenu switchMenu = new JMenu("Switch");
	private JMenuItem switchItem = new JMenuItem("Switch Player");
	private JMenuBar menuBar = new JMenuBar();
	private static JTextField textFieldGuess = new JTextField();
	private final static JTextField textFieldGuesses = new JTextField();
	public static JLabel textAreaWord = new JLabel();
	private static JButton btnGuess;
	public static SinglyLinkedList<String> gameWord = new SinglyLinkedList<String>();
	public static Scanner kb = new Scanner(System.in);

	public static SinglyLinkedList<String> wordList = new SinglyLinkedList<String>();
	public static SinglyLinkedList<String> wordList2 = new SinglyLinkedList<String>();
	private static JTextField textFieldNumGuesses = new JTextField();
	static String theWord = "";
	static int guesses = 6;
	static ImageIcon image = new ImageIcon("images\\6lives.png");
	static JLabel imageLabel;
	
	static String spacedWord;
	public static int numWins = 0;
	public static int gamesPlayed = 0;
	static SB_Frame sbframe = new SB_Frame();
	static Login_Frame playerFrame = new Login_Frame();
	static int wordLength = theWord.length();
	private static boolean hintFlag = false;
	static FileReader reader = new FileReader();
	private static boolean wordBankFlag = true;
	/**
	 * Launch the application.
	 */
	public void runProgram() {
		EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				Game_Frame frame = new Game_Frame();
				frame.setVisible(true);
				restartGame();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
	}

	public Game_Frame() {
		setResizable(false);
		setBackground(new Color(0, 0, 139));
		textFieldGuesses.setBackground(new Color(255, 250, 240));
		textFieldGuesses.setFont(new Font("Arial", Font.PLAIN, 30));
		textFieldGuesses.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldGuesses.setEditable(false);
		textFieldGuesses.setBounds(28, 80, 306, 44);
		textFieldGuesses.setColumns(10);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 255, 255));
		contentPane.setBorder(new LineBorder(new Color(175, 238, 238), 1, true));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblWordEnter = new JLabel("Enter Your Guess:");
		lblWordEnter.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 15));
		lblWordEnter.setBounds(29, 354, 134, 44);
		contentPane.add(lblWordEnter);

		textFieldGuess.setBackground(new Color(255, 250, 250));
		textFieldGuess.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldGuess.setBounds(157, 356, 42, 45);
		contentPane.add(textFieldGuess);
		textFieldGuess.setColumns(10);

		btnGuess = new JButton("Guess");
		btnGuess.setBackground(new Color(175, 238, 238));
		btnGuess.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 17));
		btnGuess.setBounds(209, 355, 125, 47);
		contentPane.add(btnGuess);

		JLabel lblWrongGuesses = new JLabel("Incorrect Guesses(Word Bank)");
		lblWrongGuesses.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 16));
		lblWrongGuesses.setBounds(29, 50, 220, 29);
		contentPane.add(lblWrongGuesses);

		contentPane.add(textFieldGuesses);

		imageLabel = new JLabel(image);
		imageLabel.setBackground(new Color(255, 228, 181));
		imageLabel.setBounds(357, 11, 245, 279);
		imageLabel.setVisible(true);
		contentPane.add(imageLabel);

		textFieldNumGuesses.setBackground(new Color(255, 250, 240));
		textFieldNumGuesses.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldNumGuesses.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldNumGuesses.setEditable(false);
		textFieldNumGuesses.setBounds(357, 301, 245, 44);
		contentPane.add(textFieldNumGuesses);
		textFieldNumGuesses.setColumns(10);
		textAreaWord.setVerticalAlignment(JLabel.CENTER);
		textAreaWord.setHorizontalAlignment(JLabel.CENTER);
		textAreaWord.setFont(new Font("Arial", Font.PLAIN, 32));
		textAreaWord.setBackground(new Color(240, 255, 255));
		textAreaWord.setForeground(new Color(0, 0, 0));

		textAreaWord.setBounds(29, 224, 305, 54);
		contentPane.add(textAreaWord);

		ImageIcon icon = new ImageIcon("images\\0lives.png");
		setIconImage(icon.getImage());

		setTitle("Hangman");
		menuBar.setBackground(new Color(245, 255, 250));
		setJMenuBar(menuBar);
		menuBar.add(newGameMenu);
		menuBar.add(sbMenu);
		menuBar.add(ruleMenu);
		menuBar.add(ruleMenu);
		rulesMenu.setBackground(new Color(135, 206, 250));
		menuBar.add(rulesMenu);
		menuBar.add(switchMenu);
		switchMenu.add(switchItem);
		rulesMenu.add(hintItem);
		ruleMenu.add(ruleMenuItem);
		sbMenu.add(sbMenuItem);
		newGameMenu.add(newGameItem);
		newGameMenu.add(quitGameItem);
		switchItem.addActionListener(this);
		hintItem.addActionListener(this);
		sbMenuItem.addActionListener(this);
		btnGuess.addActionListener(this);
		newGameItem.addActionListener(this);
		quitGameItem.addActionListener(this);
		textFieldGuess.addActionListener(this);
		ruleMenuItem.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == quitGameItem) {
			int reply = JOptionPane.showConfirmDialog(this, "Are you sure you want to continue? Game will be lost",
					"Quit Game", JOptionPane.ERROR_MESSAGE);
			if (reply == JOptionPane.YES_OPTION) {
				dispose();
			}
		} // QuitItem
		else if (e.getSource() == hintItem) {
			if (guesses == 1) {
				JOptionPane.showMessageDialog(this, "You cannot make hint with one life", "One life left",
						JOptionPane.ERROR_MESSAGE);
			} else if (hintFlag == true) {
				JOptionPane.showMessageDialog(this, "Please start a new game to get hint", "Cannot get Hint",
						JOptionPane.ERROR_MESSAGE);
			} else {
				getHint();
			}
		} // rulesItem
		else if (e.getSource() == switchItem) {
			int reply = JOptionPane.showConfirmDialog(this,
					"If you switch players now this game will be lost, are you sure you want to continue?", "New Game",
					JOptionPane.ERROR_MESSAGE);
			if (reply == JOptionPane.YES_OPTION) {
				Login_Frame.main(null);
				playerFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				restartGame();
				dispose();
			}
		} // switchItem
		else if (e.getSource() == sbMenuItem) {
//			serialize.serialize();
			sbframe.setVisible(true);
			sbframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//			dispose();
		} // ScoreboardItem
		else if (e.getSource() == newGameItem) {
			hintFlag = false;
			int reply = JOptionPane.showConfirmDialog(this,
					"If you restart now this game will be lost, are you sure you want to continue?", "New Game",
					JOptionPane.ERROR_MESSAGE);
			if (reply == JOptionPane.YES_OPTION) {
				restartGame();
			}
		} // restartItem
		else if (e.getSource() == btnGuess) { 
			String ans = textFieldGuess.getText().toLowerCase();
			if (textFieldGuess.getText().length() > 1) {
				JOptionPane.showMessageDialog(this, "Please Enter only one letter", "Guess too long",
						JOptionPane.ERROR_MESSAGE);
			} else if (textFieldGuess.getText().length() == 0) {
				JOptionPane.showMessageDialog(this, "Please Enter a letter", "Letter missing",
						JOptionPane.ERROR_MESSAGE);
			} else if ((textFieldGuesses.getText()).indexOf(textFieldGuess.getText()) != -1) {
				JOptionPane.showMessageDialog(this, "Cannot guess same letter more than once", "Guess repeated",
						JOptionPane.ERROR_MESSAGE);
				textFieldGuess.setText("");
			} else if (Character.isDigit(textFieldGuess.getText().charAt(0))) {
				JOptionPane.showMessageDialog(this, "Please Enter a letter", "Numbers not allowed",
						JOptionPane.ERROR_MESSAGE);
			} else if (!Character.isLetter(textFieldGuess.getText().charAt(0))) {
				JOptionPane.showMessageDialog(this, "Please Enter a letter", "Special characters not allowed",
						JOptionPane.ERROR_MESSAGE);
			} else if (wordList.find(ans) != null) {
				JOptionPane.showMessageDialog(this, "Cannot guess same letter more than once", "Guess repeated",
						JOptionPane.ERROR_MESSAGE);
				textFieldGuess.setText("");
			} else {
				gamePlay();
			} // ACtionPerformed if elses
		} // btnGuess
		else if (e.getSource() == ruleMenuItem) {
			JOptionPane.showMessageDialog(this, new RulesPanel(), "Rules", JOptionPane.PLAIN_MESSAGE);
		} // rules
	}// actionPerformed

	public static void checkWinner() {
		if (wordList.find("_") == null) {
			JOptionPane.showMessageDialog(null, "Congratulations You Won! Select New Game to play again or Quit to get out of here", "Game Over",
					JOptionPane.INFORMATION_MESSAGE);

			btnGuess.setEnabled(false);
			hintFlag = true;
			theWord += gameWord.remove(0);
			setWins(numWins);
		} else if (guesses == 0) {
			JOptionPane.showMessageDialog(null, "No guesses left, Word was \n" + gameWord.getElementAt(0), "Game Over",
					JOptionPane.ERROR_MESSAGE);
			btnGuess.setEnabled(false);
			hintFlag = true;
			for (int i = 0; i < wordList.getLength(); i++) {
				wordList.remove(i);
			} // for
			theWord += gameWord.remove(0);
		} // else if
	}// checkWinner()

	public static void gameSetup(int guesses) {

		reader.output();
		theWord = "";
		theWord += gameWord.getElementAt(0);
		for (int i = theWord.length() - 1; i >= 0 ; i--) {
			if (theWord.charAt(i) == ' ' || theWord.charAt(i) == '.' || theWord.charAt(i) == '/'
					|| !Character.isLetter(theWord.charAt(i))) {
				wordList.add(String.valueOf(theWord.charAt(i)));
			} else if (Character.isLetter(theWord.charAt(i))) {
				wordList.add("_");
			} else {
				wordList.add(String.valueOf(theWord.charAt(i)));
			} // if a character in the word is not a letter
		} // for
		String word = "";
		for (int i = 0; i < wordList.getLength(); i++) {
			word += wordList.getElementAt(i);
		} // for
		spacedWord = word.replaceAll(".", "$0 ");
		textAreaWord.setText(spacedWord);
		textFieldNumGuesses.setText("\n" + guesses + " guesses left ");
	} // gameSetup

	public static void gamePlay() {

		String unguessedWord = "";

		String ans = textFieldGuess.getText().toLowerCase();
		char letter = ans.charAt(0);

		for (int i = 0; i < theWord.length(); i++) {
			if (theWord.charAt(i) == letter) {
				wordList.remove(i);
				wordList.add(ans, i);
			} // if
		} // for

		if (wordList.find(ans) == null) {
			guesses -= 1;
			if (guesses == 1) {
				textFieldNumGuesses.setForeground(new Color(255, 0, 0));
			}
			textFieldNumGuesses.setText("\n" + guesses + " guesses left. ");
			if(wordBankFlag == true) {
				textFieldGuesses.setText(textFieldGuesses.getText() + ans);
			} else {
				textFieldGuesses.setText(textFieldGuesses.getText() + ", "+ ans);
			}

			changePic();
			if (guesses == 0) {
				btnGuess.setEnabled(false);
			}
			wordBankFlag = false;
		} // if
		for (int i = 0; i < wordList.getLength(); i++) {
			unguessedWord += wordList.getElementAt(i);
		} // for
		spacedWord = unguessedWord.replaceAll(".", "$0 ");
		textAreaWord.setText(spacedWord);
		textFieldGuess.setText("");
		checkWinner();
	}// gamePlay()

	public static void changePic() {
		ImageIcon img = new ImageIcon("images\\" + guesses + "lives.png");
		imageLabel.setIcon(img);
	}

	public static void restartGame() {
		int remove = 0;
		setGamesPlayed(gamesPlayed);
		while (wordList.getLength() > 0) {
			wordList.remove(remove);
		} // while
		theWord = "";
		ImageIcon img = new ImageIcon("images\\6lives.png");
		imageLabel.setIcon(img);
		textFieldNumGuesses.setForeground(new Color(0, 0, 0));
		textAreaWord.setText("");
		textFieldGuess.setText("");
		textFieldGuesses.setText("");
		btnGuess.setEnabled(true);
		guesses = 6;
		gameSetup(guesses);
	}// restartGame

	public static void getHint() {
		int randomIndex = (int) (Math.random() * theWord.length());
		while (wordList.find(String.valueOf(theWord.charAt(randomIndex))) != null) {
			randomIndex = 1 + (int) (Math.random() * theWord.length() - 1);
		}
		for (int i = 0; i < theWord.length(); i++) {
			char s = theWord.charAt(randomIndex);
			String Str = String.valueOf(s);
			if (theWord.charAt(i) == s) {
				wordList.remove(randomIndex);
				wordList.add(Str, randomIndex);
				for (int h = 0; h < theWord.length(); h++) {
					if (theWord.charAt(i) == theWord.charAt(h)) {
						wordList.remove(h);
						wordList.add(Str, h);
					}
				}
			}
		}
		String unguessedWord = "";
		for (int i = 0; i < wordList.getLength(); i++) {
			unguessedWord += wordList.getElementAt(i);
		}
		guesses--;
		spacedWord = unguessedWord.replaceAll(".", "$0 ");
		textFieldNumGuesses.setText("\n" + guesses + " guesses left. ");
		textAreaWord.setText(spacedWord);
		textFieldGuess.setText("");
		changePic();
		checkWinner();
	}// getHint

	public void serializable() {
		String filename = "savedLister.ser";
		try {
			FileOutputStream file = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(gameWord);
			out.close();
			file.close();
			System.out.println("Object has been serialized");
		} catch (IOException ex) {
			System.out.println("IOException is caught");
		} // try catch
	}// serializable

	public static void setWins(int wins) {
		numWins = wins + 1;
	}// setWins

	public static int getWins() {
		return numWins;
	}// getWins

	public static void setGamesPlayed(int gp) {
		gamesPlayed = gp + 1;
	}// setGamesPlayed

	public static int getGamesPlayed() {
		return gamesPlayed;
	}// getGamesPlayed
}// Game_Frame