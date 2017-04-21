package merge_sh_yk;



import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import db.DBManager;


public class LoginPanel extends JPanel implements ActionListener{
   KakaoMain kakaoMain;
   JPanel p_north, p_center, p_south, p_id_pw, p_pw;
   HintTextField_FIRST t_email;
   JTextField t_info;
   JPasswordField t_pw;
   JButton bt_login, bt_find_pw;
   Checkbox auto_login;
   JLabel info, link, temp;
   
   Canvas logo;
   BufferedImage image=null;
   URL url=this.getClass().getResource("/icon.png");
   
   Register register;
   Member member;//DBDTO
   
   Thread thread;
   Connection con;
   DBManager manager;
   

   String ip="211.238.142.114";//////////////////�ӽ� ������
   
   Socket socket;
   public ClientThread ct;
   int port=7777;
   
   public LoginPanel(KakaoMain kakaoMain) {
       this.kakaoMain=kakaoMain; 
       setLayout(new BorderLayout());

      
      //db�� ����
      manager=DBManager.getInstance();
      con=manager.getConnection();
      
      //�г�
      p_north=new JPanel();
      p_center=new JPanel();
      p_south=new JPanel();
      p_id_pw=new JPanel();
      p_pw=new JPanel();
            
      p_north.setPreferredSize(new Dimension(360, 250));
      p_center.setPreferredSize(new Dimension(360, 170));
      p_south.setPreferredSize(new Dimension(360, 170));
      p_id_pw.setPreferredSize(new Dimension(250, 100));
      p_pw.setPreferredSize(new Dimension(250, 55));
      
      p_north.setBackground(new Color(255, 235, 051));
      p_center.setBackground(new Color(255, 235, 051));
      p_south.setBackground(new Color(255, 235, 051));
      p_id_pw.setBackground(new Color(255, 235, 051));
      p_pw.setBackground(new Color(255, 235, 051));
      
      p_north.setLayout(new BorderLayout());
      p_center.setLayout(new FlowLayout());
      p_south.setLayout(new FlowLayout());
      p_id_pw.setLayout(new GridLayout(2,1));
      p_pw.setLayout(new FlowLayout());
      
      //ĵ����
      try {
         image=ImageIO.read(url);
      } catch (IOException e) {
         e.printStackTrace();
      }
      logo=new Canvas(){
         @Override
         public void paint(Graphics g) {
            g.drawImage(image, 125, 75, 110, 110, this);
         }
      };
      logo.setPreferredSize(new Dimension(150, 150));
      logo.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            register=new Register(kakaoMain);
         }
      });
      
      t_email=new HintTextField_FIRST("īī������(�̸���)");
      t_info=new JTextField("��й�ȣ");
      t_info.setForeground(Color.GRAY);
      t_pw=new JPasswordField();
      bt_login=new JButton("�α���");
      auto_login=new Checkbox("��ݸ��� �ڵ��α���");
      
      t_email.setPreferredSize(new Dimension(250, 25));
      t_info.setPreferredSize(new Dimension(250, 45));
      t_pw.setPreferredSize(new Dimension(250, 45));
      bt_login.setPreferredSize(new Dimension(250, 35));
      bt_login.setBackground(new Color(113, 92, 94));
      bt_login.addActionListener(this);
   
      info=new JLabel("����Ͽ��� īī�������� Ȯ���� �� �ֽ��ϴ�.");
      link= new JLabel("<HTML><U>īī�������ȳ�</U></HTML>");
      link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));   
      link.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
             JOptionPane.showMessageDialog(LoginPanel.this, "īī�������̶� ����� īī���忡�� ����� �̸��� �ּ��Դϴ�."+"\n" +"����� īī������ '������>����>����/����>īī�������� Ȯ�����ּ���"); 
         }
      });
      
      temp=new JLabel();
      temp.setPreferredSize(new Dimension(300, 35));
      
      bt_find_pw=new JButton("��й�ȣ�� �ؾ�����̳���?");
      bt_find_pw.setBackground(new Color(255, 235, 051));
      bt_find_pw.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            JOptionPane.showMessageDialog(LoginPanel.this, "����� īī���忡�� '������>����>����/����>īī������'�� ������ ��"+"\n" +"���� ��й�ȣ ������ �������ּ���."); 
         }
      });
   
      t_info.addFocusListener(new FocusAdapter() {
         @Override
         public void focusGained(FocusEvent e) {
            t_info.setVisible(false);
            t_pw.setVisible(true);
            SwingUtilities.invokeLater(new Runnable() {
               public void run() {
                  t_pw.requestFocus();
               }
             });
            p_pw.updateUI();
         }
         
         @Override
         public void focusLost(FocusEvent e) {
         }
      
      }); 

      t_pw.addFocusListener(new FocusListener() {
         
         @Override
         public void focusLost(FocusEvent e) {
            System.out.println("���� ����");
            t_pw.setVisible(false);
            t_info.setVisible(true);
            p_pw.updateUI();
         }
         
         @Override
         public void focusGained(FocusEvent e) {
            t_pw.setText("");
         }
      });
      
      p_north.add(logo);
      p_pw.add(t_info);
      p_pw.add(t_pw);
      p_id_pw.add(t_email);
      p_id_pw.add(p_pw);
      p_center.add(p_id_pw);
      p_center.add(bt_login);
      p_center.add(auto_login);
      
      p_south.add(info);
      p_south.add(link);
      p_south.add(temp);
      p_south.add(bt_find_pw);
      
      add(p_north, BorderLayout.NORTH);
      add(p_center, BorderLayout.CENTER);
      add(p_south, BorderLayout.SOUTH);
      
       kakaoMain.dragMouse(p_north);
      //pack();
      //setVisible(true);
      //setDefaultCloseOperation(EXIT_ON_CLOSE);
   }
   

   public void login(){
	   ///////////��������//////////////
	   connect();
	   ///////////////////////////////
	   
      PreparedStatement pstmt=null;
      ResultSet rs=null;
      String sql="select * from member where e_mail=? and password=?";
      String input_email=t_email.getText();
      String input_pw=t_pw.getText();
      String ori_email=null;
      String ori_pw=null;
      
      try {
         pstmt=con.prepareStatement(sql);
         pstmt.setString(1, input_email); //���� �Է��� ��
         pstmt.setString(2, input_pw); 
         rs=pstmt.executeQuery();
         
         while(rs.next()){
            member=new Member();//�ν��Ͻ� �Ѱ� ����
            member.setEmail(rs.getString("e_mail"));
            member.setName(rs.getString("nik_id"));
            member.setPw(rs.getString("password"));
         }            
         
         ori_email=member.getEmail();
         ori_pw=member.getPw();

         System.out.println("input_email="+input_email);
         System.out.println("ori_email"+ori_email);
         System.out.println("input_pw"+input_pw);
         System.out.println("ori_pw"+ori_pw);
         if((input_email.equals(ori_email))&&(input_pw.equals(ori_pw))){
            JOptionPane.showMessageDialog(this, "�α��μ���");      
         } else{
            JOptionPane.showMessageDialog(this, "�α��ν���");
         }
         
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         if(rs!=null){
            try {
               rs.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
         
         if(pstmt!=null){
            try {
               pstmt.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
      }//finally
   }
   
	public void connect(){
		try {
			socket=new Socket(ip, port);
			
			ct=new ClientThread(socket,kakaoMain.chatMain);//chat ����Ʈ�� �־ �� ä�ù��� ��ȣ�� ���� ��ȭ�� �Ѱ��ֱ�
			//ä�ù� ����Ʈ�� �� ������ �̸� �˰� �־���ϹǷ� db�� �ִ´�. ä�ù��� ������ ���������� db�� �־��ְ� �� ������� chat ����Ʈ ������ ����
			//�� ���� ����Ʈ�� db(����)���� �����ϸ� �� �����̸Ӹ�Ű�� ���� �ִ�. �������� Ŭ��� �ش��ϴ� ����ڰ� ���� ��� ���ӽ� ���� ������ �Ѱ��ش�.
			//�Ѱ��� ������ �������� �������� chat ����Ʈ�� �����Ѵ�.
			
			
			ct.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
   
   @Override
   public void actionPerformed(ActionEvent e) {
      login();
       kakaoMain.panel[0].setVisible(false);
       kakaoMain.panel[1].setVisible(true);
         
       System.out.println("��ư");
   }

}
