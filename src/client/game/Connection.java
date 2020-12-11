package client.game;

import java.io.EOFException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

import client.gui.GamePanel;
import client.gui.GameScreen;
import server.entity.ServerUser;
import server.util.GameProtocol;

public class Connection extends Thread {
	
	private GamePanel gamePanel;
	private Handler handler;
	private Player player;
	private Hashtable<String, Player> playerList;
	
	public Connection(GamePanel gamePanel, Handler handler, Player player, Hashtable<String, Player> playerList) {
		this.gamePanel = gamePanel;
		this.handler = handler;
		this.player = player;
		this.playerList = playerList;
	}
	
	@Override
	public void run() {
		System.out.printf("[%s] 작동 중..\n", this.getClass().getSimpleName());
		while(!Thread.currentThread().isInterrupted()) {
			try {
				GameProtocol protocol = (GameProtocol) player.getIn().readObject();
				if(protocol == null)
					throw new IOException("Null pointer received...");
				
				switch(protocol.getProtocol()) {
					case GameProtocol.PT_PLAYER_MOVE: {
						String[] data = ((String) protocol.getData()).split(" ");
						String nickname = data[0];
						int x = Integer.valueOf(data[1]);
						int y = Integer.valueOf(data[2]);
						int dir = Integer.valueOf(data[3]);
						int status = Integer.valueOf(data[4]);
						Player p = playerList.get(nickname);
						p.setX(x);
						p.setY(y);
						p.setDir(dir);
						p.setStatus(status);
						break;
					}
					case GameProtocol.PT_EAT_CHEESE: {
						String[] data = ((String) protocol.getData()).split(" ");
						int x = Integer.parseInt(data[1]);
						int y = Integer.parseInt(data[2]);
						
						GameObject tempObject = null;
						for(int i = 0; i < handler.object.size(); i++) {
							tempObject = handler.object.get(i);
							if(tempObject.getId() == ID.CHEESE 
									&& tempObject.getX() == x 
									&& tempObject.getY() == y) {
								handler.removeObject(tempObject);
								gamePanel.decreaseCheese();
							}
						}
						
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		System.out.printf("[%s] 종료..\n", this.getClass().getSimpleName());
	}
	
}
