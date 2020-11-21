package game.client.object;

import java.util.Vector;

public class Users {
	private Vector<User> users;

	public Users() {
		users = new Vector<>();
	}

	public Vector<User> getUsers() {
		return users;
	}
	
	public void addUser(User user) {
		this.users.addElement(user);
	}
	
	public boolean contains(String nName) {
		for(User u : users) {
			if(u.getnName().equals(nName))
				return true;
		}
		return false;
	}
	
	public User getUser(String nName) {
		for (User o : users) {
			if(o.getnName().equals(nName))
				return o;
		}
		return null;
	}

	public void removeUser(String nName) {
		nName = nName.trim();
		String temp = "";
		for (int i = 0; i < users.size(); i++) {
			User u = (User) users.elementAt(i);
			temp = u.getnName();
			if (temp.equals(nName)) {
				users.removeElement(u);
			}
		}
	}

	public int getSize() {
		return this.users.size();
	}

	public void users_SetX(int x) {
		for (Object o : users) {
			User u = (User) o;
			u.setX(u.getX() + x);
		}
	}

	public void users_SetY(int y) {
		for (Object o : users) {
			User u = (User) o;
			u.setY(u.getY() + y);
		}
	}

}
