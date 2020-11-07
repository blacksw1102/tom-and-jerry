package net;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import gui.LoginScreen;
import gui.MakeRoomScreen;
import gui.SignUpScreen;
import gui.WaitingRoom;
import gui.WaitingRoomListScreen;

public class ClientWindow extends JFrame{
	public JPanel mainPanel = null;
	private LoginScreen loginScreen = null;
	public SignUpScreen signUpScreen = null;
    public MakeRoomScreen makeRoomScreen = null;
    public WaitingRoomListScreen waitingRoomListScreen = null;
    public WaitingRoom waitingRoom = null;
    private CardLayout cards = new CardLayout();
    
    public ClientWindow() {
    	loginScreen = new LoginScreen(ClientWindow.this);
        signUpScreen = new SignUpScreen(ClientWindow.this);
        makeRoomScreen = new MakeRoomScreen(ClientWindow.this);
        waitingRoomListScreen = new WaitingRoomListScreen();
        waitingRoom = new WaitingRoom();
        
        mainPanel = new JPanel();
        mainPanel.setLayout(cards);
        add(mainPanel);
       
        mainPanel.add("loginScreen", loginScreen);
        mainPanel.add("signUpScreen", signUpScreen);
        mainPanel.add("makeRoomScreen", makeRoomScreen);
        mainPanel.add("waitingRoomListScreen", waitingRoomListScreen);
        mainPanel.add("waitingRoom", waitingRoom);
        change("signUpScreen");

        this.setTitle("frame test");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - getWidth()) / 2;
		int y = (screenSize.height - getHeight()) / 2;
		this.setLocation(x, y);
		this.setVisible(true);    
	}
    
    // 패널을 변경합니다.
    public void change(String panelName) {
    	JPanel changePanel = null;
    	if(panelName.equals("loginScreen")) {
        	changePanel = loginScreen;
        	this.setSize(960, 540);
        }
    	if(panelName.equals("signUpScreen")) {
        	changePanel = signUpScreen;
        	this.setSize(1280, 720);
        }
        if(panelName.equals("makeRoomScreen")) {
        	changePanel = makeRoomScreen;
        	this.setSize(400, 250);
        }
        if(panelName.equals("waitingRoomListScreen")) {
        	changePanel = waitingRoomListScreen;
        	this.setSize(1280, 720);        	
        }
        if(panelName.equals("waitingRoom")) {
        	changePanel = waitingRoom;
        	this.setSize(1280, 720);    
        }
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - getWidth()) / 2;
		int y = (screenSize.height - getHeight()) / 2;
		this.setLocation(x, y);
        cards.show(mainPanel, panelName);
    }
    
    public static void main(String[] args) {
        ClientWindow win = new ClientWindow();
     }
}