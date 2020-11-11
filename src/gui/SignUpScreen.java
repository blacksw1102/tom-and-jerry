package gui;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import entity.User;
import net.ClientWindow;
import util.GameProtocol;

public class SignUpScreen extends JPanel {
	private ClientWindow win;
	private JPasswordField pwField, pwConfirmField;
	private JTextField idField, nicknameField, emailField, birthField3, telField2, telField3;
	private JLabel idLabel, pwLabel, nicknameLable, emailLabel, pwConfirmLabel, birthLabel, birthLabel1, birthLabel2, birthLabel3, lineLabel1, lineLabel2;
	private Choice birthField1, birthField2, telField1;
	private JButton idCheckBtn, submitBtn, cancelBtn;

	private String verifiedId;	// 중복체크를 통과한 아이디
	
	public SignUpScreen(ClientWindow win) {
		this.win = win;
		this.setLayout(new BorderLayout());
		this.setSize(1280, 720);
		
		verifiedId = null;
		
		JLabel signUpTitle = new JLabel("\uD68C\uC6D0 \uAC00\uC785");
		signUpTitle.setHorizontalAlignment(SwingConstants.CENTER);
		signUpTitle.setFont(new Font("HY견고딕", Font.PLAIN, 36));
		add(signUpTitle, BorderLayout.NORTH);
		
		JPanel inputForm = new JPanel();
		inputForm.setLayout(null);
		add(inputForm, BorderLayout.CENTER);
		
		idLabel = new JLabel("\uC544\uC774\uB514 : ");
		idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		idLabel.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		idLabel.setBounds(409, 75, 125, 18);
		inputForm.add(idLabel);
		
		idField = new JTextField();
		idField.setFont(new Font("돋움", Font.PLAIN, 18));
		idField.setBounds(545, 70, 200, 30);
		inputForm.add(idField);
		idField.setColumns(20);
		idField.setText("testUser1");
		
		idCheckBtn = new JButton("\uC911\uBCF5\uD655\uC778");
		idCheckBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkIdDuplicate();
			}
		});
		idCheckBtn.setFont(new Font("HY견고딕", Font.PLAIN, 14));
		idCheckBtn.setBackground(Color.LIGHT_GRAY);
		idCheckBtn.setBounds(759, 65, 90, 40);
		inputForm.add(idCheckBtn);
		
		pwLabel = new JLabel("\uBE44\uBC00\uBC88\uD638 : ");
		pwLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pwLabel.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		pwLabel.setBounds(410, 125, 125, 18);
		inputForm.add(pwLabel);
		
		nicknameLable = new JLabel("\uB2C9\uB124\uC784 : ");
		nicknameLable.setHorizontalAlignment(SwingConstants.RIGHT);
		nicknameLable.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		nicknameLable.setBounds(409, 225, 125, 18);
		inputForm.add(nicknameLable);
		
		emailLabel = new JLabel("\uC774\uBA54\uC77C : ");
		emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		emailLabel.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		emailLabel.setBounds(409, 275, 125, 18);
		inputForm.add(emailLabel);
		
		pwField = new JPasswordField();
		pwField.setFont(new Font("돋움", Font.PLAIN, 18));
		pwField.setBounds(545, 120, 200, 30);
		pwField.setText("1234");
		inputForm.add(pwField);
		
		pwConfirmLabel = new JLabel("\uBE44\uBC00\uBC88\uD638 \uD655\uC778 : ");
		pwConfirmLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pwConfirmLabel.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		pwConfirmLabel.setBounds(345, 175, 188, 18);
		inputForm.add(pwConfirmLabel);
		
		pwConfirmField = new JPasswordField();
		pwConfirmField.setFont(new Font("돋움", Font.PLAIN, 18));
		pwConfirmField.setBounds(545, 170, 200, 30);
		pwConfirmField.setText("1234");
		inputForm.add(pwConfirmField);
		
		nicknameField = new JTextField();
		nicknameField.setFont(new Font("돋움", Font.PLAIN, 18));
		nicknameField.setColumns(20);
		nicknameField.setBounds(545, 220, 200, 30);
		nicknameField.setText("testUser");
		inputForm.add(nicknameField);
		
		emailField = new JTextField();
		emailField.setFont(new Font("돋움", Font.PLAIN, 18));
		emailField.setColumns(20);
		emailField.setBounds(545, 270, 200, 30);
		emailField.setText("testUser@gmail.com");
		inputForm.add(emailField);
		
		birthLabel = new JLabel("\uC0DD\uB144\uC6D4\uC77C : ");
		birthLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		birthLabel.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		birthLabel.setBounds(409, 325, 125, 18);
		inputForm.add(birthLabel);
		
		birthLabel1 = new JLabel("\uB144");
		birthLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		birthLabel1.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		birthLabel1.setBounds(635, 325, 30, 18);
		inputForm.add(birthLabel1);
		
		birthLabel2 = new JLabel("\uC6D4");
		birthLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		birthLabel2.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		birthLabel2.setBounds(745, 325, 30, 18);
		inputForm.add(birthLabel2);
		
		birthLabel3 = new JLabel("\uC77C");
		birthLabel3.setHorizontalAlignment(SwingConstants.CENTER);
		birthLabel3.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		birthLabel3.setBounds(855, 325, 28, 18);
		inputForm.add(birthLabel3);
		
		birthField1 = new Choice();
		birthField1.setSize(80, 30);
		birthField1.setFont(new Font("Dialog", Font.PLAIN, 18));
		birthField1.setLocation(545, 320);
		inputForm.add(birthField1);
		for(int year=1960; year<=2020; year++)
			birthField1.add(String.valueOf(year));

		birthField2 = new Choice();
		birthField2.setFont(new Font("Dialog", Font.PLAIN, 18));
		birthField2.setBounds(675, 320, 60, 32);
		inputForm.add(birthField2);
		for(int month=1; month<=12; month++)
			birthField2.add(String.valueOf(month));

		birthField3 = new JTextField();
		birthField3.setHorizontalAlignment(SwingConstants.CENTER);
		birthField3.setFont(new Font("돋움", Font.PLAIN, 18));
		birthField3.setColumns(2);
		birthField3.setBounds(785, 320, 60, 32);
		birthField3.setText("24");
		inputForm.add(birthField3);
		
		JLabel telLabel = new JLabel("\uC804\uD654\uBC88\uD638 : ");
		telLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		telLabel.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		telLabel.setBounds(409, 375, 125, 18);
		inputForm.add(telLabel);

		telField1 = new Choice();
		telField1.setFont(new Font("Dialog", Font.PLAIN, 18));
		telField1.setBounds(545, 370, 80, 32);
		inputForm.add(telField1);
		String[] telFirstList = {"010", "011", "016", "017", "019"};
		for(String telFirst : telFirstList)
			telField1.add(telFirst);
		
		lineLabel1 = new JLabel("-");
		lineLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		lineLabel1.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		lineLabel1.setBounds(635, 375, 30, 18);
		inputForm.add(lineLabel1);

		telField2 = new JTextField();
		telField2.setHorizontalAlignment(SwingConstants.CENTER);
		telField2.setFont(new Font("돋움", Font.PLAIN, 18));
		telField2.setColumns(4);
		telField2.setBounds(675, 370, 60, 32);
		telField2.setText("1234");
		inputForm.add(telField2);

		lineLabel2 = new JLabel("-");
		lineLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		lineLabel2.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		lineLabel2.setBounds(745, 375, 30, 18);
		inputForm.add(lineLabel2);
		
		telField3 = new JTextField();
		telField3.setHorizontalAlignment(SwingConstants.CENTER);
		telField3.setFont(new Font("돋움", Font.PLAIN, 18));
		telField3.setColumns(4);
		telField3.setBounds(785, 370, 60, 32);
		telField3.setText("1234");
		inputForm.add(telField3);
		
		submitBtn = new JButton("\uAC00\uC785");
		submitBtn.setBackground(Color.ORANGE);
		submitBtn.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		submitBtn.setBounds(492, 471, 150, 50);
		submitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isValidate())
					signUp();
			}
		});
		inputForm.add(submitBtn);
		
		cancelBtn = new JButton("\uCDE8\uC18C");
		cancelBtn.setBackground(Color.WHITE);
		cancelBtn.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		cancelBtn.setBounds(656, 471, 150, 50);
		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				win.change("loginScreen");
			}
		});
		inputForm.add(cancelBtn);
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
				showDialog("사용가능한 아이디입니다.");
			} else 
				showDialog("이미 존재하는 아이디입니다.");
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
		if(verifiedId.isEmpty() || !verifiedId.equals(idField.getText())) {
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
			// 회원가입 정보 서버에 전송
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
	
	// 회원가입에 성공했을 때 나타나는 Dialog
	public void showSignUpSuccessDialog() {
		JDialog info = new JDialog(win, true);
		info.setSize(200, 110);
		info.setLocationRelativeTo(null);
		info.setLayout(new FlowLayout());
		JButton ok = new JButton("확인");
		info.add(new JLabel("회원가입이 되었습니다.", JLabel.CENTER));
		info.add(ok);
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				info.setVisible(false);
				info.dispose();
				win.change("loginScreen");							
			}
		});
		info.setVisible(true);
	}

}

