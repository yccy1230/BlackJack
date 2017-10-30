package service;

import constants.Constants;
import constants.ConstantsMsg;
import constants.MsgType;
import entity.Dealer;
import entity.Player;
import entity.ResultDetail;
import entity.Room;
import jdk.management.resource.internal.inst.DatagramDispatcherRMHooks;
import listener.MsgReceiveListener;
import listener.NetworkListener;
import thread.MsgReceiveThread;
import utils.CommunicateUtil;
import utils.Resp;

import javax.xml.crypto.Data;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @description Server通讯服务,维护通讯清单
 *             由于服务器需要能够被客户端请求，所以端口必须固定
* @author Jack Chen
* @date 2017/10/17
*/
public class ServerCommunicateService {
    /**Port*/
    private final int COMMUNICATE_PORT = 6666;
    /**通讯超时时间*/
    private final int TIME_OUT = 30000;

    /**套接字*/
    private DatagramSocket socket;
    /**通讯结果回调*/
    private NetworkListener networkListener;
    /**消息接收线程*/
    private MsgReceiveThread serverMsgReceiveThread;
    /**Session*/
    private HashMap<Integer, HashMap<String,DatagramPacket>> hashTable;

    /**
     * 构造通讯器，并绑定端口
     * @param networkListener
     */
    public ServerCommunicateService(NetworkListener networkListener, MsgReceiveListener msgReceiveListener) {
        this.networkListener = networkListener;
        try {
            socket = new DatagramSocket(COMMUNICATE_PORT);
        } catch (SocketException e) {
            if(networkListener!=null){
                networkListener.bindPortError();
            }
            System.out.println("绑定端口出错，详细堆栈信息如下：");
            e.printStackTrace();
            return;
        }
        //初始化session空间
        hashTable = new HashMap<>();
        //创建并启动信息接受线程
        serverMsgReceiveThread = new MsgReceiveThread(socket,msgReceiveListener);
        serverMsgReceiveThread.start();
        System.out.println("服务器已成功启动。IP："+socket.getLocalAddress().getHostAddress()+";Port:"+socket.getLocalPort());
    }

