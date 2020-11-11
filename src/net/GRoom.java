package net;

import java.util.Vector;

public class GRoom {

	Vector playerList = null;		// 게임 진행 중인 플레이어 리스트
	Vector participantList = null;	// 현재 방에 있는 플레이어 리스트
	Vector sessionManList = null;	// 게임 진행 중인 플레이어 리스트
	int sessionManScore[] = null;	// 플레이어의 점수
	
	public String roomId = null;	// 룸 아이디

	// 플레이어가 게임 중인지 여부를 리턴
	public boolean inSession(ServerPlayer player) {
		if((sessionManList.size() == 0) && (playerList.size() == 0)) {
			return false;
		}
		if(sessionManList.contains(player)) {
			// 플레이어가 sessionManList 내에 있을 경우에는 고도리를 치고 있는 중
			return true;
		}
		return false;
	}

}
