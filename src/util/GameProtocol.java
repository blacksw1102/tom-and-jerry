package util;

import java.io.Serializable;

public class GameProtocol implements Serializable {
	
	// �������� Ÿ�Կ� ���� ����
	public static final int PT_UNDEFIEND = -1;			// ���������� �������� ���� ����
	public static final int PT_REQ_SIGN_UP = 1;			// ȸ������ ��û
	public static final int PT_REQ_LOGIN = 2;			// �α��� ��û
	public static final int PT_RES_LOGIN = 3;			// �α��� ����
	public static final int PT_LOGIN_RESULT = 4;		// ���� ���
	public static final int PT_RES_USER_LIST = 5;		// ��������Ʈ ����
	public static final int PT_SEND_MESSAGE = 6;		// ä�� �޽��� ����
	public static final int PT_ID_DUPLICATE_CHECK = 7;	// ���̵� �ߺ� üũ ��û
	public static final int PT_REQ_CREATE_WAIT_ROOM = 8;	// ���� ����
	public static final int PT_RES_CREATE_WAIT_ROOM = 9;// ��� �� ���� ����
	
	protected int protocolType;
	private Object data;	// �������ݰ� �������� ��������� ��ü
	
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
