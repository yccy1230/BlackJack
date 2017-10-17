package utils;

import constants.MsgType;

import java.io.Serializable;
import java.util.Date;

/**
* @description 通讯基础结构类
* @author Jack Chen
* @date 2017/10/17
*/
public class Resp implements Serializable {
    private static final long serialVersionUID = -8660197629749596025L;

    private MsgType msgType;
    private String resMsg;
    private Date resTime;
    private Object data;

    /**
     * @return the resTime
     */
    public Long getResTime() {
        if (resTime == null) {
            return 0L;
        }
        return resTime.getTime();
    }

    /**
     * @param resTime the resTime to set
     */
    public void setResTime(Date resTime) {
        this.resTime = resTime;
    }

    public Resp() {

    }


    public Resp(MsgType resCode, String resMsg) {
        this.msgType = resCode;
        this.resMsg = resMsg;

    }

    public Resp(MsgType resCode, String resMsg, Date resTime) {
        this.msgType = resCode;
        this.resMsg = resMsg;
        this.resTime = resTime;

    }

    public Resp(MsgType resCode, String resMsg, Date resTime, Object data) {
        this.msgType = resCode;
        this.resMsg = resMsg;
        this.resTime = resTime;
        this.data = data;
    }

    public Resp(MsgType resCode, String resMsg, Object data) {
        this.msgType = resCode;
        this.resMsg = resMsg;
        this.data = data;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
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
