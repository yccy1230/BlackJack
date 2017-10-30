package ui;

import entity.ResultDetail;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class RankDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private String []title = {"序号","玩家", "点数", "起始赌注","余额","胜负"};
    private Object [][]data;

	public RankDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 414, 208);
		contentPanel.add(scrollPane);
		table = new JTable();
		model = new DefaultTableModel(data,title){
            @Override
            public Class getColumnClass(int column) {
                Class returnValue;
                if ((column >= 0) && (column < getColumnCount())) {
                    returnValue = getValueAt(0, column).getClass();
                } else {
                    returnValue = Object.class;
                }
                return returnValue;
            }
        };
		table.setModel(model);
		
		//启用排序
		RowSorter<TableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
		scrollPane.setViewportView(table);
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RankDialog.this.dispose();
			}
		});
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	}
	
    /**
     * 初始化 RankList
     */
    public void initResult(List<ResultDetail> resultDetails){
		for (ResultDetail resultDetail: resultDetails) {
			Object []row = {model.getRowCount()+1,resultDetail.getNickName(),
					resultDetail.getFaceValue(),resultDetail.getBet(),
					resultDetail.getProperty(),resultDetail.getStatus()};
			model.addRow(row);
		}
        table.validate();
    }
}
