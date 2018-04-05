package com.hhs.xgn.hhsoj.client;

import java.io.File;

import com.hhs.xgn.hhsoj.client.task.TaskExecutor;
import com.hhs.xgn.hhsoj.client.ui.IPInput;

public class ClientMain {

	
	public static void main(String[] args) {
		System.out.println("Welcome to use HHS OJ!");
		System.out.println("Made by XiaoGeNintendo, Hell Hole Studios");
		System.out.println("Calling the GUI.. Please wait.");
		Solver s=new Solver();
		s.solve();
	}

}

class Solver{
	void p(String msg){
		System.out.println(msg);
	}
	void solve(){
		p("Client Solver Starts Running!");
		p("Going to check tasks");

		
		
		p("Starting IP Enter GUI");
		
		p("Checking folders...");
		File f=new File("local");
		File r=new File("task");
		
		if(f.exists()==false){
			f.mkdirs();
			p("Made local dir.");
		}
		if(r.exists()==false){
			r.mkdirs();
			p("Made task dir.");
		}
		new TaskExecutor();
		
		new IPInput();
		
		
	}
}