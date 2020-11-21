package game.server;
import java.awt.TextArea;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JTextArea;

public class Server {
	private ServerSocket ss;
	private Socket s;
	private JTextArea ta;
	private Vector users;

	public Server() {
		users = new Vector();
		ta = ServerFrame.getTa();
		try {
			ss = new ServerSocket(36720);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (ss != null)
			server_Set();
	}

	public void server_Set() {
		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {

						ta.append("접속대기----\n");
						s = ss.accept();
						UserInfo user = new UserInfo(s, users);

						user.start();

					}
				} catch (IOException e) {
					ta.append("socket error----\n");
					e.printStackTrace();
				}
			}
		});
		th.start();
	}

}
