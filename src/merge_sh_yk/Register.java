package merge_sh_yk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;




//ȸ������
public class Register extends JDialog {
	  JLabel title, info, cancle;
	   JPanel p_main, p_put, p_null, p_pw_bt;
	   HintTextField_FIRST t_email; 
	   HintTextField t_name, t_pw, t_pw_check;
	   JButton bt;
	   boolean flag=true;
	   
	   Member member;
	   KakaoMain kakaoMain;
	   
	   public Register( KakaoMain kakaoMain) {
	      this.kakaoMain=kakaoMain;
	      
	      title=new JLabel("īī���� pc���� ȸ������");
	      info=new JLabel("���� ��ư�� �����ø� ȸ�������� �Ϸ�˴ϴ�. ");
	      p_main=new JPanel();
	      p_put=new JPanel();
	      p_null=new JPanel();
	      p_pw_bt=new JPanel();
	      t_email = new HintTextField_FIRST("�̸����� �Է��ϼ���"); 
	      t_name=new HintTextField("�̸��� �Է��ϼ���");
	      t_pw= new HintTextField("��й�ȣ�� �Է��ϼ���"); 
	      t_pw_check= new HintTextField("��й�ȣ�� �ѹ� �� �Է��ϼ���");
	      bt=new JButton("����");
	      
	      p_main.setLayout(new FlowLayout());
	      p_main.setPreferredSize(new Dimension(360, 590));
	      p_main.setBackground(new Color(255, 235, 051));
	      
	      title.setPreferredSize(new Dimension(360, 50));
	      title.setHorizontalAlignment(JLabel.CENTER);
	      
	      p_put.setPreferredSize(new Dimension(240, 150));
	      p_put.setBackground(new Color(255, 235, 051));
	      t_email.setPreferredSize(new Dimension(240, 30));
	      t_name.setPreferredSize(new Dimension(240, 30));
	      p_null.setPreferredSize(new Dimension(240, 1));
	      p_null.setBackground(new Color(255, 235, 051));
	      t_pw.setPreferredSize(new Dimension(170, 30));
	      p_pw_bt.setPreferredSize(new Dimension(280, 40));
	      p_pw_bt.setBackground(new Color(255, 235, 051));
	      
	      bt.setPreferredSize(new Dimension(60, 40));
	      bt.setBackground(new Color(113, 92, 94));
	      
	      p_pw_bt.add(t_pw);
	      p_pw_bt.add(bt);
	      p_put.add(t_email);
	      p_put.add(t_name);
	      p_put.add(p_null);
	      p_put.add(p_pw_bt);
	      
	      p_main.add(title);
	      p_main.add(p_put);
	      add(p_main);
	      
	      bt.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            join();
	         }
	      });   

	      setSize(380, 650);
	      setVisible(flag);
	   }

	   public void join(){   
	       member=new Member();//�ν��Ͻ� �Ѱ� ����
	      
	      member.setEmail(t_email.getText());
	      member.setName(t_name.getText());
	      member.setPw(t_pw.getText());
	      
	      PreparedStatement pstmt=null;
	      ResultSet rs=null;
	      String sql="insert into member(e_mail, nik_id, password) values(?, ?, ?)";
	      
	      try {
	         pstmt=kakaoMain.con.prepareStatement(sql);
	         System.out.println("��� ���̵� = " + member.getEmail());
	         System.out.println("��� ���̵� = " + member.getName());
	         System.out.println("��� ���̵� = " + member.getPw());
	         
	         pstmt.setString(1, member.getEmail());
	         pstmt.setString(2, member.getName());
	         pstmt.setString(3, member.getPw());
	         rs=pstmt.executeQuery();
	      
	         JOptionPane.showMessageDialog(this, "ȸ�����Լ���!");
	         
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
	         if(kakaoMain.con!=null){
	            try {
	            	kakaoMain.con.close();
	            } catch (SQLException e) {
	               e.printStackTrace();
	            }
	         }
	         if(rs!=null){
	            try {
	               rs.close();
	            } catch (SQLException e) {
	               e.printStackTrace();
	            }
	         }
	      }//finally
	      
	   }


}
