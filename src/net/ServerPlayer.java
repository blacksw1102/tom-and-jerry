package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/*
 * -클라이언트와 데이터주고받기위한 서버측 네트워크 모듈
 * -
 */

public class ServerPlayer {
	public Socket socket= null;
	public BufferedReader is = null;
	public PrintWriter os = null;
	
	Lobby lobby = null;
	GRoom room = null;
	
	// 플레이어 정보 저장
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
	
	// GameServer의 run()에서 생성된다.
	// GServerPlayer 객체 생성 시, 룸 서버 정보느 null이다.
	public ServerPlayer(Socket socket, Lobby lobby, GRoom room) throws IOException {
		// 플레이어의 아이디 생성
		intId = instanceCount++;
		
		// 클라이언트와 연결된 네트워크 소켓으로부터
		// 데이터를 읽고 쓰기 위한 입출력 스트림 객체 생성
		is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		os = new PrintWriter(socket.getOutputStream(), true);
		
		if(is == null)
			throw new IOException();
		if(os == null)
			throw new IOException();
		
		// 소켓, 로비 서버, 룸 서버 등의 정보를 저장
		this.socket = socket;
		this.lobby = lobby;
		this.room = room;
	}
	
	// 클라이언트가 처음 접속하면
	// 톰과제리게임에 가입되어있는 클라이언트인지 확인하기 위한 메시지를 보낸다.
	public void validation() {
		if(is != null) {
			try {
				sendMessage("Username");	// 사용자 아이디를 클라이언트에게 요청
				id = receiveMessage();		// 클라이언트로부터 사용자 아이디를 읽어드림
			} catch(SocketException e) {
				clear();
			} catch(IOException e) {
				clear();
			}
	}
}
	
	// 연결되어 있는 클라이언트에게 주어진 데이터를 전송한다.
	public void sendMessage(String message) {
		if(message != null) {
			os.println(message);
			os.flush();
			if(os.checkError()) {
				// 클라이언트와 네트워크 연결이 끊어진 상태
				// 해당 클라이언트를 로비/룸에서 제거해서, 연결이 끊어졌음을 처리한다.
				lobby.removePlayer(this);
			}
		}
	}
	
	// 연결되어 있는 클라이언트로부터 온 데이터를 읽어들인다.
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

	// 네트워크 연결 해제
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