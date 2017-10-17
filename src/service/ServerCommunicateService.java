package service;
import constants.Constants;
import constants.MsgType;
import listener.MsgReceiveListener;
import listener.NetworkListener;
import thread.ServerMsgReceiveThread;
import utils.CommunicateUtil;
import utils.Resp;

import java.net.*;
import java.util.HashMap;
import java.util.Map;

/**
* @description Server通讯服务,维护通讯清单
 *             由于服务器需要能够被客户端请求，所以端口必须固定
* @author Jack Chen
* @date 2017/10/17
*/
public class ServerCommunicateService {
    /**Port*/
    private final int COMMUNICATE_PORT = 6666;
    /**通讯超时时间*/
    private final int TIME_OUT = 30000;

    /**套接字*/
    private DatagramSocket socket;
    /**通讯结果回调*/
    private NetworkListener networkListener;
    /**消息接收线程*/
    private ServerMsgReceiveThread serverMsgReceiveThread;

    /**
     * 构造通讯器，并绑定端口
     * @param networkListener
     */
    public ServerCommunicateService(NetworkListener networkListener, MsgReceiveListener msgReceiveListener) {
        this.networkListener = networkListener;
        try {
            socket = new DatagramSocket(COMMUNICATE_PORT);
        } catch (SocketException e) {
            if(networkListener!=null){
                networkListener.bindPortError();
            }
            System.out.println("绑定端口出错，详细堆栈信息如下：");
            e.printStackTrace();
            return;
        }
        //创建并启动信息接受线程
        serverMsgReceiveThread = new ServerMsgReceiveThread(socket,msgReceiveListener);
        serverMsgReceiveThread.start();
    }

    /**
     * 用户上线广播消息
     */
    public void userConnectedBroadcast(DatagramPacket datagramPacket){

    }


    public void userLogin(Resp resp){
//        if(networkListener!=null){
//            //失败，服务器不理人
//            if(resp==null){
//                networkListener.sendUDPMsgError(MsgType.METHOD_LOGIN,"连接服务器失败！");
//                return;
//            }
//            //成功
//            if(resp.getResCode()==Constants.SUCCESS_CODE){
//                networkListener.connectServerSuccess();
//            }else{//失败，有原因的那种
//                networkListener.connectServerFailure(resp.getResMsg());
//            }
//        }
    }
}
