package gui;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

import static gui.Borders.*;

public class WaitingRoom extends JPanel {
    private ArrayList users = new ArrayList();

    public WaitingRoom() {
        this.setLayout(new GridBagLayout());
        this.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 5), new EmptyBorder(40, 200, 40, 200)));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.weightx = 1.0;
        c.gridwidth = 2;

        c.gridy = 0;
        this.add(new TopArea(), c);

//        c.insets = new Insets(5, 0, 0, 0);
//        c.gridy = 1;
//        this.add(new MiddleArea(), c);
//
//        c.gridy = 2;
//        this.add(new BottomArea(), c);

        c.insets = new Insets(5, 0, 0, 0);
        c.weightx = 0.7;
        c.gridwidth = 1;
        c.gridy = 1;
        c.gridx = 0;
        this.add(new LeftArea(), c);

        c.gridx = 1;
        c.weightx = 0.3;
        c.weighty = 1.0;
        c.insets = new Insets(5, 10, 0, 0);
        this.add(new RightArea(), c);

//        c.weightx = 1;
//        c.gridy = 0;
//        c.gridwidth = 2;
//        this.add(new TopArea(), c);
//
//        c.gridwidth = 1;
//
//        c.weightx = 0.8;
//        c.gridy = 1;
//        this.add(new RoomUserList(), c);
//
//        c.weightx = 0.2;
//        c.gridx = 1;
//        this.add(new MapInfo(), c);
//
//        c.gridy = 2;
//
//        c.weightx = 0.8;
//        c.gridx = 0;
//        this.add(new ChatArea(), c);
//
//        c.weightx = 0.2;
//        c.gridx = 2;
//        this.add(new Buttons(), c);

        this.setVisible(true);
    }

    public void setUserState(int slot) {

    }
}

/**
 * ��� ����
 */
class TopArea extends JPanel {
    TopArea() {
        this.setLayout(new GridLayout());

        this.add(new RoomTitle());
        //this.setBorder(gui.Borders.debugBorder);

        this.setVisible(true);
    }
}

class LeftArea extends JPanel {
    LeftArea() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.gridy = 0;
        c.gridx = 0;
        this.add(new RoomUserList(), c);

        c.insets = new Insets(5, 0, 0, 0);
        c.weighty = 1.0;
        c.gridy = 1;
        this.add(new ChatArea(), c);

        this.setVisible(true);
    }
}

class RightArea extends JPanel {
    RightArea() {
//        this.setLayout(new GridBagLayout());
        this.setLayout(new BorderLayout());
//        this.setBorder(gui.Borders.debugBorder);

//        GridBagConstraints c = new GridBagConstraints();
//
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.weightx = 1.0;
//        c.gridx = 0;
//
//        c.anchor = GridBagConstraints.NORTH;
//        c.gridy = 0;
//        this.add(new MapInfo(), c);
//
//        c.insets = new Insets(5, 0, 0, 0);
//        c.anchor = GridBagConstraints.SOUTH;
//        c.gridy = 1;
//        this.add(new Buttons(), c);

        this.add(new MapInfo(), BorderLayout.PAGE_START);
        this.add(new Buttons(), BorderLayout.PAGE_END);

        this.setVisible(true);
    }
}

/**
 * �߾� ����
 */
class MiddleArea extends JPanel {
    MiddleArea() {
        this.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 0;

        c.gridx = 0;
        c.weightx = 0.8;
        this.add(new RoomUserList(), c);

        c.insets = new Insets(0, 10, 0, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 5;
        c.weightx = 0.2;
        this.add(new MapInfo(), c);

        this.setVisible(true);
    }
}

/**
 * �ϴ� ����
 */
class BottomArea extends JPanel {
    BottomArea() {
        this.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 0;

        c.gridx = 0;
        c.weightx = 0.8;
        this.add(new ChatArea(), c);

        c.insets = new Insets(0, 10, 0, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_END;
        c.gridx = 5;
        c.weightx = 0.2;
        this.add(new Buttons(), c);

        this.setVisible(true);
    }
}

/**
 * WaitingRoom�� �� ����
 */
class RoomTitle extends JPanel {
    private JLabel labelTitle;               // Ÿ��Ʋ
    private JButton btnRoomSetting;      // �� ���� ���� ��ư
    private int roomNum;

    RoomTitle() {
        this.setLayout(new GridBagLayout());
        this.setBorder(new CompoundBorder(gui.Borders.normalBorder, new EmptyBorder(3, 5, 3, 5)));
        this.setBackground(Color.WHITE);

        // �� ����
        this.labelTitle = new JLabel();
        this.roomNum = 41;
        this.setRoomTitle("ABC");
        this.labelTitle.setFont(gui.Fonts.defaultFont);

        // �� ���� ���� ��ư
        this.btnRoomSetting = new JButton("�� ��������");
        this.btnRoomSetting.setBackground(new Color(0xD9D9D9));
        this.btnRoomSetting.setBorder(new CompoundBorder(gui.Borders.normalBorder, new EmptyBorder(3, 7, 3, 7)));
        this.btnRoomSetting.setFont(gui.Fonts.font16);
        this.btnRoomSetting.setFocusPainted(false);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.weightx = 1;

        c.gridy = 0;
        this.add(this.labelTitle, c);

        c.gridy = 1;
        this.add(this.btnRoomSetting);

        this.setVisible(true);
    }

