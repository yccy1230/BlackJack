package listener;

import constants.MsgType;
import utils.Resp;

/**
* @description 网络监听接口，负责回调网络结果
* @author Jack Chen
* @date 2017/10/17
*/
public interface NetworkListener {
    /**初始化端口失败*/
    void bindPortError();

    /**
     * 发送UDP消息失败
     * @param msgType 失败消息类型
     * @param msg 返回消息
     */
    void sendUDPMsgError(MsgType msgType, String msg);

    /**连接服务器成功*/
    void connectServerSuccess();

    /**
     * 连接服务器失败
     * @param msg 失败原因
     */
    void connectServerFailure(String msg);



}
