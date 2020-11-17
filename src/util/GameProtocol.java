package util;

import java.io.Serializable;

public class GameProtocol implements Serializable {
	
	// 프로토콜 타입에 관한 변수
	public static final int PT_UNDEFIEND = -1;			// 프로토콜이 지정되지 않은 상태
	public static final int PT_REQ_SIGN_UP = 1;			// 회원가입 요청
	public static final int PT_RES_SIGN_UP = 1;			// 회원가입 응답
	public static final int PT_REQ_LOGIN = 2;			// 로그인 요청
	public static final int PT_RES_LOGIN = 3;			// 로그인 응답
	public static final int PT_LOGIN_RESULT = 4;		// 인증 결과
	public static final int PT_RES_USER_LIST = 5;		// 유저리스트 응답
	public static final int PT_SEND_MESSAGE = 6;		// 채팅 메시지 전송
	public static final int PT_ID_DUPLICATE_CHECK = 7;		// 아이디 중복 체크 요청
	
	protected int protocolType;
	private Object data;	// 프로토콜과 데이터의 저장공간이 객체
	
	public GameProtocol() {
		this(PT_UNDEFIEND);
	}
	
	public GameProtocol(int protocolType) {
		this.protocolType = protocolType;
	}
	
	public GameProtocol(int protocolType, Object data) {
		this.protocolType = protocolType;
		this.data = data;
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
