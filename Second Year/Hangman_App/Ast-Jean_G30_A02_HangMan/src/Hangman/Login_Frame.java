package Hangman;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.MutableComboBoxModel;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class Login_Frame extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField textFieldPlayerName;
	private JButton btnPlayGame;
	private JComboBox<String> cmbxPlayers;
	String name;
	DoublyLinkedList<String> players = new DoublyLinkedList<String>();
	File fileName = new File("playersList.txt");
	int lineNum = 0;
	Scanner scanner;
	String playerNameToAdd;
	private String playerName;
	boolean flag;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login_Frame frame = new Login_Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login_Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 270);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 255, 255));
		contentPane.setBorder(new LineBorder(new Color(224, 255, 255), 1, true));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Welcome to Hangman");
		JLabel lblPlayers = new JLabel("Player:");
		lblPlayers.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 14));
		lblPlayers.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPlayers.setBounds(49, 53, 106, 15);
		contentPane.add(lblPlayers);

		ImageIcon icon = new ImageIcon("images\\0lives.png");
		setIconImage(icon.getImage());

		cmbxPlayers = new JComboBox();
		cmbxPlayers.setForeground(new Color(0, 0, 0));
		cmbxPlayers.setBackground(new Color(255, 250, 250));
//		cmbxPlayers.setModel(new DefaultComboBoxModel(new String[] {}));
		cmbxPlayers.setMaximumRowCount(10);
		cmbxPlayers.setBounds(165, 49, 145, 23);
		for (int i = 0; players.getLength() > i; i++) {
			cmbxPlayers.addItem(players.getElementAt(i));
		} // for
		contentPane.add(cmbxPlayers);

		JLabel lblPlayerName = new JLabel("New Player Name:");
		lblPlayerName.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 14));
		lblPlayerName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPlayerName.setBounds(10, 102, 145, 14);
		contentPane.add(lblPlayerName);

		textFieldPlayerName = new JTextField();
		textFieldPlayerName.setBackground(new Color(255, 250, 250));
		textFieldPlayerName.setBounds(165, 99, 145, 23);
		contentPane.add(textFieldPlayerName);
		textFieldPlayerName.setColumns(10);

		btnPlayGame = new JButton("Play Game");
		btnPlayGame.setBackground(new Color(175, 238, 238));
		btnPlayGame.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 14));
		btnPlayGame.setBounds(165, 145, 109, 23);
		contentPane.add(btnPlayGame);

		btnPlayGame.addActionListener(this);
		textFieldPlayerName.addActionListener(this);
		readFile();
	}// player_Frmae

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnPlayGame || e.getSource() == textFieldPlayerName) {
			if (cmbxPlayers.getSelectedIndex() != 0) {
				addName();
				setPlayerName(textFieldPlayerName.getText());
				Game_Frame frame = new Game_Frame();
				frame.runProgram();
				frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				dispose();
			} else if (textFieldPlayerName.getText().length() == 0) {
				JOptionPane.showMessageDialog(this, "Please Enter or select a Player Name", "Player Name missing",
						JOptionPane.ERROR_MESSAGE);
			} else if (nameTooLong(textFieldPlayerName.getText())) {
				JOptionPane.showMessageDialog(this, "Name can only be 20 characters long", "Player Name too long",
						JOptionPane.ERROR_MESSAGE);
			} else if (textFieldPlayerName.getText().isBlank() && cmbxPlayers.getSelectedIndex() == 0) {
				JOptionPane.showMessageDialog(this, "Name Cannot be white space", "Enter New Name",
						JOptionPane.ERROR_MESSAGE);
			} else if (nameUnique(textFieldPlayerName.getText())) {
				JOptionPane.showMessageDialog(this, "Name is already taken", "Name must be Unique",
						JOptionPane.ERROR_MESSAGE);
			} else {
				addName();
				Game_Frame frame = new Game_Frame();
				frame.runProgram();
				frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				dispose();
			} // if user doesn't enter player name
		} // if btnPlay is pressed
	}// actionPerformed

	/**
	 * Add player name to file
	 */

	public void writeToPlayersFile(String name) {
		try {
			FileWriter myWriter = new FileWriter(fileName, true);
			myWriter.write("\n");
			myWriter.write(name);
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} // catch
	}// writeToPlayersFile

	/**
	 * Add name to list
	 */

	public void addName() {
		setName(textFieldPlayerName.getText());
		if (textFieldPlayerName.getText().length() != 0) {
			writeToPlayersFile(getName());
		} // if
	}// add name to combobox

	public void readFile() {
		try {
			FileOutputStream oFile = new FileOutputStream(fileName, true);
			scanner = new Scanner(fileName);
			if (fileName.length() != 0) {
				while (scanner.hasNextLine()) {
					lineNum++;
					String line = scanner.nextLine();
					players.addAtEnd(line);
				} // while
				for (int i = 0; lineNum > i; i++) {
					cmbxPlayers.addItem(players.getElementAt(i));
				} // for
			} // if
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} // catch
	}// readFile

	public boolean nameUnique(String name) {
		playerNameToAdd = textFieldPlayerName.getText();
		flag = true;
		try {
			scanner = new Scanner(fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // catch
		if (players.find(playerNameToAdd) == null) {
			flag = false;
		} // if
		return flag;
	}// nameUnique

	public boolean nameTooLong(String name) {
		playerNameToAdd = textFieldPlayerName.getText();
		flag = true;
		if (playerNameToAdd.length() < 10) {
			flag = false;
		} // if
		return flag;
	}// nameTooLong

	public void setNameToAdd(String NameToAdd) {
		playerNameToAdd = NameToAdd;
	}// setName

	public String getNameToAdd() {
		return playerNameToAdd;
	}// getName

	
	public void setPlayerName(String Name) {
		playerName = Name;
	}// setName

	public String getPlayerName() {
		return playerName;
	}// getName
}// Player_Frame
