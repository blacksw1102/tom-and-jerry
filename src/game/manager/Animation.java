package game.manager;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation {
	private int speed;
	private int frames;
	private int index = 0;
	private int count = 0;
	private BufferedImage[] fotos;
	private BufferedImage currentImg;

	public Animation(int speed, BufferedImage... imgs) {
		int i = 0;
		frames = imgs.length;
		fotos = new BufferedImage[imgs.length];

		for (BufferedImage img : imgs) {
			fotos[i] = img;
			i++;
		}
		this.speed = speed;

	}

	public void runAnimation() {
		index++;
		if (index > speed) {
			index = 0;
			nextFrame();
		}
	}

	public void nextFrame() {
		currentImg = fotos[count];
		count++;
		if (count > frames - 1)
			count = 0;
	}

	public void drawAnimation(Graphics g, double x, double y, int offset) {
		g.drawImage(currentImg, (int) x - offset, (int) y, null);
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

}