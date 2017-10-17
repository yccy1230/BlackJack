package ui;

import listener.OperationListener;

import javax.swing.*;

/**
* @description 用户操作面板，负责显示用户操作的按钮，提供相关操作及回调（注意解耦）
* @author Jack Chen
* @date 2017/10/17
*/
public class OperationPanel extends JPanel {

    //回调接口
    private OperationListener operationListener;

}
