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
				// SERVER_PORT(=4001)���� Ŭ���̾�Ʈ ���� ���
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
						System.out.println("�α��� ����");
						continue;
					}
					System.out.println("�α��� ����!");
					output.writeBoolean(true);
					// player = new ServerPlayer(socket, lobby, null);
					// Ŭ���̾�Ʈ�� ������� ������ Ŭ���̾�Ʈ�� �´��� üũ
//					player.validation();
					
					// ������ Ŭ���̾�Ʈ���� �� ����Ʈ�� ������.
					// lobby.sendRoomList(player.id);
					
					// ������ Ŭ���̾�Ʈ�� �κ��� �÷��̾� ����Ʈ�� �߰��Ѵ�.
					// lobby.addPlayer(player);
					
					// �κ� �� ����Ʈ�� ��ü���� �ٽ� ������.
					// lobby.broadcastRoomList();
					
					// �÷��̾��� ���� Ÿ�Ӿƿ��� 10msec�� �����Ѵ�.
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
	
	// �α��� ����
	public boolean loginValidate(Login loginInfo) {
		return DBManager.login(loginInfo);
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println("Tom N Jerry Server up and running...");
		GameServer server = new GameServer(SERVER_PORT);
		server.start();
	}
}
