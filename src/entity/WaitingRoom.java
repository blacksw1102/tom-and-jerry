package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import net.ServerPlayer;

public class WaitingRoom extends Thread implements Serializable {
	int roomId;				// �� ���̵�
	String roomName;		// �� �̸�
	String roomPassword;	// �� ��й�ȣ
	int maxPlayerCount;
	
	ArrayList<ServerPlayer> playerList = null;		// ���� ���� ���� �÷��̾� ����Ʈ
	ArrayList participantList = null;					// ���� �濡 �ִ� �÷��̾� ����Ʈ
	ArrayList sessionManList = null;					// ���� ���� ���� �÷��̾� ����Ʈ
	int sessionManScore[] = null;					// �÷��̾��� ����
	
	public WaitingRoom(String roomName, String roomPassword) {
		this.roomName = roomName;
		this.roomPassword = roomPassword;
		this.maxPlayerCount = 7;
		this.playerList = new ArrayList<>();
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

}
