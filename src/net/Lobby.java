package net;

import java.util.Enumeration;
import java.util.Hashtable;

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
		super.run();
	}
	
	// ��� ������ Ŭ���̾�Ʈ�� �κ� �߰��Ѵ�.
	public void addPlayer(ServerPlayer player) {
		if(player.id != null && chatList.get(player.id) == null) {
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
			Enumeration<ServerPlayer> e = chatList.elements();
			while(e.hasMoreElements()) {
				player = e.nextElement();
				i++;
			}
			
			// �κ��� ��ο��� ���ŵ� ����� ����Ʈ�� ������.
			broadcastUserList("�κ�");
		}
		
	}
	
	// �־��� Ŭ���̾�Ʈ�� �κ񿡼� �����Ѵ�.
	public void removePlayer(ServerPlayer serverPlayer) {
		// TODO Auto-generated method stub
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
	public void sendRoomList(String id) {
		// TODO Auto-generated method stub
	}

	// �κ� ���� �ִ� ��ü���� �� ����Ʈ�� �����Ѵ�.
	public void broadcastRoomList() {
		// TODO Auto-generated method stub
		
	}
	
	// �κ� ���� �ִ� ��ü���� �־��� �� ���� �ִ� ����� ����Ʈ�� ������.
	public void broadcastUserList(String roomId) {
		// TODO Auto-generated method stub
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
