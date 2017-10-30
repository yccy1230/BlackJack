package controller;

import constants.Constants;
import constants.MsgType;
import entity.Player;
import entity.Room;
import listener.MsgReceiveListener;
import service.ServerCommunicateService;
import utils.Resp;

import java.net.DatagramPacket;
import java.util.HashMap;
import java.util.Map;
/**
* @description 游戏主控制器
* @author Jack Chen
* @date 2017/10/17
*/
public class GameController implements MsgReceiveListener {
    private ServerCommunicateService communicateService;

    private Map<Integer,Room> rooms;

    public GameController() {
        this.rooms =new HashMap<>();
    }

    @Override
    public void onReceiveMsg(Resp resp, DatagramPacket datagramPacket) {

        Map<String,Object> param = (Map<String, Object>) resp.getData();
        switch(resp.getMsgType()){
            case MsgType.METHOD_LOGIN:
                int roomId =  resp.getRoomId();
                if(rooms.get(roomId)==null){
                    Room room =new Room(communicateService);
                    rooms.put(roomId,room);
                }
                if(rooms.get(roomId).playerFull()) {
                    communicateService.sendUDPMsgWithoutResult(datagramPacket);
                    return;
                }
                String nickName = (String) param.get(Constants.PARAM_NICK_NAME);
                Player player = rooms.get(roomId).addPlayers(nickName);
                communicateService.sendLoginResult(player.getId(),rooms.get(roomId).getPlayers(),datagramPacket);
                communicateService.userConnectedBroadcast(rooms.get(roomId),player,datagramPacket);
                break;
            case MsgType.METHOD_READY:
                roomId =  resp.getRoomId();
                if(rooms.get(roomId)==null){
                    return;
                }
                communicateService.sendReadyMsgWithoutResult(datagramPacket);
                int bet = (int)param.get(Constants.PARAM_BET);
                String userId = (String) param.get(Constants.PARAM_USER_ID);
                rooms.get(roomId).userReady(userId,bet);
                rooms.get(roomId).checkAllReady();
                break;
            case MsgType.METHOD_CANCLE_READY:
                roomId =  resp.getRoomId();
                if(rooms.get(roomId)==null){
                    return;
                }
                userId =(String) param.get(Constants.PARAM_USER_ID);
                rooms.get(roomId).userCancleReady(userId);
                communicateService.sendCancleReadyMsgWithoutResult(datagramPacket);
                break;
            case MsgType.METHOD_SURRENDER:
                String id = (String) param.get(Constants.PARAM_USER_ID);
                roomId =  resp.getRoomId();
                if(rooms.get(roomId)==null){
                    return;
                }
                for(int i=0; i<rooms.get(roomId).getPlayers().size();i++){
                    if(rooms.get(roomId).getPlayers().get(i).getId().equals(id)) {
                        rooms.get(roomId).getPlayers().get(i).setStatus(Constants.USER_SURRENDER);
                        break;
                    }
                }
                communicateService.sendUDPMsgWithoutResult(datagramPacket);
                break;
            case MsgType.METHOD_USER_EXIT:
                roomId =  resp.getRoomId();
                if(rooms.get(roomId)==null){
                    return;
                }
                userId = (String) param.get(Constants.PARAM_USER_ID);
                rooms.get(roomId).userExit(userId);
                communicateService.sendExitMsgWithoutResult(roomId,userId);
                break;
            default:
                break;
        }
    }

    public void setCommunicateService(ServerCommunicateService communicateService) {
        this.communicateService = communicateService;
    }
}
