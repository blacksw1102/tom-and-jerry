package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoadingScreen extends JPanel {
    private JProgressBar loadingBar = new JProgressBar(0, 100);
    private BufferedImage img;

    public LoadingScreen() {
        this.setLayout(new BorderLayout());

        this.loadingBar.setValue(0);
        this.loadingBar.setStringPainted(true);

        this.add(this.loadingBar, BorderLayout.PAGE_END);
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
