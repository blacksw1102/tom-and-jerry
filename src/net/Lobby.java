package net;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import entity.WaitingRoom;
import entity.WaitingRoomListRow;
import util.GameProtocol;

public class Lobby extends Thread {
	private Hashtable<String, ServerPlayer> chatList = null;	// 로비 내에 있는 클라이언트 리스트 (플레이어 아이디, ServerPlayer 객체)
	private Hashtable<Integer, WaitingRoom> roomList = null;		// 게설된 룸 리스트 (룸아이디, GRoom 객체)
	
	private static int currentRoomId;
	
	public Lobby() {
		chatList = new Hashtable<>();
		roomList = new Hashtable<>();
		currentRoomId = 0;
	}
	
	@Override
	public void run() {
		System.out.println("Lobby run start");
		while(true) {
			// CPU 독식 방지
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Enumeration<ServerPlayer> e = chatList.elements();
			while(e.hasMoreElements()) {
				ServerPlayer player = e.nextElement();
				WaitingRoom waitingRoom = null;
				int result = 0;
				
				try {
					GameProtocol protocol = (GameProtocol) player.in.readObject();
					if(protocol == null)
						throw new IOException("Null pointer received...");
					switch(protocol.getProtocol()) {
						case GameProtocol.PT_SEND_MESSAGE:
							// 로비 채팅메시지 브로드캐스팅
							String message = (String) protocol.getData();
							Enumeration<ServerPlayer> elements = chatList.elements();
							while(elements.hasMoreElements()) {
								ServerPlayer toPlayer = elements.nextElement();
								GameProtocol toProtocol = new GameProtocol(GameProtocol.PT_SEND_MESSAGE, message);
								toPlayer.out.writeObject(toProtocol);
							}
							break;
						case GameProtocol.PT_REQ_CREATE_WAITING_ROOM:
							// 대기방 생성 
							waitingRoom = (WaitingRoom) protocol.getData();
							
							chatList.remove(player.getId());
							player.setPlayerState(2);
							waitingRoom.addPlayer(player);
							waitingRoom.setRoomId(currentRoomId++);
							waitingRoom.start(); // 대기방 스레드 시작
							roomList.put(waitingRoom.getRoomId(), waitingRoom);
							
							result = 1;
							protocol = new GameProtocol(GameProtocol.PT_RES_CREATE_WAITING_ROOM, result);
							player.out.writeObject(protocol);
							
							broadcastUserList(); // 유저 리스트 브로드캐스팅
							broadcastRoomList(); // 대기방 리스트 브로드캐스팅
							waitingRoom.broadcastUserList();
							break;
						case GameProtocol.PT_REQ_ENTER_WAITING_ROOM:
							// 입장할 대기방 번호를 클라이언트로부터 받는다.
							int roomId = (int) protocol.getData();
							
							// 플레이어를 로비에서 없애고, 대기방에 추가한다.
							waitingRoom = roomList.get(roomId);
							result = waitingRoom.addPlayer(player);
							
							if(result == 1) {
								// 대기방 유저 리스트를 대기방에 있는 클라이언트들에게 알려준다
								chatList.remove(player.getId());
								waitingRoom.broadcastUserList();
							} else {
								// 이미 방에 인원이 다찼음을 로비에 있는 클라이언트들에게 알려준다.
								protocol = new GameProtocol(GameProtocol.PT_RES_ENTER_WAITING_ROOM, result);
								broadcastUserList();
								broadcastRoomList();
							}
							break;
					}
					
				} catch(ClassNotFoundException ne) {
					ne.printStackTrace();
				} catch(SocketException ne) {
					System.out.println("socket exception");
					removePlayer(player);
				} catch(IOException ne) {
					System.out.println("ioe exception");
				}
			}
		}
	}
	
	// 방금 접속한 클라이언트를 로비에 추가한다.
	public void addPlayer(ServerPlayer player) {
		if(chatList.get(player.id) == null) {
			int i = 0;
			String message = null;
			
			// 방금 접속한 클라이언트에게 환영 메시지를 보낸다.
			message = "[" + player.id + "]님 환영합니다 ^^";
			player.sendMessage(message);
			
			// 방금 접속한 클라이언트가 들어왔음을 로비 내의 모두에게 알린다.
			message = "[" + player.id + "]님이 입장하였습니다.";
			broadcastMessage(message);
			
			// 방금 접속한 클라이언트를 클라이언트 리스트에 추가한다.
			chatList.put(player.id, player);
			
			// 로비내의 모두에게 갱신된 사용자 리스트를 보낸다.
			broadcastUserList();
		}
		
	}
	
	// 접속이 끊긴 클라이언트를 로비에서 삭제한다.
	public void removePlayer(ServerPlayer player) {
		chatList.remove(player.id);
		broadcastUserList();
	}
	
