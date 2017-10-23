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
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
