package client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import client.entity.User;
import client.gui.component.WaitingRoomListRow;
import client.net.ClientWindow;
import server.util.GameProtocol;

public class LobbyScreen extends JFrame implements Runnable {
	ClientWindow win;
	User user;

	private JLabel chatLabel, playerLabel;
	private JTextArea chatArea, userArea;
	private JScrollPane scrollChatArea, scrolPlayArea;
	private JTextField chatField;
	private JButton btnMakeRoom, btnWaitRoom, btnLogout, sendButton;

	private JFrame frame;
	private Thread t;
	
	private JTable table; 
	private DefaultTableModel model;

	MakeRoomScreen makeRoomScreen;

	public LobbyScreen(User user) {
		this.frame = this;
		this.user = user;
		
		this.setTitle(String.format("로비 창 - (접속 유저:%s)", user.getNickname()));
		this.setSize(1280, 720);
		this.setLayout(null);
		//this.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 5), new EmptyBorder(40, 300, 40, 300)));

		this.initTopArea();
		this.initMiddleArea();
		this.initBottomArea();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - getWidth()) / 2;
		int y = (screenSize.height - getHeight()) / 2;
		this.setLocation(x, y);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		t = new Thread(this);
		t.start();

		this.setVisible(true);
	}

	public LobbyScreen(ClientWindow win, User user) {
		this.win = win;
		this.user = user;
	}

	@Override
	public void run() {
		boolean isRun = true;
		String message = null;
		String[] data = null;
		
		System.out.printf("[%s] 작동 중..\n", this.getClass().getName());
		try {
			while (isRun) {
				// CPU 독식 방지
				Thread.sleep(100);

				GameProtocol protocol = (GameProtocol) user.in.readObject();
				if (protocol == null)
					throw new IOException("Null pointer received...");

				switch (protocol.getProtocol()) {
				case GameProtocol.PT_RES_USER_LIST: // 유저 리스트 조회
					List<String> userList = (ArrayList) protocol.getData();
					initUserList(userList);
					break;
				case GameProtocol.PT_SEND_MESSAGE: // 채팅 메시지 수신
					message = (String) protocol.getData();
					if (!chatArea.getText().equals(""))
						message = "\n" + message;
					chatArea.append(message);
					chatArea.setCaretPosition(chatArea.getDocument().getLength()); // 맨아래로 스크롤
					break;
				case GameProtocol.PT_RES_CREATE_WAITING_ROOM:
					data = ((String) protocol.getData()).split(",");
					makeRoomScreen.dispose();
					this.dispose();
					new WaitingRoomScreen(user, Integer.valueOf(data[0]), data[1]);
					this.t.interrupt();
					/*
					if ((int) protocol.getData() == 1) {
						makeRoomScreen.setVisible(false);
						makeRoomScreen.dispose();
						this.dispose();
						new WaitingRoomScreen(user, 1, "테스트 제목 입니다.");
						this.t.interrupt();
					}
					*/
					break;
				case GameProtocol.PT_BROADCAST_WAITING_ROOM_LIST:
					List<WaitingRoomListRow> rows = (ArrayList) protocol.getData();
					model.setRowCount(0);
					for (WaitingRoomListRow value : rows) {
						model.addRow(new Object[]{value.getRoomId(), 
								value.getRoomName(), 
								value.getCurrentPlayerCount()+"/"+value.getMaxPlayerCount(), 
								value.getRoomState()});
					}
					
					break;
				case GameProtocol.PT_RES_ENTER_WAITING_ROOM:
					data = ((String) protocol.getData()).split(",");
					this.dispose();
					new WaitingRoomScreen(user, Integer.valueOf(data[0]), data[1]);
					this.t.interrupt();
					
					/*
					this.dispose();
					new WaitingRoomScreen(user, 1, "테스트 제목입니다.");
					this.t.interrupt();
					*/
					break;
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (EOFException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
		} finally {
			System.out.printf("[%s] 종료..\n", this.getClass().getName());
		}
	}

	private void initUserList(List<String> userList) {
		userArea.setText("");
		for (String user : userList)
			userArea.append(user + "\n");
	}

	private void initTopArea() {
		
		/* 방만들기 버튼 */
		btnMakeRoom = new MakeRoomButton();
		btnMakeRoom.setBounds(240, 60, 140, 50);
		
		/* 대기 방 버튼 */
		btnWaitRoom = new WaitRoomButton();
		btnWaitRoom.setBounds(390, 60, 140, 50);

		/* 로그아웃 */
		btnLogout = new LogoutButton();
		btnLogout.setBounds(900, 60, 140, 50);
		
		this.add(btnMakeRoom);
		this.add(btnWaitRoom);
		this.add(btnLogout);
		
	}

	private void initMiddleArea() {
		
        //add the table to the frame
        model = new DefaultTableModel(); 
        model.addColumn("방 번호");
        model.addColumn("방 제목");
        model.addColumn("방 인원");
        model.addColumn("방 상태");
        table = new JTable(model) {
        	public boolean isCellEditable(int row, int column) {
        		return false;
        	}
        };
        table.setFont(new Font("HY견고딕", Font.PLAIN, 18));
        table.setRowHeight(40);
        table.setShowGrid(false);
        table.getTableHeader().setPreferredSize(new Dimension(800, 40));
        table.getTableHeader().setFont(new Font("HY견고딕", Font.PLAIN, 18));
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                	GameProtocol protocol = new GameProtocol(GameProtocol.PT_REQ_ENTER_WAITING_ROOM);
                	int roomId = (int) table.getValueAt(row, 0);
                	protocol.setData(roomId);
                	try {
						user.getOut().writeObject(protocol);
					} catch (IOException e) {
						e.printStackTrace();
					}
                }
            }
        });
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
        table.setDefaultRenderer(Object.class, centerRenderer);

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(new LineBorder(Color.BLACK, 3));
        sp.setBounds(1280/2-800/2, 120, 800, 300);
        this.add(sp);
		
	}

	private void initBottomArea() {

		chatLabel = new JLabel("채팅창");
		chatLabel.setFont(new Font("HY견고딕", Font.PLAIN, 18));

		playerLabel = new JLabel("접속자 목록");
		playerLabel.setFont(new Font("HY견고딕", Font.PLAIN, 18));

		chatArea = new JTextArea();
		chatArea.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		chatArea.setEditable(false);
		scrollChatArea = new JScrollPane(chatArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollChatArea.setBorder(new LineBorder(Color.BLACK, 3));
		scrollChatArea.setBounds(240, 430, 600, 160);
		this.add(scrollChatArea);

		userArea = new JTextArea();
		userArea.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		userArea.setEditable(false);
		scrolPlayArea = new JScrollPane(userArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrolPlayArea.setBorder(new LineBorder(Color.BLACK, 3));
		scrolPlayArea.setBounds(850, 430, 190, 200);
		this.add(scrolPlayArea);

		chatField = new JTextField();
		chatField.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		chatField.setBorder(new LineBorder(Color.BLACK, 3));
		chatField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					sendMessage(chatField.getText());
			}
		});
		chatField.setBounds(240, 600, 500, 30);
		this.add(chatField);

		sendButton = new JButton("전송");
		sendButton.setBackground(Color.LIGHT_GRAY);
		sendButton.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		sendButton.setBorder(new LineBorder(Color.BLACK, 3));
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendMessage(chatField.getText());
			}
		});
		sendButton.setBounds(750, 600, 90, 30);
		this.add(sendButton);
	}

	// 서버로 채팅 메시지를 전달한다
	public void sendMessage(String message) {
		GameProtocol protocol = new GameProtocol(GameProtocol.PT_SEND_MESSAGE);
		try {
			if (message != null && !message.equals("")) {
				String data = String.format("[%s]:%s", user.getNickname(), message);
				protocol.setData(data);
				user.out.writeObject(protocol);
				chatField.setText("");
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	class MakeRoomButton extends WaitRoomListButton {
		/* 방 만들기 버튼 */
		MakeRoomButton() {
			super("방 만들기");
			this.setBackground(new Color(0xFFC000));
			this.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.setEnabled(false);
					makeRoomScreen = new MakeRoomScreen(user, frame);
					makeRoomScreen.setLocationRelativeTo(frame);
				}
			});
		}
	}

	/* 버튼 컴포넌트 */

	class WaitRoomListButton extends JButton {
		/* 버튼들 조상 */
		WaitRoomListButton(String text) {
			super(text);

			this.setBackground(Color.WHITE);
			this.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 3), new EmptyBorder(5, 5, 5, 5)));
			this.setFont(new Font("맑은 고딕", Font.BOLD, 18));
			this.setFocusPainted(false);
		}
	}

	class WaitRoomButton extends WaitRoomListButton {
		/* 대기방 버튼 */
		WaitRoomButton() {
			super("대기 방");

			this.setBackground(new Color(0x4472C4));
			this.setForeground(Color.WHITE);
		}
	}

	class LogoutButton extends WaitRoomListButton {
		
		/* 로그아웃 버튼 */
		LogoutButton() {
			super("로그아웃");
			this.setBackground(Color.WHITE);
			this.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					GameProtocol protocol = new GameProtocol(GameProtocol.PT_LOGOUT);
					try {
						user.out.writeObject(protocol);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					frame.setVisible(false);
					new LoginScreen();
				}
			});
		}
		
	}

}
