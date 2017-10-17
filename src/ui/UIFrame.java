package ui;

import listener.OperationListener;
import listener.UiListener;

import javax.swing.*;

/**
* @description 负责主界面展示，实现调用接口，整合JPanel布局
* @author Jack Chen
* @date 2017/10/17
*/
public class UIFrame extends JFrame implements UiListener,OperationListener {

    @Override
    public void showNotification(String msg) {

    }

    @Override
    public void onHitClicked() {

    }

    @Override
    public void onStandClicked() {

    }

    @Override
    public void onDoubleClicked() {

    }

    @Override
    public void onSurrenderClicked() {

    }

    @Override
    public void onReadyClicked(Double bet) {

    }

    @Override
    public void onConnectClicked() {

    }
}
