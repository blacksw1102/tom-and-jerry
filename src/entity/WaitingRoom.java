package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import net.ServerPlayer;

public class WaitingRoom extends Thread implements Serializable {
	int roomId;				// �� ���̵�
	String roomName;		// �� �̸�
	String roomPassword;	// �� ��й�ȣ
	int maxPlayerCount;		// �� �ִ�����ο�
	int roomState;			// �� ���� (0:�����, 1:������)
	
	ArrayList<ServerPlayer> playerList = null;		// ���� ���� ���� �÷��̾� ����Ʈ
	ArrayList participantList = null;					// ���� �濡 �ִ� �÷��̾� ����Ʈ
	ArrayList sessionManList = null;					// ���� ���� ���� �÷��̾� ����Ʈ
	int sessionManScore[] = null;					// �÷��̾��� ����
	
	public WaitingRoom(String roomName, String roomPassword) {
		this.roomName = roomName;
		this.roomPassword = roomPassword;
		this.maxPlayerCount = 7;
		this.playerList = new ArrayList<>();
		this.roomState = 0;
	}
	
	@Override
	public void run() {
		super.run();
		// TODO Auto-generated method stub
	}
	
	public int addPlayer(ServerPlayer player) {
		if(playerList.size() < maxPlayerCount) {
			playerList.add(player);
			return 1;
		} else
			return 0;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomPassword() {
		return roomPassword;
	}

	public void setRoomPassword(String roomPassword) {
		this.roomPassword = roomPassword;
	}

	public int getMaxPlayerCount() {
		return maxPlayerCount;
	}

	public void setMaxPlayerCount(int maxPlayerCount) {
		this.maxPlayerCount = maxPlayerCount;
	}
	
	public int getCurrentPlayerCount() {
		return playerList.size();
	}

	public int getRoomState() {
		return roomState;
	}

	public void setRoomState(int roomState) {
		this.roomState = roomState;
	}
	
}
