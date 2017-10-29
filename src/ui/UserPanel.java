package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import constants.Constants;
import constants.ConstantsMsg;
import entity.Player;


/**
* @description 负责玩家界面管理统筹，玩家ui控制类
* @author Jack Chen
* @date 2017/10/28
*/
public class UserPanel extends JPanel {
	//ui
	private List<PlayerPanel> playerPanels;
	private List<Player> playerData;
	
	/**
	 * 添加玩家
	 * @param player 玩家资料封装实体类
	 */
	public void addUserPanel(Player player){
		PlayerPanel playerPanel = getIdlePlayerPanel();
		if(playerPanel == null){
			//玩家已满，系统出错
			System.out.println(ConstantsMsg.ERROR_PLAYER_FULL);
			return;
		}
		//初始化界面
		playerPanel.addPlayer(player);
		//存储用户数据
		playerData.add(player);
	}
	
	/**
	 * 删除玩家
	 * @param playerParam 包含用户ID即可
	 */
	public void removeUserPanel(Player playerParam){
		//以data为基准进行遍历
		for (int i = 0; i < playerData.size(); i++) {
			Player player = playerData.get(i);
			if(player.getProperty()!=Constants.EMPTY_USER
					&&player.getId().equals(playerParam.getId())){
				playerPanels.get(i).remove();//关闭UI
				playerData.get(i).setProperty(Constants.EMPTY_USER);//清空该用户数据
				break;
			}
		}
	}
	
	/**
	 * 初始化玩家界面
	 * @param players 玩家s
	 */
	public void initUserPanel(List<Player> players){
		for (Player player : players) {
			addUserPanel(player);
		}
	}
	
	/**
	 * 更新玩家UI
	 */
	public void updatePlayerPanel(Player playerParam){
		//以data为基准进行遍历
		for (int i = 0; i < playerData.size(); i++) {
			Player player = playerData.get(i);
			if(player.getProperty()!=Constants.EMPTY_USER
					&&player.getId().equals(playerParam.getId())){
				playerPanels.get(i).refreashPlayerView(playerParam);
			}
		}
	}
	
	
	/**
	 * 默认构造5个PlayerPanel并固定位置 
	 */
	public UserPanel() {
		playerPanels = new ArrayList<>();
		playerData = new ArrayList<>();
		
		setLayout(null);
		
		PlayerPanel playerPanel1 = new PlayerPanel();
		playerPanel1.setBounds(20, 15, 215, 210);
		add(playerPanel1);
		
		PlayerPanel playerPanel2 = new PlayerPanel();
		playerPanel2.setBounds(260, 55, 215, 210);
		add(playerPanel2);
		
		PlayerPanel playerPanel3 = new PlayerPanel();
		playerPanel3.setBounds(490, 80, 215, 210);
		add(playerPanel3);
		
		PlayerPanel playerPanel4 = new PlayerPanel();
		playerPanel4.setBounds(720, 55, 215, 210);
		add(playerPanel4);
		
		PlayerPanel playerPanel5 = new PlayerPanel();
		playerPanel5.setBounds(960, 15, 215, 210);
		add(playerPanel5);
		
		//UI
		playerPanels.add(playerPanel1);
		playerPanels.add(playerPanel2);
		playerPanels.add(playerPanel3);
		playerPanels.add(playerPanel4);
		playerPanels.add(playerPanel5);
		
		setVisible(true);
	}
	
	/**
	 * 获取空闲UI
	 */
	private PlayerPanel getIdlePlayerPanel(){
		//查找是否有空闲UI（根据data数据判断）
		for (int i =0;i<playerData.size();i++) {
			Player player = playerData.get(i);
			if(player.getProperty()==Constants.EMPTY_USER){
				return playerPanels.get(i);
			}
		}
		
		//不存在空闲UI，获取全新UI
		if(playerData.size()>=Constants.MAX_PLAYER_NUM){
			return null;
		}else{
			return playerPanels.get(playerData.size());
		}
		
	}
}
