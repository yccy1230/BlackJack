package ui;

import javax.swing.JPanel;
import listener.OperationListener;

import java.awt.*;
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

    /**
     * 默认显示准备界面
     * @param operationListener
     */
	public OperationPanel(OperationListener operationListener) {
	    this.operationListener = operationListener;

		setLayout(new BorderLayout(0, 0));
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
		validate();
	}
	
	/**
	 * 显示准备操作界面
	 */
	public void showReadyOperation(){
		hideAll();
		btnReady.setVisible(true);
		btnReady.setEnabled(true);
        validate();
	}
	
	/**
	 * 显示取消准备操作界面
	 */
	public void showCancelReadyOperation() {
        hideAll();
        btnReady.setText(ConstantsMsg.BUTTON_CANCEL_READY);
        btnReady.setVisible(true);
        btnReady.setEnabled(true);
        validate();
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
        validate();
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
        validate();
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
	    JPanel btnPanel = new JPanel();
	    btnPanel.setLayout(new FlowLayout());
		btnReady = new JButton(ConstantsMsg.BUTTON_READY);
        btnPanel.add(btnReady);

		hitBtn = new JButton(ConstantsMsg.BUTTON_HIT);
        btnPanel.add(hitBtn);
		
		btnStand = new JButton(ConstantsMsg.BUTTON_STAND);
        btnPanel.add(btnStand);
		
		btnDouble = new JButton(ConstantsMsg.BUTTON_DOUBLE);
        btnPanel.add(btnDouble);
		
		btnSurrender = new JButton(ConstantsMsg.BUTTON_SURRENDER);
        btnPanel.add(btnSurrender);

        btnPanel.setOpaque(false);
        add(btnPanel,BorderLayout.CENTER);

		msgLabel = new JLabel("");
		msgLabel.setHorizontalAlignment(SwingConstants.CENTER);

        msgLabel.setOpaque(false);
        add(msgLabel,BorderLayout.NORTH);
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
				bet = JOptionPane.showInputDialog(OperationPanel.this,ConstantsMsg.MSG_ENTER_BET);
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
