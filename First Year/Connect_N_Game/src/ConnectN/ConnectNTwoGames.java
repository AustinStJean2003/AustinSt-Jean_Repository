package ConnectN;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Font;

public class ConnectNTwoGames extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JButton btnConsole;
	private JButton btnFrame;
	private JTextArea txtChoose;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectNTwoGames frame = new ConnectNTwoGames();
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
	
	public ConnectNTwoGames() {
		setTitle("Welcome to Connect N");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 240));
		contentPane.setForeground(new Color(255, 255, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnConsole = new JButton("CONSOLE");
		btnConsole.setForeground(new Color(106, 90, 205));
		btnConsole.setBounds(30, 116, 125, 62);
		contentPane.add(btnConsole);
		
		btnFrame = new JButton("FRAME");
		btnFrame.setForeground(new Color(95, 158, 160));
		btnFrame.setBounds(279, 116, 125, 62);
		contentPane.add(btnFrame);
		
		txtChoose = new JTextArea();
		txtChoose.setBackground(new Color(255, 255, 240));
		txtChoose.setEditable(false);
		txtChoose.setLineWrap(true);
		txtChoose.setText("Please choose what version of Connect N you would like to play");
		txtChoose.setBounds(30, 77, 357, 28);
		contentPane.add(txtChoose);
		
		JTextArea txtrWelcomeToConnect = new JTextArea();
		txtrWelcomeToConnect.setBackground(new Color(255, 255, 240));
		txtrWelcomeToConnect.setFont(new Font("Impact", Font.PLAIN, 13));
		txtrWelcomeToConnect.setText("Welcome to Connect N");
		txtrWelcomeToConnect.setBounds(155, 11, 136, 42);
		contentPane.add(txtrWelcomeToConnect);
		btnFrame.addActionListener(this);
		btnConsole.addActionListener(this);
	}//ConnectNTwoGames
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnFrame) {
			ConnectNFrame frame = new ConnectNFrame();
			frame.setVisible(true);
			frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			dispose();
		} else if (e.getSource() == btnConsole) {
			ConnectNInterface console = new ConnectNInterface();
			dispose();
			console.gameStatus();	
		}
	}//actionPerformed
}//ConnectNTwoGames
