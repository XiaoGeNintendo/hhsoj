package com.hhs.xgn.hhsoj.client.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.hhs.xgn.hhsoj.client.ClientInfo;
import com.hhs.xgn.hhsoj.util.Net;


public class Login extends JFrame {
	Login self=this;
	JTextField ipi;
	JPasswordField ipp;
	
	public Login(){
		this.setTitle("Login");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(4, 2));
		
		JLabel info=new JLabel("You need to be logged in");
		
		JLabel ip=new JLabel("Username:");
		JLabel port=new JLabel("Password:");
		ipi=new JTextField("");
		ipp=new JPasswordField("");
		
		
		JButton jb=new JButton("OK");
		JButton jb2=new JButton("Cancel");
		
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//TODO check password and open main window
				
				int ver=Net.checkUser(ipi.getText(),String.valueOf(ipp.getPassword()));
				
				if(ver==0){
					JOptionPane.showMessageDialog(null, "Cannot connect to the server.","Error",JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(ver==1){
					JOptionPane.showMessageDialog(null, "Wrong Username or password.Please try again.","Warning",JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				ClientInfo.user=ipi.getText();
				
				new MainMenu();
				self.dispose();
			}
		});
		
		jb2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				self.dispose();
			}
		});
		
		this.add(info);
		this.add(new JLabel());
		
		this.add(ip);
		this.add(ipi);
		this.add(port);
		this.add(ipp);
		this.add(jb);
		this.add(jb2);
		
		this.setSize(500, 100);
		this.setVisible(true);
	}
}
