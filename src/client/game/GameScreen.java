package client.game;

import javax.swing.JFrame;

import client.entity.User;

public class GameScreen extends JFrame {
	
	private User user;
	
	public GameScreen(User user) {
		this.user = user;
		
		// 플레이어 리스트 받아와서 저장
		
		// 본인 플레이어를 플레이어 리스트에서 찾아서 따로 변수로 관리
		
		
		
		setSize(1280, 720);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
