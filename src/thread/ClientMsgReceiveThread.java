package thread;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
* @description Client数据接收器（线程）
* @author Jack Chen
* @date 2017/10/17
*/
public class ClientMsgReceiveThread extends Thread {
    /**套接字*/
    private DatagramSocket socket;

    public ClientMsgReceiveThread(DatagramSocket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true){
            byte[] bytes = new byte[1024 * 128];
            DatagramPacket dp = new DatagramPacket(bytes, bytes.length);
        }
    }
}
