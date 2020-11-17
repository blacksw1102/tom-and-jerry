package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import entity.Login;
import entity.User;

public class DBManager {
	/* 원굑 DB 전용 */
	// private static String url = "jdbc:mysql://cloud1.igkim.com:30306/tomnjerry_db";
	//private static String uid = "tomnjerry";
	// private static String pwd = "tnj6jo!";

	private static Connection conn;
	
	static{
        //클래스를 로딩할 때 단 한번 실행돼는 영역
        
        //환경성정 파일을 읽어오기 위한 객체 생성
        Properties pro = new Properties();
        Reader reader;
        try {
            reader = new FileReader("src/config/db.properties");//읽어올 파일 지정
            //설정 파일 로딩하기(인자가 Reader로 된 메서드 선택했음. 필요에 따라 다양한 생성자 사용)
            pro.load(reader);
            
        } catch (FileNotFoundException e1) {
            System.out.println("예외: 지정한 파일을 찾을수없습니다 :" + e1.getMessage());
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
        //설정 파일에서 읽어와 변수에 담기
        String driver = pro.getProperty("driver");
        String url = pro.getProperty("url");
        String uid = pro.getProperty("uid");
        String pwd = pro.getProperty("pwd");
        
        System.out.println("jdbc.properties 에서 불러온 데이터");
        System.out.println("driverName :"+driver);
        System.out.println("url :"+url +" /user :"+uid+" /pwd :"+pwd);
        
        try{
            Class.forName(driver);        
            conn = DriverManager.getConnection(url,uid,pwd);
            System.out.println("connection success");        
        }
        
        catch(ClassNotFoundException e){
            System.out.println("예외: 드라이버로드 실패 :" + e.getMessage());
            e.printStackTrace();
            //printStackTrace() : 메소드 호출관계를 스텍영역을 추적해 올라가면서 
            //어느 메소드를 실행하다가 어떤 예외가 발생했는지의 정보를 출력하는 메소드
        } catch (SQLException e) {
            System.out.println("예외: connection fail :" + e.getMessage());
            e.printStackTrace();
        }
    }
	
	// DB 연결
	public static Connection getConnection() {
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
