package listener;

/**
* @description 主控制器与UI协定交互接口标准定义
* @author Jack Chen
* @date 2017/10/17
*/
public interface UiListener {
    /**
     * 负责显示系统通知，如：“请等待其他玩家准备”，“请进行操作”
     * @param msg
     */
    void showNotification(String msg);
    //这里我只给出了范例，你们协商往下定义
}
