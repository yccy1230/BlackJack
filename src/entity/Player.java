package entity;

import java.net.SocketAddress;

/**
 * 游戏者
 * @author Jack Chen
 * @create 2017-10-17 13:13
 */
public class Player {
    private Hand hand;
    private String id;
    private String nickname;
    private int status;
    private int bet;
    private int property;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public void doubleBet(){
        if(property-bet>=0) {
            bet = bet * 2;
            property =property -bet;
        }else{
            //弹出提醒，余额不足
        }
    }

}
