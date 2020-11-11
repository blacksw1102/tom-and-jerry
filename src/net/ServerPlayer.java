package net;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import entity.User;

/*
 * -클라이언트와 데이터주고받기위한 서버측 네트워크 모듈
 * -
 */

public class ServerPlayer {
	public Socket socket= null;
	public ObjectInputStream in = null;
	public ObjectOutputStream out = null;
	
	Lobby lobby = null;
	GRoom room = null;
	
	// 플레이어 정보 저장
	String id;
	String nickname;
	private int playerState; //	(0:준비 안됨, 1:준비)
	
	// GameServer의 run()에서 생성된다.
	// GServerPlayer 객체 생성 시, 룸 서버 정보느 null이다.
	public ServerPlayer(Socket socket, Lobby lobby, GRoom room, User user) {
		// 소켓, 로비 서버, 룸 서버 등의 정보를 저장
		this.socket = socket;
		this.lobby = lobby;
		this.room = room;
		
		// 유저 정보 저장
		this.id = user.getId();
		this.nickname = user.getNickname();
		this.playerState = 0;
	}
	
	@Override
	public String toString() {
		return String.format("[ServerPlayer] 아이디:%s, 닉네임:%s", id, nickname);
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
	
	public int getPlayerState() {
		return playerState;
	}

	public void setPlayerState(int playerState) {
		this.playerState = playerState;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	// 연결되어 있는 클라이언트에게 주어진 데이터를 전송한다.
	public void sendMessage(String message) {

	}
	
	// 연결되어 있는 클라이언트로부터 온 데이터를 읽어들인다.
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

	// 네트워크 연결 해제
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
			playerInfoMessage = "대기실,대기중";
		else {
			if(room.inSession(this))
				playerInfoMessage = room.roomId + ",참가중";
			else
				playerInfoMessage = room.roomId + ",관전중";
		}
		return playerInfoMessage;
	}

}