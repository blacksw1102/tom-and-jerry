package game.client.player;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;

import game.client.connect.Connection;
import game.client.object.CreateUserImg;
import game.client.object.User;
import game.client.object.Users;

public class Player implements Action {
	private Connection conn;
	private String nName;

	private int x, y;

	private Image img;
	private Image blackView;
	private int way = 4;
	private ArrayList<Image> moveImg;
	private ArrayList<Image> stopImg;
	private ArrayList<Image> slashImg;
	private Rectangle pArea;
	private boolean[] isKeyPressed;

	private ArrayList<Action> actions;

	private boolean itemKey = false;
	private boolean moving = false;
	private boolean slash = false;
	private boolean hiding = false;

	public boolean role = false;
	public boolean escape = false;

	public Player(String nName, Connection conn) {
		this.conn = conn;
		isKeyPressed = new boolean[41];
		actions = new ArrayList<>();
		this.nName = nName;
		pArea = new Rectangle(x + 3, y + 9, 20, 13);
		blackView = Toolkit.getDefaultToolkit().getImage("sk/skVIew.png");

	}

	public void initImg(CreateUserImg cui) {
		//CreateUserImg cui = new CreateUserImg();
		if (!this.role) {
			moveImg = cui.getMoveImg();
			stopImg = cui.getStopImg();

		} else {
			moveImg = cui.getSkMoveImg();
			stopImg = cui.getSkStopImg();
			slashImg = cui.getSkSlashImg();
			pArea = new Rectangle(x + 8, y + 35, 20, 13);

		}

	}
	
	public boolean isMoving() {
		return this.moving;
	}

	public Image getBlackView() {
		return this.blackView;
	}

	public void setMoveImg(ArrayList<Image> moveImg) {
		this.moveImg = moveImg;

	}

	public void setStopImg(ArrayList<Image> stopImg) {
		this.stopImg = stopImg;

	}

	public boolean isHidding() {
		return hiding;
	}

	public void setHidding(boolean hide) {
		this.hiding = hide;
	}

	public void myRole() {
		for (Object o : conn.getUsers().getUsers()) {
			User u = (User) o;
			if (u.getnName().equals(this.getNname())) {
				this.role = u.isRole();
				conn.role = u.isRole();
			}
		}

	}

	public Connection getConn() {
		return this.conn;
	}

	public void addActions(Action a) {
		this.actions.add(a);
	}

	public ArrayList<Action> getActions() {
		return this.actions;
	}

	public boolean[] getIKP() {
		return this.isKeyPressed;
	}

	public void setMoving(boolean b) {
		this.moving = b;
	}

	public boolean isSlash() {
		return slash;
	}

	public void setSlash(boolean slash) {
		this.slash = slash;
	}

	public boolean isItemKey() {
		return itemKey;
	}

	public void setItemKey(boolean itemKey) {
		this.itemKey = itemKey;
	}

	public Image getImg() {
		if (moving == true) {
			img = moveImg.get(way - 1);
		} else if (slash == true) {
			img = slashImg.get(way - 1);
		} else
			img = stopImg.get(way - 1);

		return img;
	}

	public void setPX(int x) {
		this.x = x;
		if (!role)
			pArea.setLocation(x + 3, y + 19);
		else
			pArea.setLocation(x + 9, y + 35);
	}

	public void setPY(int y) {
		this.y = y;
		if (!role)
			pArea.setLocation(x + 3, y + 19);
		else
			pArea.setLocation(x + 9, y + 35);
	}

	public int getPX() {
		return this.x;
	}

	public int getPY() {
		return this.y;
	}

	public String getNname() {
		return this.nName;
	}

	public void setPlayerWay(int w) {
		this.way = w;
	}

	public int getPlayerWay() {
		return this.way;
	}

	public Rectangle getArea() {
		return this.pArea;
	}

	public void initPlayerXY(Users user) {
		for (Object o : user.getUsers()) {
			User u = (User) o;
			if (u.getnName().equals(this.nName)) {
				this.setPX(u.getX());
				this.setPY(u.getY());
			}
		}
	}

	@Override
	public void action() {
		for (Action a : actions) {
			a.action();
		}
	}
}
