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
	public Vector<Chat> vec = new Vector<Chat>(); //1차원
	Chat chatDto;
	//public Vector<Vector> data= new Vector<Vector>(); //2차원

	
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
		
		//1차원 벡터에 담을 dto을 생성하자
		chatDto = new Chat();
		
		try {
			msg=buffr.readLine();
			
			JSONObject obj=(JSONObject)parser.parse(msg);
			System.out.println("클라이언트  파써"+msg);
			String type=(String)obj.get("type");
			
			
			if(type.equals("chat")){
				String str=(String)obj.get("content");
				chatDto.setMsg(str);
				
				//System.out.println("서버로부터 받는 str : "+str);
				//System.out.println("Chat main에 있는 chat"+main.chat);
				
			}
			else if(type.equals("time")){
				String str=(String)obj.get("content");
				//System.out.println("서버로부터 time받는 str : "+str);
				chatDto.setTime(str);
								
			}
			else if(type.equals("sender")){
				String str=(String)obj.get("content");
				chatDto.setSender(str);				
			}
			else if(type.equals("join")){//접속시 아이디 부여
				String str=(String)obj.get("content");
			}
			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}finally{
			vec.add(chatDto);
			
		}
	}
	
	public void sendMsg(String msg){
		try {
			JSONObject obj=new JSONObject();
			obj.put("type", "chat");
			obj.put("content", msg);
			
			String myString = obj.toString();
			//System.out.println("클라이언트에서 서버로 보내는말: "+myString);
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
