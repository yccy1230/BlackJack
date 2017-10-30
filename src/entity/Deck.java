package entity;

import java.util.ArrayList;
import java.util.Collections;
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

    public Deck(){
        this.cards=new ArrayList<>();
    }

    //洗牌
    public void shuffle(){
        Collections.shuffle(cards);
    }

    public void initCards(){
        Card newCard;
        //Clubs
        for(int i=1;i<=10;i++){
            newCard =new Card("Clubs_"+i);
            cards.add(newCard);
        }
        newCard =new Card("Clubs_J");
        cards.add(newCard);
        newCard =new Card("Clubs_Q");
        cards.add(newCard);
        newCard =new Card("Clubs_K");
        cards.add(newCard);

        //Diamonds
        for(int i=1;i<=10;i++){
            newCard =new Card("Diamonds_"+i);
            cards.add(newCard);
        }
        newCard =new Card("Diamonds_J");
        cards.add(newCard);
        newCard =new Card("Diamonds_Q");
        cards.add(newCard);
        newCard =new Card("Diamonds_K");
        cards.add(newCard);

        //Hearts
        for(int i=1;i<=10;i++){
            newCard =new Card("Hearts_"+i);
            cards.add(newCard);
        }
        newCard =new Card("Hearts_J");
        cards.add(newCard);
        newCard =new Card("Hearts_Q");
        cards.add(newCard);
        newCard =new Card("Hearts_K");
        cards.add(newCard);

        //Spades
        for(int i=1;i<=10;i++){
            newCard =new Card("Spades_"+i);
            cards.add(newCard);
        }
        newCard =new Card("Spades_J");
        cards.add(newCard);
        newCard =new Card("Spades_Q");
        cards.add(newCard);
        newCard =new Card("Spades_K");
        cards.add(newCard);
    }

    //发牌给玩家
    public void  deal2Player(Player player){
        int random = (int) (Math.random() * cards.size());
        Card newCard = cards.remove(random);
        player.getHand().getCards().add(newCard);
        leftCardsNum --;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deal2Dealer(Dealer dealer,boolean visible){
        int random = (int) (Math.random() * cards.size());
        Card newCard = cards.remove(random);
        dealer.getHand().getCards().add(newCard);
        leftCardsNum --;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
