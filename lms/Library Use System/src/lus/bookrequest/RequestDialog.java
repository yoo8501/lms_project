package lus.bookrequest;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import lus.db.UserInformation;

public class RequestDialog extends JDialog{
	JPanel p_label, p_input, p_south, p_wrapper;
	JLabel l_publisher, l_genre, l_writer, l_book_name, l_cost;
	JTextField t_publisher, t_writer, t_book_name, t_cost;
	Choice ch_genre;
	RequestPanel parent;
	JButton bt_ok, bt_cancel;
	Dimension d_label, d_input;
	public RequestDialog(RequestPanel parent) {
		setLayout(new BorderLayout());
		Font f_label = new Font("HY엽서L", Font.BOLD, 14);
		p_wrapper = new JPanel();
		p_wrapper.setLayout(new BorderLayout());
		
		d_label = new Dimension(100, 50);
		d_input = new Dimension(300, 50);
		this.parent = parent;
		p_label = new JPanel();
		l_book_name = new JLabel("도서 명 : ");
		l_book_name.setHorizontalAlignment(SwingConstants.RIGHT);
		l_genre = new JLabel("분류 : ");
		l_genre.setHorizontalAlignment(SwingConstants.RIGHT);
		l_publisher = new JLabel("출판사 : ");
		l_publisher.setHorizontalAlignment(SwingConstants.RIGHT);
		l_writer = new JLabel("저자 : ");
		l_writer.setHorizontalAlignment(SwingConstants.RIGHT);
		l_cost = new JLabel("도서 가격 : ");
		l_cost.setHorizontalAlignment(SwingConstants.RIGHT);

				
		p_label.add(l_book_name);
		p_label.add(l_genre);
		p_label.add(l_publisher);
		p_label.add(l_writer);
		p_label.add(l_cost);

		FontChange(p_label, f_label);
		setChildSize(p_label, d_label);
		
		p_input = new JPanel();
		t_book_name = new JTextField();
		ch_genre = new Choice();
		setChoices();
		t_publisher = new JTextField();
		t_writer = new JTextField();
		t_cost = new JTextField();
		
		p_input.add(t_book_name);
		p_input.add(ch_genre);
		p_input.add(t_publisher);
		p_input.add(t_writer);
		p_input.add(t_cost);
		setChildSize(p_input, d_input);
		ch_genre.setSize(d_input);
		
		p_south = new JPanel();
		bt_ok = new JButton("확인");
		bt_ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registRequest();
				dispose();
			}
		});
		bt_cancel = new JButton("취소");
		bt_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		p_south.add(bt_ok);
		p_south.add(bt_cancel);
		
		p_wrapper.add(p_label, BorderLayout.WEST);
		p_wrapper.add(p_input, BorderLayout.EAST);
		p_wrapper.add(p_south, BorderLayout.SOUTH);
		add(p_wrapper);
		p_wrapper.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 50));
		setModal(true);
		setSize(500, 500);
		setLocationRelativeTo(parent);
		setVisible(true);
	}
	public void registRequest() {
		String[] data = new String[7];
		data[0] = Integer.toString(ch_genre.getSelectedIndex()+1);
		data[1] = t_book_name.getText();
		data[2] = t_publisher.getText();
		data[3] = t_writer.getText();
		data[4] = t_cost.getText();
		data[5] = UserInformation.getCurrentUserID();
		RequestQuery.registRequest(parent.conn, data);
		parent.showAllRequest();
	}
	
	public void FontChange(Component component, Font font) {
		component.setFont(font);
		if(component instanceof Container) {
			for(Component child : ((Container)component).getComponents()) {
				FontChange(child, font);
			}
		}
	}
	
	public void setChildSize(Component component, Dimension d) {
		component.setPreferredSize(d);
		if(component instanceof Container) {
			for(Component child : ((Container)component).getComponents()) {
				setChildSize(child, d);
			}
		}
	}
	public void setChoices() {
		String[] data = RequestQuery.getGenre(parent.conn);
		for(int i = 0; i <data.length; i++) {
			ch_genre.add(data[i]);
		}
	}
}
