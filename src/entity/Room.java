package entity;

import constants.Constants;
import constants.ConstantsMsg;
import constants.MsgType;
import service.ServerCommunicateService;
import utils.Resp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Room {
    private int id;
    private int currentId ;
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
        this.currentId=-1;
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

    public Player getPlayer(String playerId){
        for(int i=0;i<players.size();i++){
            if(players.get(i).getId().equals(playerId)){
                return players.get(i);
            }
        }
        return null;
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

        //判断是否有BlackJack
        for(int i = 0; i<players.size(); i++){
            if (players.get(i).getHand().isBlackJack()) {
                players.get(i).getHand().show();
                players.get(i).setStatus(Constants.USER_BLACKJACK);
                serverCommunicateService.sendUserBlackJackBroadcast(id,players.get(i));
            }
        }
        //启动游戏
        nextUser();
    }

    public void handlerUser(int opreationType) {
        switch (opreationType) {
            case MsgType.METHOD_HIT:
                deck.deal2Player(players.get(currentId));
                if(players.get(currentId).getHand().bust()){
                    players.get(currentId).setStatus(Constants.USER_OVER);
                    serverCommunicateService.sendNewUI(players.get(currentId),id);
                    serverCommunicateService.sendOperationResult(MsgType.METHOD_BUST,players.get(currentId),id);
                    break;
                }
                serverCommunicateService.sendNewUI(players.get(currentId),id);
                serverCommunicateService.sendOperationResult(MsgType.METHOD_HIT_RESULT,players.get(currentId),id);
                break;
            case MsgType.METHOD_STAND:
                players.get(currentId).setStatus(Constants.USER_STAND);
                serverCommunicateService.sendOperationResult(MsgType.METHOD_STAND_RESULT,players.get(currentId),id);
                break;
            case MsgType.METHOD_DOUBLE:
                players.get(currentId).doubleBet();
                serverCommunicateService.sendOperationResult(MsgType.METHOD_DOUBLE_RESULT,players.get(currentId),id);
                break;
            case MsgType.METHOD_SURRENDER:
                players.get(currentId).setStatus(Constants.USER_SURRENDER);
                serverCommunicateService.sendOperationResult(MsgType.METHOD_SURRENDER_RESULT,players.get(currentId),id);
                break;
            default:
                break;
        }
        if(isPlaying){
            nextUser();
        }
    }

    private void nextUser(){
        currentId = (++currentId) % players.size();
        if(!playersOver()) {
            while(players.get(currentId).getStatus() != Constants.USER_READY){
                currentId = (++currentId) % players.size();
            }
            serverCommunicateService.sendUserTurnMsg(id,players.get(currentId).getId());
            isPlaying =true;
        }else{
            //电脑操作
            while(!dealer.dealerStand()){
                deck.deal2Dealer(dealer,true);
                serverCommunicateService.sendDealCard2DealerBroadcast(id,dealer);
            }
            //发送游戏结果(用户状态置为结束)
            sendResult();
            isPlaying =false;
            reInitRoom();
        }
    }

    public void reInitRoom(){
        for(int i=0;i<players.size();i++){
            players.get(i).getHand().getCards().clear();
            players.get(i).setStatus(Constants.USER_IDEL);
        }
        dealer.getHand().getCards().clear();
    }

    //计算牌局结果
    public void sendResult(){
        List<ResultDetail> resultDetails = new ArrayList<>();
        for(int i=0;i<players.size();i++){
            ResultDetail rd = new ResultDetail();
            Player player = players.get(i);
            if(dealer.getHand().computeValue()>21){
                rd = new ResultDetail(player.getId(), player.getNickname(), player.getHand().computeValue(), player.getBet());
                if(players.get(i).getHand().computeValue()>21){
                    players.get(i).setProperty(players.get(i).getProperty()+players.get(i).getBet());
                    players.get(i).setBet(0);
                    rd.setStatus(ConstantsMsg.RESULT_TIE);
                }else{
                    if(players.get(i).getStatus()==Constants.USER_BLACKJACK){
                        rd.setStatus(ConstantsMsg.RESULT_SUCCESS);
                        players.get(i).setProperty(players.get(i).getProperty()+players.get(i).getBet()*3);
                        players.get(i).setBet(0);
                    }else {
                        rd.setStatus(ConstantsMsg.RESULT_SUCCESS);
                        players.get(i).setProperty(players.get(i).getProperty()+players.get(i).getBet()*2);
                        players.get(i).setBet(0);
                    }
                }
            }else{
                if(players.get(i).getStatus()==Constants.USER_BLACKJACK){
                    rd = new ResultDetail(player.getId(), player.getNickname(), 21, player.getBet());
                    if(dealer.getHand().getCards().size()>= 5) {
                        players.get(i).setBet(0);
                    }else{
                        rd.setStatus(ConstantsMsg.RESULT_SUCCESS);
                        players.get(i).setProperty(players.get(i).getProperty()+players.get(i).getBet()*3);
                        players.get(i).setBet(0);
                    }
                }else if(players.get(i).getStatus()==Constants.USER_SURRENDER || players.get(i).getStatus()==Constants.USER_OVER){
                    rd = new ResultDetail(player.getId(), player.getNickname(), player.getHand().computeValue(), player.getBet());
                    players.get(i).setBet(0);
                    rd.setStatus(ConstantsMsg.RESULT_FAILURE);
                }else if(players.get(i).getStatus()==Constants.USER_STAND){
                    int playerFaceValue =player.getHand().computeValue();
                    int dealerFaceValue = dealer.getHand().computeValue();
                    rd = new ResultDetail(player.getId(), player.getNickname(), playerFaceValue, player.getBet());
                    if(playerFaceValue>dealerFaceValue){
                        players.get(i).setProperty(players.get(i).getProperty()+players.get(i).getBet()*2);
                        players.get(i).setBet(0);
                        rd.setStatus(ConstantsMsg.RESULT_SUCCESS);
                    }else if(playerFaceValue == dealerFaceValue){
                        players.get(i).setProperty(players.get(i).getProperty()+players.get(i).getBet());
                        players.get(i).setBet(0);
                        rd.setStatus(ConstantsMsg.RESULT_TIE);
                    }else{
                        players.get(i).setBet(0);
                        rd.setStatus(ConstantsMsg.RESULT_FAILURE);
                    }
                }
            }
            rd.setProperty(players.get(i).getProperty());
            resultDetails.add(rd);
        }

        serverCommunicateService. sendGameResultBroadcast(id,resultDetails);
    }

    //判断是否所有玩家均结束
    public boolean playersOver(){
        for (int i=0;i<players.size();i++){
            if(players.get(i).getStatus()==Constants.USER_READY) {
                return false;
            }
        }
        return true;
    }

    public void userReady(String userID,int bet){
        for (int i=0;i<players.size();i++) {
            if(players.get(i).getId().equals(userID)) {
                if(players.get(i).getProperty() >= bet) {
                    players.get(i).setStatus(Constants.USER_READY);
                    players.get(i).setProperty(players.get(i).getProperty()-bet);
                    players.get(i).setBet(bet);
                    return;
                }else{
                    serverCommunicateService.sendPropertyNotEnoughMsg(id,players.get(i).getId());
                    return;
                }
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
