package client.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class LoadingPanel extends JPanel implements Runnable {
    private JProgressBar loadingBar = new JProgressBar(0, 100);
    private BufferedImage img;

    private int value = 0;
    
    private Thread t;
    
    private GameScreen gameScreen;
    
    public LoadingPanel(GameScreen gameScreen) {
    	this.gameScreen = gameScreen;
    	
        this.setLayout(new BorderLayout());
        this.setSize(1280, 720);
        this.loadingBar.setValue(value);
        this.loadingBar.setFont(new Font("HY견고딕", Font.PLAIN, 36));
        this.loadingBar.setStringPainted(true);
        this.add(this.loadingBar, BorderLayout.CENTER);
        setVisible(true);
        
        t = new Thread(this);
        t.start();
    }
    
	@Override
	public void run() {
		try {
			while(value < 100) {
				Thread.sleep(150);
				value += 3;
				setProgressBarValue(value);
			}
			
			// 로딩이 끝나면 게임화면으로 전환
			gameScreen.changeGameScreen();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

    public void loadImage() {
        //TODO 로딩 화면 이미지를 제작하여 대체 하여야 함.
        try {
            this.img = ImageIO.read(new File("test.png"));
        } catch (IOException e) {

        }
    }

    /**
     * 프로그래스 바의 크기를 결정합니다.
     * @param value 0-100 사이의 정수
     */
    public void setProgressBarValue(int value) {
        this.loadingBar.setValue(value);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.loadImage();

        g.drawImage(img, 0, 0, 1280, 720, this);
    }

}

