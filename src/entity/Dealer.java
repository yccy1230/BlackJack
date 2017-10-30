package entity;

/**
 * 庄家
 * @author Jack Chen
 * @create 2017-10-17 13:14
 */
public class Dealer {
    private Hand hand;

    public Dealer(){
        this.hand = new Hand();
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public boolean dealerStand(){
        return hand.computeValue() >= 17;
    }

}
