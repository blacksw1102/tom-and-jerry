package client.gui;

import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JFrame;

import client.entity.User;
import client.game.Player;
import client.game.Tom;
import server.util.GameProtocol;

public class GameScreen extends JFrame {
	
	private CardLayout cards = new CardLayout();
	
	final public int gGameWidth = 1280;
	final public int gGameHeight = 720;
	
	
	
	public GameScreen(User user, Hashtable<String, User> userList) {

		this.setLayout(cards);
		this.setSize(gGameWidth, gGameHeight);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.add("loading", new LoadingPanel(this));
		this.setVisible(true);
		this.add("game", new GamePanel(this, user, userList));
		
		
		// 플레이어 리스트 받아와서 저장
		// 본인 플레이어를 플레이어 리스트에서 찾아서 따로 변수로 관리
		
	}
	
	public void changeGameScreen() {
		cards.next(this.getContentPane());
	}

}
