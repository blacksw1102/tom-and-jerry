package gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.Random;

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
        bottomArea.setLayout(new FlowLayout(FlowLayout.CENTER));

        JTextArea textarea = new JTextArea("ä�� â\n[����] : �׽�Ʈ ä�� ����..\n[����] : �׽�Ʈ ä�� ����..");
        bottomArea.add(textarea);

        JScrollPane scrollArea = new JScrollPane(textarea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollArea.setBorder(new CompoundBorder(new EmptyBorder(5, 0, 0, 0), new LineBorder(Color.BLACK, 3)));

        this.add(scrollArea, BorderLayout.PAGE_END);
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

//        this.numberGbc.weightx = 0.1;
//        this.titleGbc.weightx = 0.5;
//        this.userCntGbc.weightx = 0.2;
//        this.roomStateGbc.weightx = 0.2;

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