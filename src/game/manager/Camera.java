package game.manager;

import game.object.GameObject;

public class Camera {
	
	private float x, y;
	
	public Camera(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void tick(GameObject object) {
		
//		x += ((object.getX() - x) - 1000/2) * 0.05f;
//		y += ((object.getY() - y) - 563/2) * 0.05f;
		x = ((object.getX()) - 1000/2 + 32);
		y = ((object.getY()) - 563/2 + 32);
		
		if(x <= 0) x = 0;
		if(x >= (2048 - 1000 + 18)) x = 2048 - 1000 + 18;
		if(y <= 0) y = 0;
		if(y >= (1952 - 563 + 47)) y = 1952 - 563 + 47;
		
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
}
