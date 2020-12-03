package client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import client.entity.User;
import client.gui.component.Borders;
import client.gui.component.WaitingRoomRow;
import client.net.ClientWindow;
import server.util.GameProtocol;

public class WaitingRoomScreen extends JFrame implements Runnable {
	ClientWindow win;
	private User user;
    private ArrayList<User> users = new ArrayList<>();
    /// private RoomUserList roomUserList;
    
    private Thread t;
    
	public WaitingRoomScreen(User user) {
    	this.user = user;

		this.setSize(1280, 780);
        this.setLayout(new GridBagLayout());
        // this.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 5), new EmptyBorder(40, 200, 40, 200)));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.weightx = 1.0;
        c.gridwidth = 2;

        c.gridy = 0;
        this.add(new TopArea(), c);

        c.insets = new Insets(5, 0, 0, 0);
        c.weightx = 0.7;
        c.gridwidth = 1;
        c.gridy = 1;
        c.gridx = 0;
        this.add(new LeftArea(), c);

        c.gridx = 1;
        c.weightx = 0.3;
        c.weighty = 1.0;
        c.insets = new Insets(5, 10, 0, 0);
        this.add(new RightArea(), c);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - getWidth()) / 2;
		int y = (screenSize.height - getHeight()) / 2;
		this.setLocation(x, y);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				GameProtocol protocol = new GameProtocol(GameProtocol.PT_LOGOUT);
				try {
					user.getOut().writeObject(protocol);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});

		t = new Thread(this);
		t.start();
        
        this.setVisible(true);
    }
	
	@Override
	public void run() {
		System.out.printf("[%s] 작동 중..\n", this.getClass().getSimpleName());
		try {
			while(true) {
				// CPU 과부하 방지
				Thread.sleep(100);
				
				GameProtocol protocol = (GameProtocol) user.getIn().readObject();
				if(protocol == null)
					throw new IOException("Null pointer received...");
				
				switch(protocol.getProtocol()) {
					case GameProtocol.PT_BROADCAST_USER_LIST_IN_WAITING_ROOM:	// 유저 리스트 조회
						ArrayList<User> userList = (ArrayList) protocol.getData();
						users = userList;
		
						System.out.printf("닉네임 : %s, 현재 상태 : %d\n", user.getNickname(), user.getPlayerSatete());
						System.out.println("-----------현재 대기방 접속 유저 -----------");
						for(User user : users) {
							System.out.printf("닉네임 : %s, 현재 상태 : %d\n", user.getNickname(), user.getPlayerSatete());
						}
						System.out.println("------------------------------------");
						revalidate();
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
			System.out.printf("[%s] 종료..\n", this.getClass().getSimpleName());
		}
		
	}
    
	public WaitingRoomScreen(ClientWindow win, User user) {
    	this.win = win;
    	this.user = user;

		this.setSize(1280, 780);
        this.setLayout(new GridBagLayout());
        // this.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 5), new EmptyBorder(40, 200, 40, 200)));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.weightx = 1.0;
        c.gridwidth = 2;

        c.gridy = 0;
        this.add(new TopArea(), c);

        c.insets = new Insets(5, 0, 0, 0);
        c.weightx = 0.7;
        c.gridwidth = 1;
        c.gridy = 1;
        c.gridx = 0;
        this.add(new LeftArea(), c);

        c.gridx = 1;
        c.weightx = 0.3;
        c.weighty = 1.0;
        c.insets = new Insets(5, 10, 0, 0);
        this.add(new RightArea(), c);

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

    public void setUserState(int slot) {

    }

	/**
	 * 상단 영역
	 */
	class TopArea extends JPanel {
	    TopArea() {
	        this.setLayout(new GridLayout());

	        this.add(new RoomTitle());
	        //this.setBorder(gui.Borders.debugBorder);

	        this.setVisible(true);
	    }
	}

	class LeftArea extends JPanel {
	    LeftArea() {
	        this.setLayout(new GridBagLayout());
	        GridBagConstraints c = new GridBagConstraints();

	        c.fill = GridBagConstraints.BOTH;
	        c.weightx = 1.0;
	        c.gridy = 0;
	        c.gridx = 0;
	       // this.add(roomUserList, c);

	        c.insets = new Insets(5, 0, 0, 0);
	        c.weighty = 1.0;
	        c.gridy = 1;
	        this.add(new ChatArea(), c);

	        this.setVisible(true);
	    }
	}

	class RightArea extends JPanel {
	    RightArea() {
//	        this.setLayout(new GridBagLayout());
	        this.setLayout(new BorderLayout());
//	        this.setBorder(gui.Borders.debugBorder);

//	        GridBagConstraints c = new GridBagConstraints();
	//
//	        c.fill = GridBagConstraints.HORIZONTAL;
//	        c.weightx = 1.0;
//	        c.gridx = 0;
	//
//	        c.anchor = GridBagConstraints.NORTH;
//	        c.gridy = 0;
//	        this.add(new MapInfo(), c);
	//
//	        c.insets = new Insets(5, 0, 0, 0);
//	        c.anchor = GridBagConstraints.SOUTH;
//	        c.gridy = 1;
//	        this.add(new Buttons(), c);

	        this.add(new MapInfo(), BorderLayout.PAGE_START);
	        this.add(new Buttons(), BorderLayout.PAGE_END);

	        this.setVisible(true);
	    }
	}

	/**
	 * 중앙 영역
	 */
	class MiddleArea extends JPanel {
	    MiddleArea() {
	        this.setLayout(new GridBagLayout());

	        GridBagConstraints c = new GridBagConstraints();
	        c.fill = GridBagConstraints.BOTH;
	        c.gridy = 0;

	        c.gridx = 0;
	        c.weightx = 0.8;
	        // this.add(new RoomUserList(), c);

	        c.insets = new Insets(0, 10, 0, 0);
	        c.fill = GridBagConstraints.HORIZONTAL;
	        c.anchor = GridBagConstraints.PAGE_START;
	        c.gridx = 5;
	        c.weightx = 0.2;
	        this.add(new MapInfo(), c);

	        this.setVisible(true);
	    }
	}

	/**
	 * 하단 영역
	 */
	class BottomArea extends JPanel {
	    BottomArea() {
	        this.setLayout(new GridBagLayout());

	        GridBagConstraints c = new GridBagConstraints();
	        c.fill = GridBagConstraints.BOTH;
	        c.gridy = 0;

	        c.gridx = 0;
	        c.weightx = 0.8;
	        this.add(new ChatArea(), c);

	        c.insets = new Insets(0, 10, 0, 0);
	        c.fill = GridBagConstraints.HORIZONTAL;
	        c.anchor = GridBagConstraints.PAGE_END;
	        c.gridx = 5;
	        c.weightx = 0.2;
	        this.add(new Buttons(), c);

	        this.setVisible(true);
	    }
	}

	/**
	 * WaitingRoom의 방 제목
	 */
	class RoomTitle extends JPanel {
	    private JLabel labelTitle;               // 타이틀
	    private JButton btnRoomSetting;      // 방 정보 변경 버튼
	    private int roomNum;

	    RoomTitle() {
	        this.setLayout(new GridBagLayout());
	        this.setBorder(new CompoundBorder(client.gui.component.Borders.normalBorder, new EmptyBorder(3, 5, 3, 5)));
	        this.setBackground(Color.WHITE);

	        // 방 제목
	        this.labelTitle = new JLabel();
	        this.roomNum = 41;
	        this.setRoomTitle("ABC");
	        this.labelTitle.setFont(client.gui.component.Fonts.defaultFont);

	        // 방 정보 변경 버튼
	        this.btnRoomSetting = new JButton("방 정보변경");
	        this.btnRoomSetting.setBackground(new Color(0xD9D9D9));
	        this.btnRoomSetting.setBorder(new CompoundBorder(client.gui.component.Borders.normalBorder, new EmptyBorder(3, 7, 3, 7)));
	        this.btnRoomSetting.setFont(client.gui.component.Fonts.font16);
	        this.btnRoomSetting.setFocusPainted(false);

	        GridBagConstraints c = new GridBagConstraints();
	        c.fill = GridBagConstraints.BOTH;
	        c.gridx = 0;
	        c.weightx = 1;

	        c.gridy = 0;
	        this.add(this.labelTitle, c);

	        c.gridy = 1;
	        this.add(this.btnRoomSetting);

	        this.setVisible(true);
	    }

	    public void setRoomTitle(String str) {
	        if(this.labelTitle != null) {
	            this.labelTitle.setText(String.format("%03d %s", this.roomNum, str));
	        }
	    }
	}

	/**
	 * WaitingRoom의 유저 목록
	 */
	class RoomUserList extends JPanel {
	    RoomUserList() {
	        this.setLayout(new GridLayout(7, 1));
	        this.setBorder(client.gui.component.Borders.normalBorder);
	        this.setBackground(Color.WHITE);
	        
	        this.add(new RoomUserListRow("null", 1)); // 화면 안무너지게 하려면 이 데이터가 필요함 (버그)
//	        this.add(new RoomUserListRow("테스트", 1));
//	        this.add(new RoomUserListRow("테스트", 1));
//	        this.add(new RoomUserListRow("테스트", 1));
//	        this.add(new RoomUserListRow("테스트", 1));
	    }
	}

	class RoomUserListRow extends JPanel {
	    private JButton btnState;
	    private JLabel labelNickname;

	    private int STATE_NONE = 0;
	    private int STATE_READY = 1;
	    private int STATE_OWNER = 2;

	    RoomUserListRow(String nickname, int state) {
	        this.setLayout(new GridBagLayout());
	        this.setBorder(Borders.listRowBorder);
	        this.setBorder(
	        new CompoundBorder(Borders.listRowBorder, Borders.listRowPadding));
	        this.setBackground(Color.WHITE);

	        labelNickname = new JLabel();
	        labelNickname.setFont(client.gui.component.Fonts.defaultFont);
	        btnState = new JButton();
	        btnState.setFont(client.gui.component.Fonts.defaultFont);
	        btnState.setFocusPainted(false);
	        btnState.setBorder(new CompoundBorder(Borders.normalBorder, new EmptyBorder(0, 7, 0, 7)));

	        this.setState(state);
	        this.labelNickname.setText(nickname);

	        GridBagConstraints c = new GridBagConstraints();
	        c.fill = GridBagConstraints.BOTH;
	        c.gridy = 0;
	        c.weightx = 1;

	        c.gridx = 0;
	        this.add(this.labelNickname, c);

	        c.gridx = 1;
	        c.weightx = 0;
	        this.add(this.btnState, c);
	    }

	    public void setState(int state) {
	        this.btnState.setVisible(true);
	        switch (state) {
	            case 0:
	                this.btnState.setVisible(false);
	                break;
	            case 1:
	                this.btnState.setVisible(true);
	                this.btnState.setBackground(new Color(0xFFC000));
	                this.btnState.setText("준비");
	                break;
	            case 2:
	                this.btnState.setVisible(true);
	                this.btnState.setBackground(new Color(0xFF5369));
	                this.btnState.setText("방장");
	                break;
	        }
	    }

	    public void setNickname(String name) {
	        this.labelNickname.setText(name);
	    }
	}

	class MapInfo extends JPanel {
	    MapInfo() {
	        this.setLayout(new GridBagLayout());
//	        this.setBorder(new EmptyBorder(0, 10, 0, 0));
	        this.setBorder(client.gui.component.Borders.normalBorder);
	        this.setBackground(Color.WHITE);

	        GridBagConstraints c = new GridBagConstraints();
	        c.fill = GridBagConstraints.HORIZONTAL;
	        c.gridx = 0;
	        c.weightx = 1;
	        c.anchor = GridBagConstraints.PAGE_START;

	        c.gridy = 0;
	        JLabel labelLoading = new JLabel("로딩중...");

	        this.add(labelLoading, c);
	    }
	}

	/**
	 * WaitingRoom의 채팅 영역
	 */
	class ChatArea extends JPanel {
	    private JTextArea txtChatArea;

	    ChatArea() {
	        this.setLayout(new GridLayout());
	        this.txtChatArea = new JTextArea();
	        txtChatArea.setFont(client.gui.component.Fonts.defaultFont);
	        JScrollPane scrollChatArea = new JScrollPane(this.txtChatArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	        scrollChatArea.setBorder(Borders.normalBorder);

	        // 샘플 데이터
	        txtChatArea.append("[도움말] : 테스트 채팅 내용..\n");

	        this.add(scrollChatArea);
	    }

	    public void clearChat() {
	    }
	}

	/**
	 * 우측 하단 버튼 묶음
	 */
	class Buttons extends JPanel {
	    private JButton btnGameStart;
	    private JButton btnExit;

	    Buttons() {
	        this.setLayout(new GridLayout(2, 1, 0, 10));
//	        this.setBorder(new EmptyBorder(0, 10, 0, 0));

	        this.btnGameStart = new client.gui.component.Button("게임 시작");
	        this.btnGameStart.setFont(client.gui.component.Fonts.defaultFont);
	        this.btnGameStart.setBackground(new Color(0x4472C4));
	        this.btnGameStart.setForeground(Color.WHITE);

	        btnExit = new client.gui.component.Button("나가기");
	        this.btnExit.setFont(client.gui.component.Fonts.defaultFont);
	        this.btnExit.setBackground(Color.WHITE);

	        this.add(btnGameStart);
	        this.add(btnExit);
	    }
	}
}