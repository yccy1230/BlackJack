package constants;

import java.io.Serializable;

/**
* @description 通讯类型枚举类
* @author Jack Chen
* @date 2017/10/17
*/
public class MsgType {
    //Local Method
    public static final int METHOD_LOGIN =  1;      //连接服务器
    public static final int METHOD_LOGOUT =  2;     //断开服务器
    public static final int METHOD_READY =  3;    //准备，并下注
    public static final int METHOD_HIT =  4;     //要牌
    public static final int METHOD_STAND =  5;      //停牌
    public static final int METHOD_DOUBLE =  6;     //加倍，输本金+加倍金，赢两倍
    public static final int METHOD_SURRENDER =  7;    //投降，输一半
    public static final int METHOD_RESULT =  8;     ///返回值
    public static final int METHOD_NEWUSER =  9;     //新用户
    public static final int METHOD_GAME_BEGIN =  10;     //游戏开始
    public static final int METHOD_UPDATE_USER =  11;     //更新user视图
    public static final int METHOD_UPDATE_DEALER =  12;    //更新dealer视图
    public static final int METHOD_SHOW_RESULT =  13;     //显示游戏结果
    public static final int METHOD_LOGIN_RESULT =  14;     //登陆结果
    public static final int METHOD_READY_RESULT = 15;   //准备结果
    public static final int METHOD_USER_EXIT = 16;   //用户退出
}