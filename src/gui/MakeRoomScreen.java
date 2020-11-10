package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import entity.User;
import entity.WaitingRoom;
import net.ClientWindow;
import util.GameProtocol;

public class MakeRoomScreen extends JDialog {
	private JButton createButton, cancelButton;
	private JTextField roomName;
	private JPasswordField roomPassword;
	private JCheckBox pwCheckbox;
	
	private ClientWindow win;
	private User user;
	

	public MakeRoomScreen(ClientWindow win, User user) {
		this.win = win;
		this.user = user;
		
		this.setBackground(Color.WHITE);
		this.getRootPane().setBorder(new LineBorder(Color.BLACK, 3));
		this.setUndecorated(true);
		
		JLabel label1 = new JLabel("방 만들기");
		label1.setBounds(20, 10, 150, 40);
		label1.setFont(new Font("HY견고딕", Font.BOLD, 18));
		
		JLabel label2 = new JLabel("방 제목");
		label2.setHorizontalAlignment(SwingConstants.CENTER);
		label2.setBounds(20, 50, 80, 30);
		label2.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		label2.setOpaque(true);
		label2.setBackground(Color.ORANGE);
		label2.setBorder(new LineBorder(Color.BLACK, 3));

		roomName = new JTextField();
		roomName.setBounds(110, 50, 200, 30);
		roomName.setFont(new Font("돋움", Font.PLAIN, 12));
		roomName.setBorder(new LineBorder(Color.BLACK, 3));

		JLabel lb3 = new JLabel("비밀번호");
		lb3.setHorizontalAlignment(SwingConstants.CENTER);
		lb3.setBounds(20, 100, 80, 30);
		lb3.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		lb3.setOpaque(true);
		lb3.setBackground(Color.ORANGE);
		lb3.setBorder(new LineBorder(Color.BLACK, 3));

		roomPassword = new JPasswordField();
		roomPassword.setBounds(110, 100, 200, 30);
		roomPassword.setFont(new Font("돋움", Font.PLAIN, 12));
		roomPassword.setOpaque(true);
		roomPassword.setBorder(new LineBorder(Color.BLACK, 3));

		pwCheckbox = new JCheckBox();
		pwCheckbox.setBounds(320, 105, 20, 20);
		pwCheckbox.setBorder(new LineBorder(Color.BLACK, 3));
		pwCheckbox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					roomPassword.setText("");
					roomPassword.setEditable(false);
				} else {
					roomPassword.setEditable(true);
				}
			}
		});

		createButton = new JButton("생성");
		createButton.setBounds(100, 150, 80, 30);
		createButton.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		createButton.setOpaque(true);
		createButton.setBackground(Color.WHITE);
		createButton.setBorder(new LineBorder(Color.BLACK, 3));
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createWaitingRoom();
			}
		});

		cancelButton = new JButton("취소");
		cancelButton.setBounds(200, 150, 80, 30);
		cancelButton.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		cancelButton.setOpaque(true);
		cancelButton.setBackground(Color.WHITE);
		cancelButton.setBorder(new LineBorder(Color.BLACK, 3));
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				win.setEnabled(true);
				dispose();
			}
		});

		getContentPane().add(label1);
		getContentPane().add(roomPassword);
		getContentPane().add(pwCheckbox);
		getContentPane().add(label2);
		getContentPane().add(roomName);
		getContentPane().add(lb3);
		getContentPane().add(createButton);
		getContentPane().add(cancelButton);
		setSize(370, 200);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setVisible(true);
	}
	
	public void createWaitingRoom() {
		WaitingRoom waitingRoom = new WaitingRoom(roomName.getText(), roomPassword.getText());
		GameProtocol protocol = new GameProtocol(GameProtocol.PT_REQ_CREATE_WAIT_ROOM);
		protocol.setData(waitingRoom);
		try {
			user.out.writeObject(protocol);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showAlreadyCreateDialog(String message) {
		// 확인 메시지 표시
		JDialog info = new JDialog(win, true);
		info.setSize(200, 110);
		info.setLocationRelativeTo(null);
		info.setLayout(new FlowLayout());
		JButton ok = new JButton("확인");
		info.add(new JLabel(message, JLabel.CENTER));
		info.add(ok);
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				info.setVisible(false);
				info.dispose();
			}
		});
		info.setVisible(true);
	}
}
