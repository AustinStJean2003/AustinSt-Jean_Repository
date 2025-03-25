package ConnectN;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;

public class ConnectNFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textFieldPlayer1Name;
	private JTextField textFieldPlayer2Name;
	private final JButton btnNewGame = new JButton("NEW GAME");
	private final JButton btnResumeGame = new JButton("RESUME GAME");
	int rows;
	int columns;
	String player1;
	String player2;
	int checker;
	char board[][];
	private JComboBox cmbRows;
	private JComboBox cmbColumn;
	private JComboBox cmbCheck;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectNFrame frame = new ConnectNFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ConnectNFrame() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 240));
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitle = new JLabel("Connect N");
		lblTitle.setFont(new Font("Impact", Font.PLAIN, 11));
		lblTitle.setBounds(180, 0, 63, 26);
		contentPane.add(lblTitle);

		JLabel lblPlayer1 = new JLabel("Player 1");
		lblPlayer1.setBounds(87, 31, 46, 14);
		contentPane.add(lblPlayer1);

		JLabel lblPlayer2 = new JLabel("Player 2");
		lblPlayer2.setBounds(284, 31, 46, 14);
		contentPane.add(lblPlayer2);

		JLabel lblPlayer1Name = new JLabel("Name:");
		lblPlayer1Name.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPlayer1Name.setBounds(31, 48, 46, 14);
		contentPane.add(lblPlayer1Name);

		JLabel lblPlayer2Name = new JLabel("Name:");
		lblPlayer2Name.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPlayer2Name.setBounds(228, 48, 46, 14);
		contentPane.add(lblPlayer2Name);

		textFieldPlayer1Name = new JTextField();
		textFieldPlayer1Name.setBounds(87, 45, 86, 20);
		contentPane.add(textFieldPlayer1Name);
		textFieldPlayer1Name.setColumns(10);

		textFieldPlayer2Name = new JTextField();
		textFieldPlayer2Name.setBounds(284, 45, 86, 20);
		contentPane.add(textFieldPlayer2Name);
		textFieldPlayer2Name.setColumns(10);

		JLabel lblRows = new JLabel("Number of Rows:");
		lblRows.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRows.setBounds(47, 86, 137, 14);
		contentPane.add(lblRows);

		JLabel lblColumns = new JLabel("Number of Columns:");
		lblColumns.setHorizontalAlignment(SwingConstants.RIGHT);
		lblColumns.setBounds(47, 119, 137, 14);
		contentPane.add(lblColumns);

		cmbRows = new JComboBox();
		cmbRows.setModel(new DefaultComboBoxModel(new String[] { "", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
		cmbRows.setBounds(215, 83, 86, 20);
		contentPane.add(cmbRows);

		cmbColumn = new JComboBox();
		cmbColumn.setModel(
				new DefaultComboBoxModel(new String[] { "", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
		cmbColumn.setBounds(215, 116, 86, 20);
		contentPane.add(cmbColumn);

		JLabel lblChecker = new JLabel("Number of Checkers:");
		lblChecker.setHorizontalAlignment(SwingConstants.RIGHT);
		lblChecker.setBounds(47, 154, 137, 14);
		contentPane.add(lblChecker);

		cmbCheck = new JComboBox();
		cmbCheck.setModel(new DefaultComboBoxModel(new String[] { "", "3", "4", "5", "6", "7", "8" }));
		cmbCheck.setBounds(215, 151, 86, 20);
		contentPane.add(cmbCheck);

		btnNewGame.setForeground(new Color(0, 100, 0));
		btnNewGame.setFont(new Font("Arial", Font.PLAIN, 11));
		btnNewGame.setBounds(76, 203, 97, 23);
		contentPane.add(btnNewGame);

		btnResumeGame.setForeground(new Color(0, 128, 128));
		btnResumeGame.setFont(new Font("Arial", Font.PLAIN, 11));
		btnResumeGame.setBounds(256, 203, 117, 23);
		contentPane.add(btnResumeGame);

		btnNewGame.addActionListener(this);
	}//ConnectNFrame

	private void validation() {
//		if (textFieldPlayer1Name.getText().length() == 0 || textFieldPlayer2Name.getText().length() == 0
//				|| cmbRows.getSelectedItem().toString().length() == 0 || cmbColumn.getSelectedItem().toString().length() == 0
//				|| cmbCheck.getSelectedItem().toString().length() == 0) {
			if (textFieldPlayer1Name.getText().length() == 0) {
				JOptionPane.showMessageDialog(this, "Please enter the name of Player 1", "Player 1 missing name",
						JOptionPane.ERROR_MESSAGE);
			} else if (textFieldPlayer2Name.getText().length() == 0) {
				JOptionPane.showMessageDialog(this, "Please enter the name of Player 2", "Player 2 missing name",
						JOptionPane.ERROR_MESSAGE);
			} else if (cmbRows.getSelectedItem().toString().length() == 0) {
				JOptionPane.showMessageDialog(this, "Please enter the number of rows", "Rows missing",
						JOptionPane.ERROR_MESSAGE);
			} else if (cmbColumn.getSelectedItem().toString().length() == 0) {
				JOptionPane.showMessageDialog(this, "Please enter the number of columns", "Columns missing",
						JOptionPane.ERROR_MESSAGE);
			} else if (cmbCheck.getSelectedItem().toString().length() == 0) {
				JOptionPane.showMessageDialog(this, "Please enter the number of checkers", "Checker missing",
						JOptionPane.ERROR_MESSAGE);
			} else if (cmbRows.getSelectedItem().toString().length() < cmbCheck.getSelectedItem().toString().length()) {
				JOptionPane.showMessageDialog(this, "Please a number a checker within range of rows", "Checker out of bounds",
						JOptionPane.ERROR_MESSAGE);
			} else if (cmbColumn.getSelectedItem().toString().length() < cmbCheck.getSelectedItem().toString().length()) {
				JOptionPane.showMessageDialog(this, "Please a number a checker within range of columns", "Checker out of bounds",
						JOptionPane.ERROR_MESSAGE);
			} else {
				gameInfo();
			}
//		}
	}// validation

	private void gameInfo() {
//		player1 = textFieldPlayer1Name.getText();
//		player2 = textFieldPlayer2Name.getText();
//		rows = Integer.parseInt(cmbRows.getSelectedItem().toString());
//		columns = Integer.parseInt(cmbColumn.getSelectedItem().toString());
//		checker = Integer.parseInt(cmbCheck.getSelectedItem().toString());
		ConnectNGameFrame frame = new ConnectNGameFrame();
		//rows, columns, player1, player2, checker
		frame.setVisible(true);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		dispose();
	}//gameInfo()

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnNewGame) {
			validation();

			
		} else if (e.getSource() == btnResumeGame) {

		}
	}//actionPerformed()
}
