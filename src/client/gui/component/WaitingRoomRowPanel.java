package client.gui.component;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class WaitingRoomRowPanel extends JPanel {
	private JLabel lblUserName, lblUserState;

	private Color color = null;
	private String userStateStr = null;

	private static final String USER_STATE_NOT_READY = "대기";
	private static final String USER_STATE_READY = "준비";

	public WaitingRoomRowPanel(int x, int y) {
		this.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.setBackground(Color.WHITE);
		this.setBounds(x, y, 380, 60);
		this.setLayout(null);
		
		lblUserName = new JLabel();
		lblUserName.setHorizontalAlignment(SwingConstants.LEFT);
		lblUserName.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		lblUserName.setBounds(14, 12, 127, 36);
		this.add(lblUserName);
		
		lblUserState = new JLabel(userStateStr);
		lblUserState.setOpaque(true);
		lblUserState.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblUserState.setBackground(color);
		lblUserState.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserState.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		lblUserState.setBounds(303, 12, 65, 36);
		this.add(lblUserState);
	}
	
	public void setUserName(String name) {
		lblUserName.setText(name);
	}
	
	public void setUserState(int userState) {
		if(userState == 0) {
			color = new Color(240, 240, 240);
			userStateStr = USER_STATE_NOT_READY;
		} else {
			color = new Color(255, 165, 0);
			userStateStr = USER_STATE_READY;
		}
		
		lblUserState.setBackground(color);
		lblUserState.setText(userStateStr);
	}
	
}
