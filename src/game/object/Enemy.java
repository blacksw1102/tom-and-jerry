package game.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import game.manager.Animation;
import game.manager.Handler;
import game.manager.SpriteSheet;

public class Enemy extends GameObject {

	private Handler handler;
	Random r = new Random();
	int choose = 0;
	int hp = 100;
	
	private BufferedImage[] enemy_image = new BufferedImage[3];
	Animation anim;
	
	public Enemy(int x, int y, ID id, Handler handler, SpriteSheet ss) {
		super(x, y, id, ss);
		this.handler = handler;
		
		enemy_image[0] = ss.grabImage(4, 1, 32, 32);
		enemy_image[1] = ss.grabImage(5, 1, 32, 32);
		enemy_image[2] = ss.grabImage(6, 1, 32, 32);
		
		anim = new Animation(3, enemy_image[0], enemy_image[1], enemy_image[2]);
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		choose = r.nextInt(10);
		
		for(int i = 0; i <handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Block) {
				if(getBounds().intersects(tempObject.getBounds())) {
					x -= velX;
					y -= velY;
				}
			}
			
			if(tempObject.getId() == ID.Bullet) {
				if(getBounds().intersects(tempObject.getBounds())) {
					hp -= 50;
					if(hp <= 0) handler.removeObject(this);
				}
			}
		}
		
		if(choose == 0) {
			velX = (r.nextInt(4 - -4) + -4);
			velY = (r.nextInt(4 - -4) + -4);
		}
		
		anim.runAnimation();
		
	}

	@Override
	public void render(Graphics g) {
		//g.setColor(Color.yellow);
		//g.fillRect(x, y, 32, 32);
		anim.drawAnimation(g, x, y, 0);
		
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.green);
		g2d.draw(getBoundsBig());
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}
	
	public Rectangle getBoundsBig() {
		return new Rectangle(x-16, y-16, 64, 64);
	}
	
}
