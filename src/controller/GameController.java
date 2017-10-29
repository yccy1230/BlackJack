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
        switch(resp.getMsgType()){
            case MsgType.METHOD_LOGIN:
                if(room.playerFull()) {
                    communicateService.sendUDPMsgWithoutResult(datagramPacket);
                    return;
                }
                Map<String,Object> param = (Map<String, Object>) resp.getData();
                String nickName = (String) param.get(Constants.PARAM_NICK_NAME);
                Player player = room.addPlayers(nickName);
                communicateService.sendLoginResult(room.getPlayers(),datagramPacket);
                communicateService.userConnectedBroadcast(room,player,datagramPacket);
                break;
            case MsgType.METHOD_READY:
                room.checkAllReady();
                communicateService.sendUDPMsgWithoutResult(datagramPacket);
                break;
            case MsgType.METHOD_SURRENDER:
                if(resp.getResCode()==Constants.SUCCESS_CODE){
                    param = (Map<String, Object>) resp.getData();
                    String id = (String) param.get(Constants.PARAM_USER_ID);
                    for(int i=0; i<room.getPlayers().size();i++){
                        if(room.getPlayers().get(i).getId().equals(id)) {
                            room.getPlayers().get(i).setStatus(Constants.USER_SURRENDER);
                            break;
                        }
                    }
                    communicateService.sendUDPMsgWithoutResult(datagramPacket);
                }
                break;
            default:
                break;
        }
    }

    public void setCommunicateService(ServerCommunicateService communicateService) {
        this.communicateService = communicateService;
    }
}
