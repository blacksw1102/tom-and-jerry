package game.client.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class StartView extends JFrame {

	private JTextField nickname;

	public StartView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(250, 100, 800, 600);
		setTitle("톰과 제리 게임");

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblId = new JLabel("닉네임을 입력해 주세요");
		lblId.setBounds(300, 200, 200, 50);
		contentPane.add(lblId);

		nickname = new JTextField();
		nickname.setBounds(250, 300, 230, 50);
		contentPane.add(nickname);

		JButton btn = new JButton("접속하기");
		btn.setBounds(500, 300, 100, 50);
		contentPane.add(btn);

		Action action = new Action();
		btn.addActionListener(action);

		setVisible(true);
	}

	class Action implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String _nickname = nickname.getText().trim();
			ReadyView readyView = new ReadyView(_nickname);
			setVisible(false);
		}
	}

}
