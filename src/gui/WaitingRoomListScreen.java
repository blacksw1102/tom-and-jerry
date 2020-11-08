package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class WaitingRoomListScreen extends JPanel {
    public WaitingRoomListScreen() {
        super();

        this.setSize(1280, 720);
        this.setLayout(new BorderLayout());
        this.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 5), new EmptyBorder(40, 300, 40, 300)));

        this.initTopArea();
        this.initMiddleArea();
        this.initBottomArea();
    }

    private void initTopArea() {
        JPanel topArea = new JPanel();
        topArea.setLayout(new GridLayout(1, 2));

        /* ù��° ��� ��ư �׷� */
        JPanel buttonsPanel1 = new JPanel();
        buttonsPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));

        /* �游��� ��ư */
        JButton btnMakeRoom = new MakeRoomButton();

        /* ��� �� ��ư */
        JButton btnWaitRoom = new WaitRoomButton();

        buttonsPanel1.add(btnMakeRoom);
        buttonsPanel1.add(btnWaitRoom);

        /* �ι�° ��� ��ư �׷� */
        JPanel buttonsPanel2 = new JPanel();
        buttonsPanel2.setLayout(new FlowLayout(FlowLayout.RIGHT));

        /* �α׾ƿ� */
        JButton btnLogout = new LogoutButton();
        /* ���� */
        JButton btnSetting = new SettingButton();

        buttonsPanel2.add(btnLogout);
        buttonsPanel2.add(btnSetting);

        topArea.add(buttonsPanel1);
        topArea.add(buttonsPanel2);

        this.add(topArea, BorderLayout.PAGE_START);
    }

    private void initMiddleArea() {
        RoomListHeaderRow header = new RoomListHeaderRow();

        JPanel middleArea = new JPanel();
        middleArea.setLayout(new GridLayout(1,1));
        middleArea.setBackground(new Color(0xFFFFFF));
        middleArea.setBorder(new LineBorder(Color.BLACK, 3));

        JPanel roomList = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;

        roomList.add(new RoomListHeaderRow(), c);
        for (int i = 0; i < 20; i++) {
            c.gridy = i;
            roomList.add(new RoomListBodyRow(i, "���� �׽�Ʈ" + (i + 1), new Random().nextInt(7),  new Random().nextInt(2)), c);
        }

        JScrollPane scrollArea = new JScrollPane(roomList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        middleArea.add(scrollArea);

        this.add(middleArea, BorderLayout.CENTER);
    }
    
    private void initBottomArea() {
        JPanel bottomArea = new JPanel();

        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        bottomArea.setLayout(gbl);
        bottomArea.setPreferredSize(new Dimension(0, 200));
        
        JLabel chatLabel = new JLabel("ä��â");
        chatLabel.setFont(new Font("HY�߰��", Font.PLAIN, 18));
        gbc.insets = new Insets(3, 0, 0, 0);
    	gbc.gridx = 0;
    	gbc.gridy = 0;
    	gbc.gridwidth = 1;
    	gbc.gridheight = 1;
    	gbc.weightx = 0;
    	gbc.weighty = 0;
    	gbl.setConstraints(chatLabel, gbc);
    	bottomArea.add(chatLabel);
    	
        JLabel playerLabel = new JLabel("������ ���");
        playerLabel.setFont(new Font("HY�߰��", Font.PLAIN, 18));
        gbc.insets = new Insets(3, 0, 0, 0);
    	gbc.gridx = 1;
    	gbc.gridy = 0;
    	gbc.gridwidth = 1;
    	gbc.gridheight = 1;
    	gbc.weightx = 0;
    	gbc.weighty = 0;
    	gbl.setConstraints(playerLabel, gbc);
    	bottomArea.add(playerLabel);
        
        JTextArea chatArea = new JTextArea();
        chatArea.setFont(new Font("���� ���", Font.PLAIN, 18));
        JScrollPane scrollChatArea = new JScrollPane(chatArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollChatArea.setBorder(new CompoundBorder(new EmptyBorder(5, 0, 0, 0), new LineBorder(Color.BLACK, 3)));
        // ���� ������
        chatArea.append("[����] : �׽�Ʈ ä�� ����..\n");
        chatArea.append("[����] : �׽�Ʈ ä�� ����..\n");
        chatArea.append("[����] : �׽�Ʈ ä�� ����..\n");
        chatArea.append("[����] : �׽�Ʈ ä�� ����..\n");
        chatArea.append("[����] : �׽�Ʈ ä�� ����..\n");
        chatArea.append("[����] : �׽�Ʈ ä�� ����..\n");
        chatArea.append("[����] : �׽�Ʈ ä�� ����..\n");
        chatArea.append("[����] : �׽�Ʈ ä�� ����..\n");
        chatArea.append("[����] : �׽�Ʈ ä�� ����..\n");
        chatArea.append("[����] : �׽�Ʈ ä�� ����..\n");
        chatArea.append("[����] : �׽�Ʈ ä�� ����..\n");
        chatArea.append("[����] : �׽�Ʈ ä�� ����..\n");
        chatArea.append("[����] : �׽�Ʈ ä�� ����..\n");
        chatArea.append("[����] : �׽�Ʈ ä�� ����..\n");
        gbc.insets = new Insets(0, 0, 3, 3);
    	gbc.gridx = 0;
    	gbc.gridy = 1;
    	gbc.gridwidth = 1;
    	gbc.gridheight = 1;
    	gbc.weightx = 1;
    	gbc.weighty = 1;
    	gbl.setConstraints(scrollChatArea, gbc);
    	bottomArea.add(scrollChatArea);
    	
        JTextArea playArea = new JTextArea();
        playArea.setFont(new Font("���� ���", Font.PLAIN, 18));
        JScrollPane scrolPlayArea= new JScrollPane(playArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrolPlayArea.setBorder(new CompoundBorder(new EmptyBorder(5, 0, 0, 0), new LineBorder(Color.BLACK, 3)));
        // ���� ������
        playArea.append("user\n");
        playArea.append("user\n");
        playArea.append("user\n");
        playArea.append("user\n");
        playArea.append("user\n");
        playArea.append("user\n");
        playArea.append("user\n");
        playArea.append("user\n");
        playArea.append("user\n");
        playArea.append("user\n");
        playArea.append("user\n");
        gbc.insets = new Insets(0, 3, 3, 0);
    	gbc.gridx = 1;
    	gbc.gridy = 1;
    	gbc.gridwidth = 1;
    	gbc.gridheight = 1;
    	gbc.weightx = 1;
    	gbc.weighty = 1;
    	gbl.setConstraints(scrolPlayArea, gbc);
    	bottomArea.add(scrolPlayArea);
        
        JTextField chatField = new JTextField();
        chatField.setFont(new Font("���� ���", Font.PLAIN, 18));
        chatField.setBorder(new CompoundBorder(new EmptyBorder(5, 0, 0, 0), new LineBorder(Color.BLACK, 3)));
        gbc.insets = new Insets(0, 0, 3, 3);
    	gbc.gridx = 0;
    	gbc.gridy = 2;
    	gbc.gridwidth = 1;
    	gbc.gridheight = 1;
    	gbc.weightx = 8;
    	gbc.weighty = 0;
    	gbl.setConstraints(chatField, gbc);
        bottomArea.add(chatField);
        
        JButton button = new JButton("����");
        button.setBackground(Color.LIGHT_GRAY);
        button.setFont(new Font("HY�߰��", Font.PLAIN, 18));
        button.setBorder(new CompoundBorder(new EmptyBorder(5, 0, 0, 0), new LineBorder(Color.BLACK, 3)));
        gbc.insets = new Insets(0, 3, 3, 0);
    	gbc.gridx = 1;
    	gbc.gridy = 2;
    	gbc.gridwidth = 1;
    	gbc.gridheight = 1;
    	gbc.weightx = 1;
    	gbc.weighty = 0;
    	gbl.setConstraints(button, gbc);
        bottomArea.add(button);
        
        this.add(bottomArea, BorderLayout.SOUTH);
    }
}

abstract class RoomListRow extends JPanel {
    /*
     ���� ����� ǥ���ϱ� ǥ�� �� ��
     */
    protected GridBagConstraints numberGbc;
    protected GridBagConstraints titleGbc;
    protected GridBagConstraints userCntGbc;
    protected GridBagConstraints roomStateGbc;

    public RoomListRow() {
        super();
        
        this.setLayout(new GridBagLayout());
        this.setBackground(new Color(0xFFFFFF));

        this.setBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK));

        this.numberGbc = new GridBagConstraints();
        this.titleGbc = new GridBagConstraints();
        this.userCntGbc = new GridBagConstraints();
        this.roomStateGbc = new GridBagConstraints();

        this.numberGbc.fill = GridBagConstraints.BOTH;
        this.titleGbc.fill = GridBagConstraints.BOTH;
        this.userCntGbc.fill = GridBagConstraints.BOTH;
        this.roomStateGbc.fill = GridBagConstraints.BOTH;

        this.numberGbc.weightx = 0.5;
        this.titleGbc.weightx = 1;
        this.userCntGbc.weightx = 1;
        this.roomStateGbc.weightx = 1;

        this.numberGbc.gridx = 0;
        this.titleGbc.gridx = 1;
        this.userCntGbc.gridx = 2;
        this.roomStateGbc.gridx = 3;

        this.numberGbc.gridy = 0;
        this.titleGbc.gridy = 0;
        this.userCntGbc.gridy = 0;
        this.roomStateGbc.gridy = 0;

        this.numberGbc.gridwidth = 1;
        this.titleGbc.gridwidth = 1;
        this.userCntGbc.gridwidth = 1;
        this.roomStateGbc.gridwidth = 1;
    }
}

