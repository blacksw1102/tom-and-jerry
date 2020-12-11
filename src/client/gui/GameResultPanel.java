package client.gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import client.entity.User;

public class GameResultPanel extends JPanel {
	
	private GameScreen gameScreen;
	private User user;

	private BufferedImage jerryWin;
	private BufferedImage jerryLose;
	private BufferedImage tomWin;
	private BufferedImage tomLose;
	private BufferedImage draw;
	
	private BufferedImage resultImg;
	
	private JButton backWaitingRoomBtn;
	
	public GameResultPanel(GameScreen gameScreen, User user, int resultFlag) {
		this.gameScreen = gameScreen;
		this.user = user;

		this.jerryWin = loadImage("/jerry_win.png");
		this.jerryLose = loadImage("/jerry_lose.png");
		this.tomWin = loadImage("/tom_win.png");
		this.tomLose = loadImage("/tom_lose.png");
		this.draw = loadImage("/draw.png");
		
		if(resultFlag == 1) {
			resultImg = jerryWin;
		} else if(resultFlag == 2) {
			resultImg = jerryLose;
		} else if(resultFlag == 3) {
			resultImg = tomWin;
		} else if(resultFlag == 4) {
			resultImg = tomLose;
		} else if(resultFlag == 5) {
			resultImg = draw;
		}
		
		this.backWaitingRoomBtn = new JButton("대기실로");
		this.backWaitingRoomBtn.setBounds(545, 514, 190, 52); //734, 565
		this.backWaitingRoomBtn.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		this.backWaitingRoomBtn.setBackground(new Color(217, 217, 217));
		this.backWaitingRoomBtn.setBorder(new LineBorder(new Color(0, 0, 0), 7));

		this.add(backWaitingRoomBtn);
        this.setSize(1280, 720);
        this.setLayout(null);
        setVisible(true);
        
	}
	
	@Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
        g.drawImage(resultImg, 0, 0, null);
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
	
	private void drawGameResult(Graphics g) {
		g.drawImage(jerryWin, 0, 0, this);
	}

}
