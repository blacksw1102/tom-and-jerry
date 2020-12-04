package client.gui;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import client.entity.User;

public class Test extends JFrame {

	private JPanel contentPane;
	
	int roomId;
	String roomTitle;
	private static final int MAX_PLAYER_COUNT = 4;
	
	public static void main(String[] args) {
		Test frame = new Test();
	}

	/**
	 * 프레임 생성
	 */
	public Test() {
		setRoomId(1);
		setRoomTitle("테스트 제목입니다.");
		init();
	}
	
	public void init() {
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
		
		JLabel lblRoomTitle = new JLabel(roomTitle);
		lblRoomTitle.setHorizontalAlignment(SwingConstants.LEFT);
		lblRoomTitle.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		lblRoomTitle.setBounds(74, 12, 224, 36);
		titlePanel.add(lblRoomTitle);
		
		drawUserList(null);
		
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
		
		this.setVisible(true);
	}
	
	/* 유저 리스트 행 패널 그리기*/
	public void drawUserList(ArrayList<User> userList) {
		int startX = 30;
		int startY = 107;
		
		for(int i=0; i<3; i++) {
			drawUserListRow(startX, startY, "admin", 1);
			startY = startY + 60 + 5;
		}
		
		for(int i=0; i<MAX_PLAYER_COUNT - 3; i++) {
			drawUserListRow(startX, startY, "admin", 1);
			startY = startY + 60 + 5;
		}
		
	}
	
	public void drawUserListRow(int x, int y, String name, int userState) {
		JPanel userRowPanel;
		JLabel lblUserName, lblUserState;

		Color color = null;
		String userStateStr = null;
		
		final String USER_STATE_NOT_READY = "대기";
		final String USER_STATE_READY = "준비";
		
		userRowPanel = new JPanel();
		userRowPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		userRowPanel.setBackground(Color.WHITE);
		userRowPanel.setBounds(x, y, 380, 60);
		contentPane.add(userRowPanel);
		userRowPanel.setLayout(null);
		
		lblUserName = new JLabel(name);
		lblUserName.setHorizontalAlignment(SwingConstants.LEFT);
		lblUserName.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		lblUserName.setBounds(14, 12, 127, 36);
		userRowPanel.add(lblUserName);
		
		if(userState == 0) {
			color = new Color(240, 240, 240);
			userStateStr = USER_STATE_NOT_READY;
		} else {
			color = new Color(255, 165, 0);
			userStateStr = USER_STATE_READY;
		}
		
		lblUserState = new JLabel(userStateStr);
		lblUserState.setOpaque(true);
		lblUserState.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblUserState.setBackground(color);
		lblUserState.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserState.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		lblUserState.setBounds(303, 12, 65, 36);
		userRowPanel.add(lblUserState);
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getRoomTitle() {
		return roomTitle;
	}

	public void setRoomTitle(String roomTitle) {
		this.roomTitle = roomTitle;
	}
	
}
