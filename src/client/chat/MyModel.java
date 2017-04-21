package client.chat;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class MyModel extends AbstractTableModel{
	 ArrayList<Chat> list;
	
	public MyModel() {
		list = new ArrayList<Chat>();
	}
	
	public void addRow(Chat chat) {
		list.add(chat);
		System.out.println(chat.getMsg());
		System.out.println(chat.getSender());
		System.out.println(chat.getTime());
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

	
	public Object getValueAt(int row, int col) {
		return list.get(row);
	}
	
	
}