    /**
     * 用户上线广播消息，同时添加用户到session维护
     */
    public void userConnectedBroadcast(Room room,Player player, DatagramPacket address){
        int roomId = room.getId();
        HashMap<String,DatagramPacket> roomAddress = new HashMap<>();
        if(hashTable.get(roomId)!=null){
            roomAddress.putAll(hashTable.get(roomId));
        }
        for (DatagramPacket dp: roomAddress.values()) {
            Map<String,Object> param = new HashMap<>();
            param.put(Constants.PARAM_PLAYER,player);
            CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_NEWUSER,param,dp,socket);
        }
        roomAddress.put(player.getId(),address);
        hashTable.remove(roomId);
        hashTable.put(roomId,roomAddress);
       return ;
    }

    /**
     * 发送操作请求(By Socket Address)
     * @param socketAddress 目标地址
     */
    public Resp requireUserOperation(SocketAddress socketAddress){
        return null;
    }

    /**
     * 发送操作请求
     */
    public Resp requireUserOperate(){
        return null;
    }

    /**
     * 发送 UI数据 更新请求
     */
    public Resp invalidateUserUI(){
        return null;
    }

    public void sendLoginErrorMsgWithoutResult(DatagramPacket datagramPacket){
        Map<String,Object> param = new HashMap<>(16);
        param.put(Constants.PARAM_RESULT_CODE, Constants.ERROR_CODE);
        param.put(Constants.PARAM_ERROR_MSG, ConstantsMsg.MSG_USER_FULL);
        CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_LOGIN_RESULT,param,datagramPacket,socket);
    }

    public void sendSurrenderMsgWithoutResult(DatagramPacket datagramPacket){
        Map<String,Object> param = new HashMap<>(16);
        param.put(Constants.PARAM_SURRENDER_MSG,Constants.SUCCESS_CODE);
        CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_SURRENDER,param,datagramPacket,socket);
    }

    public void sendReadyMsgWithoutResult(DatagramPacket datagramPacket){
        Map<String,Object> param = new HashMap<>(16);
        param.put(Constants.PARAM_RESULT_CODE, Constants.SUCCESS_CODE);
        CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_READY_RESULT,param,datagramPacket,socket);
    }

    public void sendCancleReadyMsgWithoutResult(DatagramPacket datagramPacket){
        Map<String,Object> param = new HashMap<>(16);
        param.put(Constants.PARAM_RESULT_CODE, Constants.SUCCESS_CODE);
        CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_CANCEL_READY_RESULT,param,datagramPacket,socket);
    }

    public void sendExitMsgWithoutResult(int roomId,Player player){
        HashMap<String,DatagramPacket> roomAddress = new HashMap<>();
        if(hashTable.get(roomId)!=null){
            roomAddress.putAll(hashTable.get(roomId));
        }
        for (DatagramPacket dp: roomAddress.values()) {
            Map<String,Object> param = new HashMap<>();
            param.put(Constants.PARAM_PLAYER,player);
            CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_USER_EXIT,param,dp,socket);
        }
        return ;
    }

    public void sendStartGameBroadcast(int roomId) {
        HashMap<String,DatagramPacket> roomAddress = new HashMap<>();
        if(hashTable.get(roomId)!=null){
            roomAddress.putAll(hashTable.get(roomId));
        }
        for (DatagramPacket dp: roomAddress.values()) {
            Map<String,Object> param = new HashMap<>();
            param.put(Constants.PARAM_START_GAME,Constants.SUCCESS_CODE);
            CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_GAME_BEGIN,param,dp,socket);
        }
        return ;
    }

    public void sendDealCardBroadcast(int roomId,Player player) {
        HashMap<String,DatagramPacket> roomAddress = new HashMap<>();
        if(hashTable.get(roomId)!=null){
            roomAddress.putAll(hashTable.get(roomId));
        }
        for (DatagramPacket dp: roomAddress.values()) {
            Map<String,Object> param = new HashMap<>();
            param.put(Constants.PARAM_PLAYER,player);
            CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_UPDATE_USER,param,dp,socket);
        }
        return ;
    }

    public void sendGameResultBroadcast(int roomId, List<ResultDetail> resultDetails) {
        HashMap<String,DatagramPacket> roomAddress = new HashMap<>();
        if(hashTable.get(roomId)!=null){
            roomAddress.putAll(hashTable.get(roomId));
        }
        for (DatagramPacket dp: roomAddress.values()) {
            Map<String,Object> param = new HashMap<>();
            param.put(Constants.PARAM_GAME_RESULT,resultDetails);
            CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_GAME_OVER,param,dp,socket);
        }
        return ;
    }

    public void sendDealCard2DealerBroadcast(int roomId, Dealer dealer) {
        HashMap<String,DatagramPacket> roomAddress = new HashMap<>();
        if(hashTable.get(roomId)!=null){
            roomAddress.putAll(hashTable.get(roomId));
        }
        for (DatagramPacket dp: roomAddress.values()) {
            Map<String,Object> param = new HashMap<>();
            param.put(Constants.PARAM_DEALER,dealer);
            CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_UPDATE_DEALER,param,dp,socket);
        }
        return ;
    }

    public void sendPropertyNotEnoughMsg(int roomId,String userID){
        HashMap<String,DatagramPacket> userAddress= hashTable.get(roomId);
        DatagramPacket dp = userAddress.get(userID);
        Map<String,Object> param = new HashMap<>();
        param.put(Constants.PARAM_RESULT_CODE,Constants.ERROR_CODE);
        param.put(Constants.PARAM_ERROR_MSG,ConstantsMsg.MSG_PROPERTY_NOT_ENOUGH );
        CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_READY_RESULT,param,dp,socket);
    }

    public void sendUserTurnMsg(int roomId,String userID){
        HashMap<String,DatagramPacket> userAddress= hashTable.get(roomId);
        DatagramPacket dp = userAddress.get(userID);
        Map<String,Object> param = new HashMap<>();
        CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_USER_TURN,param,dp,socket);
    }

    public void sendUserBlackJackBroadcast(int roomId,Player player) {
        HashMap<String,DatagramPacket> roomAddress = new HashMap<>();
        if(hashTable.get(roomId)!=null){
            roomAddress.putAll(hashTable.get(roomId));
        }
        for (String userId : roomAddress.keySet()) {
            DatagramPacket dp = roomAddress.get(userId);
            if(userId.equals(player.getId())){
                Map<String,Object> param = new HashMap<>();
                CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_BLACK_JACK,param,dp,socket);
                continue;
            }
            Map<String,Object> param = new HashMap<>();
            param.put(Constants.PARAM_PLAYER,player);
            CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_OTHER_BLACK_JACK,param,dp,socket);
        }
    }

    public void sendOperationResult(int msgType,Player player,int roomId){
        HashMap<String,DatagramPacket> roomAddress = new HashMap<>();
        if(hashTable.get(roomId)!=null){
            roomAddress.putAll(hashTable.get(roomId));
        }
        for (String  uid : roomAddress.keySet()) {
            DatagramPacket dp = roomAddress.get(uid);
            if(uid.equals(player.getId())){
                Map<String,Object> param = new HashMap<>();
                param.put(Constants.PARAM_RESULT_CODE,Constants.SUCCESS_CODE);
                param.put(Constants.PARAM_PLAYER,player);
                CommunicateUtil.sendUDPMsgWithoutResult(msgType,param,dp,socket);
                continue;
            }
            Map<String,Object> param = new HashMap<>();
            param.put(Constants.PARAM_RESULT_CODE,Constants.SUCCESS_CODE);
            CommunicateUtil.sendUDPMsgWithoutResult(msgType-4,param,dp,socket);
        }
    }

    public void sendNewUI(Player player,int roomId){
        HashMap<String,DatagramPacket> roomAddress = new HashMap<>();
        if(hashTable.get(roomId)!=null){
            roomAddress.putAll(hashTable.get(roomId));
        }
        for (String  uid : roomAddress.keySet()) {
            DatagramPacket dp = roomAddress.get(uid);
            Map<String,Object> param = new HashMap<>();
            param.put(Constants.PARAM_PLAYER,player);
            CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_UPDATE_USER,param,dp,socket);
        }
    }

    public void sendBroadcast() {
        for (Integer key : hashTable.keySet()) {
            DatagramPacket dp = hashTable.get(key).get(key);
            Map<String, Object> param = new HashMap<>();
            CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_NEWUSER, param, dp, socket);
        }
    }

    public void sendLoginResult(String userId,List<Player> players,DatagramPacket datagramPacket){
        Map<String,Object> param =new HashMap<>();
        param.put(Constants.PARAM_PLAYERS,players);
        param.put(Constants.PARAM_USER_ID,userId);
        param.put(Constants.PARAM_RESULT_CODE, Constants.SUCCESS_CODE);
        CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_LOGIN_RESULT,param,datagramPacket,socket);
    }

    //*******************************************************************
    /**抽象四大方法*/

    /**
     * 群发消息，包括自己
     */
    public void sendUDPMsg(int msgType,int roomId){

    }
}
