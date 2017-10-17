import thread.ClientMsgReceiveThread;

/**
 * 客户端驱动类
 * @author Jack Chen
 * @create 2017-10-17 13:01
 */
public class ClientDriver {
    public static void main(String[] args) {

        // 创建接收信息线程
        new ClientMsgReceiveThread().start();
    }
}
