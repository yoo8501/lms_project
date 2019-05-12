package lus.bookrequest;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import lus.MainFrame;

public class RequestPanel extends JPanel {
	JPanel p_north, p_center, p_south, p_search;
	JLabel l_notify;
	JScrollPane scroll;
	JTable table;
	JButton bt_search, bt_addReq;
	JTextField t_search;

	RequestTableModel model;
	String[] columnName = { "요청번호", "요청자", "도서명", "요청일", "처리 상태" };
	
	Connection conn;
	MainFrame main;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public RequestPanel(MainFrame main) {
		model = new RequestTableModel();
		model.columnName = this.columnName;
		this.main = main;
		conn = main.getConn();
		
		this.setLayout(new BorderLayout());
		p_north = new JPanel(new BorderLayout());
		l_notify = new JLabel("원하시는 책이 없나요?");
		l_notify.setFont(new Font("HY엽서L", Font.BOLD, 20));
		p_north.add(l_notify, BorderLayout.NORTH);

		p_search = new JPanel();
		t_search = new JTextField();
		t_search.setPreferredSize(new Dimension(400, 30));
		t_search.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					searchRequest();
				}
			}
		});
		
		bt_search = new JButton("검색");
		bt_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchRequest();
			}
		});
		
		bt_addReq = new JButton("요청하기");
		bt_addReq.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayReqDialog();
			}
		});
		
		p_search.add(t_search);
		p_search.add(bt_search);
		p_search.add(bt_addReq);
		p_north.add(p_search);

		add(p_north, BorderLayout.NORTH);

		p_center = new JPanel();
		table = new JTable();
		scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(1000, 400));
		p_center.add(scroll);
		add(p_center);
		showAllRequest();
		setVisible(false);
	}

	public void showAllRequest(){
		rs = RequestQuery.showAllRequests(conn);
		System.out.println("전체");
		setTableModel(rs);
	}
	public void searchRequest() {
		if(t_search.getText().equals("")) {
			showAllRequest();
		}else {
			rs = RequestQuery.searchRequest(conn, t_search.getText());
			System.out.println("검색");
			setTableModel(rs);
		}
	}
	public void setTableModel(ResultSet rs) {
		try {
			rs.last();
			int total = rs.getRow();
			String[][] data = new String[total][columnName.length];
			rs.first();
			for(int i = 0; i<total;i++) {
				data[i][0] = Integer.toString(rs.getInt("req_id"));
				data[i][1] = rs.getString("mem_id");
				data[i][2] = rs.getString("book_name");
				data[i][3] = rs.getString("request_date");
				data[i][4] = rs.getString("request_state");
				rs.next();
			}
			model.data = data;
			
			table.setModel(model);
			table.updateUI();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void displayReqDialog() {
		new RequestDialog(this);
	}
	
}
