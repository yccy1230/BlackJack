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
import entity.Card;
import entity.Dealer;
import entity.Hand;
import entity.Player;
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
		
		//test
		test();
		
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

		//初始化消息显示框
		msgLabel = new JLabel("maglabel");
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
	 * 显示主界面(With Ready Button)
	 */
	public void showMainFrame(){
		setVisible(true);
		loginFrame.setVisible(false);
	}
	
	/**
	 * 显示用户准备按钮
	 */
	public void showUserReadyBtn(){
		setVisible(true);
		operationPanel.showReadyOperation();
		loginFrame.setVisible(false);
	}
	
	/**
	 * 添加用户
	 * @param player
	 */
	public void addUserPanel(Player player){
		userPanel.addUserPanel(player);
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

	private void test(){
		//测试玩家1
		Player player = new Player();
		player.setNickname("玩家1");
		player.setId("玩家1");
		Hand hand = new Hand();
		List<Card> cards = new ArrayList<>();
		cards.add(new Card(Constants.CLUBS_1));
		cards.add(new Card(Constants.DIAMONDS_1));
		cards.add(new Card(Constants.CLUBS_3));
		cards.add(new Card(Constants.DIAMONDS_1));
		cards.add(new Card(Constants.CLUBS_5));
		cards.add(new Card(Constants.DIAMONDS_1));
		cards.add(new Card(Constants.CLUBS_7));
		cards.add(new Card(Constants.DIAMONDS_1));
		cards.add(new Card(Constants.CLUBS_9));
		hand.setCards(cards);
		player.setHand(hand);
		addUserPanel(player);

		//测试玩家2
		Player player2 = new Player();
		player2.setNickname("玩家2");
		player2.setId("玩家2");
		player2.setHand(hand);
		addUserPanel(player2);

		//测试玩家3
		Player player3 = new Player();
		player3.setNickname("玩家3");
		player3.setId("玩家3");
		player3.setHand(hand);
		addUserPanel(player3);
		
		Dealer dealer = new Dealer();
		dealer.setHand(hand);
		dealerPanel.refreashCardView(dealer);
		
		addUserPanel(player3);
		addUserPanel(player3);
		
		operationPanel.showTurnOperation();
	}
	
}
