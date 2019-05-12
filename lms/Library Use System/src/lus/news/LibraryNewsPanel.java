package lus.news;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import lus.MainFrame;

public class LibraryNewsPanel extends JPanel{
	JPanel p_lib, p_user, p_newBooks, p_readKing, p_userInfo, p_rentalRecord, p_center;
	JLabel l_newBooks, l_rentalRecord, l_readKing;
	JTable table_newBook, table_rentRec, table_readKing;
	JScrollPane scroll_book, scroll_king, scroll_rent;
	MainFrame main;
	public LibraryNewsPanel(MainFrame main) {
		setLayout(new BorderLayout());
		p_lib = new JPanel(new BorderLayout());		
		
		p_newBooks = new JPanel(new BorderLayout());
		l_newBooks = new JLabel("이달의 신간 도서");
		table_newBook = new JTable();
		scroll_book = new JScrollPane(table_newBook);
		scroll_book.setPreferredSize(new Dimension(900, 250));
		p_newBooks.add(l_newBooks, BorderLayout.NORTH);
		p_newBooks.add(scroll_book);
		
		p_readKing = new JPanel(new BorderLayout());
		l_readKing = new JLabel("이달의 독서왕");
		table_readKing = new JTable();
		scroll_king = new JScrollPane(table_readKing);
		scroll_king.setPreferredSize(new Dimension(900, 250));
		p_readKing.add(l_readKing, BorderLayout.NORTH);
		p_readKing.add(scroll_king);
		
		p_lib.add(p_newBooks, BorderLayout.NORTH);
		p_lib.add(p_readKing);
		
		p_user = new JPanel(new BorderLayout());
		p_userInfo = new JPanel();
		
		p_rentalRecord = new JPanel(new BorderLayout());
		l_rentalRecord = new JLabel("나의 도서 대출 기록");
		table_rentRec = new JTable();
		scroll_rent = new JScrollPane(table_rentRec);
		scroll_rent.setPreferredSize(new Dimension(250, 300));
		p_rentalRecord.add(scroll_rent);
		p_rentalRecord.add(l_rentalRecord, BorderLayout.NORTH);
		
		p_user.add(p_userInfo, BorderLayout.NORTH);
		p_user.add(p_rentalRecord, BorderLayout.SOUTH);
		
		p_center = new JPanel();
		p_center.setPreferredSize(new Dimension(30, 400));
		add(p_center);
		add(p_lib, BorderLayout.WEST);
		add(p_user, BorderLayout.EAST);
	}
}
