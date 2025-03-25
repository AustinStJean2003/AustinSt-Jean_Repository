package Hangman;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.LineBorder;

public class RulesPanel extends JPanel {

	private JTextArea text = new JTextArea();

	public RulesPanel() {
		setBackground(new Color(240, 255, 255));
		setForeground(new Color(0, 0, 255));
		setBorder(new LineBorder(new Color(224, 255, 255), 1, true));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);
//		text.setForeground(new Color(255, 255, 255));
		text.setEditable(false);
		text.setFont(new Font("Comic sans MS", 0, 11));
		text.setBounds(10, 55, 648, 525);
		text.setText(
				"Welcome to Hangman!!\n"
				+ "Here are the rules for how to play\n"
				+ "1. You have 6 total lives\n"
				+ "2. You lose a life when you guess incorrectly or request a hint\n"
				+ "3. The only guessable characters are letters\n"
				+ "4. You cannot guess the same letter twice\n"
				+ "5. The game ends when the whole word is guessed or when you run out of lives\n"
				+ "   Good Luck!");
		text.setOpaque(false);
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		add(text, gbc_lblTitle);
	}
}