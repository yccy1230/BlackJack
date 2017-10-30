package controller;

import constants.Constants;
import constants.MsgType;
import entity.Player;
import entity.Room;
import listener.MsgReceiveListener;
import service.ServerCommunicateService;
import utils.Resp;

import java.net.DatagramPacket;
import java.util.Map;
/**
* @description 游戏主控制器
* @author Jack Chen
* @date 2017/10/17
*/
public class GameController implements MsgReceiveListener {
    private ServerCommunicateService communicateService;

    private Room room;

    public GameController() {
        this.room =new Room(communicateService);
    }

    @Override
    public void onReceiveMsg(Resp resp, DatagramPacket datagramPacket) {

        Map<String,Object> param = (Map<String, Object>) resp.getData();
        switch(resp.getMsgType()){
            case MsgType.METHOD_LOGIN:
                if(room.playerFull()) {
                    communicateService.sendUDPMsgWithoutResult(datagramPacket);
                    return;
                }
                String nickName = (String) param.get(Constants.PARAM_NICK_NAME);
                Player player = room.addPlayers(nickName);
                communicateService.sendLoginResult(player.getId(),room.getPlayers(),datagramPacket);
                communicateService.userConnectedBroadcast(room,player,datagramPacket);
                break;
            case MsgType.METHOD_READY:
                communicateService.sendReadyMsgWithoutResult(datagramPacket);
                int bet = (int)param.get(Constants.PARAM_BET);
                String userId = (String) param.get(Constants.PARAM_USER_ID);
                room.userReady(userId,bet);
                room.checkAllReady();
                break;
            case MsgType.METHOD_CANCLE_READY:
                userId =(String) param.get(Constants.PARAM_USER_ID);
                room.userCancleReady(userId);
                communicateService.sendCancleReadyMsgWithoutResult(datagramPacket);
                break;
            case MsgType.METHOD_SURRENDER:
                param = (Map<String, Object>) resp.getData();
                String id = (String) param.get(Constants.PARAM_USER_ID);
                for(int i=0; i<room.getPlayers().size();i++){
                    if(room.getPlayers().get(i).getId().equals(id)) {
                        room.getPlayers().get(i).setStatus(Constants.USER_SURRENDER);
                        break;
                    }
                }
                communicateService.sendUDPMsgWithoutResult(datagramPacket);
                break;
            case MsgType.METHOD_USER_EXIT:
                userId = (String) param.get(Constants.PARAM_USER_ID);
                room.userExit(userId);
                communicateService.sendExitMsgWithoutResult(userId);
                break;
            default:
                break;
        }
    }

    public void setCommunicateService(ServerCommunicateService communicateService) {
        this.communicateService = communicateService;
    }
}
