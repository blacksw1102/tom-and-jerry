package client.gui;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import client.entity.User;
import server.util.GameProtocol;

public class GamePanel extends JPanel implements Runnable {

	private User user;
	
    private Thread t;
    private GameScreen gameScreen;
    
    public GamePanel(GameScreen gameScreen, User user) {
    	this.gameScreen = gameScreen;
    	this.user = user;
    	
        t = new Thread(this);
        t.start();
        
    }
    
	@Override
	public void run() {
		System.out.printf("[%s] 작동 중..\n", this.getClass().getSimpleName());
		try {
			while(true) {
				// CPU 과부하 방지
				Thread.sleep(100);
				
				GameProtocol protocol = (GameProtocol) user.getIn().readObject();
				if(protocol == null)
					throw new IOException("Null pointer received...");
				switch(protocol.getProtocol()) {
					case GameProtocol.PT_BROADCAST_PLAYER_ROLE:
						String[] datas = ((String) protocol.getData()).split(" ");
						int role = Integer.parseInt(datas[0]);
						int x = Integer.parseInt(datas[1]);
;						int y = Integer.parseInt(datas[2]);
						System.out.println("role : " + role);
						System.out.println("x : " + x);
						System.out.println("y : " + y);
						break;
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (EOFException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
		} finally {
			System.out.printf("[%s] 종료..\n", this.getClass().getSimpleName());
		}
	}

}
