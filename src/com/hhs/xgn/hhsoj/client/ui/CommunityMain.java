package com.hhs.xgn.hhsoj.client.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.hhs.xgn.hhsoj.client.ClientInfo;

public class CommunityMain extends JFrame {
	public CommunityMain(){
		
		//Init
		this.setTitle("Community - "+ClientInfo.user);
		this.setLayout(new BorderLayout());
		
		//Upper: My Information
		JButton mi=new JButton("My information");
		
		mi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//TODO User panel
				new UserDisplay(ClientInfo.user);
			}
		});
		
		//Down: about
		
		JButton about=new JButton("About HHSOJ");
		about.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "About\nBy XiaoGeNintendo\nHell Hole Studios\nThe software is open-sourced on Github.","About",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		//Left:Rating
		JButton rating=new JButton("Global rating");
		rating.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Rating Window
				new GlobalRating();
				
			}
		});
		
		//Right:Blog
		JButton blog=new JButton("Blogs");
		blog.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Blog
				new AllBlog();
			}
		});
		
		//Middle:jl
		JLabel jl=new JLabel("Welcome to community - "+ClientInfo.user);
		
		
		//together
		this.add("North",mi);
		this.add("West",rating);
		this.add("East",blog);
		this.add("South",about);
		this.add("Center", jl);
		this.setSize(500,500);
		this.setVisible(true);
		
	}
}
