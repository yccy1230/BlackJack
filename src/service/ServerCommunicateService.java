package service;

import com.sun.corba.se.impl.orbutil.closure.Constant;
import constants.Constants;
import constants.ConstantsMsg;
import constants.MsgType;
import entity.Player;
import entity.Room;
import listener.MsgReceiveListener;
import listener.NetworkListener;
import org.omg.PortableInterceptor.INACTIVE;
import sun.awt.image.IntegerComponentRaster;
import thread.MsgReceiveThread;
import utils.CommunicateUtil;
import utils.Resp;

import java.lang.management.PlatformLoggingMXBean;
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
        param.put(Constants.PARAM_ERROR_MSG, ConstantsMsg.LOGIN_ERROR);
        param.put(Constants.PARAM_LOGIN_RESULT, Constants.LOGIN_ERROR);
        CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_LOGIN_RESULT,param,datagramPacket,socket);
    }

    public void sendSurrenderMsgWithoutResult(DatagramPacket datagramPacket){
        Map<String,Object> param = new HashMap<>(16);
        param.put(Constants.PARAM_SURRENDER_MSG,Constants.SURRENDER_SUCCESS);
        CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_SURRENDER,param,datagramPacket,socket);
    }

    public void sendReadyMsgWithoutResult(DatagramPacket datagramPacket){
        Map<String,Object> param = new HashMap<>(16);
        param.put(Constants.PARAM_READY_RESULT, Constants.READY_SUCCESS);
        CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_READY_RESULT,param,datagramPacket,socket);
    }

    public void sendCancleReadyMsgWithoutResult(DatagramPacket datagramPacket){
        Map<String,Object> param = new HashMap<>(16);
        param.put(Constants.PARAM_CANCLE_READY_RESULT, Constants.CANCLE_READY_SUCCESS);
        CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_CANCLE_READY_RESULT,param,datagramPacket,socket);
    }

    public void sendExitMsgWithoutResult(int roomId,String userID){
        for(Integer key : hashTable.keySet()){
            if(key == roomId){
                hashTable.get(key).remove(userID);
                for(int i =0; i< hashTable.get(key).size(); i++){
                    DatagramPacket dp = hashTable.get(key).get(i);
                    Map<String,Object> param = new HashMap<>();
                    param.put(Constants.PARAM_USER_ID,userID);
                    CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_USER_EXIT,param,dp,socket);
                }
                return ;
            }
        }
    }

    public void sendStartGameBroadcast(int roomId) {
        HashMap<String,DatagramPacket> roomAddress = new HashMap<>();
        if(hashTable.get(roomId)!=null){
            roomAddress.putAll(hashTable.get(roomId));
        }
        for (DatagramPacket dp: roomAddress.values()) {
            Map<String,Object> param = new HashMap<>();
            param.put(Constants.PARAM_START_GAME,Constants.START_SUCCESS);
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
            param.put(Constants.PARAM_START_GAME,Constants.START_SUCCESS);
            param.put(Constants.PARAM_PLAYER,player);
            CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_HIT,param,dp,socket);
        }
        return ;
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
        param.put(Constants.PARAM_INIT_USER,players);
        param.put(Constants.PARAM_USER_ID,userId);
        param.put(Constants.PARAM_LOGIN_RESULT, Constants.LOGIN_SUCCESS);
        CommunicateUtil.sendUDPMsgWithoutResult(MsgType.METHOD_LOGIN_RESULT,param,datagramPacket,socket);
    }
}
