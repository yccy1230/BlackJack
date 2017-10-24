package ui;

import javax.swing.JPanel;

import constants.Constants;
import entity.Card;
import entity.Dealer;
import entity.Player;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;

public class PlayerPanel extends JPanel {
	private List<CardView> cardViews;
	
	public PlayerPanel(Player player) {
		setLayout(null);
		
		cardViews = new ArrayList<>();
		JLabel label = new JLabel("玩家：");
		label.setBounds(35, 296, 54, 15);
		add(label);
		
		JLabel nickNameLabel = new JLabel(player.getNickname());
		label.setLabelFor(nickNameLabel);
		nickNameLabel.setBounds(97, 296, 116, 15);
		add(nickNameLabel);
	}
	
	public PlayerPanel(Dealer dealer) {
		setLayout(null);
		
		cardViews = new ArrayList<>();
		
		JLabel nickNameLabel = new JLabel("庄家");
		nickNameLabel.setBounds(97, 296, 116, 15);
		add(nickNameLabel);
	}
	
	/**
	 * 刷新UI
	 * @param player
	 */
	public void refreshUI(Player player){
		cardViews.clear();
		List<Card> cards = player.getHand().getCards();
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
				add(c);
				cardViews.add(c);
			}
		}
	}
}
