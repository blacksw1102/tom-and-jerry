package client.game;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import client.entity.User;
import client.gui.GamePanel;

public class Jerry extends Player {

	private static final int JERRY_WIDTH = 64;
	private static final int JERRY_HEIGHT = 64;
	private static final int JERRY_SPEED = 4;
	
	public Jerry(User user, GamePanel gameScreen, int x, int y, ID id,
			BufferedImage spritesheet, BufferedImage spritesheet_flipx, Handler handler) {
		super(user, gameScreen, x, y, JERRY_WIDTH, JERRY_HEIGHT, JERRY_SPEED, id, spritesheet, spritesheet_flipx, handler);
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, JERRY_WIDTH, JERRY_HEIGHT);
	}

}
