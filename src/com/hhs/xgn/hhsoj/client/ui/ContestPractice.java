package com.hhs.xgn.hhsoj.client.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.hhs.xgn.hhsoj.client.ClientInfo;
import com.hhs.xgn.hhsoj.type.Contest;
import com.hhs.xgn.hhsoj.util.CmdUtil;
import com.hhs.xgn.hhsoj.util.Net;

public class ContestPractice extends JFrame {
	Contest c;
	ContestPractice self=this;
	
	public ContestPractice(Contest c){
		this.c=c;
		
		//Set Basic Information
		this.setTitle("Pratising: "+c.title+" - "+ClientInfo.user);
		this.setLayout(new BorderLayout());
		
		//Set Middle
		JLabel jl=new JLabel("Pratising:"+c.title);
		
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
				
				new SubmitWindow(self.c,true);
				//System.out.println("hello/");
			}
		});
		
		//Status
		JButton jb3=new JButton("Status");
		jb3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ContestStatus(self.c, true);
				
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
		
		
		//Put together
		this.add("Center",jl);
		this.add("West",jb3);
		this.add("South",jb2);
		this.add("North", jb);
		this.add("East", jb4);
		
		this.setSize(500,500);
		this.setVisible(true);
	}
}
