package client.game;

public class Camera {
	
	private int x, y;
	
	public Camera(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public  void  tick(GameObject object) {
//		x += ((object.getX() - x) - 1280/2) * 0.05f;
//		y += ((object.getY() - y) - 720/2) * 0.05f;
		x = ((object.getX() - 1280/2) + object.width/2);
		y = ((object.getY() - 720/2) + object.height/2);
		
		//if(x <= 0) x = 0;
		//if(x >= (2208 - 1280 + 18)) x = 2208 - 1280 + 18;
		//if(y <= 0) y = 0;
		//if(y >= (2208 - 720 + 47)) y = 2208 - 720 + 47;		
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
	
}
