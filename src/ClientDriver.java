import constants.MsgType;
import controller.UIController;
import listener.NetworkListener;
import service.ClientCommunicateService;

/**
 * 客户端驱动类
 * @author Jack Chen
 * @create 2017-10-17 13:01
 */
public class ClientDriver implements NetworkListener {

    public static void main(String[] args) {
        //创建驱动类
        ClientDriver driver = new ClientDriver();
        //创建主控制器
        UIController uiController = new UIController();
        //创建通讯器
        ClientCommunicateService communicator = new ClientCommunicateService(driver,uiController);
        //为UI主控制器绑定通讯器
        uiController.setCommunicateService(communicator);
    }

    @Override
    public void bindPortError() {

    }

    @Override
    public void sendUDPMsgError(MsgType msgType, String msg) {

    }

    @Override
    public void connectServerSuccess() {

    }

    @Override
    public void connectServerFailure(String msg) {

    }

}
