package server.net;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import client.gui.component.WaitingRoomListRow;
import server.entity.ServerUser;
import server.util.GameProtocol;

public class Lobby extends Thread {
	private Hashtable<String, ServerUser> chatList = null;	// 로비 내에 있는 클라이언트 리스트 (플레이어 아이디, ServerPlayer 객체)
	private Hashtable<Integer, WaitingRoom> roomList = null;		// 게설된 룸 리스트 (룸아이디, GRoom 객체)
	
	private static int currentRoomId;
	
	public Lobby() {
		chatList = new Hashtable<>();
		roomList = new Hashtable<>();
		currentRoomId = 0;
		
		this.start();
	}
	
	@Override
	public void run() {
		System.out.printf("[%s] 작동 중..\n", this.getClass().getName());
		while(true) {
			 // CPU 독식 방지
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Enumeration<ServerUser> e = chatList.elements();
			while(e.hasMoreElements()) {
				ServerUser serverUser = e.nextElement();
				WaitingRoom waitingRoom = null;
				int result = 0;
				StringBuffer response = null;
				
				try {
					GameProtocol protocol = (GameProtocol) serverUser.getIn().readObject();
					if(protocol == null)
						throw new IOException("Null pointer received...");
					switch(protocol.getProtocol()) {
						case GameProtocol.PT_SEND_MESSAGE:
							System.out.println("send message 전");
							broadcastMessage(protocol);
							break;
						case GameProtocol.PT_REQ_CREATE_WAITING_ROOM:
							// 대기방 생성 
							//waitingRoom = (WaitingRoom) protocol.getData();
							String[] data = ((String) protocol.getData()).split(",");
							
							if(data.length == 1)
								waitingRoom = new WaitingRoom(++currentRoomId, data[0], this);
							else
								waitingRoom = new WaitingRoom(++currentRoomId, data[0], data[1], this);
							
							//waitingRoom.setRoomId(++currentRoomId);
							//waitingRoom.setLobby(this);
							//waitingRoom.start(); // 대기방 스레드 시작
							addWaitingRoom(waitingRoom);
							
							removePlayer(serverUser);
							waitingRoom.addPlayer(serverUser);
							
							response = new StringBuffer();
							response.append(waitingRoom.getRoomId() + ",");
							response.append(waitingRoom.getRoomName());
							protocol = new GameProtocol(GameProtocol.PT_RES_CREATE_WAITING_ROOM, response.toString());
							serverUser.getOut().writeObject(protocol);
							
							waitingRoom.broadcastUserList();
							broadcastUserList(); // 유저 리스트 브로드캐스팅
							broadcastRoomList(); // 대기방 리스트 브로드캐스팅
							break;
						case GameProtocol.PT_REQ_ENTER_WAITING_ROOM:
							// 입장할 대기방 번호를 클라이언트로부터 받는다.
							int roomId = (int) protocol.getData();
							
							// 플레이어를 로비에서 없애고, 대기방에 추가한다.
							waitingRoom = roomList.get(roomId);
							result = waitingRoom.addPlayer(serverUser);
							
							if(result == 1) {
								// 대기방 입장에 성공했음을 클라이언트에게 알림
								removePlayer(serverUser);
								
								response = new StringBuffer();
								response.append(waitingRoom.getRoomId() + ",");
								response.append(waitingRoom.getRoomName() + ",");
								response.append(waitingRoom.getRoomPassword());
								protocol = new GameProtocol(GameProtocol.PT_RES_ENTER_WAITING_ROOM, response.toString());
								serverUser.getOut().writeObject(protocol);
								
								/*
								protocol = new GameProtocol(GameProtocol.PT_RES_ENTER_WAITING_ROOM, result);
								serverUser.getOut().writeObject(protocol);
								*/
								waitingRoom.broadcastUserList();
								broadcastUserList(); // 유저 리스트 브로드캐스팅
								broadcastRoomList(); // 대기방 리스트 브로드캐스팅
							} else {
								// 이미 방에 인원이 다찼음을 로비에 있는 클라이언트들에게 알려준다.
								protocol = new GameProtocol(GameProtocol.PT_RES_ENTER_WAITING_ROOM, result);
								
								broadcastUserList();
								broadcastRoomList();
							}
							break;
						case GameProtocol.PT_LOGOUT:
							removePlayer(serverUser);
							break;
					}
					
				} catch(ClassNotFoundException ne) {
					ne.printStackTrace();
				} catch(SocketException ne) {
					removePlayer(serverUser);
				} catch(IOException ne) {

				}
			}
		}
	}
	
