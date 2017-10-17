package utils;
import com.alibaba.fastjson.JSON;
import listener.NetworkListener;

import java.net.*;
import java.util.HashMap;
import java.util.Map;

/**
* @description Serve服务器通讯工具类
* @author Jack Chen
* @date 2017/10/17
*/
public class ServerUtil {
    /**服务器IP*/
    private final String SERVER_IP = "115.159.35.11";
    /**服务器Port*/
    private final int SERVER_PORT = 6666;

    /**本地Port*/
    private final int LOCAL_PORT= 6666;
    /**通讯子*/
    private DatagramSocket socket;

    private NetworkListener networkListener;

    public ServerUtil() {
        socket = new DatagramSocket();
    }

    /**
     * @param msgType 消息类别
     * @param uname
     * @return 发送是否成功
     */
    public void sendUDPMsg(int msgType,String uname,String messae,String tar_uname){
        try
        {
            Map<String,Object> param = new HashMap<>(16);
            param.put("",)
            /*使用UTF-8编码*/
            byte[] msg = (JSON.toJSONString(param)).getBytes("UTF-8");
            /*得到主机的internet地址*/
            InetAddress address = InetAddress.getByName(SERVER_IP);

            /*用数据和地址初始化一个数据报分组（数据包）*/
            DatagramPacket packet = new DatagramPacket(msg, msg.length, address,
                    SERVER_PORT);

            socket.send(packet);

        }
        catch (Exception e)
        {
            System.out.println("发送UDP消息异常，详细堆栈信息如下：");
            e.printStackTrace();
        }
    }
}
