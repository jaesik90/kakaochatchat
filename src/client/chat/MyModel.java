package client.chat;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class MyModel extends AbstractTableModel{
	 //ArrayList<Vector> list;
	Vector<Chat> list;
	
	
	
	//Vector<Vector> two;\
	ArrayList<Vector> two;
	
	public MyModel() {
		list = new Vector<Chat>();
		two = new ArrayList<Vector>();
	}
	
	public void addRow(Vector data) {
		//list.add(chat);
		list = data;
		two.add(list);
		
		System.out.println(two.get(0)+"ê?");
		System.out.println(list+"�迭�� �ּҰ�");
		//System.out.println(    "ù��° ��ȭ"+ ((Chat)two.get(0).get(0)).getMsg()  );
		
		
		int len = list.size();
		System.out.println("list.size�� ũ��:"+len);
		//System.out.println(list.get(0));
		//this.fireTableRowsInserted(len-1, len-1);
		//System.out.println("fire���Ŀ� len�� ����: "+(len-1));

	}
	
	public int getRowCount() {
		return list.size();
	}

	public int getColumnCount() {
		return 1;
	}

	
	public String getValueAt(int row, int col) {
		System.out.println("���̺� ������?"+((Chat)two.get(0).get(0)).getMsg());
		return list.get(0).getMsg();
		//return list.get(row);
	}
	
	
}
