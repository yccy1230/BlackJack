package controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import constants.MsgType;
import listener.MsgReceiveListener;
import listener.NetworkListener;
import listener.OperationListener;
import service.ClientCommunicateService;
import ui.MainFrame;
import utils.Resp;

import java.net.DatagramPacket;
import java.util.List;
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
	}

	@Override
    public void onReceiveMsg(Resp resp, DatagramPacket dp) {
    	if(resp.getResCode()==Constants.SUCCESS_CODE) {
    		Map<String,Object> param = (Map<String, Object>) resp.getData();
			switch (resp.getMsgType()) {
				case MsgType.METHOD_NEWUSER:
					Player playerParam = JSONObject.parseObject(((JSONObject)param.get(Constants.PARAM_PLAYER)).toJSONString(),Player.class);
					mainFrame.addUserPanel(playerParam);
					break;
				case MsgType.METHOD_GAME_BEGIN:
					mainFrame.onGameStart();
					break;
				case MsgType.METHOD_STAND:
					break;
				case MsgType.METHOD_DOUBLE:
					break;
				case MsgType.METHOD_READY_RESULT:
					if(Constants.SUCCESS_CODE == (int) param.get(Constants.PARAM_READY_RESULT)){
						mainFrame.showToastMsg(ConstantsMsg.MSG_WAIT_OTHER_USER);
						mainFrame.onUserReady();
					}else{
						mainFrame.showMessage(resp.getResMsg());
						mainFrame.onUserLogin();
					}
					break;
				case MsgType.METHOD_CANCLE_READY_RESULT:
					if(Constants.SUCCESS_CODE == (int) param.get(Constants.PARAM_CANCLE_READY_RESULT)){
						mainFrame.showToastMsg("");
						mainFrame.onUserLogin();
					}else{
						mainFrame.showMessage(resp.getResMsg());
						mainFrame.onUserReady();
					}
					break;
				case MsgType.METHOD_USER_EXIT:
					String userid = (String) param.get(Constants.PARAM_USER_ID);
					if(userid!=null){
						mainFrame.removeUserPanel(userid);
					}
					break;
				case MsgType.METHOD_LOGIN_RESULT:
					if(Constants.SUCCESS_CODE == (int) param.get(Constants.PARAM_LOGIN_RESULT)){
						communicateService.setDeviceID((String) param.get(Constants.PARAM_USER_ID));
						mainFrame.showMainFrame();
						List<Player> players = JSONObject.parseArray(((JSONArray)param.get(Constants.PARAM_INIT_USER)).toJSONString(),Player.class);
						for (int i = 0; i < players.size(); i++) {
							Player player = players.get(i);
							mainFrame.addUserPanel(player);
						}
					}else{
						mainFrame.showMessage((String) param.get(Constants.PARAM_ERROR_MSG));
					}
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
		mainFrame.showToastMsg("onStandClicked");
		mainFrame.showMessage("onStandClicked");
    }

	@Override
	public void onDoubleClicked() {
		mainFrame.showToastMsg("onDoubleClicked");
		mainFrame.showMessage("onDoubleClicked");
    }

	@Override
	public void onSurrenderClicked() {

		mainFrame.showToastMsg("onSurrenderClicked");
		mainFrame.showMessage("onSurrenderClicked");
	}

	@Override
	public void onReadyClicked(int bet) {
		communicateService.sendUserReadyMsg(bet);
	}

	@Override
	public void onCancelReadyClicked() {
		communicateService.sendUserCancelReady();
	}

	@Override
	public void onConnectClicked(String ip, int port, String nickName) {
		communicateService.setServerIP(ip);
		communicateService.setServerPort(port);
		communicateService.connectServer(nickName);
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
