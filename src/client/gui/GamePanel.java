package client.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import com.sun.javafx.embed.swing.Disposer;

import client.entity.User;
import client.game.Block;
import client.game.Camera;
import client.game.Cheese;
import client.game.Connection;
import client.game.Handler;
import client.game.ID;
import client.game.Jerry;
import client.game.KeyInput;
import client.game.Player;
import client.game.Timer;
import client.game.Tom;
import server.util.GameProtocol;

public class GamePanel extends Canvas implements Runnable {

    private GameScreen gameScreen;

    private Player player;
    private Player watchPlayer;
//    private ArrayList<Player> playerList;
    private Hashtable<String, Player> playerList;
    private Connection conn;
    
	private static final int SELECTED_TOM_ROLE = 1;
	private static final int SELECTED_JERRY_ROLE = 2;

	private Handler handler;
	private Camera camera;
	
	private BufferedImage buff;	// 더블버퍼링용 백버퍼
	private Graphics2D gc2;		// 더블버퍼링용 그래픽 컨텍스트
	private BufferedImage gameMapImg;
	private BufferedImage mapStructureImg = null;
	private BufferedImage viewImg = null;
	private BufferedImage cheeseSprite = null;
	
	// 왼쪽 사이드 아이콘 이미지
	private BufferedImage tomAliveImg;
	private BufferedImage jerryAliveImg;
	private BufferedImage jerryDeadImg;
	
	// 미니맵 이미지
	private BufferedImage minimapImg;
	
	private int delay;		// 루프 딜레이. 1/1000초 단위.
	private long pretime;	// 루프 간격을 조절하기 위한 시간 체크값

	private int remainCheeseCount;
	private int remainJerryCount;
	private int currentWatchPlayerIndex;
	
	private boolean isTimeOver;
	
	private Timer timer;
	private Thread t;
	
	//private static int selectedRole;

