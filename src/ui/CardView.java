package ui;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import constants.Constants;

public class CardView extends JLabel {
	private boolean inUsed;
	
	
	
	public CardView() {
		
	}

	public CardView(String icon) {
		setFaceValue(icon);
		setVisible(true);
	}
	
	public void setFaceValue(String icon){
		ImageIcon image = new ImageIcon(CardView.class.getResource("/assets/"+icon+".jpg"));
		image.setImage(image.getImage().getScaledInstance(Constants.CARD_WIDTH, Constants.CARD_HEIGHT,Image.SCALE_DEFAULT ));
		setIcon(image);
//		setSize(new Dimension(100, 160));
	}

	public boolean getInUsed() {
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
	
	
}
