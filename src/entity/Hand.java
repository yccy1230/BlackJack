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

    public boolean bust(){
        int totalValue=0;
        for(int i=0;i<cards.size();i++){
            totalValue = totalValue + cards.get(i).getValue();
        }
        if(totalValue > 21)
            return true;
        else
            return false;
    }

    public int computeValue(){
        int sum=0;
        int aNum=0;
        for(int i=0;i<cards.size();i++){
            if(cards.get(i).getValue()==1){
                aNum ++;
            }else{
                sum = sum + cards.get(i).getValue();
            }
        }
        for(int i=0;i<aNum;i++){
            if(sum + 11 > 21) {
                sum = sum + 1;
            }else{
                sum = sum + 11;
            }
        }
        return sum;
    }
}
