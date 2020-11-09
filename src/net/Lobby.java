package net;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import util.GameProtocol;

public class Lobby extends Thread {
	Hashtable<String, ServerPlayer> chatList = null;	// �κ� ���� �ִ� Ŭ���̾�Ʈ ����Ʈ (�÷��̾� ���̵�, ServerPlayer ��ü)
	Hashtable<String, GRoom> roomList = null;			// �Լ��� �� ����Ʈ (����̵�, GRoom ��ü)
	GRoom room = null;
	
	public Lobby() {
		chatList = new Hashtable<>();
		roomList = new Hashtable<>();
	}
	
	@Override
	public void run() {
		System.out.println("Lobby run start");
		while(true) {
			// CPU ���� ����
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Enumeration<ServerPlayer> e = chatList.elements();
			while(e.hasMoreElements()) {
				ServerPlayer player = e.nextElement();
				try {
					GameProtocol protocol = (GameProtocol) player.in.readObject();
					if(protocol == null)
						throw new IOException("Null pointer received...");
					switch(protocol.getProtocol()) {
						case GameProtocol.PT_SEND_MESSAGE:
							String message = (String) protocol.getData();
							Enumeration<ServerPlayer> elements = chatList.elements();
							while(elements.hasMoreElements()) {
								ServerPlayer toPlayer = elements.nextElement();
								GameProtocol toProtocol = new GameProtocol(GameProtocol.PT_SEND_MESSAGE, message);
								toPlayer.out.writeObject(toProtocol);
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
	
	// ��� ������ Ŭ���̾�Ʈ�� �κ� �߰��Ѵ�.
	public void addPlayer(ServerPlayer player) {
		if(chatList.get(player.id) == null) {
			int i = 0;
			String message = null;
			
			// ��� ������ Ŭ���̾�Ʈ���� ȯ�� �޽����� ������.
			message = "[" + player.id + "]�� ȯ���մϴ� ^^";
			player.sendMessage(message);
			
			// ��� ������ Ŭ���̾�Ʈ�� �������� �κ� ���� ��ο��� �˸���.
			message = "[" + player.id + "]���� �����Ͽ����ϴ�.";
			broadcastMessage(message);
			
			// ��� ������ Ŭ���̾�Ʈ�� Ŭ���̾�Ʈ ����Ʈ�� �߰��Ѵ�.
			chatList.put(player.id, player);
			
			// �κ��� ��ο��� ���ŵ� ����� ����Ʈ�� ������.
			broadcastUserList();
		}
		
	}
	
	// ������ ���� Ŭ���̾�Ʈ�� �κ񿡼� �����Ѵ�.
	public void removePlayer(ServerPlayer player) {
		chatList.remove(player.id);
		broadcastUserList();
	}
	
	// �־��� ���̵��� Ŭ���̾�Ʈ���� �޽����� ������.
	public void sendMessage(String playerId, String message) {
		// TODO Auto-generated method stub
	}

	// �κ� ���� ��ο��� �־��� �޽����� ������.
	public void broadcastMessage(String message) {
		// TODO Auto-generated method stub
	}
	
	// toIds ���� �ִ� ���̵� ���� �÷��̾�� �޽����� ������.
	// toIds���� �� �÷��̾��� ���̵� ','�� �̿��Ͽ� �����ϰ� �ִ�.
	public void multicastMessage(String tolds, String fromId, String charStr) {
		// TODO Auto-generated method stub
	}
	
	// �־��� ���̵��� �÷��̾�� �ش� �뿡 �ִ� ����� ����Ʈ�� ������.
	public void sendUserList(String playerId, String roomId) {
		// TODO Auto-generated method stub
	}

	// �־��� ���̵��� �÷��̾�� �� ����Ʈ�� ������.
	public void sendRoomList(ServerPlayer player) {
	}

	// �κ� ���� �ִ� ��ü���� �� ����Ʈ�� �����Ѵ�.
	public void broadcastRoomList() {
		// TODO Auto-generated method stub
		
	}
	
	// �κ� ���� �ִ� ��ü���� �־��� �� ���� �ִ� ����� ����Ʈ�� ������.
	public void broadcastUserList() {
		Enumeration elements = chatList.elements();
		List<String> userList = new ArrayList<>();
		
		// ���� ����Ʈ�� �����.
		while(elements.hasMoreElements()) {
			ServerPlayer player = (ServerPlayer) elements.nextElement();
			userList.add(player.getNickname());
		}
		
		// ���� ����Ʈ�� ��� �����鿡�� ������.
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
	
	// �־��� �̸��� ���� ���� �����Ѵ�.
	public void makeRoom(String playerId, String roomId) {
		// TODO Auto-generated method stub
	}
	
	// �־����÷��̾ �κ񿡼� ���� �����Ͽ� ������ ���� ���� ó���Ѵ�.
	public void enterRoom(String playerId, String roomId) {
		// TODO Auto-generated method stub
	}
	
	// �־��� ���̵��� ���� �κ� ��ü�� �� ����Ʈ���� �����Ѵ�.
	public void removeRoom(String roomId) {
		// TODO Auto-generated method stub
	}
	
	// �־��� ���̵� �ش��ϴ� Ŭ���̾�Ʈ�� �������� ������ �����Ѵ�.
	// �������� ����(profile) : "sex, age, win, lose, total, position"
	public void setProfile(String playerId, String profile) {
		// TODO Auto-generated method stub
	}
	
	// �־��� ���̵��� Ŭ���̾�Ʈ�� ���� ServerPlayer ��ü�� ��´�.
	public ServerPlayer getPlayer(String playerid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// �־��� ���̵�(who)�� Ŭ���Ʈ�� ���� �������� ������
	// �־��� ���̵� ���� Ŭ���̾�Ʈ(to)���� ��� Ŭ���̾�Ʈ���� ������.
	public void sendProfile(String to, String who) {
		// TODO Auto-generated method stub
	}
	
	// �־��� ���̵�(who)�� Ŭ���Ʈ�� ���� ������
	// �־��� ���̵� ���� Ŭ���̾�Ʈ(to)���� ��� Ŭ���̾�Ʈ���� ������.
	public void sendProfileInfo(String to, String who) {
		// TODO Auto-generated method stub
	}
	
	// �뿡 �ִ� Ŭ���̾�Ʈ�� �ٽ� ���Ƿ� ���ƿ��� �� ���,
	// ������ Ŭ���̾�Ʈ ����Ʈ�� �ش� Ŭ���̾�Ʈ�� �ٽ� �߰��Ѵ�.
	public void takePlayer(ServerPlayer player) {
		// TODO Auto-generated method stub
	}
}
