package com.hhs.xgn.hhsoj.client.ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.hhs.xgn.hhsoj.client.ClientInfo;
import com.hhs.xgn.hhsoj.type.Blog;
import com.hhs.xgn.hhsoj.util.Net;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

public class AllBlog extends JFrame {
	List<Blog> b=new ArrayList<Blog>();
	
	public AllBlog(){
		//Get blogs from the server
		this.b=Net.getAllBlogs();
		
		//Basic Information
		this.setTitle("All blogs - "+ClientInfo.user);
		this.setLayout(new BorderLayout());
		
		//Add a button
		JButton jb=new JButton("+ Create a blog!");
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Blog creating window
				new BlogCreate();
				
			}
		});
		
		//Add a list
		List<String> ls=new ArrayList<String>();
		for(Blog bs:b){
			ls.add(bs.toString());
		}
		
		//Put the list into JList
		JList<Object> jl=new JList<Object>(ls.toArray());
		
		jl.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				JList<?> jl=(JList<?>)e.getComponent();
				
				if(jl.getSelectedIndex()==-1) return;
				
				new ViewBlog(b.get(jl.getSelectedIndex()));
			}
		});
		
		jl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane jsp=new JScrollPane(jl);
		
		//Add them together
		this.add("North",jb);
		this.add("Center",jsp);
		this.setSize(500,500);
		this.setVisible(true);
		
	}
}
