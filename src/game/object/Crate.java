package game.object;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.manager.SpriteSheet;

public class Crate extends GameObject {
	
	private BufferedImage crate_image;
	
	public Crate(int x, int y, ID id, SpriteSheet ss) {
		super(x, y, id, ss);
		crate_image = ss.grabImage(6, 2, 32, 32);
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		// g.setColor(Color.cyan);
		// g.fillRect(x, y, 32, 32);
		g.drawImage(crate_image, x, y, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}

}
