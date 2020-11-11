package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/*
 * -Ŭ���̾�Ʈ�� �������ְ�ޱ����� ������ ��Ʈ��ũ ���
 * -
 */

public class ServerPlayer {
	public Socket socket= null;
	public BufferedReader is = null;
	public PrintWriter os = null;
	
	Lobby lobby = null;
	GRoom room = null;
	
	// �÷��̾� ���� ����
	int point = 0;
	String id = "noname";
	String sex = null;
	String age = null;
	String win = null;
	String lose = null;
	String total= null;
	String position = null;
	
	int intId = 0;
	static int instanceCount = 0;
	
	// GameServer�� run()���� �����ȴ�.
	// GServerPlayer ��ü ���� ��, �� ���� ������ null�̴�.
	public ServerPlayer(Socket socket, Lobby lobby, GRoom room) throws IOException {
		// �÷��̾��� ���̵� ����
		intId = instanceCount++;
		
		// Ŭ���̾�Ʈ�� ����� ��Ʈ��ũ �������κ���
		// �����͸� �а� ���� ���� ����� ��Ʈ�� ��ü ����
		is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		os = new PrintWriter(socket.getOutputStream(), true);
		
		if(is == null)
			throw new IOException();
		if(os == null)
			throw new IOException();
		
		// ����, �κ� ����, �� ���� ���� ������ ����
		this.socket = socket;
		this.lobby = lobby;
		this.room = room;
	}
	
	// Ŭ���̾�Ʈ�� ó�� �����ϸ�
	// ����������ӿ� ���ԵǾ��ִ� Ŭ���̾�Ʈ���� Ȯ���ϱ� ���� �޽����� ������.
	public void validation() {
		if(is != null) {
			try {
				sendMessage("Username");	// ����� ���̵� Ŭ���̾�Ʈ���� ��û
				id = receiveMessage();		// Ŭ���̾�Ʈ�κ��� ����� ���̵� �о�帲
			} catch(SocketException e) {
				clear();
			} catch(IOException e) {
				clear();
			}
	}
}
	
	// ����Ǿ� �ִ� Ŭ���̾�Ʈ���� �־��� �����͸� �����Ѵ�.
	public void sendMessage(String message) {
		if(message != null) {
			os.println(message);
			os.flush();
			if(os.checkError()) {
				// Ŭ���̾�Ʈ�� ��Ʈ��ũ ������ ������ ����
				// �ش� Ŭ���̾�Ʈ�� �κ�/�뿡�� �����ؼ�, ������ ���������� ó���Ѵ�.
				lobby.removePlayer(this);
			}
		}
	}
	
	// ����Ǿ� �ִ� Ŭ���̾�Ʈ�κ��� �� �����͸� �о���δ�.
	public String receiveMessage() throws SocketException, IOException {
		String message = null;
		try {
			message = is.readLine();
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
			if(is != null) {
				is.close();
				is = null;
			}
		} catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
		try {
			if(os != null) {
				os.close();
				os = null;
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
	
	String getProfileMessage() {
		String profileMessage = null;
		
		profileMessage = point + ",";
		profileMessage += sex + ",";
		profileMessage += sex + ",";
		profileMessage += sex + ",";
		profileMessage += sex + ",";
		profileMessage += sex + ",";
		profileMessage += sex + ",";
		
		return profileMessage;
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