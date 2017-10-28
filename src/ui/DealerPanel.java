package ui;

import entity.Card;
import entity.Dealer;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.SwingConstants;

import constants.Constants;

/**
* @description Player主界面
* @author Jack Chen
* @date 2017/10/24
*/
public class DealerPanel extends PlayerPanel {
	
	public DealerPanel() {
		super();
		initView();
	    nickNameLabel.setText("庄家");
	    nickNameLabel.setForeground(Color.WHITE);
	    nickNameLabel.setFont(new Font("宋体", Font.PLAIN, 16));
	    nickNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    nickNameLabel.setBounds(240, 145, 54, 15);
		add(nickNameLabel);
	}
	
	/**
	 * 刷新UI
	 */
	public void refreashUI(Dealer dealer){
		List<Card> cards = dealer.getHand().getCards();
		for (int i = 0; i <cardViews.size(); i++) {
			if(i<cards.size()){
				cardViews.get(i).setFaceValue(cards.get(i).getFaceValue());
				cardViews.get(i).setInUsed(true);
			}else{
				cardViews.get(i).setInUsed(false);
			}
		}
	}
	
	private void initView(){
		for (int i = 0; i < 10; i++) {
			CardView card = new CardView();
			card.setBounds(70+i*Constants.DEALER_CARD_WIDTH_DISTANCE, 10, Constants.DEALER_CARD_WIDTH, Constants.DEALER_CARD_WIDTH);
			card.setInUsed(false);
			cardViews.add(card);
		}
	}
}
