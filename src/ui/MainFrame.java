package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import listener.OperationListener;
import listener.UIListener;

/**
* @description 负责主界面展示，实现调用接口，整合JPanel布局
* @author Jack Chen
* @date 2017/10/17
*/
public class MainFrame extends JFrame implements UIListener,OperationListener{

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

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
