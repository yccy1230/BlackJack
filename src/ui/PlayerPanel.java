package ui;

import javax.swing.*;

import constants.Constants;
import entity.Card;
import entity.Dealer;
import entity.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
* @description Player主界面
* @author Jack Chen
* @date 2017/10/24
*/
public class PlayerPanel extends JPanel {
	protected List<CardView> cardViews;
	protected JPanel cardPanel;
	protected JPanel userInfoPanel;

	public PlayerPanel() {
		initView();
	}

	public PlayerPanel(Player player) {
		initView();

		JLabel label = new JLabel("玩家：");
		userInfoPanel.add(label);
		
		JLabel nickNameLabel = new JLabel(player.getNickname());
		label.setLabelFor(nickNameLabel);
		userInfoPanel.add(nickNameLabel);

		add(cardPanel,BorderLayout.CENTER);
		add(userInfoPanel,BorderLayout.SOUTH);
	}

	/**
	 * 刷新UI
	 * @param cards
	 */
	public void refreshCards(List<Card> cards){
		cardViews.clear();
		int size = cards.size();
		int cardViewSize = cardViews.size();
		int x = 0,y = 30;
		for (int i =0;i<size;i++) {
			Card card  = cards.get(i);
			//若视图中已经卡牌数量足够则更新，否则添加新的卡牌
			if(i<cardViewSize){
				cardViews.get(i).setFaceValue(card.getFaceValue());
			}else{
				CardView c = new CardView(card.getFaceValue());
				c.setBounds(x+30, y, Constants.CARD_WIDTH, Constants.CARD_HEIGHT);
				cardPanel.add(c);
				cardViews.add(c);
			}
		}
	}

	private void initView(){
		setLayout(new BorderLayout());
		cardViews = new ArrayList<>();
		cardPanel = new JPanel();
		cardPanel.setLayout(null);
		userInfoPanel = new JPanel();
		userInfoPanel.setLayout(new FlowLayout());
		cardPanel.setOpaque(false);
		userInfoPanel.setOpaque(false);
	}
}
