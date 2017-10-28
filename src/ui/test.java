package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

/**
* @description ui测试类
* @author Jack Chen
* @date 2017/10/29
*/
public class test extends JFrame {

	private JPanel contentPane;
	private JLabel background;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test frame = new test();
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
	public test() {
		setResizable(false);
		setBounds(new Rectangle(0, 0, 1200, 750));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1200, 780);
		contentPane = new JPanel();
		contentPane.setBounds(new Rectangle(0, 0, 1200, 750));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 19));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(400, 340, 400, 30);
		contentPane.add(lblNewLabel);
		
		JPanel dealerPanel = new JPanel();
		dealerPanel.setOpaque(false);
		dealerPanel.setBounds(330, 0, 610, 180);
		contentPane.add(dealerPanel);
		dealerPanel.setLayout(null);
		
		JLabel label_10 = new JLabel("");
		label_10.setBounds(70, 10, 90, 120);
		dealerPanel.add(label_10);
		
		JLabel label_11 = new JLabel("");
		label_11.setBounds(110, 10, 90, 120);
		dealerPanel.add(label_11);
		
		JLabel label_12 = new JLabel("");
		label_12.setBounds(150, 10, 90, 120);
		dealerPanel.add(label_12);
		
		JLabel label_13 = new JLabel("");
		label_13.setBounds(190, 10, 90, 120);
		dealerPanel.add(label_13);
		
		JLabel label_14 = new JLabel("");
		label_14.setBounds(230, 10, 90, 120);
		dealerPanel.add(label_14);
		
		JLabel label_15 = new JLabel("");
		label_15.setBounds(270, 10, 90, 120);
		dealerPanel.add(label_15);
		
		JLabel label_16 = new JLabel("");
		label_16.setBounds(310, 10, 90, 120);
		dealerPanel.add(label_16);
		
		JLabel label_17 = new JLabel("");
		label_17.setBounds(350, 10, 90, 120);
		dealerPanel.add(label_17);
		
		JLabel label_18 = new JLabel("");
		label_18.setBounds(390, 10, 90, 120);
		dealerPanel.add(label_18);
		
		JLabel label_19 = new JLabel("");
		label_19.setBounds(430, 10, 90, 120);
		dealerPanel.add(label_19);
		
		JLabel label_20 = new JLabel("xxx");
		label_20.setFont(new Font("宋体", Font.PLAIN, 16));
		label_20.setHorizontalAlignment(SwingConstants.CENTER);
		label_20.setForeground(Color.WHITE);
		label_20.setBounds(240, 145, 54, 15);
		dealerPanel.add(label_20);
		
		JPanel userPanel = new JPanel();
		userPanel.setOpaque(false);
		userPanel.setBounds(0, 331, 1194, 282);
		contentPane.add(userPanel);
		userPanel.setLayout(null);
		
		JPanel playerPanel1 = new JPanel();
		playerPanel1.setOpaque(false);
		playerPanel1.setBounds(19, 3, 216, 209);
		userPanel.add(playerPanel1);
		playerPanel1.setLayout(null);
		ImageIcon image = new ImageIcon(CardView.class.getResource("/assets/Diamod1.png"));
		image.setImage(image.getImage().getScaledInstance(77, 111,Image.SCALE_DEFAULT ));
		
		JLabel label_1 = new JLabel("");
		label_1.setBounds(20, 10, 77, 111);
		playerPanel1.add(label_1);
		label_1.setIcon(image);
		
		JLabel label = new JLabel("");
		label.setBounds(45, 10, 77, 111);
		playerPanel1.add(label);
		
		JLabel label_2 = new JLabel("");
		label_2.setBounds(70, 10, 77, 111);
		playerPanel1.add(label_2);
		
		JLabel label_3 = new JLabel("");
		label_3.setBounds(95, 10, 77, 111);
		playerPanel1.add(label_3);
		
		JLabel label_4 = new JLabel("");
		label_4.setBounds(120, 10, 77, 111);
		playerPanel1.add(label_4);
		
		JLabel label_5 = new JLabel("");
		label_5.setBounds(20, 70, 77, 111);
		playerPanel1.add(label_5);
		
		JLabel label_6 = new JLabel("");
		label_6.setBounds(45, 70, 77, 111);
		playerPanel1.add(label_6);
		
		JLabel label_7 = new JLabel("");
		label_7.setBounds(70, 70, 77, 111);
		playerPanel1.add(label_7);
		
		JLabel label_8 = new JLabel("");
		label_8.setBounds(95, 68, 77, 111);
		playerPanel1.add(label_8);
		
		JLabel label_9 = new JLabel("");
		label_9.setBounds(120, 68, 77, 111);
		playerPanel1.add(label_9);
		
		JLabel nameHint = new JLabel("玩家：");
		nameHint.setForeground(Color.WHITE);
		nameHint.setBounds(43, 188, 54, 15);
		playerPanel1.add(nameHint);
		
		JLabel nameLabel = new JLabel("xxx");
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setBounds(92, 188, 54, 15);
		playerPanel1.add(nameLabel);
		
		JPanel playerPanel2 = new JPanel();
		playerPanel2.setOpaque(false);
		playerPanel2.setBounds(258, 44, 216, 209);
		userPanel.add(playerPanel2);
		playerPanel2.setLayout(null);
		
		JPanel playerPanel3 = new JPanel();
		playerPanel3.setOpaque(false);
		playerPanel3.setBounds(490, 69, 216, 209);
		userPanel.add(playerPanel3);
		playerPanel3.setLayout(null);
		
		JPanel playerPanel4 = new JPanel();
		playerPanel4.setOpaque(false);
		playerPanel4.setBounds(724, 45, 216, 209);
		userPanel.add(playerPanel4);
		playerPanel4.setLayout(null);
		
		JPanel playerPanel5 = new JPanel();
		playerPanel5.setOpaque(false);
		playerPanel5.setBounds(960, 4, 216, 209);
		userPanel.add(playerPanel5);
		playerPanel5.setLayout(null);
		
		JPanel operationPanel = new JPanel();
		operationPanel.setOpaque(false);
		operationPanel.setBounds(0, 589, 1184, 152);
		contentPane.add(operationPanel);
		operationPanel.setLayout(null);
		
		JButton button = new JButton("");
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorder(null);
		button.setBounds(700, 60, 80, 80);
		operationPanel.add(button);
		
		JButton btnHit = new JButton("");
		btnHit.setBounds(800, 50, 80, 80);
		operationPanel.add(btnHit);
		btnHit.setBorder(null);
		btnHit.setContentAreaFilled(false);
		btnHit.setOpaque(false);
		
		JButton btnStand = new JButton("");
		btnStand.setBounds(900, 40, 80, 80);
		operationPanel.add(btnStand);
		btnStand.setOpaque(false);
		btnStand.setContentAreaFilled(false);
		btnStand.setBorder(null);
		
		JButton button_1 = new JButton("");
		button_1.setBounds(1000, 20, 80, 80);
		operationPanel.add(button_1);
		button_1.setOpaque(false);
		button_1.setContentAreaFilled(false);
		button_1.setBorder(null);
		btnHit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "onclick");
			}
		});
		
		background = new JLabel("");
		background.setIcon(new ImageIcon(test.class.getResource("/assets/deck.png")));
		background.setBounds(0, 0, 1194, 750);
		contentPane.add(background);
		
	}
}
