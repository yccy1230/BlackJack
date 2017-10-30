package utils;

import com.alibaba.fastjson.JSON;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;

/**
* @description 通讯工具类
* @author Jack Chen
* @date 2017/10/17
*/
public class CommunicateUtil {
    /**
     * 用于发送UDP请求（指定IP、Port）
     * @param msgType 消息类别
     * @param param 传递参数
     * @return 请求返回结果，如果发生异常返回null
     */
    public static Resp sendUDPMsgByIP(int msgType, Map<String,Object> param,
                                  String targetIp, int targetPort,int roomID,DatagramSocket socket){
        try
        {
            Resp resp = new Resp(roomID,msgType,param);
            /*使用UTF-8编码*/
            byte[] msg = (JSON.toJSONString(resp)).getBytes("UTF-8");
            /*得到主机的internet地址*/
            InetAddress address = InetAddress.getByName(targetIp);
            /*用数据和地址初始化一个数据报分组（数据包）*/
            DatagramPacket packet = new DatagramPacket(msg, msg.length, address,targetPort);
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
            System.out.println("发送UDP消息异常，详细堆栈信息如下：");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 用于发送UDP请求,不接受返回（指定IP、Port）
     * @param msgType 消息类别
     * @param param 传递参数
     * @return 请求返回结果，如果发生异常返回null
     */
    public static void sendUDPMsgWithoutResult(int msgType, Map<String,Object> param,
                                      String targetIp, int targetPort,int roomID, DatagramSocket socket){
        try
        {
            Resp resp = new Resp(roomID,msgType,param);
            /*使用UTF-8编码*/
            byte[] msg = (JSON.toJSONString(resp)).getBytes("UTF-8");
            /*得到主机的internet地址*/
            InetAddress address = InetAddress.getByName(targetIp);
            /*用数据和地址初始化一个数据报分组（数据包）*/
            DatagramPacket packet = new DatagramPacket(msg, msg.length, address,targetPort);
            socket.send(packet);
        }
        catch (Exception e)
        {
            System.out.println("发送UDP消息异常，详细堆栈信息如下：");
            e.printStackTrace();
        }
    }

    /**
     * 用于发送UDP请求（指定SocketAddress）
     * @param msgType 消息类别
     * @param param 传递参数
     * @return 请求返回结果，如果发生异常返回null
     */
    public static Resp sendUDPMsgBySocket(int msgType, Map<String,Object> param,
                                          DatagramPacket datagramPacket, DatagramSocket socket){
        try
        {
            Resp resp = new Resp(msgType,param);
            byte[] msg = (JSON.toJSONString(resp)).getBytes("UTF-8");
            DatagramPacket packet = new DatagramPacket(msg, msg.length, datagramPacket.getAddress(), datagramPacket.getPort());
            packet.setSocketAddress(datagramPacket.getSocketAddress());
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
            System.out.println("发送UDP消息异常，详细堆栈信息如下：");
            e.printStackTrace();
            return null;
        }
    }

    public static void sendUDPMsgWithoutResult(int msgType, Map<String,Object> param,
                                                       DatagramPacket datagramPacket, DatagramSocket socket){
        try
        {
            Resp resp = new Resp(msgType,param);
            byte[] msg = (JSON.toJSONString(resp)).getBytes("UTF-8");
            DatagramPacket packet = new DatagramPacket(msg, msg.length, datagramPacket.getAddress(), datagramPacket.getPort());
            packet.setSocketAddress(datagramPacket.getSocketAddress());
            socket.send(packet);
        }
        catch (Exception e)
        {
            System.out.println("发送UDP消息异常，详细堆栈信息如下：");
            e.printStackTrace();
        }
    }
}
