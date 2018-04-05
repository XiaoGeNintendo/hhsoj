package com.hhs.xgn.hhsoj.client.ui;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.hhs.xgn.hhsoj.client.ClientInfo;
import com.hhs.xgn.hhsoj.type.Verdict;
import com.hhs.xgn.hhsoj.util.Net;

public class MySubmission extends JFrame {
	JFrame self=this;
	JList<String> jl;
	
	public MySubmission(){
		this.setTitle("My Submission - "+ClientInfo.user);
		this.setLayout(new BorderLayout());
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		String[] s=Net.mySubmission(ClientInfo.user);
		
		jl=new JList<String>(s);
		
		jl.setBorder(BorderFactory.createTitledBorder("My Submission"));
		jl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JButton jb=new JButton("Watch the submission");
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(jl.getSelectedValue()==null){
					JOptionPane.showMessageDialog(null,"Please choose a verdict to see!","Warning",JOptionPane.WARNING_MESSAGE);
					return ;
				}
				
				Verdict v=Net.getSubmissionDetail(jl.getSelectedValue());
				
				System.out.println("Transfer:"+v);
				
				new WatchSubmission(v);
				
			}
		});
		
		JScrollPane jsp=new JScrollPane(jl);
		//默认的设置是超过文本框才会显示滚动条，以下设置让滚动条一直显示
        jsp.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(jsp);
		
		this.add("Center",jsp);
		this.add("South",jb);
		
		this.setSize(500,250);
		this.setVisible(true);
	}


}
