package game.client.frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import game.client.connect.Connection;
import game.client.object.User;
import game.client.object.Users;
import game.client.player.Player;

public class ReadyView extends FrameView {

	private Connection conn;
	private Users users;
	private JPanel contentPane;
	private JButton readyButton;
	private User me;
	
	private static final int REQUIRED_USER_NUM = 2;

	public ReadyView(String nName) {
		conn = new Connection(nName);
		conn.setView(this);
		conn.send_Message(nName, 1);
		users = conn.getUsers();
		init();
		me = users.getUser(nName);

	}

	public void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(150, 100, 400, 400);
		setTitle("게임대기 방");

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		readyButton = new JButton("게임 준비");
		readyButton.setBackground(Color.LIGHT_GRAY);
		readyButton.setBounds(20, 200, 100, 100);
		readyButton.addActionListener(new Action());
		print_Component();
		setVisible(true);
	}

	@Override
	public void view() {
		users = conn.getUsers();
		if (contentPane != null)
			contentPane.removeAll();
		print_Component();
		contentPane.repaint();

	}
	
	public void print_Component() {
		
		JLabel ntLabel = new JLabel("닉네임");
		JLabel rtLabel = new JLabel("게임 상태");
		ntLabel.setBounds(50, 30, 100, 50);
		rtLabel.setBounds(150, 30, 100, 50);
		contentPane.add(ntLabel);
		contentPane.add(rtLabel);

		Vector<User> vUsers = users.getUsers();
		for (int i = 0; i < users.getSize(); i++) {
			User u = vUsers.elementAt(i);
			JLabel readyLabel = new JLabel();
			if(u.isReady()) {
				readyLabel.setOpaque(true);
				readyLabel.setForeground(Color.red);
				readyLabel.setText("준비"); 
			} else {
				readyLabel.setOpaque(true);
				readyLabel.setForeground(Color.black);
				readyLabel.setText("대기");
			}
			JLabel nickLabel = new JLabel(u.getnName());
			readyLabel.setBounds(150, 30 * (i + 2), 100, 50);
			nickLabel.setBounds(50, 30 * (i + 2), 100, 50);
			
			if (readyLabel != null)
				contentPane.add(readyLabel);
			
			if (nickLabel != null)
				contentPane.add(nickLabel);
		}
		if (readyButton != null)
			contentPane.add(readyButton);
	}

	public void changeReadyState() {
		if(me.isReady()) {
			me.setReady(false);
		} else {
			me.setReady(true);
		}
		String message = me.getnName() + " " + me.isReady();
		conn.send_Message(message, 2);
		view();
	}
	
	private boolean isGameAvailable() {
		if(users.getSize() != REQUIRED_USER_NUM)
			return false;
		
		for(User user : users.getUsers()) {
			if(!user.isReady())
				return false;
		}
		
		return true;
	}

	class Action implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			changeReadyState();
	
			if(isGameAvailable()) {
				conn.send_Message("", 3);
			}
		}
	}
}
