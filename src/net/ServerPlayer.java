package net;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import entity.User;

/*
 * -Ŭ���̾�Ʈ�� �������ְ�ޱ����� ������ ��Ʈ��ũ ���
 * -
 */

public class ServerPlayer {
	public Socket socket= null;
	public ObjectInputStream in = null;
	public ObjectOutputStream out = null;
	
	Lobby lobby = null;
	GRoom room = null;
	
	// �÷��̾� ���� ����
	String id;
	String nickname;
	
	// GameServer�� run()���� �����ȴ�.
	// GServerPlayer ��ü ���� ��, �� ���� ������ null�̴�.
	public ServerPlayer(Socket socket, Lobby lobby, GRoom room, User user) {
		// ����, �κ� ����, �� ���� ���� ������ ����
		this.socket = socket;
		this.lobby = lobby;
		this.room = room;
		
		// ���� ���� ����
		this.id = user.getId();
		this.nickname = user.getNickname();
	}
	
	@Override
	public String toString() {
		return String.format("[ServerPlayer] ���̵�:%s, �г���:%s", id, nickname);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	// ����Ǿ� �ִ� Ŭ���̾�Ʈ���� �־��� �����͸� �����Ѵ�.
	public void sendMessage(String message) {

	}
	
	// ����Ǿ� �ִ� Ŭ���̾�Ʈ�κ��� �� �����͸� �о���δ�.
	public String receiveMessage() throws SocketException, IOException {
		String message = null;
		try {
			message = in.readLine();
			if(message == null)
				throw(new IOException("Null pointer received..."));
		} catch (SocketException e) {
			throw(e);
		} catch(InterruptedIOException e) {
			message = "";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return message;
	}

	// ��Ʈ��ũ ���� ����
	protected void clear() {
		try {
			if(in != null) {
				in.close();
				in = null;
			}
		} catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
		try {
			if(out != null) {
				out.close();
				out = null;
			}
		} catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
		try {
			if(socket!= null) {
				socket.close();
				socket = null;
			}
		} catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	
	String getPlayerInfoMessage() {
		String playerInfoMessage = "null,null";
		
		if(room == null)
			playerInfoMessage = "����,�����";
		else {
			if(room.inSession(this))
				playerInfoMessage = room.roomId + ",������";
			else
				playerInfoMessage = room.roomId + ",������";
		}
		return playerInfoMessage;
	}

}