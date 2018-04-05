package com.hhs.xgn.hhsoj.client.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.hhs.xgn.hhsoj.client.ClientInfo;
import com.hhs.xgn.hhsoj.type.Contest;
import com.hhs.xgn.hhsoj.type.Problem;
import com.hhs.xgn.hhsoj.util.Net;

public class SubmitWindow extends JFrame{
	Contest c;
	boolean parctice;
	MyTextPane mtp;
	SubmitWindow self=this;
	JComboBox<Object> jcb;
	JComboBox<String> jcb2;
	
	public SubmitWindow(Contest c,boolean parctice){
		this.c=c;
		this.parctice=parctice;
		
		this.setTitle("Submit : "+c.title+" - "+ClientInfo.user);
		this.setLayout(new BorderLayout());
		
		List<String> ls=new ArrayList<String>();
		
		for(Problem p:c.problems){
			ls.add(p.title+" Time Limit:"+p.tl+"ms ");
		}
		//Making North Problem Choose List
		jcb=new JComboBox<Object>(ls.toArray());
		
		//Making Middle Text Pane
		mtp=new MyTextPane();
		JScrollPane jsp=new JScrollPane(mtp);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		//Left label
		JTextArea jta=new JTextArea("Welcome to submitting!\nHere's a few rules to obey!\nFirst, don't send rubbish programs.\nSecond,don't connect to the Internet or use file system\nThird,don't try to hack the system.\nFourth,don't send program that will interrupt the testing system.\nAnd,language will be chosen by the program\nYou can use Python,C++,Java to submit\nSome problem doesn't support some languages.\nMake sure you know!\nFor Java user,please name your class __CLASS_33_\nAnd for other languages, plz don't use __CLASS_33_ as names!");
		jta.setEditable(false);
		
		//Right Combo box for language choose
		jcb2=new JComboBox<String>(new String[]{"C++","Java","Python"});
		
		
		//Down Button
		JButton jb=new JButton("Submit!!!");
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//TODO Submit code
				Net.submitCode(mtp.getText(),self.c.title,jcb.getSelectedIndex(),self.parctice,(String)jcb2.getSelectedItem());
				
				JOptionPane.showMessageDialog(null, "Submit solution ok!Please wait for judging.\nGo to 'Status' or 'My Submission' to watch results.","Submit",JOptionPane.INFORMATION_MESSAGE);
				self.dispose();
			}
		});
		
		//make them together
		this.add("North",jcb);
		this.add("Center",jsp);
		this.add("South",jb);
		this.add("West", jta);
		this.add("East", jcb2);
		
		this.setSize(1000,500);
		this.setVisible(true);
	}
}
