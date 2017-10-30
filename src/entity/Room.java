package entity;

import constants.Constants;
import constants.MsgType;
import service.ServerCommunicateService;
import utils.Resp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Room {
    private int id;
    private List<Player> players;
    private Deck deck;
    private Dealer dealer;
    private boolean isPlaying;
    private ServerCommunicateService serverCommunicateService;

    public Room() {
        init();
    }

    public Room(int roomID,ServerCommunicateService serverCommunicateService) {
        init();
        this.id= roomID;
        this.serverCommunicateService = serverCommunicateService;
    }

    private void init(){
        this.deck=new Deck();
        this.players = new ArrayList<>();
        this.dealer=new Dealer();
        this.isPlaying=false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    //添加用户
    public Player addPlayers(String nickName){
        Player player = new Player();
        player.setId(UUID.randomUUID().toString());
        player.setHand(new Hand());
        player.setNickname(nickName);
        player.setProperty(1000);
        player.setStatus(Constants.USER_IDEL);
        players.add(player);
        return player;
    }

    //检测当前房间人数
    public boolean playerFull(){
        return players.size() >= 5;
    }

    //检查是否所有用户都已准备好
    public void checkAllReady() {
        for(int i=0; i< players.size();i++){
            if(players.get(i).getStatus()!= Constants.USER_READY) {
                return;
            }
        }
        startGame();
    }

    //开始游戏
    public void startGame(){
        //向客户端发送游戏开始的信息
        serverCommunicateService.sendStartGameBroadcast(id);
        isPlaying=true;
        //初始化牌组
        deck.initCards();
        //洗牌
        deck.shuffle();
        //第一次发牌
        for(int i=0;i<players.size();i++){
            deck.deal2Player(players.get(i));
            serverCommunicateService.sendDealCardBroadcast(id,players.get(i));
        }
        deck.deal2Dealer(dealer,true);
        serverCommunicateService.sendDealCard2DealerBroadcast(id,dealer);

        for(int i=0;i<players.size();i++){
            deck.deal2Player(players.get(i));
            serverCommunicateService.sendDealCardBroadcast(id,players.get(i));
        }
        deck.deal2Dealer(dealer,false);
        serverCommunicateService.sendDealCard2DealerBroadcast(id,dealer);

        //轮流请求用户操作
        while(playersOver(players) != players.size()) {
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getStatus() == Constants.USER_READY) {
                    if (players.get(i).getHand().isBlackJack()) {
                        players.get(i).getHand().show();
                        players.get(i).setStatus(Constants.USER_BLACKJACK);
                        serverCommunicateService.sendUserBlackJackBroadcast(id,players.get(i));
                    }else{
                        //获取用户消息,再进行不同操作
                        handlerUser(i);
                    }
                }
            }
        }

        //电脑操作
        while(!dealer.dealerStand()){
            deck.deal2Dealer(dealer,true);
            serverCommunicateService.sendBroadcast();
        }

        //发送游戏结果(用户状态置为结束)
        sendResult();

        isPlaying=false;
    }

    private void handlerUser(int i) {
        Resp resp = null;
        do {
            resp = serverCommunicateService.requireUserOperate();
                switch (resp.getMsgType()) {
                    case MsgType.METHOD_HIT:
                        deck.deal2Player(players.get(i));
                        if(players.get(i).getHand().bust()){
                            players.get(i).setStatus(Constants.USER_OVER);
                        }
                        serverCommunicateService.requireUserOperate();
                        break;
                    case MsgType.METHOD_STAND:
                        players.get(i).setStatus(Constants.USER_STAND);
                        serverCommunicateService.requireUserOperate();
                        break;
                    case MsgType.METHOD_DOUBLE:
                        players.get(i).doubleBet();
                        serverCommunicateService.requireUserOperate();
                        break;
                    case MsgType.METHOD_SURRENDER:
                        players.get(i).setStatus(Constants.USER_SURRENDER);
                        serverCommunicateService.requireUserOperate();
                        break;
                    default:
                        break;
                }
        }while (resp.getMsgType() == MsgType.METHOD_DOUBLE);
    }

    //计算牌局结果
    public void sendResult(){
        for(int i=0;i<players.size();i++){
            if(players.get(i).getStatus()==Constants.USER_BLACKJACK){
                if(dealer.getHand().getCards().size()>= 5) {
                    players.get(i).setBet(0);
                }else{
                    players.get(i).setProperty(players.get(i).getProperty()+players.get(i).getBet()*2);
                    players.get(i).setBet(0);
                }
            }else if(players.get(i).getStatus()==Constants.USER_SURRENDER || players.get(i).getStatus()==Constants.USER_OVER){
                players.get(i).setBet(0);
            }else if(players.get(i).getStatus()==Constants.USER_STAND){
                players.get(i).getHand().computeValue();
            }
        }
        serverCommunicateService.sendBroadcast();
    }

    //判断是否所有玩家均结束
    public int playersOver(List<Player> players){
        int count=0;
        for (int i=0;i<players.size();i++){
            if(players.get(i).getStatus()!=Constants.USER_READY) {
                count++;
            }
        }
        return count;
    }

    public void userReady(String userID,int bet){
        for (int i=0;i<players.size();i++) {
            if(players.get(i).getId().equals(userID)) {
                if(players.get(i).getProperty() >= bet) {
                    players.get(i).setStatus(Constants.USER_READY);
                    players.get(i).setBet(bet);
                    return;
                }else{
                    serverCommunicateService.sendPropertyNotEnoughMsg(id,players.get(i).getId());
                    return;                }
            }
        }
    }

    public void userCancleReady(String userID){
        for(int i=0;i<players.size();i++){
            if(players.get(i).getId().equals(userID)){
                players.get(i).setStatus(Constants.USER_IDEL);
                return;
            }
        }
    }

    public void userExit(String userID){
        for (int i=0;i<players.size();i++) {
            if(players.get(i).getId().equals(userID)) {
                players.remove(i);
                return;
            }
        }
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

}
