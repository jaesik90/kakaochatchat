package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//DB�� �������� ���� num/users/����
public class Server_chat extends Thread{
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	Vector<ThreadManager> userThread=new Vector<ThreadManager>();
	
	InputStream input;
	FileOutputStream fos;
	JSONArray value;
	String msgValue,timeValue,senderValue;
	JSONObject valueCheck;
	
	
	public Server_chat(Socket socket,Vector<ThreadManager> userThread) {
		this.socket=socket;
		this.userThread=userThread;
		try {
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			input=socket.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void listen(){
		//Ŭ�󿡼� �޴� ��
		String msg=null;
		
		try {
			msg=buffr.readLine();
			//���⼭ �Ǵ��ϱ�

			JSONParser parser=new JSONParser();
			JSONObject obj=(JSONObject)parser.parse(msg);
			
			String type=(String)obj.get("type");
			if(type.equals("chat")){
				 value=(JSONArray)obj.get("contents");
				 System.out.println("���� : �ļ��迭"+value.size());
				 
				 if(value.size()!=0){
					 for(int i=0;i<value.size();i++){
						 System.out.println("�ļ��� ����?" + value.get(i));
						 if(i==0){
							 JSONObject json = (JSONObject)value.get(i);
							 System.out.println("ù��°��!!!!"+json.get("msg"));
							 msgValue=(String)json.get("msg");
						 }else if(i==1){
							 JSONObject json = (JSONObject)value.get(i);
							 timeValue=(String)json.get("time");
						 }else if(i==2){
							 JSONObject json = (JSONObject)value.get(i);
							 
							 senderValue=(String)json.get("sender");
						 }
					 }
					 
					 System.out.println(msgValue+senderValue+timeValue);
					 sendMsg(msgValue,timeValue,senderValue);
				 }
				 
				// for(int i=0;i<value.size();i++){
		/*			  valueCheck = (JSONObject)value.get(0);
					 msgValue =(String)valueCheck.get("msg");
					valueCheck = (JSONObject)value.get(2);
					 timeValue =(String)valueCheck.get("time");
					 valueCheck = (JSONObject)value.get(1);
					 senderValue =(String)valueCheck.get("sender");*/
				// }
				// System.out.println("������"+msgValue);
				//System.out.println("send�� ������ str : "+str);
				//sendMsg(msgValue,senderValue,timeValue);
			
			}/*
			else if(type.equals("time")){
				String str=(String)obj.get("content");
				sendTime(str);
			}
			else if(type.equals("sender")){
				String str=(String)obj.get("content");
				sendSender(str);
			}*/
		/*	else if(type.equals("image")){
				String str=(String)obj.get("content");//file name
				
				String path="C:/myserver/data/"+str;
				fos=new FileOutputStream(path);
					
				int data=-1;

				while(true){
					data=input.read();
					if(data==-1) break;
					fos.write(data);
					fos.flush();
				}
			    fos.close();
			}*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMsg(String msg,String time,String sender){
		//������ �����ִ� ��
		
			//�Ǵ��ؼ� �����ֱ�
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append(	"\"type\":\"chat\",");
			sb.append("\"contents\":[{\"msg\":\""+msg+"\"},{\"time\":\""+time+"\"},{\"sender\":\""+sender+"\"}]");
			sb.append("}");
			String myString = sb.toString();
			
			
/*			try {
				buffw.write(myString+"\n");
				buffw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			
		for(int i=0;i<userThread.size();i++){
				try {
					userThread.get(i).chat.buffw.write(myString+"\n");
					userThread.get(i).chat.buffw.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
			
	}
	public void sendTime(String time){
		//������ �����ִ� ��
		try {
			//�Ǵ��ؼ� �����ֱ�
			JSONObject obj=new JSONObject();
			obj.put("type", "time");
			obj.put("content", time);
			
			String myString = obj.toString();
			
			for(int i=0;i<userThread.size();i++){
				userThread.get(i).chat.buffw.write(myString+"\n");
				userThread.get(i).chat.buffw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	public void sendSender(String sender){
		//������ �����ִ� ��
		try {
			//�Ǵ��ؼ� �����ֱ�
			JSONObject obj=new JSONObject();
			obj.put("type", "sender");
			obj.put("content", sender);
			
			String myString = obj.toString();
			
			for(int i=0;i<userThread.size();i++){
				userThread.get(i).chat.buffw.write(myString+"\n");
				userThread.get(i).chat.buffw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
	public void run() {
		
		while(true){
			listen();
		}
	}
}