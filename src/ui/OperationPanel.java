package ui;

import javax.swing.JPanel;
import javax.swing.JButton;

public class OperationPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public OperationPanel() {
		setLayout(null);
		
		JButton hitBtn = new JButton("要牌");
		hitBtn.setBounds(47, 26, 93, 23);
		add(hitBtn);
		
		JButton btnStand = new JButton("停牌");
		btnStand.setBounds(180, 26, 93, 23);
		add(btnStand);
		
		JButton btnReady = new JButton("准备");
		btnReady.setBounds(47, 26, 93, 23);
		add(btnReady);
		
		JButton btnExit = new JButton("退出");
		btnExit.setBounds(180, 26, 93, 23);
		add(btnExit);
		
		JButton btnDouble = new JButton("加倍");
		btnDouble.setBounds(308, 26, 93, 23);
		add(btnDouble);
		
		JButton btnSurrender = new JButton("认输");
		btnSurrender.setBounds(308, 26, 93, 23);
		add(btnSurrender);

	}
}
