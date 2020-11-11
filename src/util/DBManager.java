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

//oracle -> mysql로 수정 작업 해야함

public class DBManager {
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
	public static int signUp(User user) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		int result = 0;
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
			
			result = pstmt.executeUpdate();
			if(result == 1)
				System.out.println("[DBManager][signUp]: 회원가입 성공");
			else
				System.out.println("[DBManager][signUp]: 회원가입 실패");
		} catch(SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}
		return result;
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

	public static boolean login(Login loginInfo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT COUNT(*) FROM user WHERE id = ? AND pw = ?";
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "test");
			pstmt.setString(2, "1234");
			rs = pstmt.executeQuery();
			
			if(rs.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		return false;
	}
}
