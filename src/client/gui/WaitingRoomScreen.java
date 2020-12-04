package client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import client.entity.User;
import client.gui.component.WaitingRoomRowPanel;
import server.util.GameProtocol;

public class WaitingRoomScreen extends JFrame implements Runnable {
	
	private User user;
    private ArrayList<User> userList = new ArrayList<>();
    
    private JPanel contentPane;
    private WaitingRoomRowPanel[] rows;
    
    private int roomId;
    private String roomName;
    private String roomPassword;
    
	private static final int MAX_PLAYER_COUNT = 4;
    
    private Thread t;

    public WaitingRoomScreen(User user, int roomId, String roomName) {
    	this.user = user;
    	this.roomId = roomId;
    	this.roomName = roomName;
    	this.roomPassword = "";
    	
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 450, 600);
		this.setLocationRelativeTo(null);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		this.contentPane.setLayout(null);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		titlePanel.setBackground(Color.WHITE);
		titlePanel.setBounds(30, 23, 380, 60);
		contentPane.add(titlePanel);
		titlePanel.setLayout(null);
		
		JLabel lblRoomId = new JLabel(String.format("%03d", roomId));
		lblRoomId.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		lblRoomId.setBounds(14, 12, 62, 36);
		lblRoomId.setHorizontalAlignment(SwingConstants.LEFT);
		titlePanel.add(lblRoomId);
		
		JLabel lblRoomTitle = new JLabel(roomName);
		lblRoomTitle.setHorizontalAlignment(SwingConstants.LEFT);
		lblRoomTitle.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		lblRoomTitle.setBounds(74, 12, 224, 36);
		titlePanel.add(lblRoomTitle);
		
		
		int startX = 30;
		int startY = 107;
		rows = new WaitingRoomRowPanel[MAX_PLAYER_COUNT];
		for(int i=0; i<rows.length; i++) {
			rows[i] = new WaitingRoomRowPanel(startX, startY);
			startY = startY + 60 + 5;
			contentPane.add(rows[i]);
		}
		
		JButton startBtn = new JButton("게임 준비");
		startBtn.setBorder(new LineBorder(new Color(0, 0, 0)));
		startBtn.setForeground(Color.WHITE);
		startBtn.setBackground(new Color(64, 118, 196));
		startBtn.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		startBtn.setBounds(30, 410, 380, 60);
		contentPane.add(startBtn);
		
		JButton cancelBtn = new JButton("나가기");
		cancelBtn.setBorder(new LineBorder(new Color(0, 0, 0)));
		cancelBtn.setBackground(Color.WHITE);
		cancelBtn.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		cancelBtn.setBounds(30, 475, 380, 60);
		contentPane.add(cancelBtn);
		
    	this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				GameProtocol protocol = new GameProtocol(GameProtocol.PT_LOGOUT);
				try {
					user.getOut().writeObject(protocol);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});
        
        t = new Thread(this);
        t.start();
        
		this.setVisible(true);
    }
	
	@Override
	public void run() {
		System.out.printf("[%s] 작동 중..\n", this.getClass().getSimpleName());
		try {
			while(true) {
				// CPU 과부하 방지
				Thread.sleep(100);
				
				GameProtocol protocol = (GameProtocol) user.getIn().readObject();
				if(protocol == null)
					throw new IOException("Null pointer received...");
				
				switch(protocol.getProtocol()) {
					case GameProtocol.PT_BROADCAST_USER_LIST_IN_WAITING_ROOM:	// 유저 리스트 조회
						this.userList = (ArrayList) protocol.getData();
						drawUserList(this.userList);
						break;
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (EOFException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
		} finally {
			System.out.printf("[%s] 종료..\n", this.getClass().getSimpleName());
		}
		
	}
	
	/* 유저 리스트 행 패널 그리기*/
	public void drawUserList(ArrayList<User> userList) {
		int i = 0;
		
		while(i < userList.size()) {
			rows[i].setUserName(userList.get(i).getNickname());
			rows[i].setUserState(userList.get(i).getUserState());
			i++;
		}
		
		while(i < rows.length) {
			rows[i].setUserName("");
			rows[i].setUserState(0);
			i++;
		}
		
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getRoomTitle() {
		return roomName;
	}

	public void setRoomTitle(String roomTitle) {
		this.roomName = roomTitle;
	}

}