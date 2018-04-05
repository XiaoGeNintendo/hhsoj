package com.hhs.xgn.hhsoj.client.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.hhs.xgn.hhsoj.client.ClientInfo;
import com.hhs.xgn.hhsoj.type.Contest;
import com.hhs.xgn.hhsoj.util.CmdUtil;
import com.hhs.xgn.hhsoj.util.Net;

public class RegisterBoard extends JFrame {
	Contest c;
	RegisterBoard self=this;
	public RegisterBoard(Contest c){
//		this.c=c;
//		
//		File f=new File("rule.pdf");
//		if(f.exists()==false){
//			JOptionPane.showMessageDialog(null, "Fail to open the rules!!Please redownload the application.","ERROR",JOptionPane.ERROR_MESSAGE);
//			return;
//		}
//		
//		CmdUtil.runCmd("\""+f.getAbsolutePath()+"\"", false);
//		
//		int cho=JOptionPane.showConfirmDialog(null, "Did you watch and will obey the rules shown in the pdf?", "Rules", JOptionPane.YES_NO_OPTION);
//		if(cho==JOptionPane.NO_OPTION){
//			return;
//		}
//		
//		boolean ok=Net.registerContest(c);
//		
//		if(!ok){
//			JOptionPane.showMessageDialog(null, "You've registered!U cannot register twice.","ERROR",JOptionPane.ERROR_MESSAGE);
//			return;
//		}
//		
		
		this.c=c;
		
		//Basic information
		this.setTitle("Contest Register: "+c.title+" - "+ClientInfo.user);
		this.setLayout(new BorderLayout());
		
		//JTextArea
		String s="HHSOJ Contest Rules\n"+
				"Make sure you know them before registering the contest\n"+
				
				"I. Contest Rules\n"+
				"	1. Do not close the client during the whole contest.\n"+
				"	2. Do not tell others the problem statement or the programs.\n"+
				"	3. Do not communicate with others during a contest except the contest"+
				" is team allowed.\n"+
				"	4. Do not copy solutions from others.\n"+
				"	5. More rules, please watch the contest statement you¡¯re registering.\n"+
				"	6. Do not use multiple accounts. Please take part in the contest using"+
				" your personal and the single account.\n"+
				"	7. Do not try to fail to obey the rules shown on this document.\n\n"+
				
				"II. Submit Rules\n"+
				"	1. Do not try to upload code that will interrupt the testing process,"+
				"hack the server, read or write the file system, connect to the Internet"+
				" or use two or more threads.\n"+
				"	2. Do not write anything about ¡°__CLASS_33_¡± if you¡¯re not using Java"+
				" as a language.\n"+
				"	3. You can only submit one file at a time.\n"+
				"	4. Any further rules, please watch the contest statement you¡¯re"+
				" registering.\n\n"+
				
				"III. Other Rules\n"+
				"	1. Do not use third-party client to submit or do anything\n"+
				"Thank you for your corporation to the HHSOJ System.\n"+
				"		By HHSOJ Main Developer, XiaoGeNintendo from Hell Hole Studio";
		
				
				
		JTextArea jta=new JTextArea(s);
		jta.setEditable(false);
		
		JScrollPane jsp=new JScrollPane(jta);
		
		//Join button
		JButton jb=new JButton("Register & Join");
		
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int cho=JOptionPane.showConfirmDialog(null, "Did you watch and will obey the rules?", "Rules", JOptionPane.YES_NO_OPTION);
				if(cho==JOptionPane.NO_OPTION){
					return;
				}
				
				boolean ok=Net.registerContest(c);
				
				if(!ok){
					JOptionPane.showMessageDialog(null, "You've registered!U cannot register twice.","ERROR",JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				self.dispose();
				new ContestTakePartIn(c);
				
			}
		});
		
		//add them together
		
		this.add("Center",jsp);
		this.add("South",jb);
		this.setSize(500,500);
		this.setVisible(true);
		
	}
}
