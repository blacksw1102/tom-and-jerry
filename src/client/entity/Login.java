package client.entity;

import java.io.Serializable;

public class Login implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String pw;

	public Login(String id, String pw) {
		this.id = id;
		this.pw = pw;
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
	
	@Override
	public String toString() {
		return String.format("id:%s, pw:%s", id, pw);
	}
}
