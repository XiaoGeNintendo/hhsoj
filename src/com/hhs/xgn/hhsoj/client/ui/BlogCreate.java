package com.hhs.xgn.hhsoj.client.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.hhs.xgn.hhsoj.client.ClientInfo;
import com.hhs.xgn.hhsoj.util.Net;

public class BlogCreate extends JFrame {
	BlogCreate self=this;
	JTextField jtf;
	JTextArea jta;
	
	public BlogCreate(){
		//Basic Information
		this.setTitle("New blog - "+ClientInfo.user);
		this.setLayout(new BorderLayout());
		
		//Title
		jtf=new JTextField("Blog title...");
		jta=new JTextArea("Blog content here...\n Notice that once submitted you can never change!");
		JScrollPane jsp=new JScrollPane(jta);
		
		//Button
		JButton submit=new JButton("Finish!");
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				boolean ok=Net.submitBlog(jtf.getText(),jta.getText());
				if(ok){
					self.dispose();
				}
			}
		});
		
		//add them together!
		
		this.add("North",jtf);
		this.add("Center", jsp);
		this.add("South", submit);
		
		this.setSize(500,500);
		this.setVisible(true);
	}
}
