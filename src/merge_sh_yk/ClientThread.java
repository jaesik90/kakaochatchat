package merge_sh_yk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Vector;

import javax.swing.JTextArea;
import javax.swing.JTextPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import client.chat.Chat;
import client.chat.ChatMain;

public class ClientThread extends Thread{
	Socket socket;
	Socket imgsocket;
	ChatMain main;
	
	BufferedReader buffr;
	BufferedWriter buffw;
	
	File file;
	
	OutputStream output;
	public Chat chatDto;
	String msgValue,timeValue,senderValue;
	JSONArray value;
	JSONObject valueCheck;
	
	public ClientThread(Socket socket,ChatMain main) {
		this.socket=socket;
		this.main=main;
		
		try {
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			output=socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void listen(){
		String msg=null;

		JSONParser parser=new JSONParser();
		chatDto=new Chat();
		try {
			msg=buffr.readLine();
			
			JSONObject obj=(JSONObject)parser.parse(msg);
			System.out.println("클라이언트  파써"+msg);
			String type=(String)obj.get("type");
			
			if(type.equals("chat")){
	/*			 value=(JSONArray)obj.get("contents");
				// for(int i=0;i<value.size();i++){
				  valueCheck = (JSONObject)value.get(0);
				 msgValue =(String)valueCheck.get("msg");
				
				 valueCheck = (JSONObject)value.get(2);
				 timeValue =(String)valueCheck.get("time");
				
				 valueCheck = (JSONObject)value.get(1);
				 senderValue =(String)valueCheck.get("sender");
				// }
				//System.out.println("send로 보내는 str : "+str);
				//sendMsg(msgValue,senderValue,timeValue);
				 
				 System.out.println("메시지"+msgValue+" 센더"+senderValue+" 타임 "+timeValue);
				 chatDto.setMsg(msgValue);
				 chatDto.setSender(senderValue);
				 chatDto.setTime(timeValue);*/
				 value=(JSONArray)obj.get("contents");
				 System.out.println("서버 : 파서배열"+value.size());
				 
				 if(value.size()!=0){
					 for(int i=0;i<value.size();i++){
						 System.out.println("파서의 값은?" + value.get(i));
						 if(i==0){
							 JSONObject json = (JSONObject)value.get(i);
							 System.out.println("첫번째값!!!!"+json.get("msg"));
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
					 //sendMsg(msgValue,senderValue,timeValue);
					 chatDto.setMsg(msgValue);
					 chatDto.setSender(senderValue);
					 chatDto.setTime(timeValue);
				 }
		
				
			}
			
			
			
			
			
	/*		if(type.equals("chat")){
				String str=(String)obj.get("content");
				chatDto.setMsg(str);

			}
			else if(type.equals("time")){
				String str=(String)obj.get("content");
				//System.out.println("서버로부터 time받는 str : "+str);
				chatDto.setTime(str);
								
			}
			else if(type.equals("sender")){
				String str=(String)obj.get("content");
				chatDto.setSender(str);				
			}*/
			if(type.equals("join")){//접속시 아이디 부여
				String str=(String)obj.get("content");
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMsg(String msg,String time,String sender){
		try {
	/*		JSONObject obj=new JSONObject();
			obj.put("type", "chat");
			obj.put("content", msg);*/
			
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append(	"\"type\":\"chat\",");
			sb.append("\"contents\":[{\"msg\":\""+msg+"\"},{\"time\":\""+time+"\"},{\"sender\":\""+sender+"\"}]");
			sb.append("}");
			
			/*
			obj.put(	"\"type\":\"chat\",");
			obj.put("\"loc\" :\"서울\",");
			obj.put("\"menus\":[{\"menu\":\"짜장\"},{\"menu\":\"짬뽕\"}]");
			obj.put("}");
			*/
			String myString = sb.toString();
			
			System.out.println("클라이언트에서 서버로 보내는말: "+myString);
			buffw.write(myString+"\n");
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendTime(String time){
		try {
			JSONObject obj=new JSONObject();
			obj.put("type", "time");
			obj.put("content", time);
			
			String myString = obj.toString();
			buffw.write(myString+"\n");
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendSender(String sender){
		try {
			JSONObject obj=new JSONObject();
			obj.put("type", "sender");
			obj.put("content", sender);
			
			String myString = obj.toString();
			buffw.write(myString+"\n");
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send(File file){
		JSONObject obj=new JSONObject();
		obj.put("type", "image");
		obj.put("content", file.getName());

		String myString = obj.toString();
	    try {
			buffw.write(myString+"\n");
			buffw.flush();
			Files.copy(file.toPath(), output);
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	

	public void run() {
		
		while(true){
			listen();
			main.table.setModel(main.model);
			main.table.updateUI();
		}
	}
}
