package client.game;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import client.gui.GamePanel;

public class KeyInput extends KeyAdapter {

	private GamePanel gamePanel;
	private Player player;
	
	public KeyInput(GamePanel gamePanel, Player player) {
		this.gamePanel = gamePanel;
		this.player = player;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			player.keyBuff_LEFT = true;
			break;
		case KeyEvent.VK_UP:
			player.keyBuff_UP = true;
			break;
		case KeyEvent.VK_RIGHT:
			player.keyBuff_RIGHT = true;
			break;
		case KeyEvent.VK_DOWN:
			player.keyBuff_DOWN = true;
			break;
		case KeyEvent.VK_SPACE:
			if(player.isDead())
				gamePanel.changeWatchPlayer();
		default:
			break;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			player.keyBuff_LEFT = false;
			break;
		case KeyEvent.VK_UP:
			player.keyBuff_UP = false;
			break;
		case KeyEvent.VK_RIGHT:
			player.keyBuff_RIGHT = false;
			break;
		case KeyEvent.VK_DOWN:
			player.keyBuff_DOWN = false;
			break;
		}
		
		if (player.status == 1)
			player.status = 0;
	}
	
}
