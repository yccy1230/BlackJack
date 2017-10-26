package listener;

/**
* @description 操作监听接口，负责用户点击回调
* @author Jack Chen
* @date 2017/10/17
*/
public interface OperationListener {

    /**
     * 点击要牌按钮回调
     */
    void onHitClicked();

    /**
     * 点击停牌按钮回调
     */
    void onStandClicked();

    /**
     * 点击加倍按钮回调
     */
    void onDoubleClicked();

    /**
     * 点击投降按钮回调
     */
    void onSurrenderClicked();

    /**
     * 点击准备按钮回调
     * @param bet 所下赌资
     */
    void onReadyClicked(int bet);

    /**
     * 点击取消准备按钮
     */
    void onCancelReadyClicked();
    
    /**
     * 点击连接服务器按钮回调
     */
    void onConnectClicked(String ip, int port, String nickName);

    /**
     * 退出游戏回调
     */
    void onExitClicked();
}
