package lus.bookrequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestQuery {
	public static ResultSet rs;
	public static PreparedStatement pstmt;
	private static StringBuffer sb = new StringBuffer();
	public static ResultSet showAllRequests(Connection conn) {
		sb.delete(0, sb.length());
		sb.append("select r.req_id, m.mem_id, r.book_name, r.request_date, rs.request_state");
		sb.append(" from lib_book_request r, lib_request_state rs, lib_member m");
		sb.append(" where r.member_id=m.mem_id");
		sb.append(" and r.request_state=rs.request_state_id");
		try {
			pstmt = conn.prepareStatement(sb.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public static ResultSet searchRequest(Connection conn, String book_name) {
		sb.delete(0,  sb.length());
		sb.append("select r.req_id, m.mem_id, r.book_name, r.request_date, rs.request_state");
		sb.append(" from lib_book_request r, lib_request_state rs, lib_member m");
		sb.append(" where r.member_id=m.mem_id");
		sb.append(" and r.request_state=rs.request_state_id");
		sb.append(" and r.book_name like '%"+book_name+"%'");
		try {
			pstmt = conn.prepareStatement(sb.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public static void registRequest(Connection conn, String[] data) {
		sb.delete(0,  sb.length());
		sb.append("insert into lib_book_request(req_id, genre, book_name, publisher, writer, request_date, pay, member_id, request_state)");
		sb.append(" values(seq_req.nextval, '"+data[0]+"', '"+data[1]+"', '"+data[2]+"', '"+data[3]+"', default, '"+data[4]+"', '"+data[5]+"', 1)");
		try {
			pstmt = conn.prepareStatement(sb.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String[] getGenre(Connection conn) {
		int total = 0;
		String[] data = null;
		sb.delete(0, sb.length());
		sb.append("select genre from lib_genre");
		try {
			pstmt = conn.prepareStatement(sb.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rs.last();
			total = rs.getRow();
			data = new String[total];
			rs.first();
			for(int i = 0; i<total; i++) {
				data[i] = rs.getString("genre");
				rs.next();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
}
