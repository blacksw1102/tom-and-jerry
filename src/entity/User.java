package entity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Arrays;

public class User implements Serializable {
	private String id;			// 아이디
	private String pw;			// 비밀번호
	private String confirmPw;	// 비밀번호 확인
	private String nickname;	// 닉네임
	private String email;		// 이메일
	private String birth; 		// 생년월일
	private String tel; 		// 전화번호
	
	public Socket socket;
	public ObjectInputStream in = null;
	public ObjectOutputStream out = null;
	
	public User(String id, String pw, String nickname, String email, String birth, String tel) {
		this.id = id;
		this.pw = pw;
		this.nickname = nickname;
		this.email = email;
		this.birth = birth;
		this.tel = tel;
	}
	
	public User(String id, String nickname) {
		this.id = id;
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "User : " + Arrays.asList(id, pw, nickname, email, birth, tel).toString();
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getConfirmPw() {
		return confirmPw;
	}

	public void setConfirmPw(String confirmPw) {
		this.confirmPw = confirmPw;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Socket getSocket() {
		return socket;
	}
	

}
