package ui;

import javax.swing.JPanel;
import listener.OperationListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import constants.ConstantsMsg;

/**
 * 提供操作面板相关操作，同时回调相关接口
 * @author Jack Chen
 *
 */
public class OperationPanel extends JPanel implements ActionListener{

	private OperationListener operationListener;
	private JButton hitBtn;
	private JButton btnStand;
	private JButton btnDouble;
	private JButton btnSurrender;
	private JButton btnReady;
	private JLabel msgLabel;
	
	public OperationPanel() {
		setLayout(null);
		initButton();
		initListener();
		hideAll();
		setVisible(true);
	}

	/**
	 * 显示游戏过程中操作界面
	 */
	public void showTurnOperation(){
		hideAll();
		hitBtn.setVisible(true);
		btnStand.setVisible(true);
		btnDouble.setVisible(true);
		btnSurrender.setVisible(true);
	}
	
	/**
	 * 显示准备操作界面
	 */
	public void showReadyOperation(){
		hideAll();
		btnReady.setVisible(true);
		btnReady.setEnabled(true);
	}
	
	/**
	 * 显示取消准备操作界面
	 */
	public void showCancelReadyOperation(){
		hideAll();
		btnReady.setText(ConstantsMsg.BUTTON_CANCEL_READY);
		btnReady.setVisible(true);
		btnReady.setEnabled(true);
	}
	
	
	/**
	 * 隐藏所有按钮
	 */
	private void hideAll(){
		hitBtn.setVisible(false);
		btnStand.setVisible(false);
		btnDouble.setVisible(false);
		btnSurrender.setVisible(false);
		btnReady.setVisible(false);
	}
	
	/**
	 * 禁用所有按钮
	 */
	private void disableAll(){
		hitBtn.setEnabled(false);
		btnStand.setEnabled(false);
		btnDouble.setEnabled(false);
		btnSurrender.setEnabled(false);
		btnReady.setEnabled(false);
	}
	
	/**
	 * 启用所有按钮
	 */
	public void enableAll(){
		hitBtn.setEnabled(true);
		btnStand.setEnabled(true);
		btnDouble.setEnabled(true);
		btnSurrender.setEnabled(true);
		btnReady.setEnabled(true);
	}
	
	/**
	 * 显示消息
	 */
	public void toastMessage(String msg){
		msgLabel.setText(msg);
	}
	
	/**
	 * 初始化按钮
	 */
	private void initButton(){
		btnReady = new JButton(ConstantsMsg.BUTTON_READY);
		btnReady.setBounds(179, 42, 93, 23);
		add(btnReady);

		hitBtn = new JButton(ConstantsMsg.BUTTON_HIT);
		hitBtn.setBounds(25, 42, 93, 23);
		add(hitBtn);
		
		btnStand = new JButton(ConstantsMsg.BUTTON_STAND);
		btnStand.setBounds(128, 42, 93, 23);
		add(btnStand);
		
		btnDouble = new JButton(ConstantsMsg.BUTTON_DOUBLE);
		btnDouble.setBounds(231, 42, 93, 23);
		add(btnDouble);
		
		btnSurrender = new JButton(ConstantsMsg.BUTTON_SURRENDER);
		btnSurrender.setBounds(335, 42, 93, 23);
		add(btnSurrender);
		
		msgLabel = new JLabel("");
		msgLabel.setHorizontalAlignment(SwingConstants.CENTER);
		msgLabel.setBounds(101, 9, 251, 23);
		add(msgLabel);
	}
	
	private void initListener() {
		hitBtn.addActionListener(this);
		btnStand.addActionListener(this);
		btnDouble.addActionListener(this);
		btnSurrender.addActionListener(this);
		btnReady.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand()){
		case ConstantsMsg.BUTTON_READY:
			String bet = new String();
			do {
				bet = JOptionPane.showInputDialog(OperationPanel.this,ConstantsMsg.MSG_ENTER_BET);
			}while (!bet.matches("\\d+"));
			if(operationListener!=null){
				operationListener.onReadyClicked(Integer.parseInt(bet));
				btnReady.setEnabled(false);
			}
			break;
		case ConstantsMsg.BUTTON_CANCEL_READY:
			if(operationListener!=null){
				operationListener.onCancelReadyClicked();
				hideAll();
			}
			
			break;
		case ConstantsMsg.BUTTON_HIT:
			if(operationListener!=null){
				operationListener.onHitClicked();
				hideAll();
			}
			break;
		case ConstantsMsg.BUTTON_STAND:
			if(operationListener!=null){
				operationListener.onStandClicked();
				hideAll();
			}
			break;
		case ConstantsMsg.BUTTON_DOUBLE:
			if(operationListener!=null){
				operationListener.onDoubleClicked();
				disableAll();
			}
			break;
		case ConstantsMsg.BUTTON_SURRENDER:
			if(operationListener!=null){
				operationListener.onSurrenderClicked();
				disableAll();
			}
			break;
		default:
			break;
		}
	}
}
