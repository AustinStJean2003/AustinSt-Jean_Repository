package ConnectN;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;

public class ConnectN_HelpPanel extends JPanel {

	private JTextArea text = new JTextArea();
	
	public ConnectN_HelpPanel() {
		setForeground(new Color(30, 144, 255));
		setBackground(new Color(0, 0, 128));
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		text.setForeground(new Color(255, 255, 255));
		
		text.setEditable(false);
		text.setFont(new Font("Comic sans MS", 0, 11));
		text.setBounds(10, 55, 648, 525);
		
		text.setText("Here is how to play ConnectN \n 1.");
		text.setOpaque(false);
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		add(text, gbc_lblTitle);

	}

}
