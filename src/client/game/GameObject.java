package client.game;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class GameObject {

	protected int x, y;
	protected int width, height;
	protected ID id;
	public BufferedImage spritesheet;// �ִϸ��̼ǿ� �̹���	
	public BufferedImage spritesheet_flipx;// �ִϸ��̼ǿ� �̹���(���ʺ���)
	
	public GameObject(int x, int y, ID id, int width, int height, BufferedImage spritesheet) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.width = width;
		this.height = height;
		this.spritesheet = spritesheet;
	}
	
	public GameObject(int x, int y, ID id, int width, int height, BufferedImage spritesheet, BufferedImage spritesheet_flipx) {
		this(x, y, id, width, height, spritesheet);
		this.spritesheet_flipx = spritesheet_flipx;
	}
	
	// object update
	public abstract void process();
	public abstract void render(Graphics2D g);
	public abstract Rectangle getBounds();
	
	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}
