package controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import constants.MsgType;
import entity.Dealer;
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
		    //新增用户
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
            //更新庄家UI
            case MsgType.METHOD_UPDATE_DEALER:
                Dealer dealer = JSONObject.parseObject(((JSONObject)param.get(Constants.PARAM_DEALER)).toJSONString(),Dealer.class);
                if(dealer!=null){
                    mainFrame.onDealerUpdate(dealer);
                }
                break;
            //更新玩家UI
            case MsgType.METHOD_UPDATE_USER:
                Player player = JSONObject.parseObject(((JSONObject)param.get(Constants.PARAM_PLAYER)).toJSONString(),Player.class);
                if(player!=null){
                    mainFrame.onPlayerUpdate(player);
                }
                break;
            //BlackJack
            case MsgType.METHOD_BLACK_JACK:
                mainFrame.onUserBlackJack();
                break;
            //Other User BlackJack
            case MsgType.METHOD_OTHER_BLACK_JACK:
                Player playerBj = JSONObject.parseObject(((JSONObject)param.get(Constants.PARAM_PLAYER)).toJSONString(),Player.class);
                mainFrame.onOtherUserBlackJack(playerBj);
                break;
            //用户操作轮次
            case MsgType.METHOD_USER_TURN:
                mainFrame.onUserTurns();
                break;
            //用户操作结果返回
            case MsgType.METHOD_HIT_RESULT:
            case MsgType.METHOD_SURRENDER_RESULT:
			case MsgType.METHOD_STAND_RESULT:
                if(Constants.SUCCESS_CODE != (int) param.get(Constants.PARAM_RESULT_CODE)){
                    mainFrame.onUserOperateError((String) param.get(Constants.PARAM_ERROR_MSG));
                }else{
                    mainFrame.showToastMsg("");
                }
			    break;
            case MsgType.METHOD_BUST:
                Player playerBust = JSONObject.parseObject(((JSONObject)param.get(Constants.PARAM_PLAYER)).toJSONString(),Player.class);
                mainFrame.onUserBoom(playerBust);
                break;
            //其他用户操作结果广播
            case MsgType.METHOD_OTHER_HIT_RESULT:
            case MsgType.METHOD_OTHER_SURRENDER_RESULT:
            case MsgType.METHOD_OTHER_STAND_RESULT:
            case MsgType.METHOD_OTHER_BUST:
                if(Constants.SUCCESS_CODE == (int) param.get(Constants.PARAM_RESULT_CODE)){
                    Player playerOp = JSONObject.parseObject(((JSONObject)param.get(Constants.PARAM_PLAYER)).toJSONString(),Player.class);
                    mainFrame.onOtherUserOperationSuccess(playerOp,resp.getMsgType());
                }
                break;
            //用户加倍操作返回
			case MsgType.METHOD_DOUBLE_RESULT:
                if(Constants.SUCCESS_CODE != (int) param.get(Constants.PARAM_RESULT_CODE)){
                    mainFrame.onDoubleOperateError((String) param.get(Constants.PARAM_ERROR_MSG));
                }else{
                    mainFrame.onDoubleOperateSuccess();
                }
				break;
			//准备操作 返回结果
			case MsgType.METHOD_READY_RESULT:
				if(Constants.SUCCESS_CODE == (int) param.get(Constants.PARAM_RESULT_CODE)){
					mainFrame.onUserReadySuccess();
				}else{
					mainFrame.onUserReadyError((String) param.get(Constants.PARAM_ERROR_MSG));
				}
				break;
			//取消准备 返回结果
			case MsgType.METHOD_CANCEL_READY_RESULT:
				if(Constants.SUCCESS_CODE == (int) param.get(Constants.PARAM_RESULT_CODE)){
					mainFrame.onUserCancelReadySuccess();
				}else{
					mainFrame.onUserCancelReadyError((String) param.get(Constants.PARAM_ERROR_MSG));
				}
				break;
			//有其他用户退出消息
			case MsgType.METHOD_USER_EXIT:
                Player playerExit = JSONObject.parseObject(((JSONObject)param.get(Constants.PARAM_PLAYER)).toJSONString(),Player.class);
				if(playerExit!=null){
					mainFrame.onOtherUserExit(playerExit);
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
