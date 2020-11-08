package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import entity.Login;
import util.DBManager;
import util.GameProtocol;

public class GameServer extends Thread {
	private static final int SERVER_PORT = 4001;
	Lobby lobby = null;
	
	int port;
	ServerSocket serverSocket;
	
	public GameServer(int port) {
		try {
			this.port = port;
			serverSocket = new ServerSocket(this.port);
			System.out.println("Sever listening on " + SERVER_PORT + " PORT");
		} catch (IOException e) {
			System.out.println("Couldn't access port " + SERVER_PORT + " PORT");
			System.exit(1);
		}
		
		lobby = new Lobby();
		lobby.start();
	}
	
	@Override
	public void run() {
		Socket socket = null;
		ServerPlayer player = null;
		
		System.out.println("Lobby thread is  started...");
		while(true) {
			try {
				// SERVER_PORT(=4001)에서 클라이언트 접속 대기
				socket = serverSocket.accept();

				ObjectOutputStream output  = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
				GameProtocol gameProtocol = (GameProtocol) input.readObject();
				
				switch(gameProtocol.getProtocol()) {
				case GameProtocol.PT_SIGN_UP:
					break;
				case GameProtocol.PT_RES_LOGIN:
					Login login = (Login) gameProtocol.getData();
					boolean loginResult = loginValidate(login);
					if(loginResult == false) {
						System.out.println("로그인 실패");
						continue;
					}
					System.out.println("로그인 성공!");
					output.writeBoolean(true);
					// player = new ServerPlayer(socket, lobby, null);
					// 클라이언트가 톰과제리 게임의 클라이언트가 맞는지 체크
//					player.validation();
					
					// 접속한 클라이언트에게 룸 리스트를 보낸다.
					// lobby.sendRoomList(player.id);
					
					// 접속한 클라이언트를 로비의 플레이어 리스트에 추가한다.
					// lobby.addPlayer(player);
					
					// 로비에 룸 리스트를 전체에게 다시 보낸다.
					// lobby.broadcastRoomList();
					
					// 플레이어의 소켓 타임아웃을 10msec로 설정한다.
					// player.socket.setSoTimeout(10);
					
					output.close();
					input.close();
					break;
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// 로그인 검증
	public boolean loginValidate(Login loginInfo) {
		return DBManager.login(loginInfo);
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println("Tom N Jerry Server up and running...");
		GameServer server = new GameServer(SERVER_PORT);
		server.start();
	}
}
