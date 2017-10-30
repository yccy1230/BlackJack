package controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import constants.MsgType;
import entity.ResultDetail;
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
            //游戏正式开始
			case MsgType.METHOD_GAME_BEGIN:
				mainFrame.onGameStart();
				break;
            //游戏结束
            case MsgType.METHOD_GAME_OVER:
                List<ResultDetail> resultDetails = JSONObject.parseArray(((JSONArray)param.get(Constants.PARAM_GAME_RESULT)).toJSONString(),ResultDetail.class);
                mainFrame.onGameOver(resultDetails);
                break;
            case MsgType.METHOD_HIT_RESULT:
                break;
            case MsgType.METHOD_SURRENDER_RESULT:

                break;
			case MsgType.METHOD_STAND_RESULT:
				break;
			case MsgType.METHOD_DOUBLE_RESULT:
				break;
			//准备返回结果
			case MsgType.METHOD_READY_RESULT:
				if(Constants.SUCCESS_CODE == (int) param.get(Constants.PARAM_RESULT_CODE)){
					mainFrame.onUserReadySuccess();
				}else{
					mainFrame.onUserReadyError((String) param.get(Constants.PARAM_ERROR_MSG));
				}
				break;
			//取消准备返回结果
			case MsgType.METHOD_CANCEL_READY_RESULT:
				if(Constants.SUCCESS_CODE == (int) param.get(Constants.PARAM_RESULT_CODE)){
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
				if(Constants.SUCCESS_CODE == (int) param.get(Constants.PARAM_RESULT_CODE)){
					communicateService.setDeviceID((String) param.get(Constants.PARAM_USER_ID));
					List<Player> players = JSONObject.parseArray(((JSONArray)param.get(Constants.PARAM_PLAYERS)).toJSONString(),Player.class);
					mainFrame.onLoginSuccess(players);
				}
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
        communicateService.sendUserOperation(MsgType.METHOD_HIT);
	}

	@Override
	public void onStandClicked() {
        communicateService.sendUserOperation(MsgType.METHOD_STAND);
    }

	@Override
	public void onDoubleClicked() {
        communicateService.sendUserOperation(MsgType.METHOD_DOUBLE);
    }

	@Override
	public void onSurrenderClicked() {
        communicateService.sendUserOperation(MsgType.METHOD_SURRENDER);
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
