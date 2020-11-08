package net;

import java.util.Vector;

public class GRoom {

	Vector playerList = null;		// ���� ���� ���� �÷��̾� ����Ʈ
	Vector participantList = null;	// ���� �濡 �ִ� �÷��̾� ����Ʈ
	Vector sessionManList = null;	// ���� ���� ���� �÷��̾� ����Ʈ
	int sessionManScore[] = null;	// �÷��̾��� ����
	
	public String roomId = null;	// �� ���̵�

	// �÷��̾ ���� ������ ���θ� ����
	public boolean inSession(ServerPlayer player) {
		if((sessionManList.size() == 0) && (playerList.size() == 0)) {
			return false;
		}
		if(sessionManList.contains(player)) {
			// �÷��̾ sessionManList ���� ���� ��쿡�� ������ ġ�� �ִ� ��
			return true;
		}
		return false;
	}

}
