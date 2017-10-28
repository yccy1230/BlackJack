package ui;

import javax.swing.*;

import listener.OperationListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import constants.ConstantsMsg;

/**
 * 提供操作面板相关操作，同时回调相关接口
 * @author Jack Chen
 *
 */
public class OperationPanel extends JPanel implements ActionListener{
	
	private JButton hitBtn;
	private JButton btnStand;
	private JButton btnDouble;
	private JButton btnSurrender;
	private JButton btnReady;
	
	private OperationListener operationListener;

    /**
     * 默认显示准备界面
     * @param operationListener
     */
	public OperationPanel(OperationListener operationListener) {
	    this.operationListener = operationListener;

		setLayout(null);
		initButton();
		initListener();
		hideAll();
		showReadyOperation();
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
	public void showCancelReadyOperation() {
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
	 * 初始化按钮
	 */
	private void initButton(){
		btnReady = new JButton(ConstantsMsg.BUTTON_READY);
		btnReady.setIcon(new ImageIcon(OperationPanel.class.getResource("/assets/BackGround.jpg")));
		btnReady.setBounds(800, 50, 80, 80);
        add(btnReady);
		
		btnStand = new JButton(ConstantsMsg.BUTTON_STAND);
		btnStand.setIcon(new ImageIcon(OperationPanel.class.getResource("/assets/BackGround.jpg")));
		btnStand.setBounds(800, 50, 80, 80);
		add(btnStand);

		hitBtn = new JButton(ConstantsMsg.BUTTON_HIT);
		hitBtn.setIcon(new ImageIcon(OperationPanel.class.getResource("/assets/BackGround.jpg")));
		hitBtn.setBounds(700, 60, 80, 80);
        add(hitBtn);
		
		btnDouble = new JButton(ConstantsMsg.BUTTON_DOUBLE);
		btnDouble.setIcon(new ImageIcon(OperationPanel.class.getResource("/assets/BackGround.jpg")));
		btnDouble.setBounds(900, 40, 80, 80);
        add(btnDouble);
		
		btnSurrender = new JButton(ConstantsMsg.BUTTON_SURRENDER);
		btnSurrender.setIcon(new ImageIcon(OperationPanel.class.getResource("/assets/BackGround.jpg")));
		btnSurrender.setBounds(1000, 20, 80, 80);
		add(btnSurrender);
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
			String bet;
			do {
				bet = JOptionPane.showInputDialog(null,ConstantsMsg.MSG_ENTER_BET);
				if(bet==null) {return;}
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
