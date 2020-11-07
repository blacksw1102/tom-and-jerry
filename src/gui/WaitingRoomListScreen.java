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

        /* 첫번째 상단 버튼 그룹 */
        JPanel buttonsPanel1 = new JPanel();
        buttonsPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));

        /* 방만들기 버튼 */
        JButton btnMakeRoom = new MakeRoomButton();

        /* 대기 방 버튼 */
        JButton btnWaitRoom = new WaitRoomButton();

        buttonsPanel1.add(btnMakeRoom);
        buttonsPanel1.add(btnWaitRoom);

        /* 두번째 상단 버튼 그룹 */
        JPanel buttonsPanel2 = new JPanel();
        buttonsPanel2.setLayout(new FlowLayout(FlowLayout.RIGHT));

        /* 로그아웃 */
        JButton btnLogout = new LogoutButton();
        /* 설정 */
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
            roomList.add(new RoomListBodyRow(i, "제목 테스트" + (i + 1), new Random().nextInt(7),  new Random().nextInt(2)), c);
        }

        JScrollPane scrollArea = new JScrollPane(roomList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        middleArea.add(scrollArea);

        this.add(middleArea, BorderLayout.CENTER);
    }
    
    private void initBottomArea() {
        JPanel bottomArea = new JPanel();
        bottomArea.setLayout(new FlowLayout(FlowLayout.CENTER));

        JTextArea textarea = new JTextArea("채팅 창\n[도움말] : 테스트 채팅 내용..\n[도움말] : 테스트 채팅 내용..");
        bottomArea.add(textarea);

        JScrollPane scrollArea = new JScrollPane(textarea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollArea.setBorder(new CompoundBorder(new EmptyBorder(5, 0, 0, 0), new LineBorder(Color.BLACK, 3)));

        this.add(scrollArea, BorderLayout.PAGE_END);
    }
}

abstract class RoomListRow extends JPanel {
    /*
     대기실 목록을 표기하기 표의 각 행
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
    대기실 목록을 표기하기 위한 표의 행 헤더
     */
    public RoomListHeaderRow() {
        super();

        this.add(new RoomListTableData("No"), this.numberGbc);
        this.add(new RoomListTableData("방 제목"), this.titleGbc);
        this.add(new RoomListTableData("방 인원"), this.userCntGbc);
        this.add(new RoomListTableData("방 상태"), this.roomStateGbc);

        this.setBackground(new Color(0xD9D9D9));
    }
}

class RoomListBodyRow extends RoomListRow {
    /*
    대기실 목록을 표기하기 위한 표의 아이템
     */
    public RoomListBodyRow(int number, String roomTitle, int roomUserCount, int roomState) {
        super();

        String roomStateString;
        Color fontColor;

        this.add(new RoomListTableData(String.format("%3d", number)), this.numberGbc);
        this.add(new RoomListTableData(roomTitle), this.titleGbc);
        this.add(new RoomListTableData(String.format("%d / 7", roomUserCount)), this.userCntGbc);
        // 아래 부분은 추후에 각 상수를 정의후에 교체할 것
        switch (roomState) {
            case 0:
                roomStateString = "대기중";
                fontColor = Color.BLACK;
                break;
            case 1:
                roomStateString = "게임중";
                fontColor = Color.RED;
                break;
            default:
                roomStateString = "오류";
                fontColor = Color.RED;
                break;
        }
        this.add(new RoomListTableData(roomStateString, fontColor), this.roomStateGbc);
    }
}

class RoomListTableData extends JLabel {
    /* 각 표의 칸 */
    RoomListTableData(String text) {
        super(text);

        this.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        this.setBorder(new EmptyBorder(5, 10, 5, 10));
    }

    RoomListTableData(String text, Color color) {
        this(text);

        this.setForeground(color);
    }
}

/* 버튼 컴포넌트 */

class WaitRoomListButton extends JButton {
    /* 버튼들 조상 */
    WaitRoomListButton(String text) {
        super(text);

        this.setBackground(Color.WHITE);
        this.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 3), new EmptyBorder(5, 5, 5, 5)));
        this.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        this.setFocusPainted(false);
    }
}

class MakeRoomButton extends WaitRoomListButton {
    /* 방 만들기 버튼 */
    MakeRoomButton() {
        super("방 만들기");

        this.setBackground(new Color(0xFFC000));
    }
}

class WaitRoomButton extends WaitRoomListButton {
    /* 대기방 버튼 */
    WaitRoomButton() {
        super("대기 방");

        this.setBackground(new Color(0x4472C4));
        this.setForeground(Color.WHITE);
    }
}

class LogoutButton extends WaitRoomListButton {
    /* 로그아웃 버튼 */
    LogoutButton() {
        super("로그아웃");

        this.setBackground(Color.WHITE);
    }
}

class SettingButton extends WaitRoomListButton {
    /* 설정 버튼 */
    SettingButton() {
        super("설정");

        this.setBackground(Color.WHITE);
    }
}