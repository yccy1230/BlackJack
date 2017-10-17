package controller;

import listener.MsgReceiveListener;
import service.ServerCommunicateService;
import utils.Resp;

import java.net.DatagramPacket;

/**
* @description 游戏主控制器
* @author Jack Chen
* @date 2017/10/17
*/
public class GameController implements MsgReceiveListener {
    private ServerCommunicateService communicateService;

    @Override
    public void onReceiveMsg(Resp resp, DatagramPacket datagramPacket) {
        switch(resp.getMsgType()){
            case METHOD_LOGIN:
                break;
            case METHOD_HIT:
                break;
            case METHOD_READY:
                break;
            case METHOD_STAND:
                break;
            case METHOD_DOUBLE:
                break;
            case METHOD_RESULT:
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
