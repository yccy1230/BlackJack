package service;
import constants.Constants;
import constants.MsgType;
import listener.NetworkListener;
import thread.ClientMsgReceiveThread;
import thread.ServerMsgReceiveThread;
import utils.CommunicateUtil;
import utils.Resp;

import java.net.*;
import java.util.HashMap;
import java.util.Map;

/**
* @description Server信息发送器
* @author Jack Chen
* @date 2017/10/17
*/
public class ServerSenderService {
    /**服务器IP*/
    private final String SERVER_IP = "115.159.35.11";
    /**服务器Port*/
    private final int SERVER_PORT = 6666;
    /**通讯超时时间*/
    private final int TIME_OUT = 30000;

    /**本地Port*/
    private int localPort;
    /**标识ID*/
    private int deviceID;
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
    public ServerSenderService(NetworkListener networkListener) {
        this.networkListener = networkListener;
        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(TIME_OUT);
            localPort = socket.getPort();
        } catch (SocketException e) {
            if(networkListener!=null){
                networkListener.bindPortError();
            }
            System.out.println("绑定端口出错，详细堆栈信息如下：");
            e.printStackTrace();
            return;
        }
        //创建并启动信息接受线程
        serverMsgReceiveThread = new ServerMsgReceiveThread(socket);
        serverMsgReceiveThread.start();
    }


}