class RoomListHeaderRow extends RoomListRow {
    /*
    ���� ����� ǥ���ϱ� ���� ǥ�� �� ���
     */
    public RoomListHeaderRow() {
        super();

        this.add(new RoomListTableData("No"), this.numberGbc);
        this.add(new RoomListTableData("�� ����"), this.titleGbc);
        this.add(new RoomListTableData("�� �ο�"), this.userCntGbc);
        this.add(new RoomListTableData("�� ����"), this.roomStateGbc);

        this.setBackground(new Color(0xD9D9D9));
    }
}

class RoomListBodyRow extends RoomListRow {
    /*
    ���� ����� ǥ���ϱ� ���� ǥ�� ������
     */
    public RoomListBodyRow(int number, String roomTitle, int roomUserCount, int roomState) {
        super();

        String roomStateString;
        Color fontColor;

        this.add(new RoomListTableData(String.format("%3d", number)), this.numberGbc);
        this.add(new RoomListTableData(roomTitle), this.titleGbc);
        this.add(new RoomListTableData(String.format("%d / 7", roomUserCount)), this.userCntGbc);
        // �Ʒ� �κ��� ���Ŀ� �� ����� �����Ŀ� ��ü�� ��
        switch (roomState) {
            case 0:
                roomStateString = "�����";
                fontColor = Color.BLACK;
                break;
            case 1:
                roomStateString = "������";
                fontColor = Color.RED;
                break;
            default:
                roomStateString = "����";
                fontColor = Color.RED;
                break;
        }
        this.add(new RoomListTableData(roomStateString, fontColor), this.roomStateGbc);
    }
}

