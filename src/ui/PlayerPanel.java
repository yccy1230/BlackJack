package ui;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class PlayerPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public PlayerPanel() {
		setLayout(null);
		
		JLabel label = new JLabel("玩家：");
		label.setBounds(35, 296, 54, 15);
		add(label);
		
		JLabel nickNameLabel = new JLabel("");
		label.setLabelFor(nickNameLabel);
		nickNameLabel.setBounds(97, 296, 54, 15);
		add(nickNameLabel);

	}
}
