package lus;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegistDialog extends JDialog{
	JLabel l_id, l_pw, l_pwcheck, l_name, l_birth, l_addr, l_space, l_emailspace, l_tspace, l_phone, l_email;
	JTextField t_id, t_name, t_addr, t_addrdetail, t_phone, t_email, t_emailaddr;
	Choice ch_year, ch_month, ch_date;
	JPasswordField t_pw, t_pwcheck;
	JPanel p_main, p_label, p_edit, p_bt;
	JButton bt_checkid, bt_ok, bt_cancel;
	Dimension d_label = new Dimension(110, 25);
	Dimension d_text = new Dimension(150, 25);
	Dimension d_ch = new Dimension(70, 25);
	LoginDialog parent;
	GregorianCalendar calendar = new GregorianCalendar(Locale.KOREA);

	public RegistDialog(Component parent) {
		p_main = new JPanel();
		p_label = new JPanel();
		p_edit = new JPanel();
		p_bt = new JPanel();
		this.parent = (LoginDialog)parent;
		bt_ok = new JButton("가입");
		bt_cancel = new JButton("취소");
		p_main.setLayout(new BorderLayout());
		
		setLabels();
		setEditors();
		setChoices();
		p_bt.add(bt_ok);
		p_bt.add(bt_cancel);
		p_main.add(p_bt, BorderLayout.SOUTH);


		add(p_main);
		addBtnEvent();
		setModal(true);
		setSize(400, 400);
		setResizable(false);
		setLocationRelativeTo(parent);
		setVisible(true);
	}
	
	
///////////////////////////////////////////////////////////////////////////////////////////////
//									입력 Panel 세팅														//
//////////////////////////////////////////////////////////////////////////////////////////////
	
	public void setEditors() {
		t_id = new JTextField();
		bt_checkid = new JButton("중복 확인");
		t_pw = new JPasswordField();		
		t_pwcheck = new JPasswordField();
		t_name = new JTextField();
		t_addr = new JTextField();
		l_tspace = new JLabel();//공백 라벨
		t_addrdetail = new JTextField(20);
		t_phone = new JTextField(15);
		t_email = new JTextField(10);
		l_emailspace = new JLabel(" @ ");
		t_emailaddr = new JTextField();
		
		ch_year = new Choice();
		ch_month = new Choice();
		ch_date = new Choice();
		
		
		p_edit.setLayout(new FlowLayout(FlowLayout.LEFT));
		p_edit.add(t_id);
		p_edit.add(bt_checkid);
		p_edit.add(t_pw);
		p_edit.add(t_pwcheck);
		p_edit.add(t_name);
		p_edit.add(l_tspace);
		p_edit.add(ch_year);
		p_edit.add(ch_month);
		p_edit.add(ch_date);
		p_edit.add(t_addr);
		p_edit.add(t_addrdetail);
		p_edit.add(t_phone);
		p_edit.add(t_email);
		setChildSize(p_edit, d_text);
		p_edit.add(l_emailspace);
		p_edit.add(t_emailaddr);
		t_emailaddr.setPreferredSize(new Dimension(100, 25));
		p_main.add(p_edit);

		l_tspace.setPreferredSize(new Dimension(80, 25));
		bt_checkid.setPreferredSize(new Dimension(90, 25));
		ch_year.setPreferredSize(d_ch);
		ch_month.setPreferredSize(d_ch);
		ch_date.setPreferredSize(d_ch);

		p_edit.setBorder(BorderFactory.createEmptyBorder(40, 10, 0, 0));
	}

	
///////////////////////////////////////////////////////////////////////////////////////////////
//										Label Panel 세팅												//
//////////////////////////////////////////////////////////////////////////////////////////////

	public void setLabels() {
		l_id = new JLabel("ID *");
		l_pw = new JLabel("비밀번호 *");
		l_pwcheck = new JLabel("비밀번호 확인 *");
		l_name = new JLabel("이름 *");
		l_addr = new JLabel("주소");
		l_space = new JLabel();
		l_birth = new JLabel("생년월일 *");
		l_phone = new JLabel("전화번호");
		l_email = new JLabel("E-Mail");

		p_label.add(l_id);
		p_label.add(l_pw);
		p_label.add(l_pwcheck);
		p_label.add(l_name);
		p_label.add(l_birth);
		p_label.add(l_addr);
		p_label.add(l_space);
		p_label.add(l_phone);
		p_label.add(l_email);
		p_main.add(p_label, BorderLayout.WEST);
		setChildSize(p_label, d_label);
		p_label.setBorder(BorderFactory.createEmptyBorder(40, 40, 0, 0));

	}

	
///////////////////////////////////////////////////////////////////////////////////////////////
//									Birth의 year, month Choice 세팅									//
//////////////////////////////////////////////////////////////////////////////////////////////
	
	public void setChoices() {
		for(int i = 0; i <100 ; i++) {
			ch_year.addItem(Integer.toString(calendar.getWeekYear()-i));
		}
		for(int i = 1; i <=12 ; i++) {
			if(i < 10) {
				ch_month.addItem(0+Integer.toString(i));
			}
			else {
				ch_month.addItem(Integer.toString(i));
			}
		}
		ch_year.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int month = Integer.parseInt(ch_month.getSelectedItem());
				int year = Integer.parseInt(ch_year.getSelectedItem());
				setDateChoice(year, month);
			}
		});
		ch_month.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int month = Integer.parseInt(ch_month.getSelectedItem());
				int year = Integer.parseInt(ch_year.getSelectedItem());
				setDateChoice(year, month);
			}
		});
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////
//						선택된 year, month Choice에 따라 date Choice 세팅						//
//////////////////////////////////////////////////////////////////////////////////////////////
	
	public void setDateChoice(int year, int month) {
		ch_date.removeAll();
		calendar.set(year, month-1, 1);
		for(int i = 1; i<=calendar.getActualMaximum(calendar.DATE);i++) {
			if(i<10) {
				ch_date.addItem(0+Integer.toString(i));
			}
			else {
				ch_date.addItem(Integer.toString(i));
			}
		}
	}

	
