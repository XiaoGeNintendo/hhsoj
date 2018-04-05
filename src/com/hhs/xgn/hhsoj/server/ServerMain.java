package com.hhs.xgn.hhsoj.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import com.hhs.xgn.hhsoj.util.ServerUtil;

public class ServerMain {

	public static void main(String[] args) {
		Solver s=new Solver();
		s.solve();
	}

}


class Solver{
	void p(String msg){
		System.out.println(msg);
	}
	void solve(){
		Scanner s=new Scanner(System.in);
		
		p("Input a port number:");
		
		int port=s.nextInt();
		
		s.close();
		
		
		p("Server ran start");
		p("Checking Files...");
		File user=new File("user");
		File contest=new File("contest");
		File verdict=new File("verdict");
		File runtime=new File("runtime");
		File standing=new File("standing");
		File rating=new File("rating");
		File blog=new File("blog");
		
		if(user.exists()==false){
			user.mkdirs();
			p("Created User Dir");
		}
		
		if(contest.exists()==false){
			contest.mkdirs();
			p("Created contest Dir");
		}
		
		if(verdict.exists()==false){
			verdict.mkdirs();
			p("Created Verdict Dir");
		}
		
		if(runtime.exists()==false){
			runtime.mkdirs();
			p("Created Runtime Dir");
		}
		
		if(standing.exists()==false){
			standing.mkdirs();
			p("Created Standings Dir");
		}
		
		if(rating.exists()==false){
			rating.mkdirs();
			p("Created Rating Dir");
		}
		
		if(blog.exists()==false){
			blog.mkdirs();
			p("Created Blog dir");
		}
		
		//Reading things
		p("Reading Users");
		ServerUtil.readUsers();
		
		p("Reading Contests");
		ServerUtil.readContests();
		
		//Init Things
		ServerUtil.initStanding();
		
		//Thread for judging~
		
		//Server working
		p("Server working start");
		try {
			ServerSocket ss=new ServerSocket(port);
			while(true){
				Socket fs=ss.accept();
				Thread t=new ServerThread(fs);
				t.run();
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
	}
}