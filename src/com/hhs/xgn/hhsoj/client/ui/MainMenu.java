package com.hhs.xgn.hhsoj.client.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.hhs.xgn.hhsoj.client.ClientInfo;

public class MainMenu extends JFrame {
	JFrame self=this;
	
	public MainMenu(){
		this.setTitle("Welcome to HHSOJ - "+ClientInfo.user+" !");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		JLabel middle=new JLabel("Welcome to HHSOJ,"+ClientInfo.user+"!\n");
		JButton contest=new JButton("Contests Online");
		JButton submission=new JButton("My Submission");
		JButton exit=new JButton("Exit");
		JButton about=new JButton("Community");
		
		this.add("North",contest);
		this.add("South",submission);
		this.add("East",exit);
		this.add("West",about);
		this.add("Center",middle);
		
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		about.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//
				//TODO Show community panel
				new CommunityMain();
			}
			
		});
			
		submission.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//TODO show the submission window.
				new MySubmission();
				
			}
			
		});
		
		contest.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new ContestBoard();
			}
		});
		this.setSize(500,500);
		this.setVisible(true);
	}
}
