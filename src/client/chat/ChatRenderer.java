package client.chat;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ChatRenderer implements TableCellRenderer {
	ChatHolder holder;
	LeftViewHolder leftHolder= new LeftViewHolder();
	RightViewHolder rightHolder= new RightViewHolder();
	Map<String, Integer> mCacheCount= new HashMap<String, Integer>();
	ChatMain main;
	String sender,msg,time;
	
	int count = 0;
	Vector<String> dto = new Vector<>();
	
	public ChatRenderer(ChatMain main) {
		this.main=main;
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value,
		boolean isSelected, boolean hasFocus, int row, int col) {
		ChatHolder holder = null;
		//Chat chat = (Chat)value;
		if(main.data.size()!=0){
			//for(int i=0;i<main.data.size();i++){
				sender = main.data.get(row).getSender();
				msg =main.data.get(row).getMsg();
				time =main.data.get(row).getTime();
				
				System.out.println("그리는값"+msg);
			//}
		}

		
		//System.out.println("렌더링에 있는 chat"+main.chat);
		
		
		Integer iDisplaySide = mCacheCount.get(sender);
		if (iDisplaySide == null) {
			iDisplaySide = count++;
			mCacheCount.put(sender, iDisplaySide);
		}
		if (iDisplaySide % 2 == 0) {
			holder = leftHolder;
		} else {
			holder = rightHolder;
		}
		
		
		holder.la_user.setText(sender);
		holder.la_time.setText(time);
		holder.chatbox.setText(msg);
		holder.readCount.setText(Integer.toString(count));
		int iHeight = holder.chatbox.getPreferredSize().height + 20;
		iHeight = iHeight < 80 ? 80 : iHeight;
		
		int iH = table.getRowHeight(row);
		if (iH != iHeight) {
			table.setRowHeight(row, iHeight);
		}
		return holder;
	}

}