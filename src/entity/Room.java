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
    private List<Player> players = new ArrayList<>();
    private Deck deck;
    private Dealer dealer;
    private boolean isPlaying;
    private ServerCommunicateService serverCommunicateService;

    public Room(ServerCommunicateService serverCommunicateService) {
        this.serverCommunicateService = serverCommunicateService;
    }

    //添加用户
    public void initPlayers(Resp resp){
        Player player = new Player();
        player.setId(UUID.randomUUID().toString());
        player.setHand(new Hand());
        Map<String,Object> param = (Map<String, Object>) resp.getData();
        player.setNickname((String) param.get(Constants.PARAM_NICK_NAME));
        player.setStatus(Constants.USER_IDEL);
        players.add(player);

        serverCommunicateService.userConnectedBroadcast();
    }

    //检查是否所有用户都已准备好
    public void checkAllReady() {
        int count=0;
        for(int i=0; i< players.size();i++){
            if(players.get(i).getStatus()== Constants.USER_READY)
                count++;
        }
        if(count==players.size())
            startGame();
    }

    //开始游戏
    public void startGame(){
        serverCommunicateService.userConnectedBroadcast();

        isPlaying=true;
        //初始化牌组
        deck.initCards();
        //洗牌
        deck.shuffle();
        //第一次发牌
        for(int i=0;i<players.size();i++){
            deck.deal2Player(players.get(i));
            serverCommunicateService.requireUserOperate();
        }
        deck.deal2Dealer(dealer,true);
        serverCommunicateService.requireUserOperate();
        for(int i=0;i<players.size();i++){
            deck.deal2Player(players.get(i));
            serverCommunicateService.requireUserOperate();
        }
        deck.deal2Dealer(dealer,false);
        serverCommunicateService.requireUserOperate();

        //轮流请求用户操作
        while(playersOver(players) != players.size()) {
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getStatus() == Constants.USER_READY) {
                    if (players.get(i).getHand().isBlackJack()) {
                        players.get(i).getHand().show();
                        players.get(i).setStatus(Constants.USER_BLACKJACK);
                        serverCommunicateService.requireUserOperate();
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
            serverCommunicateService.requireUserOperate();
        }

        //发送游戏结果(用户状态置为结束)
        sendResult();

    }

    private void handlerUser(int i) {
        Resp resp = null;
        do {
            resp = serverCommunicateService.requireUserOperate();
                switch (resp.getMsgType()) {
                    case METHOD_HIT:
                        deck.deal2Player(players.get(i));
                        if(players.get(i).getHand().bust()){
                            players.get(i).setStatus(Constants.USER_OVER);
                        }
                        serverCommunicateService.requireUserOperate();
                        break;
                    case METHOD_STAND:
                        players.get(i).setStatus(Constants.USER_STAND);
                        serverCommunicateService.requireUserOperate();
                        break;
                    case METHOD_DOUBLE:
                        players.get(i).doubleBet();
                        serverCommunicateService.requireUserOperate();
                        break;
                    case METHOD_SURRENDER:
                        players.get(i).setStatus(Constants.USER_SURRENDER);
                        serverCommunicateService.requireUserOperate();
                        break;
                    default:
                        break;
                }
        }while (resp.getMsgType() == MsgType.METHOD_DOUBLE);
    }

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
    }

    public int playersOver(List<Player> players){
        int count=0;
        for (int i=0;i<players.size();i++){
            if(players.get(i).getStatus()!=Constants.USER_READY)
                count++;
        }
        return count;
    }

}
