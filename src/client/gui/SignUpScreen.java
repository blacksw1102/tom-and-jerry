package client.gui;

import java.awt.Choice;
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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import client.entity.User;
import client.net.ClientWindow;
import server.util.GameProtocol;

public class SignUpScreen extends JFrame {
	private ClientWindow win;
	private JPasswordField pwField, pwConfirmField;
	private JTextField idField, nicknameField, emailField, birthField3, telField2, telField3;
	private JLabel idLabel, pwLabel, nicknameLable, emailLabel, pwConfirmLabel, birthLabel, birthLabel1, birthLabel2, birthLabel3, lineLabel1, lineLabel2;
	private Choice birthField1, birthField2, telField1;
	private JButton idCheckBtn, submitBtn, cancelBtn;

	private static final int FRAME_WIDTH = 1280;
	private static final int FRAME_HEIGHT = 720;
	
	private String verifiedId;	// 중복체크를 통과한 아이디
	
	public SignUpScreen() {
		this.setTitle("회원가입 창");
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
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
		
		JLabel signUpTitle = new JLabel("회원 가입");
		signUpTitle.setHorizontalAlignment(SwingConstants.CENTER);
		signUpTitle.setFont(new Font("HY견고딕", Font.PLAIN, 36));
		signUpTitle.setBounds(540, 50, 200, 70);
		this.add(signUpTitle);
		
		idLabel = new JLabel("아이디 : ");
		idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		idLabel.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		idLabel.setBounds(409, 155, 125, 18);
		this.add(idLabel);
		
		idField = new JTextField();
		idField.setFont(new Font("돋움", Font.PLAIN, 18));
		idField.setBounds(545, 150, 200, 30);
		idField.setColumns(20);
		idField.setText("testUser1");
		this.add(idField);
		
		idCheckBtn = new JButton("중복 확인");
		idCheckBtn.setFont(new Font("HY견고딕", Font.PLAIN, 14));
		idCheckBtn.setBackground(Color.LIGHT_GRAY);
		idCheckBtn.setBounds(759, 150, 100, 30);
		idCheckBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkIdDuplicate();
			}
		});
		this.add(idCheckBtn);
		
		pwLabel = new JLabel("비밀번호 : ");
		pwLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pwLabel.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		pwLabel.setBounds(410, 205, 125, 18);
		this.add(pwLabel);
		
		pwField = new JPasswordField();
		pwField.setFont(new Font("돋움", Font.PLAIN, 18));
		pwField.setBounds(545, 200, 200, 30);
		pwField.setText("1234");
		this.add(pwField);
		
		pwConfirmField = new JPasswordField();
		pwConfirmField.setFont(new Font("돋움", Font.PLAIN, 18));
		pwConfirmField.setBounds(545, 250, 200, 30);
		pwConfirmField.setText("1234");
		this.add(pwConfirmField);
		
		pwConfirmLabel = new JLabel("비밀번호 확인 : ");
		pwConfirmLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pwConfirmLabel.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		pwConfirmLabel.setBounds(345, 255, 188, 18);
		this.add(pwConfirmLabel);
		
		nicknameLable = new JLabel("닉네임 : ");
		nicknameLable.setHorizontalAlignment(SwingConstants.RIGHT);
		nicknameLable.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		nicknameLable.setBounds(409, 305, 125, 18);
		this.add(nicknameLable);
		
		nicknameField = new JTextField();
		nicknameField.setFont(new Font("돋움", Font.PLAIN, 18));
		nicknameField.setColumns(20);
		nicknameField.setBounds(545, 300, 200, 30);
		nicknameField.setText("testUser");
		this.add(nicknameField);
		
		emailLabel = new JLabel("이메일 : ");
		emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		emailLabel.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		emailLabel.setBounds(409, 355, 125, 18);
		this.add(emailLabel);
		
		emailField = new JTextField();
		emailField.setFont(new Font("돋움", Font.PLAIN, 18));
		emailField.setColumns(20);
		emailField.setBounds(545, 350, 200, 30);
		emailField.setText("testUser@gmail.com");
		this.add(emailField);
		
		birthLabel = new JLabel("생년월일 : ");
		birthLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		birthLabel.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		birthLabel.setBounds(409, 405, 125, 18);
		this.add(birthLabel);
		
		birthField1 = new Choice();
		birthField1.setSize(80, 30);
		birthField1.setFont(new Font("Dialog", Font.PLAIN, 18));
		for(int year=1960; year<=2020; year++)
			birthField1.add(String.valueOf(year));
		birthField1.setLocation(545, 400);
		this.add(birthField1);
		
		birthLabel1 = new JLabel("년");
		birthLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		birthLabel1.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		birthLabel1.setBounds(635, 405, 30, 18);
		this.add(birthLabel1);
		
		birthField2 = new Choice();
		birthField2.setFont(new Font("Dialog", Font.PLAIN, 18));
		birthField2.setBounds(675, 400, 60, 32);
		for(int month=1; month<=12; month++)
			birthField2.add(String.valueOf(month));
		this.add(birthField2);
		
		birthLabel2 = new JLabel("월");
		birthLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		birthLabel2.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		birthLabel2.setBounds(745, 405, 30, 18);
		this.add(birthLabel2);
		
		birthField3 = new JTextField();
		birthField3.setHorizontalAlignment(SwingConstants.CENTER);
		birthField3.setFont(new Font("돋움", Font.PLAIN, 18));
		birthField3.setColumns(2);
		birthField3.setBounds(785, 400, 60, 32);
		birthField3.setText("24");
		this.add(birthField3);
		
		birthLabel3 = new JLabel("일");
		birthLabel3.setHorizontalAlignment(SwingConstants.CENTER);
		birthLabel3.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		birthLabel3.setBounds(855, 405, 28, 18);
		this.add(birthLabel3);
		
		JLabel telLabel = new JLabel("전화번호 : ");
		telLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		telLabel.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		telLabel.setBounds(409, 455, 125, 18);
		this.add(telLabel);

		telField1 = new Choice();
		telField1.setFont(new Font("Dialog", Font.PLAIN, 18));
		telField1.setBounds(545, 450, 80, 32);
		String[] telFirstList = {"010", "011", "016", "017", "019"};
		for(String telFirst : telFirstList)
			telField1.add(telFirst);
		this.add(telField1);
		
		lineLabel1 = new JLabel("-");
		lineLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		lineLabel1.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		lineLabel1.setBounds(635, 455, 30, 18);
		this.add(lineLabel1);

		telField2 = new JTextField();
		telField2.setHorizontalAlignment(SwingConstants.CENTER);
		telField2.setFont(new Font("돋움", Font.PLAIN, 18));
		telField2.setColumns(4);
		telField2.setBounds(675, 450, 60, 32);
		telField2.setText("1234");
		this.add(telField2);

		lineLabel2 = new JLabel("-");
		lineLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		lineLabel2.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		lineLabel2.setBounds(745, 455, 30, 18);
		this.add(lineLabel2);
		
		telField3 = new JTextField();
		telField3.setHorizontalAlignment(SwingConstants.CENTER);
		telField3.setFont(new Font("돋움", Font.PLAIN, 18));
		telField3.setColumns(4);
		telField3.setBounds(785, 450, 60, 32);
		telField3.setText("1234");
		this.add(telField3);
		
		submitBtn = new JButton("회원가입");
		submitBtn.setBackground(Color.ORANGE);
		submitBtn.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		submitBtn.setBounds(492, 530, 150, 50);
		submitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isValidate())
					signUp();
			}
		});
		this.add(submitBtn);
		
		cancelBtn = new JButton("취소");
		cancelBtn.setBackground(Color.WHITE);
		cancelBtn.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		cancelBtn.setBounds(656, 530, 150, 50);
		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new LoginScreen();
			}
		});
		this.add(cancelBtn);
		
		this.setVisible(true);
	}
	
	// 아이디 중복 체크
	private void checkIdDuplicate() {
		String id = idField.getText();

		Socket socket = null;
		ObjectOutputStream output = null;
		ObjectInputStream input = null;
		
		try {
			socket = new Socket("localhost", 4001);
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());
			
			GameProtocol protocol = new GameProtocol(GameProtocol.PT_ID_DUPLICATE_CHECK);
			protocol.setData(id);
			output.writeObject(protocol);
			
			// 아이디 중복체크 결과 기다림
			protocol = (GameProtocol) input.readObject();
			boolean isSuccess = (boolean) protocol.getData();
			
			// 아이디 중복체크 결과를 유저에게 알림
			if(isSuccess) {
				verifiedId = id;
				showDialog("사용 가능한 아이디 입니다.");
			} else {
				showDialog("이미 존재하는 아이디입니다.");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	// 회원가입 유효성 체크
	private boolean isValidate() {

		// 아이디 중복 체크
		if(verifiedId == null || !verifiedId.equals(idField.getText())) {
			showDialog("아이디 중복을 체크해주세요.");
			return false;
		}
		
		// 비밀번호 확인 체크
		if(!pwField.getText().equals(pwConfirmField.getText())) {
			showDialog("비밀번호를 정확히 입력해주세요.");
			return false;
		}
		
		return true;
	}
	
	private void signUp() {
		String id = idField.getText();
		String pw = pwField.getText();
		String nickname = nicknameField.getText();
		String email = emailField.getText();
		String birth = String.format("%s-%s-%s", birthField1.getSelectedItem(), birthField2.getSelectedItem(), birthField3.getText());
		String tel = String.format("%s-%s-%s", telField1.getSelectedItem(), telField2.getText(), telField3.getText());
		User user = new User(id, pw, nickname, email, birth, tel);
		
		Socket socket = null;
		ObjectOutputStream output = null;
		ObjectInputStream input = null;
		
		// 회원가입 정보에 대한 유효성 검사
		if(!isValidate())
			return; 
		
		try {
			socket = new Socket("localhost", 4001);
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());
			
			GameProtocol protocol = new GameProtocol(GameProtocol.PT_REQ_SIGN_UP);
			protocol.setData(user);
			output.writeObject(protocol);
			
			// 회원가입 처리결과 기다림
			protocol = (GameProtocol) input.readObject();
			boolean isSuccess = (boolean) protocol.getData();
			
			// 회원가입 결과를 유저에게 알림
			if(isSuccess) {
				showSignUpSuccessDialog();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}				
	}
	
	public void showDialog(String message) {
		JDialog info = new JDialog(win, true);
		JButton ok = new JButton("확인");

		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				info.setVisible(false);
				info.dispose();
			}
		});

		info.setSize(200, 110);
		info.setLocationRelativeTo(win);
		info.setLayout(new FlowLayout());
		info.add(new JLabel(message, JLabel.CENTER));
		info.add(ok);
		info.setVisible(true);

	}
	
	public void showSignUpSuccessDialog() {
		JDialog info = new JDialog(win, true);
		JButton ok = new JButton("확인");
		
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				info.setVisible(false);
				info.dispose();
				
				setVisible(false);
				new LoginScreen();
			}
		});

		info.setSize(200, 110);
		info.setLocationRelativeTo(win);
		info.setLayout(new FlowLayout());
		info.add(new JLabel("회원가입이 되었습니다.", JLabel.CENTER));
		info.add(ok);
		info.setVisible(true);
	}

}

