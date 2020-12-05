package client.net;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import client.gui.LoginScreen;
import client.gui.MakeRoomScreen;
import client.gui.SignUpScreen;
import client.gui.LobbyScreen;
import client.gui.WaitingRoomScreen;

public class ClientWindow extends JFrame{
	/*
	public JPanel mainPanel = null;
    public MakeRoomScreen makeRoomScreen = null;
    public LobbyScreen waitingRoomListScreen = null;
    public WaitingRoomScreen waitingRoomScreen = null;
    private CardLayout cards = new CardLayout();
    
    public ClientWindow() {
        // makeRoomScreen = new MakeRoomScreen(ClientWindow.this);
        // waitingRoomListScreen = new WaitingRoomListScreen();
        //waitingRoomScreen = new WaitingRoomScreen(null, null);
        
        mainPanel = new JPanel();
        mainPanel.setLayout(cards);
        add(mainPanel);
       
        // mainPanel.add("makeRoomScreen", makeRoomScreen);
        // mainPanel.add("waitingRoomListScreen", waitingRoomListScreen);
        //mainPanel.add("waitingRoomScreen", waitingRoomScreen);
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
    			this.setSize(370, 200);
    			break;
    		case "waitingRoomListScreen":
    			this.setSize(1280, 720);        	
    			break;
    		case "waitingRoomScreen":
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
     */
}