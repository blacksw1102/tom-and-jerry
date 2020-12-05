package client.gui;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.EOFException;
import java.io.IOException;

import javax.imageio.ImageIO;

import client.entity.User;
import client.game.Block;
import client.game.Camera;
import client.game.Handler;
import client.game.ID;
import client.game.Jerry;
import client.game.KeyInput;
import client.game.Player;
import client.game.Tom;
import server.util.GameProtocol;

public class GamePanel extends Canvas implements Runnable {

    private Thread t;
    private GameScreen gameScreen;
    private User user;
    
	private static final int SELECTED_TOM_ROLE = 1;
	private static final int SELECTED_JERRY_ROLE = 2;

	private Handler handler;
	private Camera camera;
	
	private BufferedImage buff;	// 더블버퍼링용 백버퍼
	private Graphics2D gc2;		// 더블버퍼링용 그래픽 컨텍스트
	private BufferedImage gameMapImg;
	private BufferedImage mapStructureImg = null;
	private BufferedImage viewImg = null;
	
	private int delay;		// 루프 딜레이. 1/1000초 단위.
	private long pretime;	// 루프 간격을 조절하기 위한 시간 체크값

	private Player player;
	//private static int selectedRole;

    public GamePanel(GameScreen gameScreen, User user) {
    	this.gameScreen = gameScreen;
    	this.user = user;
    	this.handler = new Handler();
		this.camera = new Camera(0, 0);
		
		this.gameMapImg = makeBufferedImage("res/game_map.png"); // 배경 그림 로딩
		this.mapStructureImg = loadImage("/game_map_structure.png");
		this.viewImg = makeBufferedImage("res/view.png");

		this.delay = 30;
		
		try {
			GameProtocol protocol = (GameProtocol) user.getIn().readObject();
			if(protocol == null)
				throw new IOException("Null pointer received...");
			switch(protocol.getProtocol()) {
				case GameProtocol.PT_BROADCAST_PLAYER_ROLE:
					String[] datas = ((String) protocol.getData()).split(" ");
					int role = Integer.parseInt(datas[0]);
					int x = Integer.parseInt(datas[1]);
					int y = Integer.parseInt(datas[2]);
					
					if (role == SELECTED_JERRY_ROLE) {
						BufferedImage spritesheet = makeBufferedImage("res/jerry_sprites.png");
						BufferedImage spritesheet_flipx = getFlippedImage(spritesheet, true, false);
						player = new Jerry(this, x, y, ID.Player, spritesheet, spritesheet_flipx, handler);
						handler.addObject(player);
						System.out.println("Jerry 생성 완료");
					} else if (role == SELECTED_TOM_ROLE) {
						BufferedImage spritesheet = makeBufferedImage("res/tom_sprites.png");
						BufferedImage spritesheet_flipx = getFlippedImage(spritesheet, true, false);
						player = new Tom(this, x, y, ID.Player, spritesheet, spritesheet_flipx, handler);
						handler.addObject(player);
						System.out.println("Tom 생성 완료");
					}
					
					this.setFocusable(true);
					this.addKeyListener(new KeyInput(player));
					
					loadLevel(mapStructureImg);
					
					Thread thread = new Thread(this);
					thread.start();
					break;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (EOFException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
        //t = new Thread(this);
        //t.start();
    }
    
	@Override
	public void run() {
		while (true) {
			try {
				pretime = System.currentTimeMillis();

				synchronized(this) {
					process();
					repaint();
				}
				
				long nowTime = System.currentTimeMillis();
				if (nowTime - pretime < delay)
					Thread.sleep(delay - (nowTime - pretime) < 0 ? 1 : delay - (nowTime - pretime));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}
	
	void process() {
		for(int i = 0; i<handler.object.size(); i++) {
			if(handler.object.get(i).getId() == ID.Player) {
				camera.tick(handler.object.get(i));
			}
		}
		
		handler.process();
	}

	@Override
	public void update(Graphics g) {
		dblpaint();

	}

	void dblpaint() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		
		g.clearRect(0, 0, 1280, 720);
		
		g2d.translate(-camera.getX(), -camera.getY());
		drawBG(g2d);
		g2d.drawImage(viewImg, camera.getX()-64, camera.getY()-64, this); // (1408-1270)/2 == 64
		drawPlayer(g2d);
		g2d.translate(camera.getX(), camera.getY());

		g.dispose();
		bs.show();
	}

	void drawBG(Graphics2D g2d) {
		drawImageF(g2d, gameMapImg, 0, 0, this);
	}

	void drawPlayer(Graphics2D g2d) {
		handler.render(g2d);
	}
	
	// loading the level
	private void loadLevel(BufferedImage image) {

		int w = image.getWidth();
		int h = image.getHeight();
		
		for(int xx=0; xx<w; xx++) {
			for(int yy=0; yy<h; yy++) {
				int pixel = image.getRGB(xx, yy);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				if(red==0 && green==0 && blue==0) {
					handler.addObject(new Block(xx*32, yy*32, ID.Block));
				}
			}
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
	
	/*
	 * 이 아래로는 범용으로 사용 가능한 커스텀 api
	 */

	// 확대 축소된 BufferedImage 얻기
	public BufferedImage getScaledImage(BufferedImage image, int width, int height) {
		if (width <= 0)
			width = 1;
		if (height <= 0)
			height = 1;
		
		GraphicsConfiguration gc = gameScreen.getGraphicsConfiguration();
		BufferedImage result = gc.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		Graphics2D g = result.createGraphics();

		double w = image.getWidth();
		double h = image.getHeight();
		g.scale((double) width / w, (double) height / h);
		g.drawRenderedImage(image, null);
		g.dispose();
		
		return result;
	}

	// BufferedImage 리소스 읽어들이기 - Image를 BufferedImage로 바꾸는 방식
	public BufferedImage makeBufferedImage(String furl) {

		Image img = null;
		Toolkit tk = Toolkit.getDefaultToolkit();
		img = tk.getImage(furl);
		try {
			// 여기부터
			MediaTracker mt = new MediaTracker(this);
			mt.addImage(img, 0);
			mt.waitForID(0);
			// 여기까지, getImage로 읽어들인 이미지가 로딩이 완료됐는지 확인하는 부분
		} catch (Exception ee) {
			ee.printStackTrace();
			return null;
		}

		BufferedImage bImg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D bGr = bImg.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		return bImg;
	}

	// 이미지 클리핑
	public void drawImageClip(Graphics2D g, BufferedImage img, int x, int y, int sx, int sy, int wd, int ht, int anc) {
		// sx,sy부터 // 그린다.
		if (x < 0) {
			wd += x;
			sx -= x;
			x = 0;
		}
		if (y < 0) {
			ht += y;
			sy -= y;
			y = 0;
		}
		if (wd < 0 || ht < 0)
			return;
		g.setClip(x, y, wd, ht);
		drawImageF(g, img, x - sx, y - sy, this);
		g.setClip(0, 0, gameScreen.gGameWidth, gameScreen.gGameHeight);
		
	}

	// float를 좌표로 받는 그리기
	void drawImageF(Graphics2D gc, BufferedImage image, float x, float y, ImageObserver obs) {
		gc.drawImage(image, (int) x, (int) y, obs);
	}

	// 이미지 플립
	public BufferedImage getFlippedImage(BufferedImage img, boolean flipX, boolean flipY) {

		AffineTransform at = new AffineTransform();
		at.concatenate(AffineTransform.getScaleInstance(flipX ? -1 : 1, flipY ? -1 : 1));
		at.concatenate(AffineTransform.getTranslateInstance(flipX ? -img.getWidth() : 0, flipY ? -img.getHeight() : 0));

		BufferedImage newImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = newImage.createGraphics();
		g.transform(at);
		g.drawImage(img, 0, 0, null);
		g.dispose();

		return newImage;
	}

	// 중심점으로부터의 거리
	public int GetDistance(int x1, int y1, int x2, int y2) {
		return Math.abs((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}

}
