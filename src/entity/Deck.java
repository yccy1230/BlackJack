package entity;

import java.util.List;

/**
 * 牌桌
 *
 * @author Jack Chen
 * @create 2017-10-17 13:18
 */
public class Deck {
    List<Card> cards;

    //剩下牌数量
    private int leftCardsNum;

    //是否完整
    private boolean isCompleted;

    //洗牌
    public void shuffle(){

    }

    //发牌给玩家
    public void  deal2Player(Player player){
        int random = (int) (Math.random() * cards.size());
        Card newCard = cards.remove(random);
        player.getHand().getCards().add(newCard);
        leftCardsNum --;

    }

    public void deal2Dealer(Dealer dealer,int visible){
        int random = (int) (Math.random() * cards.size());
        Card newCard = cards.remove(random);
        dealer.getCards().add(newCard);
        leftCardsNum --;
    }

}
