package game.client.frame;

import java.awt.Graphics;

import javax.swing.JFrame;

import game.client.connect.Connection;
import game.client.object.Users;
import game.client.player.Player;


public class GameView extends FrameView {

	private Connection conn;
	private Users users;
	private Player player;
	
	public GameView(Connection conn) {
		
		this.conn = conn;
		this.conn.setView(this);
		this.users = conn.getUsers();
		this.player = new Player(conn.getNname(), conn);
		
		createFrame();
		// startView();
	}
	
	public void setUsersImg() {

	}

	public void createFrame() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setBounds(150, 100, 800, 600);
		setTitle("gameFrame");
		// this.addKeyListener(ke);
		setResizable(false);
		setVisible(true);

	}

	public void start_View() {
	}

	@Override
	public void paint(Graphics g) {

	}

	@Override
	public void update(Graphics g) {

	}

	public void drawImg() {

	}

	public void drawUsers() {
	}

	public void drawGameOver(Graphics g) {
	}

	public void drawKey(int n, Graphics g) {
	}

	public void startBackgroundMusic() {
	}

	@Override
	public void view() {
		// TODO Auto-generated method stub
		
	}

}
