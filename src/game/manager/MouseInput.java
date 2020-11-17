package game.manager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import game.main.Game;

public class MouseInput extends MouseAdapter {
	
	private Handler handler;
	private Camera camera;
	private Game game;
	private SpriteSheet ss;
	
	public MouseInput(Handler handler, Camera camera, Game game, SpriteSheet ss) {
		this.handler = handler;
		this.camera = camera;
		this.game = game;
		this.ss = ss;
	}
	
	public void mousePressed(MouseEvent e) {

	}

}
