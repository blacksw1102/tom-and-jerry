package client.game;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;

import client.entity.User;
import client.gui.GamePanel;
import server.util.GameProtocol;

public abstract class Player extends GameObject {
	
	protected int dir;		// ĳ���Ͱ� ���� ���� 0-��, 1-��
	protected int status;	// ĳ������ ����

	protected int speed;
	protected int velX;
	protected int velY;
	
	protected boolean isDead;
	protected boolean isHide;
	
	protected boolean keyBuff_UP;
	protected boolean keyBuff_DOWN;
	protected boolean keyBuff_LEFT;
	protected boolean keyBuff_RIGHT;
	
	protected GamePanel gameScreen;
	protected AnimatedSprite sprite;
	
	protected Handler handler;
	
	protected User user;
	protected String nickname;
	
	public Player(User user, GamePanel gameScreen, int x, int y, int width, int height, int speed,
			ID id, BufferedImage spritesheet, BufferedImage spritesheet_flipx, Handler handler) {
		super(x, y, id, width, height, spritesheet, spritesheet_flipx);
		
		this.user = user;
		this.nickname = user.getNickname();
		this.gameScreen = gameScreen;
		this.speed = speed;
		this.velX = 0;
		this.velY = 0;
		this.dir = 1;
		this.sprite = new AnimatedSprite(this);
		this.handler = handler;
		
		this.isDead = false;
		this.isHide = false;
		
		sprite.setIdle();
	}
	
	@Override
	public String toString() {
		return String.format("nickname:%s, x:%d, y:%d, dir:%d, status:%d", nickname, x, y, dir, status);
	}
	
	public ObjectInputStream getIn() {
		return user.getIn();
	}

	public ObjectOutputStream getOut() {
		return user.getOut();
	}

	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public void process() {
		if(getOut() != null && !isDead()) {
			move();
			eatCheese();
			dead();
		}
		if(!isDead) {
			keyProcess();
			spriteProcess();
		}
	}
	
	// 캐릭터 이동 기능
	public void move() {
		x += velX;
		collisionX();
		y += velY;
		collisionY();

		GameProtocol protocol = new GameProtocol(
				GameProtocol.PT_PLAYER_MOVE, 
				user.getNickname() + " " + getX() 
				+ " " + getY() + " " + getDir() + " " + getStatus());
		sendMessage(protocol);
	}
	
	// x 좌표 충돌여부 확인
	private synchronized void collisionX() {
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
	
	// y 좌표 충돌여부 확인
	private synchronized void collisionY() {
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
	
	// 치즈 먹기 기능
	public synchronized void eatCheese() {
		if(id != ID.JERRY)
			return;
		
		GameObject tempObject = null;
		for(int i = 0; i < handler.object.size(); i++) {
			tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.CHEESE) {
				if(getBounds().intersects(tempObject.getBounds())) {
					handler.removeObject(tempObject);
					gameScreen.decreaseCheese();
					
					int x = tempObject.getX();
					int y = tempObject.getY();
					String data = user.getNickname() + " " + x + " " + y;
					GameProtocol protocol = new GameProtocol(GameProtocol.PT_EAT_CHEESE, data);
					
					sendMessage(protocol);
				}
			}
		}
	}
	
	// 제리가 톰에게 죽음
	public synchronized void dead() {
		if(id != ID.TOM) 
			return;
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.JERRY && getBounds().intersects(tempObject.getBounds())) {
				((Player) tempObject).setDead(true);
				handler.removeObject(tempObject);
				gameScreen.decreaseJerryCount();
				
				String deadNickname = ((Player) tempObject).getNickname();
				String data = user.getNickname() + " " + deadNickname;
				GameProtocol protocol = new GameProtocol(GameProtocol.PT_KILLED_JERRY, data);
				
				sendMessage(protocol);
			}
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		if(!isDead) {
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
	
	public void sendMessage(GameProtocol protocol) {
		try {
			user.getOut().writeObject(protocol);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public boolean isHide() {
		return isHide;
	}

	public void setHide(boolean isHide) {
		this.isHide = isHide;
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

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
