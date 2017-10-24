package ui;

import java.awt.BorderLayout;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import constants.Constants;
import constants.ConstantsMsg;
import entity.Player;
import entity.Room;
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
	//庄家界面
    private PlayerPanel dealerPanel;
    //操作按钮界面
    private OperationPanel operationPanel;		
	//用户界面
	private Map<String,PlayerPanel> playerPanels;
	
	private OperationListener operationListener;

	
	public MainFrame(OperationListener operationListener) {
		this.operationListener = operationListener;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(false);
	}

	/**
	 * 初始化界面
	 * @param room
	 */
	public void initFrame(Room room){
		dealerPanel = new PlayerPanel(room.getDealer());
		dealerPanel.setBounds(300, 30,Constants.PLAYER_PANEL_WIDTH, Constants.PLAYER_PANEL_HEIGHT);
		add(dealerPanel);
		
		operationPanel = new OperationPanel();
		operationPanel.setBounds(300, 45+Constants.PLAYER_PANEL_HEIGHT,Constants.PLAYER_PANEL_WIDTH, Constants.PLAYER_PANEL_HEIGHT);
		add(operationPanel);
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
		operationPanel.showReadyOperation();
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
		PlayerPanel playerPanel = new PlayerPanel(player);
		int size = playerPanels.size();
		playerPanel.setBounds(Constants.PLAYER_PANEL_WIDTH * size, 45+Constants.PLAYER_PANEL_HEIGHT*2, Constants.PLAYER_PANEL_WIDTH, Constants.PLAYER_PANEL_HEIGHT);
		setBounds(Constants.PLAYER_PANEL_WIDTH * (size+1), Constants.PLAYER_PANEL_HEIGHT*2+30,
				Constants.PLAYER_PANEL_WIDTH, Constants.PLAYER_PANEL_HEIGHT);
		add(playerPanel);
		playerPanels.put(player.getId(), playerPanel);
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
