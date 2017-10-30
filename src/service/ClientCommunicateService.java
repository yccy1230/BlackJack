package service;

import constants.Constants;
import constants.MsgType;
import listener.MsgReceiveListener;
import listener.NetworkListener;
import thread.MsgReceiveThread;
import utils.CommunicateUtil;
import utils.Resp;

import java.net.DatagramSocket;
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
    private String serverIP = "115.159.35.11";
    /**服务器Port*/
    private int serverPort = 6666;
    /**通讯超时时间*/
    private final int TIME_OUT = 30000;

    /**本地Port*/
    private int localPort;

    /**标识ID*/
    private String deviceID;
    /**通讯房间ID*/
    private int roomID;
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
    public void connectServer(String nickName){
        Map<String,Object> param = new HashMap<>(16);
        param.put(Constants.PARAM_NICK_NAME,nickName);
        param.put(Constants.PARAM_ROOM_ID,roomID);
        CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_LOGIN,param,serverIP,serverPort,roomID,socket);
    }

    /**
     * 断开服务器（退出房间）,强退==认输
     * @return 返回结果
     */
    public void disconnectServer(){
        Map<String,Object> param = new HashMap<>(16);
        param.put(Constants.PARAM_USER_ID,deviceID);
        CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_USER_EXIT,param,serverIP,serverPort,roomID,socket);
    }
    
    /**
     * 发送准备请求(By IP Address)
     */
    public void sendUserReadyMsg(int bet){
        Map<String,Object> param = new HashMap<>(16);
        param.put(Constants.PARAM_BET, bet);
        param.put(Constants.PARAM_USER_ID,deviceID);
        CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_READY,param,serverIP,serverPort,roomID,socket);
    }

    /**
     * 发送取消准备请求(By IP Address)
     */
    public void sendUserCancelReady(){
        Map<String,Object> param = new HashMap<>(16);
        param.put(Constants.PARAM_USER_ID,deviceID);
        CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_CANCEL_READY,param,serverIP,serverPort,roomID,socket);
    }

    /**
     * 发送操作请求(By IP Address)    
     */
    public void sendUserOperation(int msgType){
        Map<String,Object> param = new HashMap<>(16);
        param.put(Constants.PARAM_USER_ID,deviceID);
        CommunicateUtil.sendUDPMsgWithoutResult(msgType,param,serverIP,serverPort,roomID,socket);
    }

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }
}
