package ui;

import javax.swing.*;

import constants.Constants;
import entity.Card;
import entity.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
* @description 单个Player界面
* @author Jack Chen
* @date 2017/10/24
*/
public class PlayerPanel extends JPanel {
	protected List<CardView> cardViews;
	protected List<Card> cardData;
	protected JLabel nickNameLabel;
	private boolean inUsed;

	public PlayerPanel() {
		initData();
	}

	/**
	 * 根据玩家信息构造界面
	 * @param player
	 */
	public PlayerPanel(Player player) {
		initData();
		initView();
		setPlayer(player);
		JLabel nameLabel = new JLabel("玩家：");
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setBounds(43, 188, 54, 15);
		add(nameLabel);
		
		nickNameLabel = new JLabel(player.getNickname());
		nickNameLabel.setForeground(Color.WHITE);
		nickNameLabel.setBounds(92, 188, 54, 15);
		nameLabel.setLabelFor(nickNameLabel);
		add(nickNameLabel);
	}

	/**
	 * 设置用户资料，并启用界面
	 * @param player
	 */
	public void setPlayer(Player player){
		nickNameLabel.setText(player.getNickname());
		refreashUI(player);
	}

	/**
	 * 刷新UI
	 */
	public void refreashUI(Player player){
		List<Card> cards = player.getHand().getCards();
		for (int i = 0; i <cardViews.size(); i++) {
			if(i<cards.size()){
				cardViews.get(i).setFaceValue(cards.get(i).getFaceValue());
				cardViews.get(i).setInUsed(true);
			}else{
				cardViews.get(i).setInUsed(false);
			}
		}
	}
	
	public boolean isInUsed() {
		return inUsed;
	}

	public void setInUsed(boolean inUsed) {
		this.inUsed = inUsed;
		if(inUsed){
			setVisible(true);
		}else{
			setVisible(false);
		}
	}
	
	/**
	 * 初始化UI
	 */
	private void initView(){
		
		for (int i = 0; i < 5; i++) {
			CardView card = new CardView();
			card.setBounds(20+i*Constants.CARD_WIDTH_DISTANCE, 10, Constants.CARD_WIDTH, Constants.CARD_HEIGHT);
			card.setInUsed(false);
			cardViews.add(card);
		}
		
		for (int i = 0; i < 5; i++) {
			CardView card = new CardView();
			card.setBounds(20+i*Constants.CARD_WIDTH_DISTANCE, 10+Constants.CARD_WIDTH_DISTANCE, Constants.CARD_WIDTH, Constants.CARD_HEIGHT);
			card.setInUsed(false);
			cardViews.add(card);
		}	
	}
	
	private void initData(){
		inUsed = false;
		setLayout(null);
		cardData = new ArrayList<>();
		cardViews = new ArrayList<>();
		nickNameLabel = new JLabel();
	}
}
