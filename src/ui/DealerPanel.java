package ui;

import constants.Constants;
import entity.Dealer;

import javax.swing.*;
import java.awt.*;

/**
* @description Player主界面
* @author Jack Chen
* @date 2017/10/24
*/
public class DealerPanel extends PlayerPanel {

	public DealerPanel() {
	    super();
		JLabel nickNameLabel = new JLabel("庄家");
        userInfoPanel.add(nickNameLabel);
        cardPanel.setPreferredSize(new Dimension(600, Constants.CARD_HEIGHT));
        add(cardPanel,BorderLayout.CENTER);
        add(userInfoPanel,BorderLayout.SOUTH);
	}
	/**
	 * 刷新UI
	 * @param dealer
	 */
	public void refreshUI(Dealer dealer){
		super.refreshCards(dealer.getHand().getCards());
	}
}
