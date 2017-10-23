package ui;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class CardView extends JLabel {
	
	public CardView(String icon) {
		ImageIcon image = new ImageIcon(CardView.class.getResource("/assets/"+icon+".jpg"));
		image.setImage(image.getImage().getScaledInstance(100, 160,Image.SCALE_DEFAULT ));
		setIcon(image);
		setSize(new Dimension(100, 160));
		setVisible(false);
	}
	
}
