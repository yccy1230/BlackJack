package controller;

import listener.MsgReceiveListener;
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
public class UIController implements MsgReceiveListener,OperationListener{
    private ClientCommunicateService communicateService;//通讯器
    private MainFrame mainFrame;	//主UI
    
    public UIController() {
    	mainFrame = new MainFrame(this);
    	mainFrame.showLoginFrame();
	}

	@Override
    public void onReceiveMsg(Resp resp, DatagramPacket dp) {
    	if(resp.getResCode()==Constants.SUCCESS_CODE) {
    		Map<String,Object> param = (Map<String, Object>) resp.getData();
			switch (resp.getMsgType()) {
				case METHOD_NEWUSER:
					mainFrame.addUserPanel((Player) param.get(Constants.PARAM_PLAYER));
					break;
				case METHOD_READY:
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

    public void setCommunicateService(ClientCommunicateService communicateService) {
        this.communicateService = communicateService;
        mainFrame.showLoginFrame();
    }

	@Override
	public void onHitClicked() {
		
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
			mainFrame.showReadyCancelFrame();
		}else{
			mainFrame.showMessage(resp.getResMsg());
		}
	}

	@Override
	public void onCancelReady() {

	}

	@Override
	public void onConnectClicked(String ip, int port, String nickName) {
		communicateService.setServerIP(ip);
		communicateService.setServerPort(port);
		Resp resp = communicateService.connectServer(nickName);
		if(resp.getResCode()==Constants.SUCCESS_CODE){
			mainFrame.showMainFrame();
		}else{
			Map<String,Object> param = (Map<String, Object>) resp.getData();
			mainFrame.showMessage((String) param.get("msg"));
		}
	}
}
