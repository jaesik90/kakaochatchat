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
		
		System.out.println(two.get(0)+"챗?");
		System.out.println(list+"배열의 주소값");
		//System.out.println(    "첫번째 대화"+ ((Chat)two.get(0).get(0)).getMsg()  );
		
		
		int len = list.size();
		System.out.println("list.size의 크기:"+len);
		//System.out.println(list.get(0));
		//this.fireTableRowsInserted(len-1, len-1);
		//System.out.println("fire이후에 len의 길이: "+(len-1));

	}
	
	public int getRowCount() {
		return list.size();
	}

	public int getColumnCount() {
		return 1;
	}

	
	public String getValueAt(int row, int col) {
		System.out.println("테이블에 뭐찍음?"+((Chat)two.get(0).get(0)).getMsg());
		return list.get(0).getMsg();
		//return list.get(row);
	}
	
	
}
