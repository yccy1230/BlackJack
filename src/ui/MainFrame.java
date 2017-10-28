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
    private LoginFrame loginFrame;					
    
	private JPanel contentPane;
	private JPanel userPane;

    private DealerPanel dealerPanel;
    private OperationPanel operationPanel;
	private Map<String,PlayerPanel> playerPanels;
	
	private OperationListener operationListener;

	
	public MainFrame(OperationListener operationListener) {
		this.operationListener = operationListener;
		this.loginFrame = new LoginFrame(operationListener);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		setTitle(Constants.APP_NAME);

		contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
                ImageIcon image = new ImageIcon(getClass().getResource("/assets/deck.png"));
				g.drawImage(image.getImage(), 0, 0, image.getIconWidth(), image.getIconHeight(), image.getImageObserver());
			}
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
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
		dealerPanel.setVisible(true);
		contentPane.add(dealerPanel);

		//初始化操作面板
		operationPanel = new OperationPanel(operationListener);
		operationPanel.setBounds(45, 208, 200, 60);
        operationPanel.setOpaque(false);
		contentPane.add(operationPanel);

		//用户面板(FlowLayout)
		userPane = new JPanel();
		userPane.setLayout(new FlowLayout());
		userPane.setPreferredSize(new Dimension(600, Constants.CARD_HEIGHT));
		contentPane.add(userPane);

		//调整size
        setSize(Constants.MAIN_FRAME_WIDTH,Constants.MAIN_FRAME_HEIGHT+30);
        setResizable(false);
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
		PlayerPanel playerPanel = new PlayerPanel(player);
		int size = playerPanels.size();
		playerPanel.setBounds(Constants.PLAYER_PANEL_WIDTH * size, 45+Constants.PLAYER_PANEL_HEIGHT*2, Constants.PLAYER_PANEL_WIDTH, Constants.PLAYER_PANEL_HEIGHT);
		setBounds(Constants.PLAYER_PANEL_WIDTH * (size+1), Constants.PLAYER_PANEL_HEIGHT*2+30,
				Constants.PLAYER_PANEL_WIDTH, Constants.PLAYER_PANEL_HEIGHT);
		userPane.add(playerPanel);
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
