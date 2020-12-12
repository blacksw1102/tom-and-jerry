package server.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import client.entity.Login;
import client.entity.User;
import server.entity.ServerUser;
import server.util.DBManager;
import server.util.GameProtocol;

public class GameServer extends Thread {
	private static final int SERVER_PORT = 4001;
	private Lobby lobby = null;
	
	private int port;
	private ServerSocket serverSocket;
	
	public GameServer(int port) {
		try {
			this.port = port;
			this.serverSocket = new ServerSocket(this.port);
			System.out.println("Sever listening on " + SERVER_PORT + " PORT");
		} catch (IOException e) {
			System.out.println("Couldn't access port " + SERVER_PORT + " PORT");
			System.exit(1);
		}
		
		lobby = new Lobby();
		
		this.start();
	}
	
	@Override
	public void run() {
		System.out.printf("[%s] 작동 중..\n", this.getClass().getName());

		while(true) {
			try {
				Socket socket = null;
				ServerUser serverUser = null;
				
				socket = serverSocket.accept();
				serverUser = new ServerUser(socket, lobby);
				GameProtocol gameProtocol = (GameProtocol) serverUser.getIn().readObject();
				User user = null;
				
				switch(gameProtocol.getProtocol()) {
				case GameProtocol.PT_REQ_SIGN_UP:
					// 회원가입 요청처리후 결과 응답
					user = (User) gameProtocol.getData();
					gameProtocol = new GameProtocol(GameProtocol.PT_REQ_SIGN_UP);
					gameProtocol.setData(DBManager.signUp(user));
					serverUser.getOut().writeObject(gameProtocol);
					break;
				case GameProtocol.PT_ID_DUPLICATE_CHECK:
					// 회원가입 아이디 중복 체크
					String id = (String) gameProtocol.getData();
					gameProtocol = new GameProtocol(GameProtocol.PT_ID_DUPLICATE_CHECK);
					gameProtocol.setData(DBManager.checkIdDuplicate(id));
					serverUser.getOut().writeObject(gameProtocol);
					break;
				case GameProtocol.PT_RES_LOGIN:
					Login login = (Login) gameProtocol.getData();
					
					user = loginValidate(login);
					if(user == null) {
						serverUser.getOut().writeObject(user);
					} else {
						serverUser.setId(user.getId());
						serverUser.setNickname(user.getNickname());

						serverUser.getOut().writeObject(user);
						serverUser.getSocket().setSoTimeout(10);
						
						lobby.addPlayer(serverUser);
					}


					// 접속한 클라이언트에게 룸 리스트를 보낸다.
					// lobby.sendRoomList(player);
					
					// 로비에 룸 리스트를 전체에게 다시 보낸다.
					// lobby.broadcastRoomList();
					
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
		GameServer server = new GameServer(SERVER_PORT);
	}
}
