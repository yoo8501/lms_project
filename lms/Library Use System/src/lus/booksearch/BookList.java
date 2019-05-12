package lus.booksearch;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import lus.MainFrame;

public class BookList extends JPanel{
	
	JPanel p_center;
	JTable table;
	JScrollPane scroll;
	
	JPanel p_north;
	JTextField t_search;
	JButton bt_search;
	
	/////////////0210 조재훈 - 생성/////////////
	UseTableModel model;
	Connection conn;
	MainFrame main;
	ResultSet rs;
	
	String[] columnName = {"도서 번호", "분류", "도서 명", "출판사", "저자", "대여 상태"};
	////////////////////////////////////////
	
	
	Dimension d = new Dimension(1000,50);
	Dimension d_search = new Dimension(400,30);
	
	public BookList(MainFrame main) {
		this.setLayout(new BorderLayout());
		create();
		this.main = main;
		conn = main.getConn();
		
		add(p_north, BorderLayout.NORTH);
		p_north.setPreferredSize(d);
		
		p_north.add(t_search);
		t_search.setPreferredSize(d_search);
		p_north.add(bt_search);
		
		add(p_center);
	
		//버튼과 리스너 연결
		
		///////////////////////0210 조재훈 - 추가///////////////////////
		t_search.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					if(t_search.getText().equals("")) 
					{
						init();
					}
					else 
					{
						searchBook();
					}
					table.updateUI();
				}
			}
		});
		bt_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(t_search.getText().equals("")) {
					init();
				}
				else {
					searchBook();
				}
				table.updateUI();
			}
		});
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					getBookDetail(table.getSelectedRow());
				}
			}
		});
		////////////////////////////////////////////////////////////

		model = new UseTableModel();
		init();
		setVisible(true);

	}
	public void create(){
		p_center = new JPanel();
		table = new JTable();
		scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(1000, 450));
		p_center.add(scroll);
		
		p_north = new JPanel();
		t_search= new JTextField();
		bt_search=new JButton("검색");
	}
	/////////////////////0210 조재훈 - 생성/////////////////////
	/////////////////////초기 화면 및 검색어 없을시 모든 책 리스트를 테이블에 출력하는 메소드/////////////////
	public void init() {
		rs = UseQuery.showAllBooks(conn);
		setModelData(rs);
		table.setModel(model);
		table.updateUI();
		System.out.println("완료2");
	}
	
	/////////////////////검색어 입력후 검색시 해당 검색어에 관련된 책을 테이블에 출력하는 메소드///////////////
	public void searchBook() {
		rs = UseQuery.searchBook(conn, t_search.getText());
		setModelData(rs);
		table.setModel(model);
		System.out.println("완료");
	}
	
	///////////////////////테이블 모델의 data를 세팅  하는 메소드////////////////////////
	public void setModelData(ResultSet rs) {
		try {
			rs.last();
			int total = rs.getRow();
			String[][] data = new String[total][columnName.length];
			rs.first();
			for(int i = 0 ; i < total ; i++) {
				data[i][0] = Integer.toString(rs.getInt("book_id"));
				data[i][1] = rs.getString("genre");
				data[i][2] = rs.getString("book_name");
				data[i][3] = rs.getString("publisher");
				data[i][4] = rs.getString("writer");
				data[i][5] = rs.getString("rental_state");	
				rs.next();
			}
			model.columnName = columnName;
			model.data = data;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			main.db.disconnect(UseQuery.pstmt, UseQuery.rs);
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	//////////////////////테이블의 row클릭시 detail을 불러오는 메소드////////////////////
	public void getBookDetail(int row) {
		rs = UseQuery.getBookDetail(conn, model.getValueAt(row, 0).toString(), model.getValueAt(row, 5).toString());
		try {
			if(rs!=null) {
				ResultSetMetaData rsmd = rs.getMetaData();
				int length = model.columnName.length+rsmd.getColumnCount();
				String[] data = new String[length];
				for(int i = 0 ; i <model.columnName.length ; i++) {
					data[i] = model.getValueAt(row, i).toString();
				}
				rs.first();
				data[6] = rs.getString("rental_date");		
				new DetailDialog(main, data);
			}else {
				String[] data = new String[6];
				for(int i = 0 ; i <model.columnName.length ; i++) {
					data[i] = model.getValueAt(row, i).toString();
				}
				new DetailDialog(main, data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}







