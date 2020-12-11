package client.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import client.game.Timer;

public class TestPanel extends Canvas implements Runnable {

	private BufferedImage cheeseImg;
	private int remainCheeseCount = 7;
	
	private BufferedImage tomAliveImg;
	private BufferedImage jerryAliveImg;
	private BufferedImage jerryDeadImg;

	private BufferedImage minimapImg;
	
	private Timer timer;
	
	private Thread t;
	
	public TestPanel() {
		this.setSize(1280, 720);

		cheeseImg = loadImage("/cheese_sprites.png");
		tomAliveImg = loadImage("/tom_alive.png");		
		jerryAliveImg = loadImage("/jerry_alive.png");
		jerryDeadImg = loadImage("/jerry_dead2.png");
		minimapImg = loadImage("/mini_map.png");
		
		setVisible(true);

		// 타이머 시작
		//timer = new Timer(5, 0);
		timer.start();
		
		t = new Thread(this);
		t.start();
	}
	
	public void start() {
		//t.start();
	}
	
	@Override
	public void run() {
		int delay = 30; // 루프 딜레이. 1/1000초 단위.
		long pretime;	// 루프 간격을 조절하기 위한 시간 체크값
		
		try {
			System.out.printf("[%s] 작동 중..\n", this.getClass().getName());
			while (true) {
				pretime = System.currentTimeMillis();

				synchronized(this) {
					repaint();
				}
				
				long nowTime = System.currentTimeMillis();
				if (nowTime - pretime < delay)
					Thread.sleep(delay - (nowTime - pretime) < 0 ? 1 : delay - (nowTime - pretime));
			}
		} catch(InterruptedException e) {
			
		} finally {
			System.out.printf("[%s] 작동 종료..\n", this.getClass().getName());
		}
		
	}
	
	@Override
	public void update(Graphics gg) {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		
		g.clearRect(0, 0, 1280, 720);
		
		renderBackground(g);
		renderTimer(g);
		renderRemainCheeseInfo(g);
		renderPlayerInfo(g);
		renderMinimap(g);
		
		g.dispose();
		bs.show();
		
	}
	
	void renderBackground(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, 1280, 720);
	}
	
	void renderTimer(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("HY견고딕", Font.PLAIN, 50));
		String str = String.format("%02d : %02d", timer.getMinute(), timer.getSecond());
		int width = g.getFontMetrics().stringWidth(str);
		g.drawString(str, (640 - width / 2), 60);
	}
	
	void renderMinimap(Graphics g) {
		g.drawImage(minimapImg, 1000, 410, 250, 250, null);
	}
	
	void renderRemainCheeseInfo(Graphics g) {
		// 치즈
		g.drawImage(cheeseImg, 1, 10, 70, 70, null);
		
		// 남은 개수
		g.setColor(Color.white);
		g.setFont(new Font("HY견고딕", Font.PLAIN, 28));
		g.drawString(String.format("%02d개 남음", remainCheeseCount), 80, 50);
	}
	
	void renderPlayerInfo(Graphics g) {
		int y = 100;
		for(int i=0; i<4; i++) {
			// 배경
			g.setColor(Color.black);
			g.fillRect(0, y, 245, 60);
			
			if(i == 0) {
				// 캐릭터 얼굴
				g.drawImage(tomAliveImg, 10, y + 5, 50, 50, null);
			} else if(i % 2 == 0) {
				// 캐릭터 얼굴
				g.drawImage(jerryAliveImg, 10, y + 5, 50, 50, null);
			} else {
				// 캐릭터 얼굴
				g.drawImage(jerryDeadImg, 10, y + 5, 50, 50, null);
			}
			
			// 닉네임
			g.setColor(Color.white);
			g.setFont(new Font("HY견고딕", Font.PLAIN, 28));
			g.drawString("user1", 70, y + 40);
			
			// 좌표 이동
			y += 70;
		}
	}
	
	public BufferedImage loadImage(String path) {
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setSize(1280, 720);
		f.add(new TestPanel());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

}
