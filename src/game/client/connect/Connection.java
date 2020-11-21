package game.client.connect;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import game.client.frame.FrameView;
import game.client.frame.GameView;
import game.client.object.User;
import game.client.object.Users;

public class Connection {
	private Connection conn = null;
	private Socket s;
	private String ip = "127.0.0.1";
	private int port = 36720;
	
	private DataInputStream dis;
	private DataOutputStream dos;
	private Users users;
	private byte[] buff;
	private int comm;
	private FrameView v;
	private String nName;
	private boolean ready;
	private int key = 0;
	public boolean role = false;
	public String hiddingName = "";
	public int deadUser = 0;
	public int escapeUser = 0;
	public int n = 0;
	
	
	public Connection(String nName) {
		
		this.nName = nName;
		users = new Users();
		create_Socket();
		
	}
	
	
	public void create_Socket() {
		try {
			s = new Socket(ip,port);
			dis = new DataInputStream(s.getInputStream());
			dos = new DataOutputStream(s.getOutputStream());
			
			if(s != null) {
				conn_Thread();
			}
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("socket create error\n");
		}
	}
	
	public void initUsersXY(Users users) {
		int i = 0;
		
		for(Object o : users.getUsers()) {
			User u = (User)o;
			if(i == 0) {
				u.setRole(true);
				u.setX(500);u.setY(500);
				u.createArea();
				i++;
			}else {
			u.setX(400);u.setY(300+i);
			u.createArea();
			i += 100;
			}
		}
	}
	
	public void conn_Thread() {
		
		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
						if(deadUser == users.getSize()-1 && users.getSize()>1) {
							deadUser = 0;
							send_Message(" ", 20);
							comm = 20;
							execute_Comm();
							
						}
						if(escapeUser == users.getSize()-deadUser-1 && users.getSize()>1) {
							escapeUser = 0;
							send_Message(" ", 10);
							comm = 10;
							execute_Comm();
							
						}
						if(key ==3 && n == 0) {
							send_Message(" ", 5);
							comm = 5;
							execute_Comm();
							n++;
							
						}
						
						buff = recv_Message();	
						execute_Comm();
						buff=null;
					
					 }
				}
		});
		th.start();
	}
	
	public void execute_Comm() {
		if(comm == 1) {
			String[] names = new String(buff).trim().split(",");
			for(String n : names) {
				if(!users.contains(n))
					users.addUser(new User(n));
			}
		}
		
		else if(comm == 2) {
			String[] values = byteToString(buff).split(" ");
			nName = values[0];
			ready = Boolean.valueOf(values[1]);
			for(Object o : users.getUsers()) {
				User u = (User)o;
				if(u.getnName().equals(nName)) {
					u.setReady(ready);
				}
			}
		} 
		
		else if(comm == 3) {
			v.setVisible(false);
			GameView gv = new GameView(this);
		}
		
		else if(comm == 11) {
			users.removeUser(new String(buff));
		}
		
		else if(comm == 37 || comm == 38) {
			setUsersXY(buff);
		}
		
		else if(comm==4) {
			key++;
		}
		
		else if(comm == 6) {
			
			escapeUser++;
			String str = byteToString(buff);
			for(Object o : users.getUsers()) {
				User u = (User)o;
				if(u.getnName().equals(str)) {
					u.escape = true;
				}
			}
		}

		else if(comm == 7) {
			deadUser++;
			String str = byteToString(buff);
			for(Object o : users.getUsers()) {
				User u = (User)o;
				if(u.getnName().equals(str)) {
					u.setRole(true);	
			   }
			}
			if(this.nName.equals(str))
				this.role = true;
		}
		
		else if(comm == 8) {
			
			String str = byteToString(buff);
			for(Object o : users.getUsers()) {
				User u = (User)o;
				if(u.getnName().equals(str)) {
					u.setSImg();
			 }
			}
		}
		
		else if(comm == 9) {
			hiddingName = byteToString(buff);
			for(Object o : users.getUsers()) {
				User u = (User)o;
				if(u.getnName().equals(hiddingName)) {
					u.setHiding(true);
			 }
			}
		}
		
		v.view();
		comm = 1000;
		
	}
	
	
	
	public synchronized void setUsersXY(byte[] buff) {
		String str = byteToString(buff);
		str = str.trim();
		String[] sstr = str.split(" ");
		String[] xystr = sstr[1].split(",");
		for(Object o : users.getUsers()) {
			User u = (User)o;
			if(u.getnName().equals(sstr[0])) {
				int x,y;
				//System.out.println(xystr[0]+" "+xystr[1]);
				
				if(xystr[1].length() >4) {
					xystr[1] = xystr[1].substring(0, 3);
				}
				
				x = Integer.parseInt(xystr[0]);
				y = Integer.parseInt(xystr[1]);
				
				if(comm == 37)
				u.setMoving(true);
				else if(comm == 38)
					u.setMoving(false);
				
				u.setImg(x, y);
				
				u.setX(x);
				u.setY(y);
				}
			}
		}
		
	
	
	public byte[] recv_Message() {
		byte[] b = new byte[128];
		try {
			comm = dis.read();
			dis.read(b);
			
		} catch (IOException e) {
			
			try {
				dos.close();
				dis.close();
				s.close();
			} catch (IOException e1) {
			}
		}
		return b;
	}
	
	public void send_Message(String str, int comm) {
		try {
			byte[] b = str.getBytes();
			
			byte[] sendData = new byte[str.length()+1];
			sendData[0] = (byte) comm;
			System.arraycopy(b, 0, sendData, 1, str.length());
			dos.write(sendData);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("전송 실패\n");
		}
	}
	
	public void initUsers(Vector v) {
		for(int i =0;i<v.size();i++) {
			v.get(i);
		}
		
	}
	
	public byte[] stringToByte(String str) {
		byte[] b = str.getBytes();
		return b;
	}
	
	public String byteToString(byte[] b) {
		 String	str = new String (b);
		 str = str.trim();
		return str;
	}

	public Users getUsers() {
		return this.users;
	}
	public void setUsers(Users u) {
		this.users = u;
	}
	
	public void setView(FrameView v) {
	  this.v = v;	
	}
	
	public String getNname() {
		return this.nName;
	}
	public int getKey() {
		return this.key;
	}
	public void setKey() {
		this.key++;
	}
	public int getComm() {
		return this.comm;
	}
}
