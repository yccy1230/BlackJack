package service;

import constants.Constants;
import constants.MsgType;
import listener.MsgReceiveListener;
import listener.NetworkListener;
import thread.MsgReceiveThread;
import utils.CommunicateUtil;
import utils.Resp;

import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

/**
* @description Client通讯服务
* @author Jack Chen
* @date 2017/10/17
*/
public class ClientCommunicateService {
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
    private MsgReceiveThread clientMsgReceiveThread;

    /**
     * 构造通讯器，并绑定端口
     * @param networkListener
     */
    public ClientCommunicateService(NetworkListener networkListener, MsgReceiveListener msgReceiveListener) {
        this.networkListener = networkListener;
        try {
            socket = new DatagramSocket();
            localPort = socket.getLocalPort();
        } catch (SocketException e) {
            if(networkListener!=null){
                networkListener.bindPortError();
            }
            System.out.println("绑定端口出错，详细堆栈信息如下：");
            e.printStackTrace();
            return;
        }
        //创建并启动信息接受线程
        clientMsgReceiveThread = new MsgReceiveThread(socket,msgReceiveListener);
        clientMsgReceiveThread.start();
    }

    /**
     * 连接服务器（加入房间）
     * @param nickName 用户登陆昵称
     * @return 返回结果
     */
    public Resp connectServer(String nickName){
        Map<String,Object> param = new HashMap<>(16);
        param.put(Constants.PARAM_NICK_NAME,nickName);
        Resp resp = CommunicateUtil.sendUDPMsgByIP(MsgType.METHOD_LOGIN,param,SERVER_IP,SERVER_PORT,socket);
        return resp;
    }

    /**
     * 断开服务器（退出房间）
     * @return 返回结果
     */
    public Resp disconnectServer(){
        Map<String,Object> param = new HashMap<>(16);
        Resp resp = CommunicateUtil.sendUDPMsgByIP(MsgType.METHOD_LOGOUT,param,SERVER_IP,SERVER_PORT,socket);
        return resp;
    }

    /**
     * 发送操作请求(By IP Address)
     */
    public Resp sendUserOperation(MsgType msgType){
        Map<String,Object> param = new HashMap<>(16);
        Resp resp = CommunicateUtil.sendUDPMsgByIP(msgType,param,SERVER_IP,SERVER_PORT,socket);
        return resp;
    }
}
