package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class LoginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField ipTextField;
	private JTextField portTextField;
	private JTextField nameTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
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
	public LoginFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblip = new JLabel("服务器IP：");
		lblip.setBounds(100, 60, 74, 15);
		contentPane.add(lblip);
		
		JLabel label = new JLabel("服务器端口:");
		label.setBounds(100, 97, 74, 15);
		contentPane.add(label);
		
		ipTextField = new JTextField();
		ipTextField.setBounds(184, 57, 129, 21);
		contentPane.add(ipTextField);
		ipTextField.setColumns(10);
		
		portTextField = new JTextField();
		portTextField.setBounds(184, 94, 129, 21);
		contentPane.add(portTextField);
		portTextField.setColumns(10);
		
		JButton loginBtn = new JButton("连接服务器");
		loginBtn.setBounds(100, 170, 106, 23);
		contentPane.add(loginBtn);
		
		JButton exitBtn = new JButton("退出程序");
		exitBtn.setBounds(229, 170, 106, 23);
		contentPane.add(exitBtn);
		
		JLabel label_1 = new JLabel("登录昵称：");
		label_1.setBounds(100, 137, 74, 15);
		contentPane.add(label_1);
		
		nameTextField = new JTextField();
		nameTextField.setColumns(10);
		nameTextField.setBounds(184, 134, 129, 21);
		contentPane.add(nameTextField);
	}
}
