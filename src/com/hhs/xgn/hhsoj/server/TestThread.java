package com.hhs.xgn.hhsoj.server;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.hhs.xgn.hhsoj.type.Problem;
import com.hhs.xgn.hhsoj.type.Test;

@Deprecated
public class TestThread extends Thread {
	Test t;
	String pos;
	Problem p;
	public TestThread(Test t,String pos,Problem p){
		this.t=t;
		this.pos=pos;
		this.p=p;
	}
	
	public void run(){
		try {
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
