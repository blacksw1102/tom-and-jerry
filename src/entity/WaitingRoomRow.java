package entity;

import java.io.Serializable;

public class WaitingRoomRow implements Serializable  {
	private String nickname;
	private int playerState;
	
	public WaitingRoomRow(String nickname, int playerSatete) {
		this.nickname = nickname;
		this.playerState = playerSatete;
	}
	
	@Override
	public String toString() {
		return String.format("[WaitingRoowRow] 닉네임:%s, 상태:%d", nickname, playerState);
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getPlayerSatete() {
		return playerState;
	}

	public void setPlayerSatete(int playerSatete) {
		this.playerState = playerSatete;
	}
	
}
