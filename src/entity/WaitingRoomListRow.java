package entity;

import java.io.Serializable;

public class WaitingRoomListRow implements Serializable {
	private int roomId;
	private String roomName;
	private int currentPlayerCount;
	private int maxPlayerCount;
	private int roomState;

	public WaitingRoomListRow(int roomId, String roomName, int currentPlayerCount, 
			int maxPlayerCount, int roomState) {
		this.roomId = roomId;
		this.roomName = roomName;
		this.currentPlayerCount = currentPlayerCount;
		this.maxPlayerCount = maxPlayerCount;
		this.roomState = roomState;
	}

	@Override
	public String toString() {
		return String.format("[WaitingRoomListRow] ���ȣ:%d, ���̸�:%s, ���ο�:%d/%d, �����:%d", 
				roomId, roomName, currentPlayerCount, maxPlayerCount, roomState);
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

	public int getCurrentPlayerCount() {
		return currentPlayerCount;
	}

	public void setCurrentPlayerCount(int currentPlayerCount) {
		this.currentPlayerCount = currentPlayerCount;
	}

	public int getMaxPlayerCount() {
		return maxPlayerCount;
	}

	public void setMaxPlayerCount(int maxPlayerCount) {
		this.maxPlayerCount = maxPlayerCount;
	}

	public int getRoomState() {
		return roomState;
	}

	public void setRoomState(int roomState) {
		this.roomState = roomState;
	}
	
}
