package entity;

import java.util.List;

/**
 * 庄家
 * @author Jack Chen
 * @create 2017-10-17 13:14
 */
public class Dealer {
    private int cardsNum;
    private int cardsSum;
    private List<Card> cards;

    public boolean dealerStand(){
        if(cardsSum <17)
            return false;
        else
            return true;
    }

    public void dealerHit(){
        //要牌
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
