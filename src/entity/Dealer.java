package entity;

import java.util.List;

/**
 * 庄家
 * @author Jack Chen
 * @create 2017-10-17 13:14
 */
public class Dealer {
    private Hand hand;

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public boolean dealerStand(){
        if(hand.computeValue()<17)
            return false;
        else
            return true;
    }

}
