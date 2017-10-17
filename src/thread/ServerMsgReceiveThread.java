package thread;

import java.net.DatagramSocket;

/**
* @description Server数据接收器（线程）
* @author Jack Chen
* @date 2017/10/17
*/
public class ServerMsgReceiveThread extends Thread {
    /**套接字*/
    private DatagramSocket socket;

    public ServerMsgReceiveThread(DatagramSocket socket){
        this.socket = socket;
    }


    @Override
    public void run() {
        super.run();
    }
}
