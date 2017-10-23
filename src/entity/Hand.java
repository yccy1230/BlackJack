package entity;

import java.util.List;

/**
 * 手牌
 *
 * @author Jack Chen
 * @create 2017-10-17 13:19
 */
public class Hand {

    //手上牌数量值
    private int cardsSum;
    private List<Card> cards;

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    //亮牌
    public void show(){
        for(int i=0;i<cards.size();i++){
            cards.get(i).setVisible(true);
        }
    }

    //是否BlackJack
    public boolean isBlackJack(){
        if(cards.get(0).getValue()+cards.get(1).getValue()==21){
            return true;
        }else{
            return false;
        }
    }

    public int getCardsSum() {
        return cardsSum;
    }

    public void setCardsSum(int cardsSum) {
        this.cardsSum = cardsSum;
    }



}
