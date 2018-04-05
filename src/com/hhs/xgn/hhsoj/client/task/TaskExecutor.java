package com.hhs.xgn.hhsoj.client.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.hhs.xgn.hhsoj.type.Contest;
import com.hhs.xgn.hhsoj.util.Net;

public class TaskExecutor {
	public TaskExecutor(){
		File f=new File("task");
		for(File fs:f.listFiles()){
			boolean ans=TaskExecute(fs);
			if(ans){
				fs.delete();
			}
		}
	}

	private boolean TaskExecute(File fs) {
		try{
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(fs)));
			int mode=Integer.parseInt(br.readLine());
			switch(mode){
				case 1:
					Contest c=new Contest();
					c.title=br.readLine();
					Net.pendRatingChange(c);
					return true;
				default:
					System.out.println("Unknown code:"+mode);
					return false;
			}
		}catch(Exception e){
			return false;
		}
	}
	
	
}
