package merge_sh_yk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ChattingListPanel extends JPanel implements ActionListener{
	KakaoMain main;
	JButton bt_test;
	public ChattingListPanel(KakaoMain main){
		this.main=main;
		bt_test=new JButton("����");
		
		add(bt_test);
		bt_test.addActionListener(this);
		setVisible(false);
		setPreferredSize(new Dimension(360, 497));
		setBackground(Color.PINK);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//���⼱ �����ϸ� �ٷ� ä��â�� ����־����� ä�ø��-> ä�ù� �����ϸ� �Ʒ� ä�ù��� ����ִ°ɷ� �����ϱ�
		main.chatMain.setLocation(main.getLocation().x+360,main.getLocation().y);
		main.chatMain.setVisible(true);//ȭ�� ��ü
	}
}