    public GamePanel(GameScreen gameScreen, User user, Hashtable<String, User> userList) {
    	this.gameScreen = gameScreen;
    	this.playerList = new Hashtable<>();
    	this.handler = new Handler();
		this.camera = new Camera(0, 0);
		
		this.gameMapImg = makeBufferedImage("res/game_map.png"); // 배경 그림 로딩
		this.mapStructureImg = loadImage("/game_map_structure.png");

		this.viewImg = makeBufferedImage("res/view2.png");
		
		this.cheeseSprite = loadImage("/cheese_sprites.png");
		
		this.tomAliveImg = loadImage("/tom_alive.png");		
		this.jerryAliveImg = loadImage("/jerry_alive.png");
		this.jerryDeadImg = loadImage("/jerry_dead2.png");
		
		this.minimapImg = loadImage("/mini_map.png");
		
		this.delay = 10;
		this.remainCheeseCount = 0;
		this.remainJerryCount = 2;
		this.currentWatchPlayerIndex = -1;
		
		this.isTimeOver = false;
		
		try {
			GameProtocol protocol = (GameProtocol) user.getIn().readObject();
			if(protocol == null)
				throw new IOException("Null pointer received...");
			switch(protocol.getProtocol()) {
				case GameProtocol.PT_BROADCAST_PLAYERS_INFO:
					LinkedList<String> datas = (LinkedList) protocol.getData();
					for(String data : datas) {
						String[] playerInfos = data.split(" ");
						int role = Integer.parseInt(playerInfos[0]);
						int x = Integer.parseInt(playerInfos[1]);
						int y = Integer.parseInt(playerInfos[2]);
						String nickname = playerInfos[3];
						
						if(user.getNickname().equals(nickname)) {
							Player p = null;
							BufferedImage spritesheet;
							BufferedImage spritesheet_flipx;
							
							if (role == SELECTED_JERRY_ROLE) {
								spritesheet = makeBufferedImage("res/jerry_sprites.png");
								spritesheet_flipx = getFlippedImage(spritesheet, true, false);
								p = new Jerry(user, this, x, y, ID.JERRY, spritesheet, spritesheet_flipx, handler);
								playerList.put(p.getNickname(), p);
								handler.addObject(p);
							} else if (role == SELECTED_TOM_ROLE) {
								spritesheet = makeBufferedImage("res/tom_sprites.png");
								spritesheet_flipx = getFlippedImage(spritesheet, true, false);
								p = new Tom(user, this, x, y, ID.TOM, spritesheet, spritesheet_flipx, handler);
								playerList.put(p.getNickname(), p);
								handler.addObject(p);
							}
							
							player = p;
							watchPlayer = player;
						} else {
							Player p = null;
							BufferedImage spritesheet;
							BufferedImage spritesheet_flipx;
							
							if (role == SELECTED_JERRY_ROLE) {
								spritesheet = makeBufferedImage("res/jerry_sprites.png");
								spritesheet_flipx = getFlippedImage(spritesheet, true, false);
								p = new Jerry(userList.get(nickname), this, x, y, ID.JERRY, spritesheet, spritesheet_flipx, handler);
								playerList.put(p.getNickname(), p);
								handler.addObject(p);
							} else if (role == SELECTED_TOM_ROLE) {
								spritesheet = makeBufferedImage("res/tom_sprites.png");
								spritesheet_flipx = getFlippedImage(spritesheet, true, false);
								p = new Tom(userList.get(nickname), this, x, y, ID.TOM, spritesheet, spritesheet_flipx, handler);
								playerList.put(p.getNickname(), p);
								handler.addObject(p);
							}
							
						}
					}
					
					this.setFocusable(true);
					this.addKeyListener(new KeyInput(this, player));
					
					loadLevel(mapStructureImg);
					
					this.conn = new Connection(this, handler, player, playerList);
					this.conn.start();
					
					// 타이머 시작
					timer = new Timer(this, 5, 0);
					timer.start();
					
					// 게임 시작
					t = new Thread(this);
					t.start();
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
		try {
			System.out.printf("[%s] 작동 중..\n", this.getClass().getName());
			while (!Thread.currentThread().isInterrupted()) {
				pretime = System.currentTimeMillis();

				process();
				repaint();
				
				long nowTime = System.currentTimeMillis();
				if (nowTime - pretime < delay)
					Thread.sleep(delay - (nowTime - pretime) < 0 ? 1 : delay - (nowTime - pretime));
			}
		} catch(InterruptedException e) {

		} finally {
			System.out.printf("[%s] 작동 종료..\n", this.getClass().getName());
		}
	}
	
	private synchronized void process() throws InterruptedException {
		
		camera.tick(watchPlayer);
		handler.process();
		checkGameOver();
		
	}
	
	public void checkGameOver() {
		// 남은 치즈 개수가 0개가 됬을 경우 => 게임 종료
		if(isTimeOver == true) {
			gameScreen.changeGameResultScreen(5);
			
			// 게임 종료
			this.t.interrupt();
		} else if(remainCheeseCount == 0) {
			if(player.getId() == ID.JERRY)
				gameScreen.changeGameResultScreen(1);
			else if(player.getId() == ID.TOM)
				gameScreen.changeGameResultScreen(4);

			// 게임 종료
			this.t.interrupt();
		} else if (remainJerryCount == 0) {
			if(player.getId() == ID.JERRY)
				gameScreen.changeGameResultScreen(2);
			else if(player.getId() == ID.TOM)
				gameScreen.changeGameResultScreen(3);
			
			// 게임 종료
			this.t.interrupt();
		}
	}
	
	public void decreaseCheese() {
		System.out.println("남은 치즈 개수 : " + remainCheeseCount);
		this.remainCheeseCount--;
	}
	
	public void decreaseJerryCount() {
		this.remainJerryCount--;
	}
	
	public void timeOver() {
		isTimeOver = true;
	}
	
	// 죽었을 때, 다른 플레이어 관전 기능
	public void changeWatchPlayer() {
		ArrayList<Player> arr = new ArrayList<>(playerList.values());
		while(true) {
			++currentWatchPlayerIndex;
			if(currentWatchPlayerIndex >= arr.size())
				currentWatchPlayerIndex = 0;
			
			if(!arr.get(currentWatchPlayerIndex).isDead()) {
				watchPlayer = arr.get(currentWatchPlayerIndex);
				break;
			}
		}
	}
	
	@Override
	public void update(Graphics g) {
		dblpaint();

	}

	synchronized void dblpaint() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		
		g.clearRect(0, 0, 1280, 720);
		
		g2d.translate(-camera.getX(), -camera.getY());
		drawBacgkround(g2d);
		drawGameObject(g2d);
		drawView(g2d);
		drawTimer(g);
		drawRemainCheeseInfo(g);
		drawPlayerInfo(g);
		drawMinimap(g);
		g2d.translate(camera.getX(), camera.getY());
		
		g.dispose();
		bs.show();
	}

	private void drawBacgkround(Graphics2D g2d) {
		drawImageF(g2d, gameMapImg, 0, 0, this);
	}
	
	void drawTimer(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("HY견고딕", Font.PLAIN, 50));
		String str = String.format("%02d : %02d", timer.getMinute(), timer.getSecond());
		int width = g.getFontMetrics().stringWidth(str);
		g.drawString(str, camera.getX() + (640 - width / 2),camera.getY() +  60);
	}
	
