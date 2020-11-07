package game.entity;

import java.io.Serializable;
import java.util.Arrays;

public class User implements Serializable {
	private String id;			// ���̵�
	private String pw;			// ��й�ȣ
	private String confirmPw;	// ��й�ȣ Ȯ��
	private String nickname;	// �г���
	private String email;		// �̸���
	private String birth; 		// �������
	private String tel; 		// ��ȭ��ȣ
	
	public User(String id, String pw, String nickname, String email, String birth, String tel) {
		this.id = id;
		this.pw = pw;
		this.nickname = nickname;
		this.email = email;
		this.birth = birth;
		this.tel = tel;
	}
	
	@Override
	public String toString() {
		return "User : " + Arrays.asList(id, pw, nickname, email, birth, tel).toString();
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

}
