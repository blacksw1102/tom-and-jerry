package client.game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Cheese extends GameObject {

	private static final int CHEESE_WIDTH = 32;
	private static final int CHEESE_HEIGHT = 32;
	
	public Cheese(int x, int y, ID id, BufferedImage spritesheet) {
		super(x, y, id, CHEESE_WIDTH, CHEESE_HEIGHT, spritesheet);
	}
	
	@Override
	public void process() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(spritesheet, x, y, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}

}
