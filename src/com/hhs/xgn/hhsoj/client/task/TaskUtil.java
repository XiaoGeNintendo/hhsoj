package com.hhs.xgn.hhsoj.client.task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class TaskUtil {
	public static void addTask(Object... lines){
		try{
			File f=new File("task");
			if(f.exists()==false){
				f.mkdirs();
			}
			
			PrintWriter pw=new PrintWriter(new OutputStreamWriter(new FileOutputStream("task/"+System.currentTimeMillis())));
		
			for(Object s:lines){
				pw.println(s);
			}
			
			pw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
