import constants.MsgType;
import listener.NetworkListener;
import service.ServerCommunicateService;

/**
 * 服务器驱动类
 * @author Jack Chen
 * @create 2017-10-17 13:01
 */
public class ServerDriver implements NetworkListener{
    private ServerCommunicateService communicateService;
    public static void main(String[] args) {
        //创建驱动类
        ServerDriver driver = new ServerDriver();
        //绑定通讯器
        driver.setCommunicateService(new ServerCommunicateService(driver));
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

    public ServerCommunicateService getCommunicateService() {
        return communicateService;
    }

    public void setCommunicateService(ServerCommunicateService communicateService) {
        this.communicateService = communicateService;
    }
}