///////////////////////////////////////////////////////////////////////////////////////////////
//								해당 컴포넌트 하위 모든 컴포넌트의 Font 설정							//
//////////////////////////////////////////////////////////////////////////////////////////////
	
	public void FontChange(Component component, Font font) {
		component.setFont(font);
		if(component instanceof Container) {
			for(Component child : ((Container)component).getComponents()) {
				FontChange(child, font);
			}
		}
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////
//								해당 컴포넌트 하위 모든 컴포넌트의 Size설정							//
//////////////////////////////////////////////////////////////////////////////////////////////

	public void setChildSize(Component component, Dimension d) {
		component.setPreferredSize(d);
		if(component instanceof Container) {
			for(Component child : ((Container)component).getComponents()) {
				setChildSize(child, d);
			}
		}
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////
//							부착된 Button 이벤트 세팅														//
//////////////////////////////////////////////////////////////////////////////////////////////
	
	public void addBtnEvent() {
		bt_ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(!bt_checkid.isEnabled()) {
					btRegistAction();
				}
				else {
					JOptionPane.showMessageDialog(null, "ID 중복 확인을 해주세요");
				}
			}
		});
		
		bt_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		bt_checkid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkUser(t_id.getText())){
					try {
						int result = JOptionPane.showConfirmDialog(null, "사용 가능한 아이디입니다 사용하시겠습니까?", "중복 확인", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
						if(result == JOptionPane.OK_OPTION) {
							t_id.setEditable(false);
							bt_checkid.setEnabled(false);
						}
					} catch (HeadlessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "이미 사용중인 아이디 입니다");
				}
			}
		});
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////
//							DB에서 중복 유저 ID 검색 (중복=false, 신규=true)						//
//////////////////////////////////////////////////////////////////////////////////////////////
	
	public Boolean checkUser(String user_id) {
		StringBuffer sb = new StringBuffer();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Boolean b = false;
		if(user_id.length()>5) {
			sb.append("select MEM_ID from lib_member");
			sb.append(" where MEM_ID='"+user_id+"'");
			try {
				pstmt = parent.conn.prepareStatement(sb.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = pstmt.executeQuery();
				rs.last();
				if(rs.getRow()==0) {
					b = true;
				}else {
					b = false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				parent.main.db.disconnect(pstmt, rs);
			}
		}else {
			JOptionPane.showMessageDialog(null,"최소 6자 이상의 아이디를 입력하세요");
		}
		return b;
	}

///////////////////////////////////////////////////////////////////////////////////////////////
//		입력된 회원 정보 String[]으로 registUser()에 전달(중복확인 체크, 필수정보 입력 체크)		//
//////////////////////////////////////////////////////////////////////////////////////////////
	
	public void btRegistAction() {
		String pass = new String(t_pw.getPassword());
		String passchk = new String(t_pwcheck.getPassword());
		if(pass.equals(passchk))
		{
			String[] registinfor = new String[7];
			registinfor[0] = t_id.getText();
			registinfor[1] = pass;
			registinfor[2] = t_name.getText();
			registinfor[3] = ch_year.getSelectedItem()+ch_month.getSelectedItem()+ch_date.getSelectedItem();
			registinfor[4] = t_phone.getText();
			registinfor[5] = t_addr.getText()+" "+t_addrdetail.getText();
			registinfor[6] = t_email.getText()+"@"+t_emailaddr.getText();
			
			if(registinfor[1].length()==0 || registinfor[2].length()==0 || ch_date.getSelectedItem()==null || t_email.getText().length()==0 || t_emailaddr.getText().length()==0) {
				JOptionPane.showMessageDialog(null,"입력하지 않은 필수 정보가 있습니다.");
			}
			else {
				registUser(registinfor);
			}
		}
		else {
			JOptionPane.showMessageDialog(null,"입력한 비밀번호가 비밀번호 확인과 다릅니다");
		}
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////
//									받아온 String[] DB에 입력											//
//////////////////////////////////////////////////////////////////////////////////////////////
	
	public void registUser(String[] regist_infor) {
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into lib_member(mem_id, mem_password, mem_name, mem_state, mem_birth, mem_phone, mem_addr, mem_email, mem_regist_date)");
		sb.append(" values('"+regist_infor[0]+"', '"+regist_infor[1]+"', '"+regist_infor[2]+"', 1, '"+regist_infor[3]+"', '"+regist_infor[4]+"', '"+regist_infor[5]+"', '"+regist_infor[6]+"', TO_CHAR(SYSDATE, 'YYYYMMDD'))");
		System.out.println(sb.toString());
		try {
			System.out.println(sb.toString());
			pstmt = parent.conn.prepareStatement(sb.toString());
			int result = pstmt.executeUpdate();
			if(result != 0) {
				dispose();
				JOptionPane.showMessageDialog(null, "등록 완료");
			}
			else {
				JOptionPane.showMessageDialog(null, "등록 실패");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			parent.main.db.disconnect(pstmt);
		}
	}
}
