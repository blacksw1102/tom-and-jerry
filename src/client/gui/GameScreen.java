package client.gui;

import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import client.entity.User;
import server.util.GameProtocol;

public class GameScreen extends JFrame {
	
	private CardLayout cards = new CardLayout();
	
	public GameScreen(User user) {

		setLayout(cards);
		setSize(1280, 720);
		setLocationRelativeTo(null);
		this.add("loading", new LoadingPanel(this));
		this.add("game", new GamePanel(this, user));
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		setVisible(true);
		
		
		// 플레이어 리스트 받아와서 저장
		
		// 본인 플레이어를 플레이어 리스트에서 찾아서 따로 변수로 관리
		
		
		
	}

	public void changeGameScreen() {
		cards.next(this.getContentPane());
	}

}
