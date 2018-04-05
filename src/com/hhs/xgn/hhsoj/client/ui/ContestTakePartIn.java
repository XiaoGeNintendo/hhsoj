package com.hhs.xgn.hhsoj.client.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import com.hhs.xgn.hhsoj.client.ClientInfo;
import com.hhs.xgn.hhsoj.type.Contest;
import com.hhs.xgn.hhsoj.util.CmdUtil;
import com.hhs.xgn.hhsoj.util.Net;
import com.hhs.xgn.hhsoj.util.TimeUtil;

public class ContestTakePartIn extends JFrame {
	static Contest  c;
	ContestTakePartIn self=this;
	static JLabel jl;
	Thread t;
	boolean stop=false;
	Timer tt;
	
	public ContestTakePartIn(Contest c){
		this.c=c;
		
		//Set time
		ClientInfo.contStart=Net.currentTimeMillis();
		
		//Set Basic Information
		this.setTitle("Competing: "+c.title+" - "+ClientInfo.user);
		this.setLayout(new GridLayout(3,2));
		
		//Set Middle
		jl=new JLabel("Time Left:"+TimeUtil.minusTime(ClientInfo.contStart+c.length,Net.currentTimeMillis()));
		
		//Add close operation
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				int a=JOptionPane.showConfirmDialog(null, "You are leaving.\nAre you going to end the contest right now?","Confirm",JOptionPane.YES_NO_OPTION);
				if(a==JOptionPane.NO_OPTION){
					return;
				}
				
				//JOptionPane.showMessageDialog(null,"Plz wait for new version :D","Contest end information",JOptionPane.WARNING_MESSAGE);
				
				Net.pendRatingChange(self.c);
				
				stop=true;
				tt.stop();
				self.dispose();
			}
		});
		
		//Set statement
		JButton jb=new JButton("Problem Statement");
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO Statement
				
				//First check if we have got the statement
				File f=new File("local/"+c.title);
				if(f.exists()==false || f.list().length==0){
					if(!f.exists()) f.mkdirs();
					int ans=JOptionPane.showConfirmDialog(null, "The statement is missing. Do you want to download one?", "Question", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(ans==JOptionPane.YES_OPTION){
						Net.downloadStatement(c.title);
						JOptionPane.showMessageDialog(null, "Download ok.Press me again to see.","Info",JOptionPane.INFORMATION_MESSAGE);
					}
					
					return;
				}
				
				CmdUtil.runCmd("\""+f.getAbsolutePath()+"/"+f.list()[0]+"\"",false);
				
			}
		});
		
		//Submit
		JButton jb2=new JButton("Submit");
		jb2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				new SubmitWindow(self.c,false);
				//System.out.println("hello/");
			}
		});
		
		//Status
		JButton jb3=new JButton("Status");
		jb3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ContestStatus(self.c, false);
				
			}
		});
		
		//Standing
		JButton jb4=new JButton("Standing");
		jb4.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO open standing window
				new ContestStanding(self.c);
				
			}
		});
		
		//Set a button that will refresh
		JButton re=new JButton("Refresh");
		re.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jl.setText("Time Left:"+TimeUtil.minusTime(ClientInfo.contStart+c.length,Net.currentTimeMillis()));
			}
		});
		
		//Put together
		this.add(jb4);
		this.add(jb3);
		this.add(jb2);
		this.add(jb);
		this.add(jl);
		this.add(re);
		
		this.setSize(500,500);
		this.setVisible(true);
		
		//Timer for testing contest ends
		tt=new Timer(1500, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				jl.setText("Time Left:"+TimeUtil.minusTime(ClientInfo.contStart+c.length,Net.currentTimeMillis()));
				if(ClientInfo.contStart+ContestTakePartIn.c.length-Net.currentTimeMillis()<=0){
					System.out.println("End contest!!");
					//Calculated 
					
					if(self.isDisplayable()==false){
						return;
					}
					
					
					JOptionPane.showMessageDialog(null,"Contest has ended!Your rating is being calculated soon.","Contest end information",JOptionPane.WARNING_MESSAGE);
					
					//TODO Rating
					//JOptionPane.showMessageDialog(null,"Plz wait for new version :D","Contest end information",JOptionPane.WARNING_MESSAGE);
					
					Net.pendRatingChange(c);
					
					tt.stop();
					self.dispose();
				}
			}
		});
		
		tt.start();
		
//		t=new MyThread();
//		t.start();
	}
	
	@Deprecated
	class MyThread extends Thread{
		public void run(){
			while(ClientInfo.contStart+ContestTakePartIn.c.length-Net.currentTimeMillis()>0){
				
			}
			
			System.out.println("End contest!!");
			//Calculated 
			
			if(self.isDisplayable()==false){
				return;
			}
			
			
			JOptionPane.showMessageDialog(null,"Contest has ended!Your rating is being calculated soon.","Contest end information",JOptionPane.WARNING_MESSAGE);
			
			//TODO Rating
			//JOptionPane.showMessageDialog(null,"Plz wait for new version :D","Contest end information",JOptionPane.WARNING_MESSAGE);
			
			Net.pendRatingChange(c);
			self.dispose();
			
			//Unknown code
		}
	}
	
}



