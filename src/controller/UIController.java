package controller;

import constants.MsgType;
import listener.MsgReceiveListener;
import listener.NetworkListener;
import listener.OperationListener;
import service.ClientCommunicateService;
import ui.MainFrame;
import utils.Resp;

import java.net.DatagramPacket;
import java.util.Map;

import constants.Constants;
import constants.ConstantsMsg;
import entity.Player;

/**
* @description UI总控制器，负责客户端交互,数据通讯
* @author Jack Chen
* @date 2017/10/17
*/
public class UIController implements MsgReceiveListener,OperationListener, NetworkListener {

    private ClientCommunicateService communicateService;
    private MainFrame mainFrame;
    
    public UIController() {
    	mainFrame = new MainFrame(this);
		communicateService = new ClientCommunicateService(this,this);
		mainFrame.showLoginFrame();
//		mainFrame.showMainFrame();
	}

	@Override
    public void onReceiveMsg(Resp resp, DatagramPacket dp) {
    	if(resp.getResCode()==Constants.SUCCESS_CODE) {
    		Map<String,Object> param = (Map<String, Object>) resp.getData();
			switch (resp.getMsgType()) {
				case METHOD_NEWUSER:
					mainFrame.addUserPanel((Player) param.get(Constants.PARAM_PLAYER));
					break;
				case METHOD_GAME_BEGIN:
					break;
				case METHOD_STAND:
					break;
				case METHOD_DOUBLE:
					break;
				case METHOD_RESULT:
					break;
				case METHOD_SURRENDER:
					break;
				default:
					break;
			}
		}
    }

	@Override
	public void onHitClicked() {
		mainFrame.showToastMsg("hit clicked");
		mainFrame.showMessage("hit clicked");
	}

	@Override
	public void onStandClicked() {
		
	}

	@Override
	public void onDoubleClicked() {
		
	}

	@Override
	public void onSurrenderClicked() {
		
	}

	@Override
	public void onReadyClicked(int bet) {
		Resp resp = communicateService.sendUserReadyMsg(bet);
		if(resp.getResCode()==Constants.SUCCESS_CODE){
			mainFrame.showToastMsg(ConstantsMsg.MSG_WAIT_OTHER_USER);
		}else{
			//准备出错
			mainFrame.showMessage(resp.getResMsg());
			mainFrame.showUserReadyBtn();
		}
	}

	@Override
	public void onCancelReadyClicked() {

	}

	@Override
	public void onConnectClicked(String ip, int port, String nickName) {
		communicateService.setServerIP(ip);
		communicateService.setServerPort(port);
		Resp resp = communicateService.connectServer(nickName);
		if(resp.getResCode()==Constants.SUCCESS_CODE){
			mainFrame.showMainFrame();
		}else{
			mainFrame.showMessage(resp.getResMsg());
		}
	}

	@Override
	public void onExitClicked() {
		communicateService.disconnectServer();
		System.exit(0);
	}

	@Override
	public void bindPortError() {
		mainFrame.showMessage(ConstantsMsg.MSG_BIND_PORT_ERROR);
		System.exit(0);
	}

	@Override
	public void sendUDPMsgError(MsgType msgType, String msg) {
		mainFrame.showMessage(ConstantsMsg.MSG_SEND_UDPMSG_ERROR);
	}
}
