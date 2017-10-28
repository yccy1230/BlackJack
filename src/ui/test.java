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
		setBounds(new Rectangle(0, 0, 1200, 750));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1200, 780);
		contentPane = new JPanel();
		contentPane.setBounds(new Rectangle(0, 0, 1200, 750));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel operationPanel = new JPanel();
		operationPanel.setOpaque(false);
		operationPanel.setBounds(0, 589, 1184, 152);
		contentPane.add(operationPanel);
		operationPanel.setLayout(null);
		
		JButton btnHit = new JButton("");
		btnHit.setBounds(773, 42, 100, 100);
		operationPanel.add(btnHit);
		btnHit.setBorder(null);
		btnHit.setContentAreaFilled(false);
		btnHit.setOpaque(false);
		
		JButton btnStand = new JButton("");
		btnStand.setBounds(899, 30, 100, 100);
		operationPanel.add(btnStand);
		btnStand.setOpaque(false);
		btnStand.setContentAreaFilled(false);
		btnStand.setBorder(null);
		
		JButton button_1 = new JButton("");
		button_1.setBounds(1023, 0, 100, 100);
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
		background.setBounds(0, 0, 1184, 750);
		contentPane.add(background);
	}
}
