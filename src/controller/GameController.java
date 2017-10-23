package controller;

import com.alibaba.fastjson.JSON;
import constants.Constants;
import entity.Hand;
import entity.Player;
import entity.Room;
import listener.MsgReceiveListener;
import service.ServerCommunicateService;
import utils.Resp;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
* @description 游戏主控制器
* @author Jack Chen
* @date 2017/10/17
*/
public class GameController implements MsgReceiveListener {
    private ServerCommunicateService communicateService;

    private Room room;

    public GameController() {
        Room room =new Room(communicateService);
    }

    @Override
    public void onReceiveMsg(Resp resp, DatagramPacket datagramPacket) {
        switch(resp.getMsgType()){
            case METHOD_LOGIN:
                if(resp.getResCode()== Constants.SUCCESS_CODE){
                    room.initPlayers(resp);
                }
                break;
            case METHOD_READY:
                room.checkAllReady();
                break;
            case METHOD_SURRENDER:
                break;
            default:
                break;
        }
    }

    public void setCommunicateService(ServerCommunicateService communicateService) {
        this.communicateService = communicateService;
    }
}
