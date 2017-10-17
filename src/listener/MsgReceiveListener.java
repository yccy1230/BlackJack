package listener;

import utils.Resp;

import java.net.DatagramPacket;

/**
* @description 消息接收监听器
* @author Jack Chen
* @date 2017/10/17
*/
public interface MsgReceiveListener {
    /**
     * 接收消息回调
     * @param resp
     */
    void onReceiveMsg(Resp resp, DatagramPacket datagramPacket);
}
