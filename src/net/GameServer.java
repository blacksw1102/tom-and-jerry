package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import entity.Login;
import entity.User;
import util.DBManager;
import util.GameProtocol;

public class GameServer extends Thread {
	private static final int SERVER_PORT = 4001;
	private Lobby lobby = null;
	
	private int port;
	private ServerSocket serverSocket;
	
	public GameServer(int port) {
		try {
			this.port = port;
			serverSocket = new ServerSocket(this.port);
			System.out.println("Sever listening on " + SERVER_PORT + " PORT");
		} catch (IOException e) {
			System.out.println("Couldn't access port " + SERVER_PORT + " PORT");
			System.exit(1);
		}
		lobby = new Lobby();	// 로비에서의 네트워킹을 담당하는 서버로서 동작한다.
		lobby.start();			// 로비 스레드 실행
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

				ObjectOutputStream out  = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				GameProtocol gameProtocol = (GameProtocol) in.readObject();
				User user = null;
				
				switch(gameProtocol.getProtocol()) {
				case GameProtocol.PT_REQ_SIGN_UP:
					// 회원가입 요청처리후 결과 응답
					user = (User) gameProtocol.getData();
					gameProtocol = new GameProtocol(GameProtocol.PT_REQ_SIGN_UP);
					gameProtocol.setData(DBManager.signUp(user));
					out.writeObject(gameProtocol);
					break;
				case GameProtocol.PT_ID_DUPLICATE_CHECK:
					// 회원가입 아이디 중복 체크
					String id = (String) gameProtocol.getData();
					gameProtocol = new GameProtocol(GameProtocol.PT_ID_DUPLICATE_CHECK);
					gameProtocol.setData(DBManager.checkIdDuplicate(id));
					out.writeObject(gameProtocol);
					break;
				case GameProtocol.PT_RES_LOGIN:
					// 가입되어있는 클라이언트가 맞는지 체크
					Login login = (Login) gameProtocol.getData();
					user = loginValidate(login);
					if(user == null) {
						System.out.println("로그인 실패");
						continue;
					}
					// 로그인을 성공하고 받아온 유저 정보를 클라이언트에게 전달해줌
					System.out.println("로그인 성공!");
					out.writeObject(user);
					
					// 네트워킹을 전담하는 ServerPlayer 객체 생성
					player = new ServerPlayer(socket, lobby, null, user);
					player.out = out;
					player.in = in;
					
					// 접속한 클라이언트에게 룸 리스트를 보낸다.
					// lobby.sendRoomList(player);
					
					// 접속한 클라이언트를 로비의 플레이어 리스트에 추가한다.
					lobby.addPlayer(player);
					
					// 로비에 룸 리스트를 전체에게 다시 보낸다.
					// lobby.broadcastRoomList();
					
					// 플레이어의 소켓 타임아웃을 10msec로 설정한다.
					player.socket.setSoTimeout(10);
					break;
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// 로그인 검증
	public User loginValidate(Login loginInfo) {
		return DBManager.login(loginInfo);
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println("Tom N Jerry Server up and running...");
		GameServer server = new GameServer(SERVER_PORT);
		server.start();
	}
}
