package lus.booksearch;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import lus.MainFrame;

public class DetailDialog extends JDialog{
	JPanel p_north, p_center, p_south, p_label, p_info;
	JButton bt_reserv, bt_close;
	JLabel l_bookName, l_bookCategory, l_bookId, l_rentalState, l_returnDate, l_publisher, l_writer, l_reserv;
	JTextField t_bookCategory, t_bookId, t_rentalState, t_returnDate, t_publisher, t_writer, t_reserv;
	String[] data;
	GregorianCalendar calendar;
	char[] date = new char[8];
	DetailDialog me;
	public DetailDialog(MainFrame main, String[] data) {
		this.data = data;
		me = this;
		setLayout(new BorderLayout());
		setComponents();
		setSize(500, 500);
		setModal(true);
		setLocationRelativeTo(main);
		setResizable(false);
		setVisible(true);
	}
	public void setComponents() {
		p_north = new JPanel();
		l_bookName = new JLabel(data[2]);
		p_north.add(l_bookName);
		Font f_title = new Font("HY엽서L", Font.BOLD, 24);
		Font f_info = new Font("HY엽서L", Font.BOLD, 14);
		Dimension d_label = new Dimension(100, 50);
		Dimension d_info = new Dimension(350, 50);
		
		p_center = new JPanel(new BorderLayout());
		p_label = new JPanel();
		p_info = new JPanel();
		
		p_center.setLayout(new BorderLayout());
		p_center.add(p_label, BorderLayout.WEST);
		p_center.add(p_info);
		
		l_bookId = new JLabel("도서 번호");
		t_bookId = new JTextField(data[0]);
		t_bookId.setEditable(false);
		p_label.add(l_bookId);
		p_info.add(t_bookId);
		
		l_bookCategory = new JLabel("분류 : ");
		t_bookCategory = new JTextField(data[1]);
		t_bookCategory.setEditable(false);
		p_label.add(l_bookCategory);
		p_info.add(t_bookCategory);
		
		l_publisher = new JLabel("출판사 : ");
		t_publisher = new JTextField(data[3]);
		t_publisher.setEditable(false);
		p_label.add(l_publisher);
		p_info.add(t_publisher);
		
		l_writer = new JLabel("저자 : ");
		t_writer = new JTextField(data[4]);
		t_writer.setEditable(false);
		p_label.add(l_writer);
		p_info.add(t_writer);
	
		l_rentalState = new JLabel("대여 상태 : ");
		t_rentalState = new JTextField(data[5]);
		t_rentalState.setEditable(false);
		p_label.add(l_rentalState);
		p_info.add(t_rentalState);
		
		if(data.length>6) {
			calendar = new GregorianCalendar();
			calendar.set(Integer.parseInt(data[6].substring(0, 4)), Integer.parseInt(data[6].substring(4,6))-1, Integer.parseInt(data[6].substring(6,8))+15);
			
			
			l_returnDate = new JLabel("반납 예정일 : ");
			t_returnDate = new JTextField(calendar.get(calendar.YEAR)+"년 "+(calendar.get(calendar.MONTH)+1)+"월 "+calendar.get(calendar.DATE)+"일");
			t_returnDate.setEditable(false);
			p_label.add(l_returnDate);
			p_info.add(t_returnDate);
		}
		
		if(data.length>7) {
			l_reserv = new JLabel("예약 인원 : ");
			t_reserv = new JTextField(data[7]);
			t_reserv.setEditable(false);
			p_label.add(l_reserv);
			p_info.add(t_reserv);
		}
		
		p_south = new JPanel();
		bt_reserv = new JButton("예약");
		bt_reserv.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		bt_close = new JButton("닫기");
		bt_close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				me.dispose();
			}
		});
		
		p_south.add(bt_reserv);
		p_south.add(bt_close);
		
		FontChange(p_north, f_title);
		FontChange(p_center, f_info);
		SizeChange(p_label, d_label);
		SizeChange(p_info, d_info);
		FontChange(p_south, f_info);
		p_north.setPreferredSize(new Dimension(500, 70));
		p_south.setPreferredSize(new Dimension(500, 50));
		
		add(p_north, BorderLayout.NORTH);
		add(p_center);
		add(p_south, BorderLayout.SOUTH);
	}
	
	public void FontChange(Component component, Font font) {
		component.setFont(font);
		if(component instanceof Container) {
			for(Component child : ((Container)component).getComponents()) {
				FontChange(child, font);
			}
		}
	}
	public void SizeChange(Component component, Dimension d) {
		component.setPreferredSize(d);
		if(component instanceof Container) {
			for(Component child : ((Container)component).getComponents()) {
				SizeChange(child, d);
			}
		}
	}
}
