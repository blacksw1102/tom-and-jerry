package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import net.ServerPlayer;

public class WaitingRoom extends Thread implements Serializable {
	int roomId;				// 룸 아이디
	String roomName;		// 방 이름
	String roomPassword;	// 방 비밀번호
	int maxPlayerCount;		// 방 최대수용인원
	int roomState;			// 방 상태 (0:대기중, 1:게임중)
	
	ArrayList<ServerPlayer> playerList = null;		// 게임 진행 중인 플레이어 리스트
	ArrayList participantList = null;					// 현재 방에 있는 플레이어 리스트
	ArrayList sessionManList = null;					// 게임 진행 중인 플레이어 리스트
	int sessionManScore[] = null;					// 플레이어의 점수
	
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