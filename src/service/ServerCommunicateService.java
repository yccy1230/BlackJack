package service;
import com.alibaba.fastjson.JSON;
import constants.MsgType;
import listener.NetworkListener;
import utils.Resp;

import java.net.*;
import java.util.HashMap;
import java.util.Map;

/**
* @description Serve服务器通讯类
* @author Jack Chen
* @date 2017/10/17
*/
public class ServerCommunicateService {
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

    /**
     * 构造通讯器，并绑定端口
     * @param networkListener
     */
    public ServerCommunicateService(NetworkListener networkListener) {
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
        }
    }

    /**
     * 连接服务器
     * @param nickName 用户登陆昵称
     */
    public void connectServer(String nickName){
        Map<String,Object> param = new HashMap<>(16);
        param.put("nickname",nickName);
        Resp resp = sendUDPMsg(MsgType.METHOD_LOGIN,param);
        if(resp!=null && networkListener!=null){
            networkListener.connectServerSuccess();
        }
    }



    /**
     * 内部方法，用于向服务器发送请求
     * @param msgType 消息类别
     * @param param 传递参数
     * @return 请求返回结果，如果发生异常返回null
     */
    private Resp sendUDPMsg(MsgType msgType,Map<String,Object> param){
        try
        {
            Resp resp = new Resp(msgType,param);
            /*使用UTF-8编码*/
            byte[] msg = (JSON.toJSONString(resp)).getBytes("UTF-8");
            /*得到主机的internet地址*/
            InetAddress address = InetAddress.getByName(SERVER_IP);
            /*用数据和地址初始化一个数据报分组（数据包）*/
            DatagramPacket packet = new DatagramPacket(msg, msg.length, address,SERVER_PORT);
            socket.send(packet);

            //接收服务器反馈数据
            byte[] backBuf = new byte[1024];
            DatagramPacket backPacket = new DatagramPacket(backBuf, backBuf.length);
            socket.receive(backPacket);
            String backMsg = new String(backBuf, 0, backPacket.getLength());
            Resp response = JSON.parseObject(backMsg,Resp.class);
            return response;
        }
        catch (Exception e)
        {
            if(networkListener!=null){
                networkListener.sendUDPMsgError();
            }
            System.out.println("发送UDP消息异常，详细堆栈信息如下：");
            e.printStackTrace();
            return null;
        }
    }
}
