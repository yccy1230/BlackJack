package ui;

import entity.Card;
import entity.Dealer;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import constants.Constants;

/**
* @description Dealer主界面
* @author Jack Chen
* @date 2017/10/24
*/
public class DealerPanel extends PlayerPanel {
	
	public DealerPanel() {
		super();
		nickNameLabel = new JLabel();
		initCardView();
		initNameLabel();
		setVisible(true);
	}
	
	/**
	 * 刷新Dealer UI
	 */
	public void refreshCardView(Dealer dealer){
		List<Card> cards = new ArrayList<>();
		if(dealer != null&& dealer.getHand()!=null&&dealer.getHand().getCards()!=null){
			cards.addAll(dealer.getHand().getCards());
		}
		super.refreashCardView(cards);
	}


	/**
	 * 初始化玩家界面
	 */
	public void reInitUserPanel(){
		for (int i = 0; i < cardData.size(); i++) {
			cardData.clear();
			super.refreashCardView(cardData);
		}
	}

	private void initNameLabel(){
	    nickNameLabel.setText("庄家");
	    nickNameLabel.setForeground(Color.WHITE);
	    nickNameLabel.setFont(new Font("宋体", Font.PLAIN, 16));
	    nickNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    nickNameLabel.setBounds(240, 160, 54, 15);
	    add(nickNameLabel);
	}
	
	private void initCardView(){
		//	清除原卡片
		for (CardView card : cardViews) {
			remove(card);
		}
		cardViews.clear();
		//创建新卡片
		for (int i = 9; i >= 0 ; i--) {
			CardView card = new CardView();
			card.setBounds(70+i*Constants.DEALER_CARD_WIDTH_DISTANCE, 10, Constants.CARD_WIDTH, Constants.CARD_HEIGHT);
			card.setInUsed(false);
			cardViews.add(card);
			add(card);
		}
		Collections.reverse(cardViews);
	}
}
