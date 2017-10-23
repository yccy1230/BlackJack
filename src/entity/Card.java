package entity;

/**
 * 卡牌
 *
 * @author Jack Chen
 * @create 2017-10-17 13:16
 */
public class Card {
    String faceValue;
    int value;
    boolean visible;

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(String faceValue) {
        this.faceValue = faceValue;
    }

    public int getValue() {
       String[] values=faceValue.split("_");
       String s =values[1];
       if(s.equals("J") || s.equals("Q") || s.equals("K"))
           return 10;
       else
           return Integer.parseInt(s);
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Card(String faceValue) {
        this.faceValue = faceValue;
    }

}
