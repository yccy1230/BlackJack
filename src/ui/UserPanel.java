package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import entity.Player;


/**
* @description 负责玩家界面管理统筹，玩家ui控制类
* @author Jack Chen
* @date 2017/10/28
*/
public class UserPanel extends JPanel {
	//ui
	private List<PlayerPanel> playerPanels;
	//data
	private List<Player> playerData;
	
	/**
	 * 添加玩家
	 * @param player 玩家资料封装实体类
	 */
	public void addUserPanel(Player player){
		PlayerPanel playerPanel = playerPanels.get(playerData.size());
		playerPanel.setPlayer(player);
		playerPanel.setInUsed(true);
		playerData.add(player);
	}
	
	/**
	 * 删除玩家
	 * @param player 包含用户ID即可
	 */
	public void removeUserPanel(Player playerParam){
		for (int i = 0; i < playerData.size(); i++) {
			Player player = playerData.get(i);
			if(player.getId().equals(playerParam.getId())){
				playerPanels.get(i).setInUsed(false);
				playerPanels.get(i).setVisible(false);
				break;
			}
		}
	}
	
	/**
	 * 初始化玩家界面
	 * @param players 玩家s
	 */
	public void initUserPanel(List<Player> players){
		playerData.addAll(players);
		for (Player player : players) {
			addUserPanel(player);
		}
	}
	
	/**
	 * 初始化UI，默认构造5个PlayerPanel并固定位置
	 */
	public UserPanel() {
		playerPanels = new ArrayList<>();
		playerData = new ArrayList<>();
		
		PlayerPanel playerPanel1 = new PlayerPanel();
		playerPanel1.setOpaque(false);
		playerPanel1.setBounds(19, 3, 216, 209);
		playerPanel1.setLayout(null);
		
		PlayerPanel playerPanel2 = new PlayerPanel();
		playerPanel2.setOpaque(false);
		playerPanel2.setBounds(258, 44, 216, 209);
		add(playerPanel2);
		playerPanel2.setLayout(null);
		
		PlayerPanel playerPanel3 = new PlayerPanel();
		playerPanel3.setOpaque(false);
		playerPanel3.setBounds(490, 69, 216, 209);
		add(playerPanel3);
		playerPanel3.setLayout(null);
		
		PlayerPanel playerPanel4 = new PlayerPanel();
		playerPanel4.setOpaque(false);
		playerPanel4.setBounds(724, 45, 216, 209);
		add(playerPanel4);
		playerPanel4.setLayout(null);
		
		PlayerPanel playerPanel5 = new PlayerPanel();
		playerPanel5.setOpaque(false);
		playerPanel5.setBounds(960, 4, 216, 209);
		add(playerPanel5);
		playerPanel5.setLayout(null);
		
		//暂存UI
		playerPanels.add(playerPanel1);
		playerPanels.add(playerPanel2);
		playerPanels.add(playerPanel3);
		playerPanels.add(playerPanel4);
		playerPanels.add(playerPanel5);
	}
	
	
}
