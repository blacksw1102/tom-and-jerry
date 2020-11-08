package util;

import java.io.Serializable;

import entity.Login;

public class GameProtocol implements Serializable {
	
	// �������� Ÿ�Կ� ���� ����
	public static final int PT_UNDEFIEND = -1;			// ���������� �������� ���� ����
	public static final int PT_SIGN_UP = 1;
	public static final int PT_REQ_LOGIN = 2;			// �α��� ��û
	public static final int PT_RES_LOGIN = 3;			// �α��� ����
	public static final int PT_LOGIN_RESULT = 4;		// ���� ���
	private static final int LEN_PROTOCOL_TYPE = 1;		// �������� Ÿ���� ����
	private static final int LEN_LOGIN_ID = 20;			// ID ����
	private static final int LEN_LOGIN_PASSWORD = 20;	// PW ����
	private static final int LEN_LOGIN_RESULT = 2;		// �α��� ���� �� ����
	
	protected int protocolType;
	private Object data;	// �������ݰ� �������� ��������� ��ü
	
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
