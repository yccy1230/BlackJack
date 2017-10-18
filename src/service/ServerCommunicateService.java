package service;

import listener.MsgReceiveListener;
import listener.NetworkListener;
import thread.MsgReceiveThread;
import utils.Resp;

import java.net.*;
import java.util.HashMap;

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
    private MsgReceiveThread serverMsgReceiveThread;
    /**Session*/
    private HashMap<String, SocketAddress> hashTable;

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
        //初始化session空间
        hashTable = new HashMap<>();
        //创建并启动信息接受线程
        serverMsgReceiveThread = new MsgReceiveThread(socket,msgReceiveListener);
        serverMsgReceiveThread.start();
    }

    /**
     * 用户上线广播消息，同时添加用户到session维护
     */
    public void userConnectedBroadcast(){

    }

    /**
     * 发送操作请求(By Socket Address)
     * @param socketAddress 目标地址
     */
    public Resp requireUserOperation(SocketAddress socketAddress){
        return null;
    }

    /**
     * 发送操作请求
     */
    public Resp requireUserOperate(){
        return null;
    }

    /**
     * 发送 UI数据 更新请求
     */
    public Resp invalidateUserUI(){
        return null;
    }
}
