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
		Map<String,Object> param = (Map<String, Object>) resp.getData();
		switch (resp.getMsgType()) {
			case MsgType.METHOD_NEWUSER:
				Player playerParam = JSONObject.parseObject(((JSONObject)param.get(Constants.PARAM_PLAYER)).toJSONString(),Player.class);
				if(playerParam!=null){
					mainFrame.onOtherUserEnterRoom(playerParam);
				}
				break;
			case MsgType.METHOD_GAME_BEGIN:
				mainFrame.onGameStart();
				break;
			case MsgType.METHOD_STAND:
				break;
			case MsgType.METHOD_DOUBLE:
				break;
			//准备返回结果
			case MsgType.METHOD_READY_RESULT:
				if(Constants.SUCCESS_CODE == (int) param.get(Constants.PARAM_READY_RESULT)){
					mainFrame.onUserReadySuccess();
				}else{
					mainFrame.onUserReadyError((String) param.get(Constants.PARAM_ERROR_MSG));
				}
				break;
			//取消准备返回结果
			case MsgType.METHOD_CANCLE_READY_RESULT:
				if(Constants.SUCCESS_CODE == (int) param.get(Constants.PARAM_CANCLE_READY_RESULT)){
					mainFrame.onUserCancelReadySuccess();
				}else{
					mainFrame.onUserCancelReadyError((String) param.get(Constants.PARAM_ERROR_MSG));
				}
				break;
			//有其他用户退出消息
			case MsgType.METHOD_USER_EXIT:
				String userId = (String) param.get(Constants.PARAM_USER_ID);
				if(userId!=null){
					mainFrame.onOtherUserExit(userId);
				}
				break;
			//登录结果返回
			case MsgType.METHOD_LOGIN_RESULT:
				//登录成功
				if(Constants.SUCCESS_CODE == (int) param.get(Constants.PARAM_LOGIN_RESULT)){
					communicateService.setDeviceID((String) param.get(Constants.PARAM_USER_ID));
					List<Player> players = JSONObject.parseArray(((JSONArray)param.get(Constants.PARAM_INIT_USER)).toJSONString(),Player.class);
					mainFrame.onLoginSuccess(players);
				}
				//登录失败
				else{
					mainFrame.onLoginError((String) param.get(Constants.PARAM_ERROR_MSG));
				}
				break;
			default:
				break;
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
	public void onConnectClicked(String ip, int port, String nickName,int roomId) {
		communicateService.setServerIP(ip);
		communicateService.setServerPort(port);
		communicateService.setRoomID(roomId);
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
