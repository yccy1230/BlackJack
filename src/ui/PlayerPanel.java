package ui;

import javax.swing.*;

import constants.Constants;
import entity.Card;
import entity.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
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
	protected JLabel nameLabel;
	
	private boolean inUsed;

	/**
	 * 创建单个玩家界面，默认不显示
	 */
	public PlayerPanel() {
		initData();
		initCardView();
		setInUsed(false);
	}
	
	/**
	 * 添加Player
	 * @param player
	 */
	public void addPlayer(Player player){
		if(player.getNickname()!=null){
			nickNameLabel.setText(player.getNickname());
		}else{
			nickNameLabel.setText("");
		}

		nameLabel.setText("玩家：");
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setFont(new Font("宋体", Font.PLAIN, 14));
		nameLabel.setBounds(60, 188, 54, 15);
		
		nickNameLabel.setForeground(Color.WHITE);
	    nickNameLabel.setFont(new Font("宋体", Font.PLAIN, 14));
		nickNameLabel.setBounds(110, 188, 54, 15);

		add(nameLabel);
		add(nickNameLabel);
		
		refreashPlayerView(player);
		
		setInUsed(true);//启用Panel
	}
	
	/**
	 * 删除Player
	 */
	public void remove(){
		cardData.clear();
		for (CardView cardView : cardViews) {//关闭卡牌视图
			cardView.setInUsed(false);
		}
		setInUsed(false);//关闭界面
	}

	/**
	 * 刷新Player界面
	 */
	public void refreashPlayerView(Player player){
		List<Card> cards = new ArrayList<>();
		if(player != null&& player.getHand()!=null&&player.getHand().getCards()!=null){
			cards.addAll(player.getHand().getCards());
		}
		refreashCardView(cards);
	}

	/**
	 * 刷新Player手牌
	 */
	protected void refreashCardView(List<Card> cards){
		//刷新卡牌面值
		for (int i = 0; i <cardViews.size(); i++) {
			if(i<cards.size()){
				cardViews.get(i).setFaceValue(cards.get(i).getFaceValue());
				cardViews.get(i).setInUsed(true);
			}else{
				cardViews.get(i).setInUsed(false);
			}
		}
		
		//更新缓存数据
		cardData.clear();
		cardData.addAll(cards);
	}
	
	/**
	 * 初始化卡牌
	 */
	private void initCardView(){

		List<CardView> tmpCardViews1 = new ArrayList<>();
		for (int i = 4; i >= 0; i--) {
			CardView card = new CardView();
			card.setBounds(20+i*Constants.CARD_WIDTH_DISTANCE, 20+Constants.CARD_WIDTH_DISTANCE, Constants.CARD_WIDTH, Constants.CARD_HEIGHT);
			card.setInUsed(false);
			tmpCardViews1.add(card);
			add(card);
		}	
		Collections.reverse(tmpCardViews1);
		List<CardView> tmpCardViews2 = new ArrayList<>();
		for (int i = 4; i >= 0; i--) {
			CardView card = new CardView();
			card.setBounds(20+i*Constants.CARD_WIDTH_DISTANCE, 10, Constants.CARD_WIDTH, Constants.CARD_HEIGHT);
			card.setInUsed(false);
			tmpCardViews2.add(card);
			add(card);
		}
		Collections.reverse(tmpCardViews2);  
		cardViews.addAll(tmpCardViews2);
		cardViews.addAll(tmpCardViews1);
	}
	
	public boolean isInUsed() {
		return inUsed;
	}

	private void setInUsed(boolean inUsed) {
		this.inUsed = inUsed;
		if(inUsed){
			setVisible(true);
			nameLabel.setVisible(true);
			nickNameLabel.setVisible(true);
		}else{
			setVisible(false);
			nameLabel.setVisible(false);
			nickNameLabel.setVisible(false);
		}
	}
	
	private void initData(){
		inUsed = false;
		setLayout(null);
		setOpaque(false);
		cardData = new ArrayList<>();
		cardViews = new ArrayList<>();
		nickNameLabel = new JLabel();
		nameLabel = new JLabel();
	}
}
