package ui;

import entity.Card;
import java.util.List;

/**
* @description Player主界面
* @author Jack Chen
* @date 2017/10/24
*/
public class DealerPanel extends PlayerPanel {

	protected List<CardView> cardViews;
	protected List<Card> cardData;
	
	public DealerPanel() {
	    nickNameLabel.setText("庄家");
	}
	
}
