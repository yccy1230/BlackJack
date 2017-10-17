import thread.ServerMsgReceiveThread;

/**
 * 服务器驱动类
 * @author Jack Chen
 * @create 2017-10-17 13:01
 */
public class ServerDriver {
    public static void main(String[] args) {

        // 创建接收信息线程
        new ServerMsgReceiveThread().start();
    }
}
