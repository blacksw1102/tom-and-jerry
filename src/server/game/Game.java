package server.game;

import java.util.Hashtable;

import server.entity.ServerUser;
import server.net.WaitingRoom;

public class Game extends Thread {

	private WaitingRoom room;
	private ServerUser serverUser;
	private Hashtable<String, ServerUser> userList;
	
	public Game(WaitingRoom room, ServerUser serverUser, Hashtable<String, ServerUser> userList) {
		this.room = room;
		this.serverUser = serverUser;
		this.userList = userList;
		
		this.start();
		
		// 모든 유저들의 정보를 활용해서, 플레이어 리스트를 만든다.
		// 게임 서버를 생성해서, 플레이어 리스트를 같이 보낸다.

	}
	
	@Override
	public void run() {
		System.out.printf("[%s] 작동 중..\n", this.getClass().getName());
		try {
			while(true) {
				Thread.sleep(100);
			}
		} catch (InterruptedException e2) {
		
		} finally {
			System.out.printf("[%s] 종료..\n", this.getClass().getName());

		}
	}
	
}
