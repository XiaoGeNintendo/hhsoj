package com.hhs.xgn.hhsoj.client.ui;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.hhs.xgn.hhsoj.client.ClientInfo;
import com.hhs.xgn.hhsoj.type.Blog;

public class ViewBlog extends JFrame {
	Blog b;
	ViewBlog self=this;
	public ViewBlog(Blog b){
		this.b=b;
		
		//Basic information
		this.setLayout(new BorderLayout());
		this.setTitle("Blog viewing:"+b.title+" - "+ClientInfo.user);
		
		//Adding a title
		JLabel title=new JLabel(b.title+" By "+b.user);
		
		title.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new UserDisplay(b.user);
			}
		});
		
		//Adding a content
		JTextArea jta=new JTextArea(b.content);
		jta.setEditable(false);
		JScrollPane jsp=new JScrollPane(jta);
		
		//Add them together
		
		this.add("North",title);
		this.add("Center",jsp);
		this.setSize(500,500);
		this.setVisible(true);
	}
}
