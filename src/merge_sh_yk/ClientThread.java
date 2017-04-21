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
	public Vector<Chat> vec = new Vector<Chat>(); //1����
	Chat chatDto;
	//public Vector<Vector> data= new Vector<Vector>(); //2����

	
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
		
		//1���� ���Ϳ� ���� dto�� ��������
		chatDto = new Chat();
		
		try {
			msg=buffr.readLine();
			
			JSONObject obj=(JSONObject)parser.parse(msg);
			System.out.println("Ŭ���̾�Ʈ  �Ľ�"+msg);
			String type=(String)obj.get("type");
			
			
			if(type.equals("chat")){
				String str=(String)obj.get("content");
				chatDto.setMsg(str);
				
				//System.out.println("�����κ��� �޴� str : "+str);
				//System.out.println("Chat main�� �ִ� chat"+main.chat);
				
			}
			else if(type.equals("time")){
				String str=(String)obj.get("content");
				//System.out.println("�����κ��� time�޴� str : "+str);
				chatDto.setTime(str);
								
			}
			else if(type.equals("sender")){
				String str=(String)obj.get("content");
				chatDto.setSender(str);				
			}
			else if(type.equals("join")){//���ӽ� ���̵� �ο�
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
			//System.out.println("Ŭ���̾�Ʈ���� ������ �����¸�: "+myString);
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
