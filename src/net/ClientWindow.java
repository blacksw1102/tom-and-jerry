package net;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
        // waitingRoomListScreen = new WaitingRoomListScreen();
        waitingRoom = new WaitingRoom();
        
        mainPanel = new JPanel();
        mainPanel.setLayout(cards);
        add(mainPanel);
       
        mainPanel.add("loginScreen", loginScreen);
        mainPanel.add("signUpScreen", signUpScreen);
        mainPanel.add("makeRoomScreen", makeRoomScreen);
        // mainPanel.add("waitingRoomListScreen", waitingRoomListScreen);
        mainPanel.add("waitingRoom", waitingRoom);
        change("loginScreen");

        this.setTitle("frame test");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - getWidth()) / 2;
		int y = (screenSize.height - getHeight()) / 2;
		this.setLocation(x, y);
		this.setVisible(true);
		
		this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) { 
                System.exit(0);
            }
        });
	}
    
    public void addScreen(String screenName, JPanel panel) {
    	mainPanel.add(screenName, panel);
    }
    
    // 패널을 변경합니다.
    public void change(String panelName) {
    	switch(panelName) {
    		case "loginScreen":
    			this.setSize(960, 540);
    			break;
    		case "signUpScreen":
    			this.setSize(1280, 720);
    			break;
    		case "makeRoomScreen":
    			this.setSize(400, 250);
    			break;
    		case "waitingRoomListScreen":
    			this.setSize(1280, 720);        	
    			break;
    		case "waitingRoom":
    			this.setSize(1280, 750);    
    			break;
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