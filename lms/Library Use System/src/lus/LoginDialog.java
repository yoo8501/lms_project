package lus;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import lus.db.ConnectionManager;
import lus.db.UserInformation;

public class LoginDialog extends JDialog{
	JPanel p_input, p_bt, p_logo;
	public JTextField t_id; 
	public JPasswordField t_pw;
	JLabel la_id, la_pw;
	JButton bt_login, bt_cancel, bt_regist;
	String user_id, user_pw;
	Connection conn;
	MainFrame main;
	LoginDialog me;
	public LoginDialog(MainFrame main) {
		super(main, true);
		conn = main.getConn();
		this.main = main;
		me = this;
		la_id = new JLabel("ID : ");
		la_id.setPreferredSize(new Dimension(100, 25));
		t_id = new JTextField();
		t_id.setPreferredSize(new Dimension(200, 25));
		t_id.setHorizontalAlignment(SwingConstants.CENTER);
		t_id.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					t_pw.setFocusable(false);
					t_id.setFocusable(false);
					getUserInform();
					loginUser();
					t_id.setFocusable(true);
					t_pw.setFocusable(true);
				}
			}
		});
		la_pw = new JLabel("Password : ");
		la_pw.setPreferredSize(new Dimension(100, 25));
		t_pw = new JPasswordField();
		t_pw.setPreferredSize(new Dimension(200, 25));
		t_pw.setHorizontalAlignment(SwingConstants.CENTER);
		t_pw.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					t_pw.setFocusable(false);
					t_id.setFocusable(false);
					getUserInform();
					loginUser();
					t_pw.setFocusable(true);
					t_id.setFocusable(true);;
				}
			}
		});
		bt_login = new JButton("로그인");
		
		bt_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getUserInform();
				loginUser();
			}
		});
		bt_cancel = new JButton("취소");
		bt_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		bt_regist = new JButton("회원가입");
		bt_regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new RegistDialog(me);
				setModal(false);
			}
		});
		
		p_logo = new JPanel();
		p_logo.setPreferredSize(new Dimension(400, 25));
		p_input = new JPanel();
		p_bt = new JPanel();
		
		p_input.add(la_id);
		p_input.add(t_id);
		p_input.add(la_pw);
		p_input.add(t_pw);
		
		p_bt.add(bt_login);
		p_bt.add(bt_cancel);
		p_bt.add(bt_regist);
		
		add(p_logo, BorderLayout.NORTH);
		add(p_input);
		add(p_bt, BorderLayout.SOUTH);
		
		setSize(400, 200);
		setLocationRelativeTo(main);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				main.close();
			}
		});
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void close() {
		main.db.disconnect(conn);
		System.exit(0);
	}
	
	public void getUserInform() {
		user_id = this.t_id.getText();
		user_pw = "";
		char[] pw_arr = this.t_pw.getPassword();
		for(int i = 0 ; i < pw_arr.length ; i++) {
			user_pw += pw_arr[i];
		}
	}
	
	public void loginUser() {
		StringBuffer sb = new StringBuffer();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		sb.append("select MEM_ID, MEM_PASSWORD, MEM_NAME, MEM_STATE from lib_member");
		sb.append(" where MEM_ID='"+user_id+"'");
		sb.append(" and MEM_PASSWORD='"+user_pw+"'");
		System.out.println(sb);
		try {
			pstmt = conn.prepareStatement(sb.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rs.last();
			if(rs.getRow()==0) {
				JOptionPane.showMessageDialog(this,"등록되지 않은 사용자 입니다");	
			}
			else if(rs.getRow()==1){
				int member_state = rs.getInt("mem_state");
				if(member_state == 1 || member_state == 2 || member_state == 3) {
					System.out.println("로그인 성공");
					String user_name = rs.getString("mem_name");
					String user_id = rs.getString("mem_id");
					
					String user_pw = rs.getString("mem_password");
					new UserInformation(member_state, user_name, user_id, user_pw);
					this.setVisible(false);
					main.toFront();
					main.setTitle(user_name+"님 접속중");
				}
				if(member_state == 4) {
					JOptionPane.showMessageDialog(null, "탈퇴된 회원입니다.");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			main.db.disconnect(pstmt, rs);
		}
	}
}
