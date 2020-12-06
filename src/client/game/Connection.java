package client.game;

import java.io.EOFException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

import client.gui.GameScreen;
import server.entity.ServerUser;
import server.util.GameProtocol;

public class Connection extends Thread {
	
	private Player player;
	 private Hashtable<String, Player> playerList;
	
	public Connection(Player player, Hashtable<String, Player> playerList) {
		this.player = player;
		this.playerList = playerList;
	}
	
	@Override
	public void run() {
		System.out.printf("[%s] 작동 중..\n", this.getClass().getSimpleName());
		try {
			while(true) {
				GameProtocol protocol = (GameProtocol) player.getIn().readObject();
				if(protocol == null)
					throw new IOException("Null pointer received...");
				
				switch(protocol.getProtocol()) {
					case GameProtocol.PT_PLAYER_MOVE:
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
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (EOFException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.printf("[%s] 종료..\n", this.getClass().getSimpleName());
		}
	}

}
