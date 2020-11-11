package entity;

import java.io.Serializable;

public class WaitingRoomRow implements Serializable  {
	private String nickname;
	private int playerSatete;
	
	public WaitingRoomRow(String nickname, int playerSatete) {
		this.nickname = nickname;
		this.playerSatete = playerSatete;
	}
	
	@Override
	public String toString() {
		return String.format("[WaitingRoowRow] 닉네임:%s, 상태:%d", nickname, playerSatete);
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getPlayerSatete() {
		return playerSatete;
	}

	public void setPlayerSatete(int playerSatete) {
		this.playerSatete = playerSatete;
	}
	
}
