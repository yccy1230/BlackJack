package utils;

import constants.Constants;
import constants.MsgType;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
* @description 通讯基础结构类
* @author Jack Chen
* @date 2017/10/17
*/
public class Resp implements Serializable {
    private static final long serialVersionUID = -8660197629749596025L;

    private int msgType;
    private String resMsg;
    private int resCode;
    private Object data;
    private Date resTime;

    public Resp() {}

    /**通常用于发生错误时返回数据体*/
    public Resp(int resCode, String resMsg,int msgType) {
        this.resCode = resCode;
        this.msgType = msgType;
        this.resMsg = resMsg;
        this.resTime = new Timestamp(System.currentTimeMillis());
    }

    public Resp(int msgType, Object data) {
        this.resCode = Constants.SUCCESS_CODE;
        this.msgType = msgType;
        this.data = data;
        this.resTime = new Timestamp(System.currentTimeMillis());
    }

    public Resp(int msgType, String resMsg, Date resTime) {
        this.resCode = Constants.SUCCESS_CODE;
        this.msgType = msgType;
        this.resMsg = resMsg;
        this.resTime = resTime;
    }

    public Resp(int msgType, String resMsg, Date resTime, Object data) {
        this.resCode = Constants.SUCCESS_CODE;
        this.msgType = msgType;
        this.resMsg = resMsg;
        this.resTime = resTime;
        this.data = data;
    }

    public Resp(int msgType, String resMsg, Object data) {
        this.resCode = Constants.SUCCESS_CODE;
        this.msgType = msgType;
        this.resMsg = resMsg;
        this.data = data;
        this.resTime = new Timestamp(System.currentTimeMillis());
    }

    public int getResCode() {
        return resCode;
    }

    public void setResCode(int resCode) {
        this.resCode = resCode;
    }

    public Date getResTime() {
        return resTime;
    }

    public void setResTime(Date resTime) {
        this.resTime = resTime;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
