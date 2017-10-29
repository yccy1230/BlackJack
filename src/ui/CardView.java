package ui;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import constants.Constants;

public class CardView extends JLabel {
	private boolean inUsed;
	
	
	
	public CardView() {
		setOpaque(false);
		setInUsed(false);
	}

	public CardView(String icon) {
		setFaceValue(icon);
		setInUsed(true);
	}
	
	public void setFaceValue(String icon){
		ImageIcon image = new ImageIcon(CardView.class.getResource("/assets/"+icon+".jpg"));
		image.setImage(image.getImage().getScaledInstance(Constants.CARD_WIDTH, Constants.CARD_HEIGHT,Image.SCALE_DEFAULT ));
		setIcon(image);
	}
	
	public boolean getInUsed() {
		return inUsed;
	}

	/**
	 * 根据使用状态修改显示状态
	 * @param inUsed
	 * 		true   		显示
	 * 		false	 	不显示
	 */
	public void setInUsed(boolean inUsed) {
		this.inUsed = inUsed;
		if(inUsed){
			setVisible(true);
		}else{
			setVisible(false);
		}
	}
	
	
}
