package lus.booksearch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UseQuery {
	public static PreparedStatement pstmt;
	public static ResultSet rs;
	
	
	public static ResultSet searchBook(Connection conn, String name) {
		StringBuffer sb = new StringBuffer();
		sb.append("select b.book_id, g.genre, b.book_name, p.publisher, b.writer, r.rental_state");
		sb.append(" from lib_book b, lib_genre g, lib_publisher p, lib_book_detail d, lib_rental_state r");
		sb.append(" where b.book_name like '%"+name+"%'");//검색된 도서
		sb.append(" and b.genre=g.genre_id"); //분류
		sb.append(" and b.publisher=p.publisher_id"); //출판사
		sb.append(" and b.book_id=d.book_id");
		sb.append(" and d.rental_state=r.rental_state_id"); //대여 상태
		
		try {
			pstmt = conn.prepareStatement(sb.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public static ResultSet getBookDetail(Connection conn, String book_id, String state) {
		int id = Integer.parseInt(book_id);
		StringBuffer sb = new StringBuffer();
		if(state.equals("대여 중")||state.equals("연체")) {
			sb.append("select r.rental_date");
			sb.append(" from lib_book_detail d, lib_rental_table r");
			sb.append(" where d.book_id="+id);
			sb.append(" and d.book_id=r.book_id");
			try {
				pstmt = conn.prepareStatement(sb.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = pstmt.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else rs = null;
		return rs;
	}
	
	public static String searchWhoRent(Connection conn, int book_id) {
		String name = null;
		StringBuffer sb = new StringBuffer();
		sb.append("select m.mem_name from lib_rental_table r, lib_member m");
		sb.append(" where r.book_id="+book_id);
		sb.append(" and r.member_id=m.mem_id");
		try {
			pstmt = conn.prepareStatement(sb.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rs.first();
			if(rs.next()) {
				name = rs.getString("mem_name");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}
	
	public static ResultSet showAllBooks(Connection conn) {
		StringBuffer sb = new StringBuffer();
		sb.append("select b.book_id, g.genre, b.book_name, p.publisher, b.writer, r.rental_state");
		sb.append(" from lib_book b, lib_genre g, lib_publisher p, lib_book_detail d, lib_rental_state r");
		sb.append(" where b.genre=g.genre_id"); //분류
		sb.append(" and b.publisher=p.publisher_id"); //출판사
		sb.append(" and b.book_id=d.book_id");
		sb.append(" and d.rental_state=r.rental_state_id"); //대여 상태
		sb.append(" order by book_name asc");
		try {
			pstmt = conn.prepareStatement(sb.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
}
