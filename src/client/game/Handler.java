package client.game;
import java.awt.Graphics2D;
import java.util.LinkedList;

public class Handler {

	public LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	public void process() {
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			tempObject.process();
		}
	}
	
	public void render(Graphics2D g) {
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			tempObject.render(g);
		}
	}
	
	public void addObject(GameObject tempObject) {
		object.add(tempObject);
	}
	
	public void removeObject(GameObject tempObject) {
		object.remove(tempObject);
	}
	
}
