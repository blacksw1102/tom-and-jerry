package util;

import java.io.Serializable;

import entity.Login;

public class GameProtocol implements Serializable {
	
	// 프로토콜 타입에 관한 변수
	public static final int PT_UNDEFIEND = -1;			// 프로토콜이 지정되지 않은 상태
	public static final int PT_SIGN_UP = 1;
	public static final int PT_REQ_LOGIN = 2;			// 로그인 요청
	public static final int PT_RES_LOGIN = 3;			// 로그인 응답
	public static final int PT_LOGIN_RESULT = 4;		// 인증 결과
	private static final int LEN_PROTOCOL_TYPE = 1;		// 프로토콜 타입의 길이
	private static final int LEN_LOGIN_ID = 20;			// ID 길이
	private static final int LEN_LOGIN_PASSWORD = 20;	// PW 길이
	private static final int LEN_LOGIN_RESULT = 2;		// 로그인 인증 값 길이
	
	protected int protocolType;
	private Object data;	// 프로토콜과 데이터의 저장공간이 객체
	
	public GameProtocol() {
		this(PT_UNDEFIEND);
	}
	
	public GameProtocol(int protocolType) {
		this.protocolType = protocolType;
	}

	public int getProtocol() {
		return protocolType;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
	public Object getData() {
		return data;
	}
}
