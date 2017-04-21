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

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//DB가 가져야할 정보 num/users/내용
public class Server_chat extends Thread{
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	Vector<ThreadManager> userThread=new Vector<ThreadManager>();
	
	InputStream input;
	FileOutputStream fos;
	
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
		//클라에서 받는 것
		String msg=null;
		
		try {
			msg=buffr.readLine();
			//여기서 판단하기

			JSONParser parser=new JSONParser();
			JSONObject obj=(JSONObject)parser.parse(msg);
			//System.out.println("서버가 파서 하는 msg: "+msg);
			String type=(String)obj.get("type");
			if(type.equals("chat")){
				String str=(String)obj.get("content");
				//System.out.println("send로 보내는 str : "+str);
				sendMsg(str);
			}
			else if(type.equals("time")){
				String str=(String)obj.get("content");
				sendTime(str);
			}
			else if(type.equals("sender")){
				String str=(String)obj.get("content");
				sendSender(str);
			}
			else if(type.equals("image")){
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
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMsg(String msg){
		//서버가 보내주는 것
		try {
			//판단해서 보내주기
			JSONObject obj=new JSONObject();
			obj.put("type", "chat");
			obj.put("content", msg);
			
			String myString = obj.toString();
			
			for(int i=0;i<userThread.size();i++){
				userThread.get(i).chat.buffw.write(myString+"\n");
				userThread.get(i).chat.buffw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	public void sendTime(String time){
		//서버가 보내주는 것
		try {
			//판단해서 보내주기
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
		//서버가 보내주는 것
		try {
			//판단해서 보내주기
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