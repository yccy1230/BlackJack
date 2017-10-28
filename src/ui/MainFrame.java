package ui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import constants.Constants;
import constants.ConstantsMsg;
import entity.Player;
import listener.OperationListener;

/**
* @description 负责主界面展示，组织Panel
* @author Jack Chen
* @date 2017/10/17
*/
public class MainFrame extends JFrame {
	//登录界面
    private LoginFrame loginFrame;					
    
	private JPanel contentPane;

    private OperationPanel operationPanel;
    private DealerPanel dealerPanel;
	private UserPanel userPanel;
	
	private OperationListener operationListener;

	
	public MainFrame(OperationListener operationListener) {
		this.operationListener = operationListener;
		this.loginFrame = new LoginFrame(operationListener);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setBounds(new Rectangle(0, 0, 1200, 750));
		setTitle(Constants.APP_NAME);

		contentPane = new JPanel();
		contentPane.setLayout(null);

		initFrame();

		setContentPane(contentPane);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(contentPane,
						ConstantsMsg.MSG_CONFIRM_LOGOUT,ConstantsMsg.MSG_CONFIRM_LOGOUT_TITLE,JOptionPane.YES_NO_OPTION);
				if(result == 0 ){
					operationListener.onExitClicked();
				}
			}
		});
	}

	/**
	 * 初始化界面
	 */
	public void initFrame(){

		//初始化庄家界面
		dealerPanel = new DealerPanel();
		dealerPanel.setInUsed(true);
		contentPane.add(dealerPanel);

		//初始化操作面板
		operationPanel = new OperationPanel(operationListener);
		operationPanel.setBounds(45, 208, 200, 60);
        operationPanel.setOpaque(false);
		contentPane.add(operationPanel);
	}
	
	/**
	 * 显示登录界面
	 */
	public void showLoginFrame(){
		setVisible(false);
		loginFrame.setVisible(true);
	}
	
	/**
	 * 显示主界面(With Ready Button)
	 */
	public void showMainFrame(){
		setVisible(true);
		loginFrame.setVisible(false);
	}
	
	/**
	 * 显示取消准备界面
	 */
	public void showReadyCancelFrame(){
		operationPanel.toastMessage(ConstantsMsg.MSG_WAIT_OTHER_USER);
		operationPanel.showCancelReadyOperation();
	}
	
	/**
	 * 添加用户
	 * @param player
	 */
	public void addUserPanel(Player player){
		
	}
	
	/**
	 * 显示弹窗提示信息
	 */
	public void showMessage(String msg){
		JOptionPane.showMessageDialog(this, msg);
	}
	
	/**
	 * 显示普通消息
	 */
	public void showToastMsg(String msg){
		operationPanel.toastMessage(msg);
	}
	
}
