package gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class WaitingRoom extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public WaitingRoom() {
		this.setLayout(null);
		this.setSize(1280, 780);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(Color.WHITE);
		panel.setBounds(128, 50, 1000, 60);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("041");
		lblNewLabel.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		lblNewLabel.setBounds(14, 12, 74, 36);
		panel.add(lblNewLabel);
		
		JLabel label = new JLabel("\uD14C\uC2A4\uD2B8 \uBC29 \uC81C\uBAA9");
		label.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		label.setBounds(76, 12, 397, 36);
		panel.add(label);
		
		JButton btnNewButton_1 = new JButton("\uBC29 \uC81C\uBAA9\uBCC0\uACBD");
		btnNewButton_1.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		btnNewButton_1.setBounds(851, 12, 135, 35);
		btnNewButton_1.setBackground(new Color(0xf2f2f2));
		panel.add(btnNewButton_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(128, 130, 590, 350);
		add(panel_1);
		panel_1.setLayout(null);
		
		JLabel label_1 = new JLabel("\uD14C\uC2A4\uD2B8 \uBC29 \uC81C\uBAA9");
		label_1.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		label_1.setBounds(14, 12, 397, 36);
		panel_1.add(label_1);
		
		JLabel label_2 = new JLabel("\uD14C\uC2A4\uD2B8 \uBC29 \uC81C\uBAA9");
		label_2.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		label_2.setBounds(14, 60, 397, 36);
		panel_1.add(label_2);
		
		JLabel label_3 = new JLabel("\uD14C\uC2A4\uD2B8 \uBC29 \uC81C\uBAA9");
		label_3.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		label_3.setBounds(14, 111, 397, 36);
		panel_1.add(label_3);
		
		JLabel label_4 = new JLabel("\uD14C\uC2A4\uD2B8 \uBC29 \uC81C\uBAA9");
		label_4.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		label_4.setBounds(14, 159, 397, 36);
		panel_1.add(label_4);
		
		JLabel label_5 = new JLabel("\uD14C\uC2A4\uD2B8 \uBC29 \uC81C\uBAA9");
		label_5.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		label_5.setBounds(14, 208, 397, 36);
		panel_1.add(label_5);
		
		JLabel label_6 = new JLabel("\uD14C\uC2A4\uD2B8 \uBC29 \uC81C\uBAA9");
		label_6.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		label_6.setBounds(14, 256, 397, 36);
		panel_1.add(label_6);
		
		JLabel label_7 = new JLabel("\uD14C\uC2A4\uD2B8 \uBC29 \uC81C\uBAA9");
		label_7.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		label_7.setBounds(14, 304, 397, 36);
		panel_1.add(label_7);
		
		JLabel readyLabel1 = new JLabel("\uBC29\uC7A5");
		readyLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		readyLabel1.setFont(new Font("HY견고딕", Font.PLAIN, 15));
		readyLabel1.setBounds(511, 20, 65, 25);
		readyLabel1.setOpaque(true);
		readyLabel1.setBackground(new Color(0xffc000));
		readyLabel1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.add(readyLabel1);
		
		JLabel label_8 = new JLabel("\uC900\uBE44");
		label_8.setOpaque(true);
		label_8.setHorizontalAlignment(SwingConstants.CENTER);
		label_8.setFont(new Font("HY견고딕", Font.PLAIN, 15));
		label_8.setBorder(new LineBorder(new Color(0, 0, 0)));
		label_8.setBackground(new Color(242, 242, 242));
		label_8.setBounds(511, 119, 65, 25);
		panel_1.add(label_8);
		
		JLabel label_9 = new JLabel("\uC900\uBE44");
		label_9.setOpaque(true);
		label_9.setHorizontalAlignment(SwingConstants.CENTER);
		label_9.setFont(new Font("HY견고딕", Font.PLAIN, 15));
		label_9.setBorder(new LineBorder(new Color(0, 0, 0)));
		label_9.setBackground(new Color(242, 242, 242));
		label_9.setBounds(511, 170, 65, 25);
		panel_1.add(label_9);
		
		JLabel label_10 = new JLabel("\uC900\uBE44");
		label_10.setOpaque(true);
		label_10.setHorizontalAlignment(SwingConstants.CENTER);
		label_10.setFont(new Font("HY견고딕", Font.PLAIN, 15));
		label_10.setBorder(new LineBorder(new Color(0, 0, 0)));
		label_10.setBackground(new Color(242, 242, 242));
		label_10.setBounds(511, 71, 65, 25);
		panel_1.add(label_10);
		
		JLabel label_11 = new JLabel("\uC900\uBE44");
		label_11.setOpaque(true);
		label_11.setHorizontalAlignment(SwingConstants.CENTER);
		label_11.setFont(new Font("HY견고딕", Font.PLAIN, 15));
		label_11.setBorder(new LineBorder(new Color(0, 0, 0)));
		label_11.setBackground(new Color(242, 242, 242));
		label_11.setBounds(511, 219, 65, 25);
		panel_1.add(label_11);
		
		JLabel label_12 = new JLabel("\uC900\uBE44");
		label_12.setOpaque(true);
		label_12.setHorizontalAlignment(SwingConstants.CENTER);
		label_12.setFont(new Font("HY견고딕", Font.PLAIN, 15));
		label_12.setBorder(new LineBorder(new Color(0, 0, 0)));
		label_12.setBackground(new Color(242, 242, 242));
		label_12.setBounds(511, 267, 65, 25);
		panel_1.add(label_12);
		
		JLabel label_13 = new JLabel("\uC900\uBE44");
		label_13.setOpaque(true);
		label_13.setHorizontalAlignment(SwingConstants.CENTER);
		label_13.setFont(new Font("HY견고딕", Font.PLAIN, 15));
		label_13.setBorder(new LineBorder(new Color(0, 0, 0)));
		label_13.setBackground(new Color(242, 242, 242));
		label_13.setBounds(511, 315, 65, 25);
		panel_1.add(label_13);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(738, 130, 390, 233);
		add(panel_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_3.setBackground(Color.WHITE);
		panel_3.setBounds(128, 500, 590, 133);
		add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("돋움", Font.PLAIN, 15));
		scrollPane.setViewportView(textArea);
		panel_3.add(scrollPane, BorderLayout.CENTER);
		
		JButton btnNewButton = new JButton("\uAC8C\uC784 \uC2DC\uC791");
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(new Color(0x4472c4));
		btnNewButton.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		btnNewButton.setBounds(738, 528, 390, 70);
		add(btnNewButton);
		
		JButton button = new JButton("\uB098\uAC00\uAE30");
		button.setBackground(Color.WHITE);
		button.setFont(new Font("HY견고딕", Font.PLAIN, 18));
		button.setBounds(738, 610, 390, 70);
		add(button);
		
		textField = new JTextField();
		textField.setFont(new Font("돋움", Font.PLAIN, 15));
		textField.setBounds(128, 645, 500, 35);
		add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("\uC785\uB825");
		btnNewButton_2.setFont(new Font("HY견고딕", Font.PLAIN, 15));
		btnNewButton_2.setBounds(638, 645, 80, 35);
		btnNewButton_2.setBackground(new Color(0xf2f2f2));
		add(btnNewButton_2);
	}
}
