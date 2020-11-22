package client.gui.component;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import server.entity.ServerUser;
import server.util.GameProtocol;

public class WaitingRoom extends Thread implements Serializable {
	int roomId;				// 룸 아이디
	String roomName;		// 방 이름
	String roomPassword;	// 방 비밀번호
	int maxPlayerCount;		// 방 최대수용인원
	int roomState;			// 방 상태 (0:대기중, 1:게임중)
	
	ArrayList<ServerUser> playerList = null;		// 게임 진행 중인 플레이어 리스트
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
		
	}
	
	public int addPlayer(ServerUser player) {
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

	// 대기방에 있는 유저 리스트를 클라이언트에게 보낸다.
	public void broadcastUserList() {
		// 유저 리스트를 만든다.
		List<WaitingRoomRow> rowList = new ArrayList<>();
		for(ServerUser player : playerList) {
			WaitingRoomRow row = new WaitingRoomRow(player.getNickname(), player.getPlayerState());
			rowList.add(row);
		}
		System.out.println("size : " + rowList.size());
		
		// 유저 리스트를 모든 유저들에게 보낸다.
		for(ServerUser player : playerList) {
			GameProtocol protocol = new GameProtocol(GameProtocol.PT_BROADCAST_USER_LIST_IN_WAITING_ROOM, rowList);
			try {
				player.getOut().writeObject(protocol);
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
	}
	
}
