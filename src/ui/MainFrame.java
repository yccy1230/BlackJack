package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import constants.Constants;
import constants.ConstantsMsg;
import entity.*;
import listener.OperationListener;

/**
* @description 负责主界面展示，组织Panel
* @author Jack Chen
* @date 2017/10/17
*/
public class MainFrame extends JFrame {
    private LoginFrame loginFrame;					
    
	private JPanel contentPane;

    private OperationPanel operationPanel;
    private DealerPanel dealerPanel;
	private UserPanel userPanel;
	private JLabel msgLabel;
	
	private OperationListener operationListener;
	private WindowAdapter windowAdapter;

	
	public MainFrame(OperationListener operationListener) {
		this.operationListener = operationListener;
		this.loginFrame = new LoginFrame(operationListener);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setBounds(new Rectangle(0, 0, 1200, 780));
		setTitle(Constants.APP_NAME);

		contentPane = new JPanel();
		contentPane.setLayout(null);
		initFrame();
		setContentPane(contentPane);

		windowAdapter = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(contentPane,
						ConstantsMsg.MSG_CONFIRM_LOGOUT,ConstantsMsg.MSG_CONFIRM_LOGOUT_TITLE,JOptionPane.YES_NO_OPTION);
				if(result == 0){
					operationListener.onExitClicked();
				}
			}
		};

		addWindowListener(windowAdapter);
	}
	
	/**
	 * 初始化界面
	 */
	public void initFrame(){

		//初始化消息显示框
		msgLabel = new JLabel();
		msgLabel.setHorizontalAlignment(SwingConstants.CENTER);
		msgLabel.setFont(new Font("宋体", Font.PLAIN, 19));
		msgLabel.setForeground(Color.WHITE);
		msgLabel.setBounds(400, 340, 400, 30);
		contentPane.add(msgLabel);
		
		//初始化庄家界面
		dealerPanel = new DealerPanel();
		dealerPanel.setOpaque(false);
		dealerPanel.setBounds(330, 0, 610, 180);
		contentPane.add(dealerPanel);
		
		//初始化玩家界面
		userPanel = new UserPanel();
		userPanel.setOpaque(false);
		userPanel.setBounds(0, 330, 1200, 300);
		contentPane.add(userPanel);
		
		//初始化操作面板
		operationPanel = new OperationPanel(operationListener);
		operationPanel.setBounds(0, 590, 1180, 150);
		operationPanel.setOpaque(false);
		contentPane.add(operationPanel);
		
		//初始化背景
		JLabel background = new JLabel("");
		background.setIcon(new ImageIcon(test.class.getResource("/assets/deck.png")));
		background.setBounds(0, 0, 1200, 750);
		contentPane.add(background);
	}
	
	/**
	 * 显示登录界面
	 */
	public void showLoginFrame(){
		setVisible(false);
		loginFrame.setVisible(true);
	}

	/**
	 * 用户登录成功
	 */
	public void onLoginSuccess(List<Player> players){
		setVisible(true);
		operationPanel.showReadyOperation();
		loginFrame.setVisible(false);

		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			addUserPanel(player);
		}

		showMessage(ConstantsMsg.MSG_USER_INIT_MONEY);
	}

	/**
	 * 用户登录失败
	 */
	public void onLoginError(String msg){
		loginFrame.showLoginBtn();
		showMessage(msg);
	}


	/**
	 * 用户准备成功
	 */
	public void onUserReadySuccess(){
		operationPanel.showCancelReadyOperation();
		showToastMsg(ConstantsMsg.MSG_WAIT_OTHER_USER);
	}

	/**
	 * 用户准备失败
	 * @param msg 错误消息
	 */
	public void onUserReadyError(String msg){
		operationPanel.showReadyOperation();
		showMessage(ConstantsMsg.MSG_WAIT_OTHER_USER);
	}

	/**
	 * 用户取消准备成功
	 */
	public void onUserCancelReadySuccess(){
		operationPanel.showReadyOperation();
		showToastMsg("请准备...");
	}

	/**
	 * 用户取消准备失败
	 * @param msg
	 */
	public void onUserCancelReadyError(String msg){
		operationPanel.showCancelReadyOperation();
		showMessage(msg);
	}

	/**
	 * 用户(单次)操作失败
	 * @param msg
	 */
	public void onUserOperateError(String msg){
		showToastMsg(ConstantsMsg.MSG_OPERATION_ERROR);
		operationPanel.showTurnOperation();
	}
	/**
	 * 用户操作轮次
	 */
	public void onUserTurns(){
		showToastMsg(ConstantsMsg.MSG_USER_TURN);
		operationPanel.showTurnOperation();
	}

	/**
	 * 用户(加倍)操作成功
	 */
	public void onDoubleOperateSuccess(){
		showToastMsg(ConstantsMsg.MSG_OPERATION_DOUBLE_SUCCESS);
		operationPanel.showOperationWithoutDouble();
	}

	/**
	 * 用户(加倍)操作失败
	 */
	public void onDoubleOperateError(String msg){
		onUserOperateError(msg);
	}

	/**
	 * 接受到其他用户操作，更新用户UI
	 */
	public void onReceiveOtherOperate(Player player){
		userPanel.updatePlayerPanel(player);
		showToastMsg("玩家："+player.getNickname()+" 操作结束！");
	}

	/**
	 * 游戏开始
	 */
	public void onGameStart(){
		operationPanel.hideAll();
		showToastMsg(ConstantsMsg.MSG_GAME_START);
		removeWindowListener(windowAdapter);
	}

	/**
	 * 游戏结束
	 */
	public void onGameOver(List<ResultDetail> resultDetails){
		//展示游戏结果
		RankDialog resultDialog = new RankDialog();
		resultDialog.initResult(resultDetails);
		resultDialog.setVisible(true);
		operationPanel.showReadyOperation();
	}


	/**
	 * 其他用户退出房间
	 * @param userid
	 */
	public void onOtherUserExit(String userid){
		userPanel.removeUserPanel(userid);
	}

	/**
	 * 其他用户进入房间
	 * @param player
	 */
	public void onOtherUserEnterRoom(Player player){
		addUserPanel(player);
		showToastMsg("玩家："+player.getNickname()+" 进入房间。");
	}

	/**
	 * 刷新Dealer UI
	 * @param dealer
	 */
	public void onDealerUpdate(Dealer dealer) {
		dealerPanel.refreshCardView(dealer);
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
		msgLabel.setText(msg);
	}


	/**
	 * 添加用户
	 * @param player
	 */
	private void addUserPanel(Player player){
		userPanel.addUserPanel(player);
	}
}
