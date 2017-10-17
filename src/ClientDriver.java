import constants.MsgType;
import listener.NetworkListener;
import service.ClientCommunicateService;

/**
 * 客户端驱动类
 * @author Jack Chen
 * @create 2017-10-17 13:01
 */
public class ClientDriver implements NetworkListener {
    private ClientCommunicateService communicateService;

    public static void main(String[] args) {
        //创建驱动类
        ClientDriver driver = new ClientDriver();
        //绑定通讯器
        driver.setCommunicateService(new ClientCommunicateService(driver));
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

    public ClientCommunicateService getCommunicateService() {
        return communicateService;
    }

    public void setCommunicateService(ClientCommunicateService communicateService) {
        this.communicateService = communicateService;
    }

}