	// 방금 접속한 클라이언트를 로비에 추가한다.
	public void addPlayer(ServerUser serverUser) {
		if(chatList.get(serverUser.getId()) == null) {
			try {
				String message = "[" + serverUser.getId() + "]님이 입장하였습니다.";
				GameProtocol protocol = new GameProtocol(GameProtocol.PT_SEND_MESSAGE, message);
				broadcastMessage(protocol);
				
				chatList.put(serverUser.getId(), serverUser);
				System.out.format("[%s] 현재 접속자 수 : %d\n",this.getClass().getName(), chatList.size());

				broadcastRoomList();
				broadcastUserList();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	// 접속이 끊긴 클라이언트를 로비에서 삭제한다.
	public void removePlayer(ServerUser serverUser) {
		if(chatList.get(serverUser.getId()) != null) {
			try {
				chatList.remove(serverUser.getId());
				System.out.format("[%s] 현재 접속자 수 : %d\n",this.getClass().getName(), chatList.size());
				broadcastUserList();

				String message = "[" + serverUser.getId() + "]님이 퇴장하였습니다.";
				GameProtocol protocol = new GameProtocol(GameProtocol.PT_SEND_MESSAGE, message);
				broadcastMessage(protocol);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 대기방 추가
	public void addWaitingRoom(WaitingRoom room) {
		roomList.put(room.getRoomId(), room);
		System.out.format("[%s] 현재 대기방 개수 : %d\n",this.getClass().getName(), roomList.size());
	}
	
	// 대기방 제거
	public void removeWaitingRoom(WaitingRoom room) {
		if(roomList.get(room.getRoomId()) != null) {
			roomList.remove(room.getRoomId());
			System.out.format("[%s] 현재 대기방 개수 : %d\n",this.getClass().getName(), roomList.size());
			broadcastRoomList();
		}
	}
	
	// 주어진 아이디의 클라이언트에게 메시지를 보낸다.
	public void sendMessage(String playerId, String message) {
		// TODO Auto-generated method stub
	}

	// 로비 내의 모두에게 주어진 메시지를 보낸다.
	public void broadcastMessage(GameProtocol protocol) throws IOException {
		Enumeration<ServerUser> elements = chatList.elements();
		while(elements.hasMoreElements()) {
			ServerUser toUser = elements.nextElement();
			System.out.println("@@@@");
			toUser.getOut().writeObject(protocol);
		}
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
	public void sendRoomList(ServerUser serverUser) {
		
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
			ServerUser serverUser = (ServerUser) elements.nextElement();
			GameProtocol protocol = new GameProtocol(GameProtocol.PT_BROADCAST_WAITING_ROOM_LIST, rowList);
			try {
				serverUser.getOut().writeObject(protocol);
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
			ServerUser serverUser = (ServerUser) elements.nextElement();
			userList.add(serverUser.getNickname());
		}
		
		// 유저 리스트를 모든 유저들에게 보낸다.
		elements = chatList.elements();
		while(elements.hasMoreElements()) {
			ServerUser serverUser = (ServerUser) elements.nextElement();
			GameProtocol protocol = new GameProtocol(GameProtocol.PT_RES_USER_LIST, userList);
			try {
				serverUser.getOut().writeObject(protocol);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 주어진 이름의 게임 룸을 생성한다.
	//public void makeRoom(String playerId, String roomId) {
		// TODO Auto-generated method stub
	//}
	
	// 주어진플레이어가 로비에서 룸을 선택하여 룸으로 들어가는 것을 처리한다.
	//public void enterRoom(String playerId, String roomId) {
		// TODO Auto-generated method stub
	//}
	
	// 주어진 아이디의 룸을 로비 객체의 룸 리스트에서 제거한다.
	//public void removeRoom(String roomId) {
		// TODO Auto-generated method stub
	//}
	
	// 주어진 아이디에 해당하는 클라이언트의 프로파일 정보를 설정한다.
	// 프로파일 정보(profile) : "sex, age, win, lose, total, position"
	public void setProfile(String playerId, String profile) {
		// TODO Auto-generated method stub
	}
	
	// 주어진 아이디의 클라이언트에 대한 ServerPlayer 객체를 얻는다.
	public ServerUser getPlayer(String playerid) {
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
	public void takePlayer(ServerUser serverUser) {
		// TODO Auto-generated method stub
	}
}
