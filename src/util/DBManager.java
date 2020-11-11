package util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import entity.Login;
import entity.User;

public class DBManager {
	/* 원굑 DB 전용 */
//	private static String url = "jdbc:mysql://cloud1.igkim.com:30306/tomnjerry_db";
//	private static String uid = "tomnjerry";
//	private static String pwd = "tnj6jo!";

	/* 로컬 DB 전용 */
	private static String url = "jdbc:mysql://localhost/tom_n_jerry";
	private static String uid = "admin";
	private static String pwd = "admin";

	// DB 연결
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, uid, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	// 회원가입
	public static boolean signUp(User user) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		boolean isSuccess = false;
		try {
			String sql = "INSERT INTO user (id, pw, nickname, email, "
					+ "birth, tel) VALUES (?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getId());
			pstmt.setString(2, user.getPw());
			pstmt.setString(3, user.getNickname());
			pstmt.setString(4, user.getEmail());
			pstmt.setDate(5, new Date(new SimpleDateFormat("yyyy-MM-dd")
					.parse(user.getBirth()).getTime()));
			pstmt.setString(6, user.getTel());
			
			if(pstmt.executeUpdate() == 1)
				isSuccess = true;
				
		} catch(SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}
		return isSuccess;
	}

	// DB 연결 종료1
	public static void close(Connection conn, PreparedStatement pstmt,
			ResultSet rset) {
		if (rset != null) {
			try {
				rset.close();
			} catch (SQLException e) {
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

	// DB 연결 종료2
	public static void close(Connection conn, PreparedStatement pstmt) {

		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

	public static User login(Login loginInfo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;
		try {
			String sql = "SELECT id, nickname FROM user WHERE id = ? AND pw = ?";
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginInfo.getId());
			pstmt.setString(2, loginInfo.getPw());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				user = new User(rs.getString(1), rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		return user;
	}

	public static boolean checkIdDuplicate(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean isSuccess = false;
		
		try {
			String sql = "SELECT COUNT(*) FROM user WHERE id = ?";
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next() && rs.getInt(1) == 0)
				isSuccess = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		
		return isSuccess;
	}
}
