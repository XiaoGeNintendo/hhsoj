package com.hhs.xgn.hhsoj.client.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.hhs.xgn.hhsoj.client.ClientInfo;


public class IPInput extends JFrame {
	IPInput self=this;
	JTextField ipi,ipp;
	
	public IPInput(){
		this.setTitle("Input Server IP");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(3, 2));
		
		JLabel ip=new JLabel("Server IP:");
		JLabel port=new JLabel("Server port:");
		ipi=new JTextField("localhost");
		ipp=new JTextField("8080");
		JButton jb=new JButton("OK");
		
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					ClientInfo.ip=ipi.getText();
					ClientInfo.port=Integer.parseInt(ipp.getText());
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "Sorry, error occurred when parsing the port number.\nMake sure you entered a integer please?","Error",JOptionPane.ERROR_MESSAGE);
					return;
				}
				new Login();
				
				self.dispose();
			}
		});
		
		this.add(ip);
		this.add(ipi);
		this.add(port);
		this.add(ipp);
		this.add(jb);
		this.setSize(500, 100);
		this.setVisible(true);
	}
}
