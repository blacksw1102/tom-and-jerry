package client.game;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import client.gui.GamePanel;

public class Tom extends Player {

	private static final int TOM_WIDTH = 64;
	private static final int TOM_HEIGHT = 80;
	private static final int TOM_SPEED = 4;
	
	public Tom(GamePanel gameScreen, int x, int y, 
			ID id, BufferedImage spritesheet, BufferedImage spritesheet_flipx, Handler handler) {
		super(gameScreen, x, y, TOM_WIDTH, TOM_HEIGHT, TOM_SPEED, id, spritesheet, spritesheet_flipx, handler);
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, TOM_WIDTH, TOM_HEIGHT);
	}
	
}