	private void drawRemainCheeseInfo(Graphics g) {
		// 치즈
		g.drawImage(cheeseSprite, camera.getX() + 1, camera.getY() + 10, 70, 70, null);
		
		// 남은 개수
		g.setColor(Color.white);
		g.setFont(new Font("HY견고딕", Font.PLAIN, 28));
		g.drawString(String.format("%02d개 남음", remainCheeseCount), camera.getX() + 80, camera.getY() + 50);
	}
	
	void drawPlayerInfo(Graphics g) {
		int y = 100;
		
		Enumeration<Player> e = playerList.elements();
		while(e.hasMoreElements()) {
			Player p = e.nextElement();
			BufferedImage selectedImg = null;
			
			// 캐릭터 얼굴
			if(p.getId() == ID.TOM) {
				selectedImg = tomAliveImg;
			} else {
				if(p.isDead())
					selectedImg = jerryDeadImg;
				else
					selectedImg = jerryAliveImg;
			}
			g.drawImage(selectedImg, camera.getX() + 10, camera.getY() + y + 5, 50, 50, null);
			
			// 닉네임
			g.setColor(Color.white);
			g.setFont(new Font("HY견고딕", Font.PLAIN, 28));
			g.drawString(p.getNickname(), camera.getX() + 70, camera.getY() + y + 40);
			
			// 좌표 이동
			y += 70;
		}
		
	}
	
	void drawMinimap(Graphics g) {
		g.drawImage(minimapImg, camera.getX() + 1000, camera.getY() + 410, 250, 250, null);
	}

	private void drawGameObject(Graphics2D g2d) {
		handler.render(g2d);
	}

	private void drawView(Graphics2D g2d) {
		g2d.drawImage(viewImg, camera.getX()-64, camera.getY()-64, this); // (1408-1270)/2 == 64		
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
				} else if(red==255 && green==255 && blue==0) {
					handler.addObject(new Cheese(xx*32, yy*32, ID.CHEESE, cheeseSprite));
					remainCheeseCount++;
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
		Shape backupShape = g.getClip();
		g.setClip(x, y, wd, ht);
		drawImageF(g, img, x - sx, y - sy, this);
		g.setClip(backupShape);
		//g.setClip(0, 0, gameScreen.gGameWidth, gameScreen.gGameHeight);
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
