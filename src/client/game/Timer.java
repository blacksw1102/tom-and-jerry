
package client.game;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;

import client.gui.GamePanel;

public class Timer extends JFrame implements Runnable {
	private GamePanel gamePanel;
	
	private int minute = 0, second = 5;
	
	private Thread t;

	public Timer(GamePanel gamePanel, int minute, int second) {
		this.gamePanel = gamePanel;
		this.minute = minute;
		this.second = second;

		this.t = new Thread(this);
	}
	
	public void start() {
		t.start();
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (minute != 0 && second == 0) {
				minute--;
				second += 59;
			} else
				second--;
			if (minute == 0 && second == 0) {
				gamePanel.timeOver();
				t.interrupt();
				break;
			}
		}
	}
	
	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public void paint(Graphics g) {
//		bufferImage = createImage(1280, 720);
//		screenGraphic = bufferImage.getGraphics();
//		screenDraw(screenGraphic);
//		g.drawImage(bufferImage, 0, 0, null);
		
	}

	public void screenDraw(Graphics g) {
//		g.setColor(Color.WHITE);
//		g.setFont(new Font("HY견고딕", Font.PLAIN, 50));
//		String str = String.format("%02d : %02d", minute, second);
//		int width = g.getFontMetrics().stringWidth(str);
//		g.drawString(str, (640 - width / 2), 80);
//		this.repaint();
	}
	
}
