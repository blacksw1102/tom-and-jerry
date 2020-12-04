package server.entity;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import client.entity.User;
import server.net.Lobby;

/*
 * -클라이언트와 데이터주고받기위한 서버측 네트워크 모듈
 * -
 */

public class ServerUser {
	private String id;
	private String nickname;
	private int userState; //	(0:준비 안됨, 1:준비)

	private Socket socket= null;
	private ObjectInputStream in = null;
	private ObjectOutputStream out = null;
	
	private Lobby lobby = null;
	
	public ServerUser(Socket socket, Lobby lobby) throws SocketException {
		this.userState = 0;
		this.socket = socket;
		
		try {
			this.out = new ObjectOutputStream(socket.getOutputStream());
			this.in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.lobby = lobby;
	}
	
	@Override
	public String toString() {
		return String.format("[ServerPlayer] 아이디:%s, 닉네임:%s, 상태:%d", id, nickname, userState);
	}
	
	public ObjectInputStream getIn() {
		return in;
	}

	public ObjectOutputStream getOut() {
		return out;
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
	
	public int getUserState() {
		return userState;
	}

	public void setUserState(int userState) {
		this.userState = userState;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
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

}