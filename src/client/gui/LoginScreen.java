package client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import client.entity.Login;
import client.entity.User;
import server.util.GameProtocol;

public class LoginScreen extends JFrame {
	
	private JTextField idField;
	private JPasswordField pwField;
	
	private static final int FRAME_WIDTH = 960;
	private static final int FRAME_HEIGHT = 540;
	
	public LoginScreen() {
		this.setTitle("로그인 창");
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setResizable(false);
		this.setLayout(null);
		
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - getWidth()) / 2;
		int y = (screenSize.height - getHeight()) / 2;
		this.setLocation(x, y);
		
		this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) { 
                System.exit(0);
            }
        });
		
		JLabel titleLabel = new JLabel("톰과 제리 게임");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("HY견고딕", Font.PLAIN, 36));
		titleLabel.setBounds((getSize().width - 310) / 2, 75, 310, 86);
		this.add(titleLabel);
		
		JButton loginBtn = new JButton("로그인");
		loginBtn.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		loginBtn.setBounds((getSize().width - 310) / 2, 315, 150, 50);
		loginBtn.setBackground(new Color(0xffc000));
		loginBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Socket socket = new Socket("localhost", 4001);
					ObjectOutputStream out  = new ObjectOutputStream(socket.getOutputStream());
					ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

					// 서버에게 로그인 요청 처리
					GameProtocol protocol = new GameProtocol(GameProtocol.PT_RES_LOGIN);
					protocol.setData(new Login(idField.getText() ,pwField.getText()));
					out.writeObject(protocol);

					// 로그인 응답 결과 처리
					User user = (User) in.readObject();

					if(user == null) {
						showDialog("아이디 또는 비밀번호를 잘못입력했습니다.");
					} else {
						user.setSocket(socket);
						user.out = out;
						user.in = in;

						dispose();
						new LobbyScreen(user);
						
					}
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		this.add(loginBtn);
		
		JButton joinBtn = new JButton("회원가입");
		joinBtn.setBackground(Color.WHITE);
		joinBtn.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		joinBtn.setBounds((getSize().width - 310) / 2 + 150 + 10, 315, 150, 50);
        joinBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				SignUpScreen signUpScreen = new SignUpScreen();
			}
		});
        this.add(joinBtn);
		
		JPanel idPanel = new JPanel();
		idPanel.setLayout(null);
		idPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		idPanel.setBackground(Color.WHITE);
		idPanel.setBounds((getSize().width - 310) / 2, 195, 310, 50);
		this.add(idPanel);
		
		JLabel idLabel = new JLabel("ID : ");
		idLabel.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		idLabel.setBounds(14, 12, 62, 26);
		idPanel.add(idLabel);
		
		idField = new JTextField();
		idField.setBorder(BorderFactory.createEmptyBorder());
		idField.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		idField.setBounds(66, 12, 230, 26);
		idField.setColumns(10);
		idField.setText("admin");
		idPanel.add(idField);
		
		JPanel pwPanel = new JPanel();
		pwPanel.setLayout(null);
		pwPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		pwPanel.setBackground(Color.WHITE);
		pwPanel.setBounds((getSize().width - 310) / 2, 255, 310, 50);
		this.add(pwPanel);
		
		JLabel pwLabel = new JLabel("PW : ");
		pwLabel.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		pwLabel.setBounds(14, 12, 62, 26);
		pwPanel.add(pwLabel);
		
		pwField = new JPasswordField();
		pwField.setFont(new Font("돋움", Font.PLAIN, 18));
		pwField.setColumns(10);
		pwField.setBorder(BorderFactory.createEmptyBorder());
		pwField.setBounds(66, 12, 230, 26);
		pwField.setText("1234");
		pwPanel.add(pwField);
		
		this.setVisible(true);
	}
	
	public void showDialog(String message) {
		JDialog info = new JDialog(this, true);
		JButton ok = new JButton("확인");

		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				info.setVisible(false);
				info.dispose();
			}
		});

		info.setSize(300, 110);
		info.setLocationRelativeTo(this);
		info.setLayout(new FlowLayout());
		info.add(new JLabel(message, JLabel.CENTER));
		info.add(ok);
		info.setVisible(true);

	}
	
    public static void main(String[] args) {
    	new LoginScreen();
     }

}