class RoomListTableData extends JLabel {
    /* �� ǥ�� ĭ */
    RoomListTableData(String text) {
        super(text);

        this.setFont(new Font("���� ���", Font.BOLD, 18));
        this.setBorder(new EmptyBorder(5, 10, 5, 10));
    }

    RoomListTableData(String text, Color color) {
        this(text);

        this.setForeground(color);
    }
}

/* ��ư ������Ʈ */

class WaitRoomListButton extends JButton {
    /* ��ư�� ���� */
    WaitRoomListButton(String text) {
        super(text);

        this.setBackground(Color.WHITE);
        this.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 3), new EmptyBorder(5, 5, 5, 5)));
        this.setFont(new Font("���� ���", Font.BOLD, 18));
        this.setFocusPainted(false);
    }
}

class MakeRoomButton extends WaitRoomListButton {
    /* �� ����� ��ư */
    MakeRoomButton() {
        super("�� �����");

        this.setBackground(new Color(0xFFC000));
    }
}

class WaitRoomButton extends WaitRoomListButton {
    /* ���� ��ư */
    WaitRoomButton() {
        super("��� ��");

        this.setBackground(new Color(0x4472C4));
        this.setForeground(Color.WHITE);
    }
}

class LogoutButton extends WaitRoomListButton {
    /* �α׾ƿ� ��ư */
    LogoutButton() {
        super("�α׾ƿ�");

        this.setBackground(Color.WHITE);
    }
}

class SettingButton extends WaitRoomListButton {
    /* ���� ��ư */
    SettingButton() {
        super("����");

        this.setBackground(Color.WHITE);
    }
}