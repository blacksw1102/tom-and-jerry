package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import entity.Login;
import net.ClientWindow;
import util.GameProtocol;

public class LoginScreen extends JPanel {
	private JTextField idField;
	private JPasswordField pwField;
	private ClientWindow win;
	
	/**
	 * Create the panel.
	 */
	public LoginScreen(ClientWindow win) {
		this.win = win;
		this.setSize(960, 540);
		this.setLayout(null);
		
		JLabel titleLabel = new JLabel("\uD1B0\uACFC \uC81C\uB9AC \uAC8C\uC784");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("HY�߰���", Font.PLAIN, 36));
		titleLabel.setBounds((getSize().width - 310) / 2, 75, 310, 86);
		add(titleLabel);
		
		JButton loginBtn = new JButton("\uB85C\uADF8\uC778");
		loginBtn.setFont(new Font("HY�߰���", Font.PLAIN, 18));
		loginBtn.setBounds((getSize().width - 310) / 2, 315, 150, 50);
		loginBtn.setBackground(new Color(0xffc000));
		loginBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Socket socket = new Socket("localhost", 4001);
					ObjectOutputStream output  = new ObjectOutputStream(socket.getOutputStream());
					ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

					// �������� �α��� ���� ����
					GameProtocol protocol = new GameProtocol(GameProtocol.PT_RES_LOGIN);
					protocol.setData(new Login(idField.getText() ,pwField.getText()));
					output.writeObject(protocol);
					// �α��ο� ���� �ϸ� ������ �̵�
					if(input.readBoolean()) {
						win.change("waitingRoomListScreen");
					}
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		add(loginBtn);
		
		JButton joinBtn = new JButton("\uD68C\uC6D0\uAC00\uC785");
		joinBtn.setBackground(Color.WHITE);
		joinBtn.setFont(new Font("HY�߰���", Font.PLAIN, 18));
		joinBtn.setBounds((getSize().width - 310) / 2 + 150 + 10, 315, 150, 50);
        joinBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				win.change("signUpScreen");
			}
		});
		add(joinBtn);
		
		JPanel idPanel = new JPanel();
		idPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		idPanel.setBackground(Color.WHITE);
		idPanel.setBounds((getSize().width - 310) / 2, 195, 310, 50);
		add(idPanel);
		idPanel.setLayout(null);
		
		JLabel idLabel = new JLabel("ID : ");
		idLabel.setFont(new Font("HY�߰���", Font.PLAIN, 18));
		idLabel.setBounds(14, 12, 62, 26);
		idPanel.add(idLabel);
		
		idField = new JTextField();
		idField.setBorder(BorderFactory.createEmptyBorder());
		idField.setFont(new Font("HY�߰���", Font.PLAIN, 18));
		idField.setBounds(66, 12, 230, 26);
		idField.setColumns(10);
		idField.setText("admin");
		idPanel.add(idField);
		
		JPanel pwPanel = new JPanel();
		pwPanel.setLayout(null);
		pwPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		pwPanel.setBackground(Color.WHITE);
		pwPanel.setBounds((getSize().width - 310) / 2, 255, 310, 50);
		add(pwPanel);
		
		JLabel pwLabel = new JLabel("PW : ");
		pwLabel.setFont(new Font("HY�߰���", Font.PLAIN, 18));
		pwLabel.setBounds(14, 12, 62, 26);
		pwPanel.add(pwLabel);
		
		pwField = new JPasswordField();
		pwField.setFont(new Font("����", Font.PLAIN, 18));
		pwField.setColumns(10);
		pwField.setBorder(BorderFactory.createEmptyBorder());
		pwField.setBounds(66, 12, 230, 26);
		pwField.setText("admin");
		pwPanel.add(pwField);
	}

}
