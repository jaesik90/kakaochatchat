package client.chat;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;

import merge_sh_yk.ClientThread;
import merge_sh_yk.KakaoMain;
import merge_sh_yk.LoginPanel;
import net.miginfocom.swing.MigLayout;


public class ChatMain extends JDialog implements ActionListener{
	JPanel p_north;//���ʿ� �ٿ��� �г�
	JPanel p_center; //��� �ٿ��� �г�
	JPanel p_south;////���ʿ� �ٿ��� �г�
	public MyModel model = new MyModel();//���̺� ��
	
	//���� ����
	RButton bt_profil;//���� �г� ������ ������ ����
	JLabel la_user; //���� �г� ������ ä�ù��� ��ȭ���
	JButton bt_list, bt_totalview, bt_serch;//���� �г� ������ �Խ���, ��ƺ���, �˻����
	JComboBox cmb;// �ӽ����� ��� ����â
	JButton bt_option; //���� �г��� ���ʿ� ������ư
	
	//���Ϳ���
	JScrollPane scroll;//��� �ٿ��� ��ũ��
	public JTable table; //��� �� ���̺�
	JButton bt_imo; //�̸�Ƽ�� ��ư
	JButton bt_file; //�������� ��ư
	JButton bt_img;//�̹��� ���۹�ư	
	
	//���ʿ���
	JButton bt_send;
	JTextPane area;
	JFileChooser chooser;
	
	KakaoMain main;
	
	public Chat chatDto=new Chat();
	
	
	
	

	public ChatMain(KakaoMain main) {
		this.main=main;
		initGUI();
	}
	
	public void initGUI() {
		p_north = new JPanel();
		p_center = new JPanel();
		p_south = new JPanel();
		
		//���ʿ����� �г�
		bt_profil = new RButton(new ImageIcon(this.getClass().getResource(
				"/head2.png")));
		la_user = new JLabel("����");
		bt_list = new JButton("�Խ���");
		bt_totalview = new JButton("��ƺ���");
		bt_serch = new JButton("�˻�");
		cmb = new JComboBox(new String[]{"��","����"});
		bt_option = new JButton("����");
		
		p_north.setLayout(new MigLayout());
		p_north.setBackground(Color.RED);
		p_north.setPreferredSize(new Dimension(500, 80));
		p_north.add(bt_profil, "span 2 2"); //���� ������
		p_north.add(la_user, "span 3,wrap"); //��ȭ�� �������
		p_north.add(bt_list); //�Խ���
		p_north.add(bt_totalview); //��ƺ���
		p_north.add(bt_serch); //�˻�
		p_north.add(cmb);
		p_north.add(bt_option,"gapleft 50"); //����	
		add(p_north, BorderLayout.NORTH);
		
		//���Ϳ���
		scroll = new JScrollPane();
		table = new JTable();
		
		table.setTableHeader(null);
		table.setModel(model);
		table.getColumnModel().getColumn(0).setCellRenderer(new ChatRenderer(this));
		table.getColumnModel().getColumn(0).setPreferredWidth(260);
		table.setBackground(Color.PINK);
		table.setOpaque(true);
		table.setShowHorizontalLines(false);
		
		scroll.setViewportView(table);
		scroll.getViewport().setBackground(Color.WHITE);
		
		p_center.setLayout(new BorderLayout());
		p_center.add(scroll, BorderLayout.CENTER);
		add(p_center);
		
		//���ʿ���
		bt_send = new JButton("����");
		area = new JTextPane();
		bt_imo = new JButton("�̸�Ƽ��");
		bt_file = new JButton("��������");
		bt_img = new JButton("�̹�������");
		
		
		p_south.setLayout(new MigLayout());
		p_south.add(area, "hmin 50px,growx,pushx");
		p_south.add(bt_send, "growy,pushy, wrap");
		p_south.add(bt_imo, "split 3");
		p_south.add(bt_file);
		p_south.add(bt_img);
		p_center.add(p_south, BorderLayout.SOUTH);
		
		setBounds(100, 100, 500, 550);
		setVisible(false);
	
		
		bt_send.addActionListener(this);
		bt_file.addActionListener(this);

		
		//�ؽ�Ʈ area�� ���� ����Ű�� ������
		area.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e) {
				int key= e.getKeyCode();
				if(key==KeyEvent.VK_ENTER){
					sendsMsg();
				}
			}
		});
			
	}
	//textpane ������ �޼����� ������!!
	public void sendsMsg(){
		String msg = area.getText().trim();
		String sender = (String)cmb.getSelectedItem();
		String time =  getTime();
		
		
		LoginPanel log=(LoginPanel)main.panel[0];
		log.ct.sendMsg(msg);
		log.ct.sendTime(time);
		log.ct.sendSender(sender);
		
		model.addRow(chatDto);
		area.setText("");
		
		int a=table.getColumnModel().getColumnCount();
		System.out.println(a);
	}
	
	static String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		return sdf.format(new Date());
	}
	
	
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		if(obj==bt_send){
			sendsMsg();
		}
		else if(obj==bt_file){
			
		}
		
	}

}
