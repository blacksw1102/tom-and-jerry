package client.game;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Block extends GameObject {

	private static final int BLOCK_WIDTH = 32;
	private static final int BLOCK_HEIGHT = 32;
	
	public Block(int x, int y, ID id) {
		super(x, y, id, BLOCK_WIDTH, BLOCK_HEIGHT, null);
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}
}
