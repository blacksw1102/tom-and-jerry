package client.game;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

class AnimatedSprite {

	private int dirBuff;
	private int statusBuff;// ĳ������ ���� ����
	ArrayList<Integer> frameList;// ĳ���Ͱ� ������ ������ ���
	ArrayList<Long> frameDelay;// ���� ���������� ��ȯ�ϱ���� ������

	int nowFrame;// ���� ������ �ε���
	long nowDelay;// ���� �������� �����ְ� �ִ� ������
	boolean isRoof;// �ִϸ��̼� ���� ����
	int nextAnimation;// ������ �ƴ� ��� ���� �ִϸ��̼�

	private Player player;
	
	public AnimatedSprite(Player player) {
		this.player = player;

		frameList = new ArrayList<Integer>();
		frameDelay = new ArrayList<Long>();
		dirBuff = 1;
		setIdle();
	}

	public void process() {

		// ���º�ȯ ���� Ȯ��
		if (player.status != statusBuff || player.dir != dirBuff) {
			switch (player.status) {
			case 0:
				setIdle();
				break;
			case 1:
				setWalk();
				break;
			}

			if (player.status != statusBuff)
				statusBuff = player.status;

			if (player.dir != dirBuff)
				dirBuff = player.dir;

			return;
		}

		// �ִϸ��̼� ���� ����
		if (nowDelay-- <= 0) {

			if (nowFrame == frameList.size() - 1) {// �ִϸ��̼��� ������ �����ӿ� �����ߴ°�
				if (isRoof)// �ݺ��� ���ΰ�
					nowFrame = 0;// �������� ó������ �ǵ�����
				else {
					// �ݺ����� �ʴ´ٸ�
					switch (nextAnimation) {
					case -1:// ������ �������� �״�� �����ش�
						break;
					// �̿ܿ��� ������ �ִϸ��̼��� ȣ���Ѵ�
					case 0:
						setIdle();
						break;
					case 1:
						setWalk();
						break;
					}

				}
			} else
				nowFrame++;

			nowDelay = frameDelay.get(nowFrame);
		}
	}

	// ��������Ʈ
	void setIdle() {
		frameList.clear();
		frameDelay.clear();

		for (int i = 0; i < 20; i++) {

			frameList.add(player.dir == 1 ? i : 19 - i);
			frameDelay.add(5l);
		}

		nowFrame = 0;
		nowDelay = frameDelay.get(nowFrame);
		isRoof = true;

		player.status = 0;

	}

	// ��������Ʈ
	void setWalk() {

		long[] delay = { 7l, 5l, 3l, 3l, 5l, 7l, 5l, 3l, };

		frameList.clear();
		frameDelay.clear();

		for (int i = 20; i < 28; i++) {
			frameList.add(player.dir == 1 ? i : 29 - i + 20);
			frameDelay.add(delay[i - 20]);
		}

		nowFrame = 0;
		nowDelay = frameDelay.get(nowFrame);
		isRoof = true;

		player.status = 1;

	}
}
