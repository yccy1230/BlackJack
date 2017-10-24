package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import listener.OperationListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import constants.ConstantsMsg;

import javax.swing.JButton;

public class LoginFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField ipTextField;
	private JTextField portTextField;
	private JTextField nameTextField;
	private JButton loginBtn;
	private JButton exitBtn;
	
	private OperationListener operationListener;
	
	public LoginFrame(OperationListener operationListener) {
		this.operationListener = operationListener;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblip = new JLabel(ConstantsMsg.LABEL_IP);
		lblip.setBounds(100, 60, 74, 15);
		contentPane.add(lblip);
		
		JLabel label = new JLabel(ConstantsMsg.LABEL_PORT);
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
		
		JLabel label_1 = new JLabel(ConstantsMsg.LABEL_NICKNAME);
		label_1.setBounds(100, 137, 74, 15);
		contentPane.add(label_1);
		
		nameTextField = new JTextField();
		nameTextField.setColumns(10);
		nameTextField.setBounds(184, 134, 129, 21);
		contentPane.add(nameTextField);
		
		exitBtn = new JButton(ConstantsMsg.BUTTON_EXIT);
		exitBtn.setBounds(229, 170, 106, 23);
		exitBtn.addActionListener(this);

		contentPane.add(exitBtn);
		
		loginBtn = new JButton(ConstantsMsg.BUTTON_CONNECT_SERVER);
		loginBtn.setBounds(100, 170, 106, 23);
		loginBtn.addActionListener(this);
		contentPane.add(loginBtn);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
			case ConstantsMsg.BUTTON_CONNECT_SERVER:
				String ip = ipTextField.getText();
				String port = portTextField.getText();
				String nickName = nameTextField.getText();
				if(ip==null||"".equals(ip)||port==null|!port.matches("\\d+")
						||nickName==null||"".equals(nickName)){
					JOptionPane.showMessageDialog(contentPane,ConstantsMsg.MSG_EMPTY_INPUT);
					return;
				}
				if(operationListener!=null){
					loginBtn.setEnabled(false);
					exitBtn.setEnabled(false);
					new Thread(new Runnable() {
						@Override
						public void run() {
							operationListener.onConnectClicked(ip,Integer.parseInt(port),nickName);
						}
					}).start();
				}
				break;
			case ConstantsMsg.BUTTON_EXIT:
				System.exit(0);
				break;
			default:
				break;
		}
		
	}
}
