package game.server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JTextArea;

public class UserInfo extends Thread {
	private Socket s;
	private Vector<UserInfo> users;
	private DataInputStream dis;
	private DataOutputStream dos;
	private ObjectOutputStream oos;
	private String nName;
	private boolean ready = false;
	private JTextArea ta;
	private byte[] buff;
	//private int key = 0;
	

	public UserInfo(Socket s, Vector users) {
		this.s = s;
		this.users = users;
		
		ta = ServerFrame.getTa();
		connection();

	}

	public void connection() { // 스트림 생성
		try {
			dis = new DataInputStream(s.getInputStream());
			dos = new DataOutputStream(s.getOutputStream());

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@Override
	public void run() { 
		while (true) {
			if (Thread.interrupted()) 
				break; 
			buff = recv_Message();

			first_Byte_Check(buff);
			/*if (key == 3) {
				ta.append("<도망자가 키를 모두 모아 탈출 문이 생성>\n");
				byte[] bb = new byte[128];
				bb[0] = 5;
				send_Message(bb);
				broadCast(bb);
				key = 0;
			}*/
			
			buff = null;

		}
	}

	public void first_Byte_Check(byte[] b) { // 첫 바이트 = 명령(입장, 퇴장등..)
		if (buff[0] == 1) {
			nName = byteToString(buff);
			users.add(this);
			ta.append(nName + "님 입장\n");
			ta.append(users.size() + " : 현재 사용자 수\n");
			send_UsersInfo();
			broadCast(buff);
		} else if(buff[0] == 2) {
			String[] values = byteToString(buff).split(" ");
			nName = values[0];
			ready = Boolean.valueOf(values[1]);

			ta.append(nName + "님 게임 준비 - " + ready + "\n");
			broadCast(buff);
		} else if (buff[0] == 4) {
			String str = byteToString(buff);
			str = str.trim();
			ta.append(str + "님이 키를 발견하였다! \n");
			broadCast(buff);
			//key++;

		} else if (buff[0] == 6) {
			ta.append(byteToString(buff)+"님이 탈출\n");
			send_Message(buff);
			broadCast(buff);
		}

		else if (buff[0] == 7) {
			
			ta.append(byteToString(buff) + "님이 당했습니다" + "\n");
			send_Message(buff);
			broadCast(buff);
		}
		else if(buff[0] == 5) {
			ta.append("<도망자가 키를 모두 모아 탈출 문이 생성>\n");
		}
		else if(buff[0] == 20)
			ta.append("게임종료 : 살인마 승리\n");
		else if(buff[0] == 10)
			ta.append("게임종료 : 도망자 승리\n");

		else
			broadCast(buff);

		buff = null;
	}

	public void exit_User(String str) { 
		
		byte[] b = str.getBytes();
		byte[] sendData = new byte[str.length() + 1];
		sendData[0] = 11;

		System.arraycopy(b, 0, sendData, 1, str.length());
		broadCast(sendData);
	}

	public byte[] recv_Message() { 
		byte[] b = new byte[128];
		try {
			dis.read(b);
		} catch (IOException e) {
			
			try {
				users.removeElement(this);
				exit_User(nName);
				dos.close();
				dis.close();
				s.close();
				ta.append(users.size() + " : 현재 사용자 수\n");
				this.interrupt();
			} catch (Exception e1) {
			}

		}
		return b;
	}

	public void broadCast(byte[] b) {
		if (users.size() > 0) {
			for (Object o : users) {
				UserInfo oUser = (UserInfo) o;
				if (!oUser.getnName().equals(this.getnName())) 
					oUser.send_Message(b);
			}
		}
	}

	public void send_Message(byte[] b) {
		try {
			dos.write(b);
		} catch (IOException e) {
			e.printStackTrace();
			ta.append("메시지 송신 에러 발생\n");
		}
	}

	public void send_UsersInfo() { 
		String userInfo = "";
		for (int i = 0; i < users.size(); i++) {
			UserInfo u = (UserInfo) users.elementAt(i);
			userInfo += u.getnName();
			if (i != users.size() - 1) {
				userInfo += " ";
			}
		}
		byte[] b = userInfo.getBytes();
		byte[] sendData = new byte[userInfo.length() + 1];
		sendData[0] = 100;
		System.arraycopy(b, 0, sendData, 1, userInfo.length());
		send_Message(sendData);

	}

	public String byteToString(byte[] b) { 
		String str = new String(b);
		str = str.trim();
		return str;
	}

	public String getnName() {
		return nName;
	}
}
