package server.net;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import client.entity.User;
import server.entity.ServerUser;
import server.util.GameProtocol;

public class WaitingRoom extends Thread implements Serializable {
	private int roomId;				// 룸 아이디
	private String roomName;		// 방 이름
	private String roomPassword;	// 방 비밀번호
	private int maxPlayerCount;		// 방 최대수용인원
	private int roomState;			// 방 상태 (0:대기중, 1:게임중)
	
	private Hashtable<String, ServerUser> userList = null; // 대기방에 접속중인 유저리스트
	private Lobby lobby;
	
	public WaitingRoom(int roomId, String roomName, Lobby lobby) {
		this.roomId = roomId;
		this.roomName = roomName;
		this.roomPassword = "";
		this.maxPlayerCount = 4;
		this.userList = new Hashtable<>();
		this.roomState = 0;
		this.lobby = lobby;
		
		start();
	}
	
	public WaitingRoom(int roomId, String roomName, String roomPassword, Lobby lobby) {
		this.roomId = roomId;
		this.roomName = roomName;
		this.roomPassword = roomPassword;
		this.maxPlayerCount = 4;
		this.userList = new Hashtable<>();
		this.roomState = 0;
		this.lobby = lobby;
		
		start();
	}
	
	@Override
	public void run() {
		ServerUser user = null;
		GameProtocol protocol = null;
		int result = 0;

		try {
			System.out.printf("[%s] 작동 중..\n", this.getClass().getName());
			while(true) {
				Thread.sleep(100);
				
				Enumeration<ServerUser> e = userList.elements();
				while(e.hasMoreElements()) {
					user = e.nextElement();
					try {
						protocol = (GameProtocol) user.getIn().readObject();
						switch(protocol.getProtocol()) {
						case GameProtocol.PT_LOGOUT:
							removePlayer(user);
							break;
						}
					} catch(IOException ee) {
						
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
				}
			}
		} catch (InterruptedException e2) {
		} finally {
			System.out.printf("[%s] 종료..\n", this.getClass().getName());

		}
	}
	
	public int addPlayer(ServerUser serverUser) {
		if(userList.size() < maxPlayerCount) {
			userList.put(serverUser.getId(), serverUser);
			System.out.format("[%s] 현재 접속자 수 : %d\n",this.getClass().getName(), userList.size());
			return 1;
		} else
			return 0;
	}
	
	// 접속이 끊긴 클라이언트를 대기방에서 삭제한다.
	public void removePlayer(ServerUser serverUser) {
		if(userList.get(serverUser.getId()) != null) {
			userList.remove(serverUser.getId());
			System.out.format("[%s] 현재 접속자 수 : %d\n",this.getClass().getName(), userList.size());
			broadcastUserList();
		}
		
		if(userList.size() == 0) {
			lobby.removeWaitingRoom(this);
			interrupt();
		}
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
		return userList.size();
	}

	public int getRoomState() {
		return roomState;
	}

	public void setRoomState(int roomState) {
		this.roomState = roomState;
	}
	
	// 대기방에 있는 유저 리스트를 클라이언트에게 보낸다.
	public void broadcastUserList() {
		List<User> roomUserList = new ArrayList<>();
		
		// 유저 리스트를 만든다.
		Enumeration<ServerUser> elements = userList.elements();
		while(elements.hasMoreElements()) {
			ServerUser serverUser = elements.nextElement();
			User user = new User(serverUser.getNickname(), serverUser.getPlayerState());
			roomUserList.add(user);
		}
		
		// 유저 리스트를 모든 유저들에게 보낸다.
		elements = userList.elements();
		while(elements.hasMoreElements()) {
			ServerUser serverUser = elements.nextElement();
			GameProtocol protocol = new GameProtocol(GameProtocol.PT_BROADCAST_USER_LIST_IN_WAITING_ROOM, roomUserList);
			try {
				serverUser.getOut().writeObject(protocol);
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		
	}

	public void setLobby(Lobby lobby) {
		this.lobby = lobby;
	}
	
}