	// 주어진 아이디의 클라이언트에게 메시지를 보낸다.
	public void sendMessage(String playerId, String message) {
		// TODO Auto-generated method stub
	}

	// 로비 내의 모두에게 주어진 메시지를 보낸다.
	public void broadcastMessage(String message) {
		// TODO Auto-generated method stub
	}
	
	// toIds 내에 있는 아이디를 갖는 플레이어에게 메시지를 보낸다.
	// toIds에는 각 플레이어의 아이디를 ','를 이용하여 구분하고 있다.
	public void multicastMessage(String tolds, String fromId, String charStr) {
		// TODO Auto-generated method stub
	}
	
	// 주어진 아이디의 플레이어에게 해당 룸에 있는 사용자 리스트를 보낸다.
	public void sendUserList(String playerId, String roomId) {
		// TODO Auto-generated method stub
	}

	// 주어진 아이디의 플레이어에게 룸 리스트를 보낸다.
	public void sendRoomList(ServerPlayer player) {
	}

	// 로비 내에 있는 전체에게 룸 리스트를 전달한다.
	public void broadcastRoomList() {
		// 대기방 리스트를 만든다.
		Enumeration<WaitingRoom> roomElements = roomList.elements();
		List<WaitingRoomListRow> rowList = new ArrayList<>();
		while(roomElements.hasMoreElements()) {
			WaitingRoom room = roomElements.nextElement();
			WaitingRoomListRow row = new WaitingRoomListRow(room.getRoomId(), room.getRoomName(), 
					room.getCurrentPlayerCount(), room.getMaxPlayerCount(), room.getRoomState());
			rowList.add(row);
		}
		
		// 대기방 리스트를 모든 유저들에게 보낸다.
		Enumeration elements = chatList.elements();
		while(elements.hasMoreElements()) {
			ServerPlayer player = (ServerPlayer) elements.nextElement();
			GameProtocol protocol = new GameProtocol(GameProtocol.PT_BROADCAST_WAITING_ROOM_LIST, rowList);
			try {
				player.out.writeObject(protocol);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 로비 내에 있는 전체에게 주어진 룸 내에 있는 사용자 리스트를 보낸다.
	public void broadcastUserList() {
		Enumeration elements = chatList.elements();
		List<String> userList = new ArrayList<>();
		
		// 유저 리스트를 만든다.
		while(elements.hasMoreElements()) {
			ServerPlayer player = (ServerPlayer) elements.nextElement();
			userList.add(player.getNickname());
		}
		
		// 유저 리스트를 모든 유저들에게 보낸다.
		elements = chatList.elements();
		while(elements.hasMoreElements()) {
			ServerPlayer player = (ServerPlayer) elements.nextElement();
			GameProtocol protocol = new GameProtocol(GameProtocol.PT_RES_USER_LIST, userList);
			try {
				player.out.writeObject(protocol);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 주어진 이름의 게임 룸을 생성한다.
	public void makeRoom(String playerId, String roomId) {
		// TODO Auto-generated method stub
	}
	
	// 주어진플레이어가 로비에서 룸을 선택하여 룸으로 들어가는 것을 처리한다.
	public void enterRoom(String playerId, String roomId) {
		// TODO Auto-generated method stub
	}
	
	// 주어진 아이디의 룸을 로비 객체의 룸 리스트에서 제거한다.
	public void removeRoom(String roomId) {
		// TODO Auto-generated method stub
	}
	
	// 주어진 아이디에 해당하는 클라이언트의 프로파일 정보를 설정한다.
	// 프로파일 정보(profile) : "sex, age, win, lose, total, position"
	public void setProfile(String playerId, String profile) {
		// TODO Auto-generated method stub
	}
	
	// 주어진 아이디의 클라이언트에 대한 ServerPlayer 객체를 얻는다.
	public ServerPlayer getPlayer(String playerid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// 주어진 아이디(who)의 클라언트에 대한 프로파일 정보를
	// 주어진 아이디를 갖는 클라이언트(to)내의 모든 클라이언트에게 보낸다.
	public void sendProfile(String to, String who) {
		// TODO Auto-generated method stub
	}
	
	// 주어진 아이디(who)의 클라언트에 대한 정보를
	// 주어진 아이디를 갖는 클라이언트(to)내의 모든 클라이언트에게 보낸다.
	public void sendProfileInfo(String to, String who) {
		// TODO Auto-generated method stub
	}
	
	// 룸에 있던 클라이언트가 다시 대기실로 돌아오려 할 경우,
	// 대기실의 클라이언트 리스트에 해당 클라이언트를 다시 추가한다.
	public void takePlayer(ServerPlayer player) {
		// TODO Auto-generated method stub
	}
}
