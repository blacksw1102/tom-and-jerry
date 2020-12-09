package server.game;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;

import server.entity.Position;
import server.entity.ServerUser;
import server.net.WaitingRoom;
import server.util.GameProtocol;

public class Game extends Thread {

	private WaitingRoom room;
	private ServerUser serverUser;
	private Hashtable<String, ServerUser> userList;
	
	private static final int SELECTED_TOM_ROLE = 1;
	private static final int SELECTED_JERRY_ROLE = 2;
	ArrayList<Integer> roles = new ArrayList<>();
	ArrayList<Position> positions = new ArrayList<>();
	
	
	public Game(WaitingRoom room, ServerUser serverUser, Hashtable<String, ServerUser> userList) {
		this.room = room;
		this.serverUser = serverUser;
		this.userList = userList;

		// 역할 초기화
		roles.add(SELECTED_JERRY_ROLE);
		//roles.add(SELECTED_JERRY_ROLE);
		//roles.add(SELECTED_JERRY_ROLE);
		//roles.add(SELECTED_TOM_ROLE);
		
		// 초기 좌표 초기화
		//positions.add(new Position(96, 128));
		//positions.add(new Position(128, 1952));
		//positions.add(new Position(2016, 160));
		positions.add(new Position(1952, 1984));
		
		
		try {
			broadcastPlayersInfo();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.start();
		// 모든 유저들의 정보를 활용해서, 플레이어 리스트를 만든다.
		
		// 게임 서버를 생성해서, 플레이어 리스트를 같이 보낸다.

	}
	
	private void broadcastPlayersInfo() throws IOException {
		// 분배할 역할 섞기
		Collections.shuffle(roles);
		Collections.shuffle(positions);
		
		// 각 플레이어들의 역할을 정함
		int i = 0;
		LinkedList<String> datas = new LinkedList<String>();
		Enumeration<ServerUser> elements = userList.elements();
		while(elements.hasMoreElements()) {
			ServerUser serverUser = elements.nextElement();

			int role = roles.get(i);
			int x = positions.get(i).getX();
			int y = positions.get(i).getY();
			String nickname = serverUser.getNickname();
			String data = String.format("%d %d %d %s", role, x, y, nickname);
			datas.add(data);
			i++;
		}
		
		// 플레이어들에게 각 플레이어의 정보를 브로드 캐스팅
		Enumeration<ServerUser> toElements = userList.elements();
		while(toElements.hasMoreElements()) {
			ServerUser toServerUser = toElements.nextElement();
			GameProtocol protocol = new GameProtocol(
					GameProtocol.PT_BROADCAST_PLAYERS_INFO, datas);
			toServerUser.getOut().writeObject(protocol);
		}
	}

	@Override
	public void run() {
		GameProtocol protocol = null;
		
		System.out.printf("[%s] 작동 중..\n", this.getClass().getName());
		try {
			while(true) {
				//Thread.sleep(100);
				
				Enumeration<ServerUser> e = userList.elements();
				while(e.hasMoreElements()) {
					serverUser = e.nextElement();
					try {
						protocol = (GameProtocol) serverUser.getIn().readObject();
						switch(protocol.getProtocol()) {
							case GameProtocol.PT_PLAYER_MOVE:
								broadcastGameInfo(protocol);
								break;
						}
					} catch(IOException ee) {
						
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
				}
				
				/*
				try {
					GameProtocol protocol = new GameProtocol(GameProtocol.PT_PLAYER_MOVE, 
						user.getNickname() + " " + getX() + " " + getY() + " ");
					user.getOut().writeObject(protocol);
				} catch (IOException e) {
					e.printStackTrace();
				}
				*/
				
			}
		} finally {
			System.out.printf("[%s] 종료..\n", this.getClass().getName());

		}
	}

	private void broadcastGameInfo(GameProtocol protocol) throws IOException {
		String nickname = ((String) protocol.getData()).split(" ")[0];
		Enumeration<ServerUser> e = userList.elements();
		while(e.hasMoreElements()) {
			serverUser = e.nextElement();
			if(!serverUser.getNickname().equals(nickname))
				serverUser.getOut().writeObject(protocol);
		}
			
	}
	
}
