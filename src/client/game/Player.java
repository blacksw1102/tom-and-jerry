package client.game;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import client.gui.GamePanel;

public abstract class Player extends GameObject {
	
	protected int dir;		// ĳ���Ͱ� ���� ���� 0-��, 1-��
	protected int status;	// ĳ������ ����

	protected int speed;
	protected int velX;
	protected int velY;
	
	boolean keyBuff_UP;
	boolean keyBuff_DOWN;
	boolean keyBuff_LEFT;
	boolean keyBuff_RIGHT;
	
	protected GamePanel gameScreen;
	protected AnimatedSprite sprite;
	
	private Handler handler;
	
	public Player(GamePanel gameScreen, int x, int y, int width, int height, int speed,
			ID id, BufferedImage spritesheet, BufferedImage spritesheet_flipx, Handler handler) {
		super(x, y, id, width, height, spritesheet, spritesheet_flipx);
		
		this.gameScreen = gameScreen;
		this.speed = speed;
		this.velX = 0;
		this.velY = 0;
		this.dir = 1;
		this.sprite = new AnimatedSprite(this);
		this.handler = handler;
		
		sprite.setIdle();
	}
	
	@Override
	public void process() {
		x += velX;
		collisionX();
		y += velY;
		collisionY();

		keyProcess();
		spriteProcess();
	}
	
	@Override
	public void render(Graphics2D g) {
		BufferedImage img = null;
		int sx = (sprite.frameList.get(sprite.nowFrame) % 10) * width;
		int sy = (sprite.frameList.get(sprite.nowFrame) / 10) * height;
		
		if(dir == 0)
			img = spritesheet_flipx;
		else 
			img = spritesheet;
		
		BufferedImage imgZoom = gameScreen.getScaledImage(img, img.getWidth() * 2, img.getHeight() * 2);
		//g.setColor(Color.yellow);
		//g.fillRect((int)x, (int)y, width, height);
		gameScreen.drawImageClip(g, imgZoom, (int) x, (int) y, sx, sy, width, height, 7);		

	}
	
	public void spriteProcess() {
		sprite.process();
	}
	
	void keyProcess() {
		if (keyBuff_LEFT && status != 3) {
			if (status != 2) {
				dir = 0;
				status = 1;
			}
		}
		if (keyBuff_RIGHT && status != 3) {
			if (status != 2) {
				dir = 1;
				status = 1;
			}
		}
		if (keyBuff_UP && status != 3) {
			if (status != 2)
				status = 1;
		}
		if (keyBuff_DOWN && status != 3) {
			if (status != 2)
				status = 1;
		}
		
		if(isUp()) 
			velY = -speed;
		else if(isDown()) 
			velY = speed;
		else
			velY = 0;

		if(isRight()) 
			velX = speed;
		else if(isLeft()) 
			velX = -speed;
		else
			velX = 0;
		
	}
	
	private void collisionX() {
		for(int i = 0; i < handler.object.size(); i++) {
			
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Block) {
				if(getBounds().intersects(tempObject.getBounds())) {
					x += velX * -1;
					//y += velY * -1;
				}
			}
			
		}
	}
	
	private void collisionY() {
		for(int i = 0; i < handler.object.size(); i++) {
			
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Block) {
				if(getBounds().intersects(tempObject.getBounds())) {
					//x += velX * -1;
					y += velY * -1;
				}
			}
			
		}
	}
	
	public boolean isUp() {
		return keyBuff_UP;
	}
	
	public boolean isDown() {
		return keyBuff_DOWN;		
	}
	
	public boolean isLeft() {
		return keyBuff_LEFT;		
	}
	
	public boolean isRight() {
		return keyBuff_RIGHT;		
	}
	
}
