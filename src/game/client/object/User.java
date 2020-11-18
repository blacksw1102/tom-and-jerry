package game.client.object;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

public class User {
	private String nName;
	private String ip;
	private boolean role = false;
	private int x, y;
	private Rectangle uArea;
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	private Image img;
	private ArrayList<Image> moveImg;
	private ArrayList<Image> stopImg;
	private ArrayList<Image> slashImg;

	private boolean ready = false;
	public boolean escape = false;
	private int way = 3;
	private boolean slash = false;
	private boolean moving = false;
	private boolean hiding = false;

	public User(String nName) {
		this.nName = nName;

	}

	public void createArea() {
		if (!role)
			uArea = new Rectangle(x + 3, y + 19, 18, 11);
		else
			uArea = new Rectangle(x + 8, y + 35, 20, 13);
	}

	public void setRole(boolean b) {
		this.role = b;
	}

	public boolean isRole() {
		return role;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public boolean isHiding() {
		return hiding;
	}

	public void setHiding(boolean hide) {
		this.hiding = hide;
	}

	public String getnName() {
		return nName;
	}

	public void setX(int x) {
		this.x = x;
		if (uArea != null)
			if (!role)
				uArea.setLocation(x + 3, y + 19);
			else
				uArea.setLocation(x + 9, y + 35);
	}

	public void setY(int y) {
		this.y = y;
		if (uArea != null)
			if (!role)
				uArea.setLocation(x + 3, y + 19);
			else
				uArea.setLocation(x + 9, y + 35);
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public Rectangle getArea() {
		return this.uArea;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void setMoveImg(ArrayList<Image> moveImg) {
		this.moveImg = moveImg;

	}

	public void setStopImg(ArrayList<Image> stopImg) {
		this.stopImg = stopImg;
		this.img = stopImg.get(3);
	}

	public void setSlashImg(ArrayList<Image> slashImg) {
		this.slashImg = slashImg;

	}

	public boolean isSlash() {
		return slash;
	}

	public void setSlash(boolean slash) {
		this.slash = slash;
	}
	
	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public void setImg(int x, int y) {
		int n;

		if (!role)
			n = 5;
		else
			n = 6;

		if (moving) {

			if (x == this.x - n) {
				this.img = moveImg.get(0);
				way = 0;
			} else if (x == this.x + n) {
				this.img = moveImg.get(1);
				way = 1;
			} else if (y == this.y - n) {
				this.img = moveImg.get(2);
				way = 2;
			} else if (y == this.y + n) {
				this.img = moveImg.get(3);
				way = 3;
			}
		} else if (!moving) {
			this.img = stopImg.get(way);
		}

	}

	public void setSImg() {
		this.img = slashImg.get(way);
	}

	public Image getImg() {
		return this.img;
	}

}
