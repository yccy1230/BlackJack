package ui;

import entity.Card;
import entity.Dealer;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
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
	private JLabel nickNameLabel;
	
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
	public void refreashCardView(Dealer dealer){
		List<Card> cards = new ArrayList<>();
		if(dealer != null&& dealer.getHand()!=null&&dealer.getHand().getCards()!=null){
			cards.addAll(dealer.getHand().getCards());
		}
		super.refreashCardView(cards);
	}
	
	private void initNameLabel(){
	    nickNameLabel.setText("庄家");
	    nickNameLabel.setForeground(Color.WHITE);
	    nickNameLabel.setFont(new Font("宋体", Font.PLAIN, 16));
	    nickNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    nickNameLabel.setBounds(240, 145, 54, 15);
	    add(nickNameLabel);
	}
	
	private void initCardView(){
		for (int i = 0; i < 10; i++) {
			CardView card = new CardView();
			card.setBounds(70+i*Constants.DEALER_CARD_WIDTH_DISTANCE, 10, Constants.DEALER_CARD_WIDTH, Constants.DEALER_CARD_WIDTH);
			card.setInUsed(false);
			cardViews.add(card);
		}
	}
}
