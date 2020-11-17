package game.client.main;

import java.awt.Canvas;

public class Game extends Canvas implements Runnable {

	public Game() {
		new Window(1280, 720, "Tom and Jerry", this);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		new Game();
	}

}
