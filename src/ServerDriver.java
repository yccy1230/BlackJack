import constants.MsgType;
import controller.GameController;
import listener.NetworkListener;
import service.ServerCommunicateService;

/**
 * 服务器驱动类
 * @author Jack Chen
 * @create 2017-10-17 13:01
 */
public class ServerDriver implements NetworkListener{
    public static void main(String[] args) {
        //创建驱动类
        ServerDriver driver = new ServerDriver();
        //创建主控制器
        GameController gameController = new GameController();
        //创建通讯器
        ServerCommunicateService communicator = new ServerCommunicateService(driver,gameController);
        //为主控制器绑定通讯器
        gameController.setCommunicateService(communicator);
    }

    @Override
    public void bindPortError() {

    }

    @Override
    public void sendUDPMsgError(MsgType msgType, String msg) {

    }

}
