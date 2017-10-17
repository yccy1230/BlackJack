package controller;

import listener.MsgReceiveListener;
import service.ClientCommunicateService;
import utils.Resp;

/**
* @description UI总控制器，负责客户端交互
* @author Jack Chen
* @date 2017/10/17
*/
public class UIController implements MsgReceiveListener{
    private ClientCommunicateService communicateService;

    @Override
    public void onReceiveMsg(Resp resp) {
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

    public void setCommunicateService(ClientCommunicateService communicateService) {
        this.communicateService = communicateService;
    }
}
