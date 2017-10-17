package thread;

import com.alibaba.fastjson.JSON;
import listener.MsgReceiveListener;
import utils.Resp;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
* @description Server数据接收器（线程）
*             该线程只负责接收及转换数据，数据处理交由主控制器处理
* @author Jack Chen
* @date 2017/10/17
*/
public class ServerMsgReceiveThread extends Thread {
    /**套接字*/
    private DatagramSocket socket;

    /**消息接收回调*/
    private MsgReceiveListener msgReceiveListener;

    public ServerMsgReceiveThread(DatagramSocket socket,MsgReceiveListener msgReceiveListener) {
        this.socket = socket;
        this.msgReceiveListener = msgReceiveListener;
    }

    @Override
    public void run() {
        while (true){
            byte[] bytes = new byte[1024 * 128];
            DatagramPacket dp = new DatagramPacket(bytes, bytes.length);
            try {
                socket.receive(dp);
                String recStr = new String(bytes, 0, dp.getLength(), "UTF-8");
                Resp resp = JSON.parseObject(recStr,Resp.class);
                if(resp==null){
                    throw new Exception("接收到的消息体为空！");
                }else{
                    System.out.println(resp);
                    msgReceiveListener.onReceiveMsg(resp,dp);
                }
            } catch (Exception e) {
                System.out.println("解析客户端数据出错，详细堆栈信息如下：");
                e.printStackTrace();
                continue;
            }
        }
    }
}
