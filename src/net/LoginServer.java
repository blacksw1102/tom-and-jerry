package game.net;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import game.entity.User;
import game.util.DBManager;

public class LoginServer {
	private ServerSocket ss;
	
	public LoginServer() throws IOException {
		System.out.println("[LoginServer] start");
		ss = new ServerSocket(9001);
		
		User user = new User("admin2", "admin2", "admin2", "admin2", 
				"2012-12-12", "010-1234-1234");
	}
	
	public static void main(String[] args) throws IOException {
		new LoginServer();
	}
}
