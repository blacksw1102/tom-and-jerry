package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import net.ClientWindow;

public class MakeRoomScreen extends JPanel implements ActionListener {
	private ClientWindow win;
	private Font f1;

	public MakeRoomScreen(ClientWindow win) {
		this.win = win;
		setBackground(Color.WHITE); 
       
		f1 = new Font("바탕",Font.BOLD,18);       
		JLabel label1 = new JLabel("방 만들기");
		label1.setBounds(20,10, 150,40);
		label1.setFont(f1);
       
       JLabel label2=new JLabel("방 제목");
       label2.setBounds(20,50, 80,30);
       label2.setFont(f1);
       label2.setOpaque(true);
       label2.setBackground(Color.ORANGE);
       label2.setBorder(new LineBorder(Color.BLACK));
       
       JTextField roomname = new JTextField();
       roomname.setBounds(110,50, 200,30);
       roomname.setFont(f1);
       
       JLabel lb3=new JLabel("비밀번호");
       lb3.setBounds(20,100, 80,30);
       lb3.setFont(f1);
       lb3.setOpaque(true);
       lb3.setBackground(Color.ORANGE);
       lb3.setBorder(new LineBorder(Color.BLACK));
       
       JPasswordField pw = new JPasswordField();
       pw.setBounds(110,100,200,30);
       pw.setFont(f1);
  
       
       JCheckBox pwCheckbox= new JCheckBox();
       pwCheckbox.setBounds(320,105,20,20);
       pwCheckbox.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) { 
             if(e.getSource()==pwCheckbox)
             {
                  pw.setOpaque(true);
                  pw.setBackground(Color.GRAY);
              }
             else if (e.getSource()!=pwCheckbox){
                  pw.setOpaque(true);
                  pw.setBackground(Color.WHITE);
             }
           }
       });

       
       JButton bt1 = new JButton("생성");
       bt1.setBounds(100,150, 80,30);
       bt1.setFont(f1);
       bt1.setOpaque(true);
       bt1.setBackground(Color.WHITE);
       bt1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Confirm(); 
            }
        });
       
       JButton bt2 = new JButton("취소");
       bt2.setBounds(200,150, 80,30);
       bt2.setFont(f1);
       bt2.setOpaque(true);
       bt2.setBackground(Color.WHITE);
       bt2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Cancel(); 
            }
        }); 
       
       add(label1);
       add(pw);
       add(pwCheckbox);
       add(label2);
       add(roomname);
       add(lb3);
       add(bt1);
       add(bt2);
       setSize(400,250);
       setLayout(null);
       setVisible(true);

  }
    public void actionPerformed(ActionEvent e) {
       
    }
}

class Confirm extends JFrame{
   Font f2;
   Confirm(){
       f2 = new Font("바탕",Font.BOLD,18);    
      setTitle("방 생성 완료 창");
        JPanel ConfirmWindow = new JPanel();
        setContentPane(ConfirmWindow);
        JLabel lb = new JLabel("방 생성을 완료하였습니다!");        
        ConfirmWindow.add(lb);        
        setSize(300,100);
        setResizable(false);
        setVisible(true);
   }
}

class Cancel extends JFrame{
   Font f2;
   Cancel(){
       f2 = new Font("바탕",Font.BOLD,18);    
      setTitle("방 생성 취소 창");
        JPanel CancelWindow = new JPanel();
        setContentPane(CancelWindow);
        JLabel lb = new JLabel("방 생성을 취소합니다.");        
        CancelWindow.add(lb);        
        setSize(300,100);
        setResizable(false);
        setVisible(true);
   }
}

