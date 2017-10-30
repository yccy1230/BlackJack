package constants;

/**
 * 消息常量
 * @author Jack Chen
 *
 */
public class ConstantsMsg {

    /**提示消息*/
	public static final String MSG_WAIT_OTHER_USER ="等待其他用户准备...";
	public static final String MSG_ENTER_BET ="请输入赌注金额：";
    public static final String MSG_EMPTY_INPUT ="参数不能为空";
    public static final String MSG_BIND_PORT_ERROR ="绑定端口失败，请尝试重启应用~";
    public static final String MSG_SEND_UDPMSG_ERROR ="发送数据包出错~";
    public static final String MSG_CONFIRM_LOGOUT ="确认退出游戏？";
    public static final String MSG_CONFIRM_LOGOUT_TITLE ="提示？";
    public static final String MSG_USER_FULL = "房间已满，登录失败";
    public static final String MSG_GAME_START = "游戏开始!";
    public static final String MSG_OPERATION_ERROR = "操作失败，请重新尝试！";
    public static final String MSG_OPERATION_DOUBLE_SUCCESS = "加倍成功，请继续操作！";
    public static final String MSG_PROPERTY_NOT_ENOUGH = "余额不足！";
    public static final String MSG_USER_INIT_MONEY = "赠送您起始金额1000元，重新进入游戏后重置。";
    public static final String MSG_USER_TURN = "现在是您的轮次，请点击按钮操作。";
    public static final String MSG_USER_BLACK_JACK = "恭喜您获得BlackJack，游戏结束后将赋予您双倍赌注金额";
    public static final String MSG_OTHER_USER_BLACK_JACK = "，获得BlackJack，让我们恭喜他吧！";


    /**按钮文字常量*/
    public static final String BUTTON_READY = "准备";
    public static final String BUTTON_CANCEL_READY = "取消准备";
    public static final String BUTTON_EXIT = "退出";
    public static final String BUTTON_CONNECT_SERVER = "连接服务器";
    public static final String BUTTON_HIT = "要牌";
    public static final String BUTTON_STAND = "停牌";
    public static final String BUTTON_DOUBLE = "加倍";
    public static final String BUTTON_SURRENDER = "认输";
    
    /**Label文字常量*/
    public static final String LABEL_IP = "服务器IP：";
    public static final String LABEL_PORT = "服务器Port:";
    public static final String LABEL_NICKNAME = "昵称：";
    public static final String LABEL_ROOM_ID = "房间号：";
    
    /**系统错误消息*/
    public static final String ERROR_PLAYER_FULL = "系统出错：玩家人数已满！";
    
}
