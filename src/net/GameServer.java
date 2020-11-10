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
		lobby = new Lobby();	// �κ񿡼��� ��Ʈ��ŷ�� ����ϴ� �����μ� �����Ѵ�.
		lobby.start();			// �κ� ������ ����
	}
	
	@Override
	public void run() {
		Socket socket = null;
		ServerPlayer player = null;
		
		System.out.println("Lobby thread is  started...");
		while(true) {
			try {
				// SERVER_PORT(=4001)���� Ŭ���̾�Ʈ ���� ���
				socket = serverSocket.accept();

				ObjectOutputStream out  = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				GameProtocol gameProtocol = (GameProtocol) in.readObject();
				User user = null;
				
				switch(gameProtocol.getProtocol()) {
				case GameProtocol.PT_REQ_SIGN_UP:
					// ȸ������ ��ûó���� ��� ����
					user = (User) gameProtocol.getData();
					gameProtocol = new GameProtocol(GameProtocol.PT_REQ_SIGN_UP);
					gameProtocol.setData(DBManager.signUp(user));
					out.writeObject(gameProtocol);
					break;
				case GameProtocol.PT_ID_DUPLICATE_CHECK:
					// ȸ������ ���̵� �ߺ� üũ
					String id = (String) gameProtocol.getData();
					gameProtocol = new GameProtocol(GameProtocol.PT_ID_DUPLICATE_CHECK);
					gameProtocol.setData(DBManager.checkIdDuplicate(id));
					out.writeObject(gameProtocol);
					break;
				case GameProtocol.PT_RES_LOGIN:
					// ���ԵǾ��ִ� Ŭ���̾�Ʈ�� �´��� üũ
					Login login = (Login) gameProtocol.getData();
					user = loginValidate(login);
					if(user == null) {
						System.out.println("�α��� ����");
						continue;
					}
					// �α����� �����ϰ� �޾ƿ� ���� ������ Ŭ���̾�Ʈ���� ��������
					System.out.println("�α��� ����!");
					out.writeObject(user);
					
					// ��Ʈ��ŷ�� �����ϴ� ServerPlayer ��ü ����
					player = new ServerPlayer(socket, lobby, null, user);
					player.out = out;
					player.in = in;
					
					// ������ Ŭ���̾�Ʈ���� �� ����Ʈ�� ������.
					// lobby.sendRoomList(player);
					
					// ������ Ŭ���̾�Ʈ�� �κ��� �÷��̾� ����Ʈ�� �߰��Ѵ�.
					lobby.addPlayer(player);
					
					// �κ� �� ����Ʈ�� ��ü���� �ٽ� ������.
					// lobby.broadcastRoomList();
					
					// �÷��̾��� ���� Ÿ�Ӿƿ��� 10msec�� �����Ѵ�.
					player.socket.setSoTimeout(10);
					break;
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// �α��� ����
	public User loginValidate(Login loginInfo) {
		return DBManager.login(loginInfo);
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println("Tom N Jerry Server up and running...");
		GameServer server = new GameServer(SERVER_PORT);
		server.start();
	}
}
