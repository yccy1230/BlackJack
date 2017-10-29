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
	private JButton btnCancel;
	
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
        btnCancel.setVisible(true);
        btnCancel.setEnabled(true);
    }
	
	/**
	 * 隐藏所有按钮
	 */
	public void hideAll(){
		hitBtn.setVisible(false);
		btnStand.setVisible(false);
		btnDouble.setVisible(false);
		btnSurrender.setVisible(false);
		btnReady.setVisible(false);
		btnCancel.setVisible(false);
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
		btnCancel.setEnabled(false);
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
		btnCancel.setEnabled(true);
	}
	
	/**
	 * 初始化按钮
	 */
	private void initButton(){
		btnReady = new JButton();
		btnReady.setIcon(new ImageIcon(OperationPanel.class.getResource("/assets/btn_ready.png")));
		btnReady.setBounds(800, 50, 80, 80);
		setBtnProperty(btnReady);
		btnReady.setToolTipText(ConstantsMsg.BUTTON_READY);
        add(btnReady);
		
        btnCancel = new JButton();
        btnCancel.setIcon(new ImageIcon(OperationPanel.class.getResource("/assets/btn_cancel_ready.png")));
        btnCancel.setBounds(800, 50, 80, 80);
		setBtnProperty(btnCancel);
		btnCancel.setToolTipText(ConstantsMsg.BUTTON_CANCEL_READY);
        add(btnCancel);
        
		btnStand = new JButton();
		btnStand.setIcon(new ImageIcon(OperationPanel.class.getResource("/assets/btn_stand.png")));
		btnStand.setBounds(800, 50, 80, 80);
		setBtnProperty(btnStand);
		btnStand.setToolTipText(ConstantsMsg.BUTTON_STAND);
		add(btnStand);

		hitBtn = new JButton();
		hitBtn.setIcon(new ImageIcon(OperationPanel.class.getResource("/assets/btn_hit.png")));
		hitBtn.setBounds(700, 60, 80, 80);
		setBtnProperty(hitBtn);
		hitBtn.setToolTipText(ConstantsMsg.BUTTON_HIT);
        add(hitBtn);
		
		btnDouble = new JButton();
		btnDouble.setIcon(new ImageIcon(OperationPanel.class.getResource("/assets/btn_double.png")));
		btnDouble.setBounds(900, 40, 80, 80);
		setBtnProperty(btnDouble);
		btnDouble.setToolTipText(ConstantsMsg.BUTTON_DOUBLE);
        add(btnDouble);
		
		btnSurrender = new JButton();
		btnSurrender.setIcon(new ImageIcon(OperationPanel.class.getResource("/assets/btn_surround.png")));
		btnSurrender.setBounds(1000, 20, 80, 80);
		setBtnProperty(btnSurrender);
		btnSurrender.setToolTipText(ConstantsMsg.BUTTON_SURRENDER);
		add(btnSurrender);
	}

	private void initListener() {
		hitBtn.addActionListener(this);
		btnStand.addActionListener(this);
		btnDouble.addActionListener(this);
		btnSurrender.addActionListener(this);
		btnReady.addActionListener(this);
		btnCancel.addActionListener(this);
	}

	private void setBtnProperty(JButton button){
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setBorder(null);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton curBtn = (JButton) arg0.getSource();
		if(curBtn==btnReady){
			String bet;
			do {
				bet = JOptionPane.showInputDialog(null,ConstantsMsg.MSG_ENTER_BET);
				if(bet==null) {return;}
			}while (!bet.matches("\\d+"));
			if(operationListener!=null){
				operationListener.onReadyClicked(Integer.parseInt(bet));
				btnReady.setEnabled(false);
			}
		}else if(curBtn==btnCancel){
			if(operationListener!=null){
				operationListener.onCancelReadyClicked();
				hideAll();
			}
		}else if(curBtn==hitBtn){
			if(operationListener!=null){
				operationListener.onHitClicked();
				hideAll();
			}
		}else if(curBtn==btnStand){
			if(operationListener!=null){
				operationListener.onStandClicked();
				hideAll();
			}
		}else if(curBtn==btnDouble){
			if(operationListener!=null){
				operationListener.onDoubleClicked();
				disableAll();
			}
		}else if(curBtn==btnSurrender){
			if(operationListener!=null){
				operationListener.onSurrenderClicked();
				disableAll();
			}
		}
	}
}
