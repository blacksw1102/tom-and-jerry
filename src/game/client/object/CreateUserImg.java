package game.client.object;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class CreateUserImg {
	private ArrayList<Image> moveImg;
	private ArrayList<Image> stopImg;
	private ArrayList<Image> skMoveImg;
	private ArrayList<Image> skStopImg;
	private ArrayList<Image> skSlashImg;

	public CreateUserImg() {
		moveImg = new ArrayList<Image>();
		stopImg = new ArrayList<Image>();
		skMoveImg = new ArrayList<Image>();
		skStopImg = new ArrayList<Image>();
		skSlashImg = new ArrayList<Image>();

		moveImg.add(new ImageIcon("Img/left_ani.gif").getImage());
		moveImg.add(new ImageIcon("Img/right_ani.gif").getImage());
		moveImg.add(new ImageIcon("Img/up_ani.gif").getImage());
		moveImg.add(new ImageIcon("Img/down_ani.gif").getImage());

		stopImg.add(new ImageIcon("Img/left2.png").getImage());
		stopImg.add(new ImageIcon("Img/right2.png").getImage());
		stopImg.add(new ImageIcon("Img/up2.png").getImage());
		stopImg.add(new ImageIcon("Img/down2.png").getImage());

		skMoveImg.add(new ImageIcon("sk/left_ani.gif").getImage());
		skMoveImg.add(new ImageIcon("sk/right_ani.gif").getImage());
		skMoveImg.add(new ImageIcon("sk/up_ani.gif").getImage());
		skMoveImg.add(new ImageIcon("sk/down_ani.gif").getImage());

		skStopImg.add(new ImageIcon("sk/left.png").getImage());
		skStopImg.add(new ImageIcon("sk/right.png").getImage());
		skStopImg.add(new ImageIcon("sk/up.png").getImage());
		skStopImg.add(new ImageIcon("sk/down.png").getImage());

		skSlashImg.add(new ImageIcon("sk/S_left_ani.gif").getImage());
		skSlashImg.add(new ImageIcon("sk/S_right_ani.gif").getImage());
		skSlashImg.add(new ImageIcon("sk/S_up_ani.gif").getImage());
		skSlashImg.add(new ImageIcon("sk/S_down_ani.gif").getImage());

	}

	public ArrayList<Image> getSkMoveImg() {
		return this.skMoveImg;
	}

	public ArrayList<Image> getSkStopImg() {
		return this.skStopImg;
	}

	public ArrayList<Image> getMoveImg() {
		return this.moveImg;
	}

	public ArrayList<Image> getStopImg() {
		return this.stopImg;
	}

	public ArrayList<Image> getSkSlashImg() {
		return this.skSlashImg;
	}
}
