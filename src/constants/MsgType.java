package constants;

import java.io.Serializable;

/**
* @description 通讯类型枚举类
* @author Jack Chen
* @date 2017/10/17
*/
public enum MsgType implements Serializable{
    //Local Method
    METHOD_LOGIN,       //连接服务器
    METHOD_LOGOUT,       //断开服务器
    METHOD_READY,       //准备，并下注
    METHOD_HIT,         //要牌
    METHOD_STAND,       //停牌
    METHOD_DOUBLE,      //加倍，输本金+加倍金，赢两倍
    METHOD_SURRENDER,   //投降，输一半
    METHOD_RESULT,      //返回值
    METHOD_NEWUSER,    //新用户
    METHOD_GAME_BEGIN, //游戏开始
    METHOD_LOGIN_RESULT, //登陆结果

    //Server Method
    METHOD_UPDATE_USER,     //更新user视图
    METHOD_UPDATE_DEALER,   //更新dealer视图
    METHOD_SHOW_RESULT      //显示游戏结果
}