    public void setRoomTitle(String str) {
        if(this.labelTitle != null) {
            this.labelTitle.setText(String.format("%03d %s", this.roomNum, str));
        }
    }
}

/**
 * WaitingRoom�� ���� ���
 */
class RoomUserList extends JPanel {
    RoomUserList() {
        this.setLayout(new GridLayout(7, 1));
        this.setBorder(gui.Borders.normalBorder);
        this.setBackground(Color.WHITE);

        this.add(new RoomUserListRow());
        this.add(new RoomUserListRow());
        this.add(new RoomUserListRow());
        this.add(new RoomUserListRow());
        this.add(new RoomUserListRow());
    }
}

class RoomUserListRow extends JPanel {
    private JButton btnState;
    private JLabel labelNickname;

    private int STATE_NONE = 0;
    private int STATE_READY = 1;
    private int STATE_OWNER = 2;

    RoomUserListRow() {
        this.setLayout(new GridBagLayout());
        this.setBorder(listRowBorder);
        this.setBorder(
        new CompoundBorder(listRowBorder, listRowPadding));
        this.setBackground(Color.WHITE);

        labelNickname = new JLabel();
        labelNickname.setFont(gui.Fonts.defaultFont);
        btnState = new JButton();
        btnState.setFont(gui.Fonts.defaultFont);
        btnState.setFocusPainted(false);
        btnState.setBorder(new CompoundBorder(normalBorder, new EmptyBorder(0, 7, 0, 7)));

        this.setState(STATE_READY);
        this.labelNickname.setText("�׽�Ʈ");

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 0;
        c.weightx = 1;

        c.gridx = 0;
        this.add(this.labelNickname, c);

        c.gridx = 1;
        c.weightx = 0;
        this.add(this.btnState, c);
    }

    public void setState(int state) {
        this.btnState.setVisible(true);
        switch (state) {
            case 0:
                this.btnState.setVisible(false);
                break;
            case 1:
                this.btnState.setVisible(true);
                this.btnState.setBackground(new Color(0xFFC000));
                this.btnState.setText("�غ�");
                break;
            case 2:
                this.btnState.setVisible(true);
                this.btnState.setBackground(new Color(0xFF5369));
                this.btnState.setText("����");
                break;
        }
    }

    public void setNickname(String name) {
        this.labelNickname.setText(name);
    }
}

class MapInfo extends JPanel {
    MapInfo() {
        this.setLayout(new GridBagLayout());
//        this.setBorder(new EmptyBorder(0, 10, 0, 0));
        this.setBorder(gui.Borders.normalBorder);
        this.setBackground(Color.WHITE);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.weightx = 1;
        c.anchor = GridBagConstraints.PAGE_START;

        c.gridy = 0;
        JLabel labelLoading = new JLabel("�ε���...");

        this.add(labelLoading, c);
    }
}

/**
 * WaitingRoom�� ä�� ����
 */
class ChatArea extends JPanel {
    private JTextArea txtChatArea;

    ChatArea() {
        this.setLayout(new GridLayout());
        this.txtChatArea = new JTextArea();
        txtChatArea.setFont(gui.Fonts.defaultFont);
        JScrollPane scrollChatArea = new JScrollPane(this.txtChatArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollChatArea.setBorder(normalBorder);

        // ���� ������
        txtChatArea.append("[����] : �׽�Ʈ ä�� ����..\n");
        txtChatArea.append("[����] : �׽�Ʈ ä�� ����..\n");
        txtChatArea.append("[����] : �׽�Ʈ ä�� ����..\n");
        txtChatArea.append("[����] : �׽�Ʈ ä�� ����..\n");
        txtChatArea.append("[����] : �׽�Ʈ ä�� ����..\n");
        txtChatArea.append("[����] : �׽�Ʈ ä�� ����..\n");
        txtChatArea.append("[����] : �׽�Ʈ ä�� ����..\n");
        txtChatArea.append("[����] : �׽�Ʈ ä�� ����..\n");
        txtChatArea.append("[����] : �׽�Ʈ ä�� ����..\n");
        txtChatArea.append("[����] : �׽�Ʈ ä�� ����..\n");
        txtChatArea.append("[����] : �׽�Ʈ ä�� ����..\n");
        txtChatArea.append("[����] : �׽�Ʈ ä�� ����..\n");
        txtChatArea.append("[����] : �׽�Ʈ ä�� ����..\n");
        txtChatArea.append("[����] : �׽�Ʈ ä�� ����..\n");

        this.add(scrollChatArea);
    }

    public void clearChat() {
    }
}

/**
 * ���� �ϴ� ��ư ����
 */
class Buttons extends JPanel {
    private JButton btnGameStart;
    private JButton btnExit;

    Buttons() {
        this.setLayout(new GridLayout(2, 1, 0, 10));
//        this.setBorder(new EmptyBorder(0, 10, 0, 0));

        this.btnGameStart = new gui.component.Button("���� ����");
        this.btnGameStart.setFont(gui.Fonts.defaultFont);
        this.btnGameStart.setBackground(new Color(0x4472C4));
        this.btnGameStart.setForeground(Color.WHITE);

        btnExit = new gui.component.Button("������");
        this.btnExit.setFont(gui.Fonts.defaultFont);
        this.btnExit.setBackground(Color.WHITE);

        this.add(btnGameStart);
        this.add(btnExit);
    }
}