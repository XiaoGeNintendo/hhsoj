package com.hhs.xgn.hhsoj.client.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.hhs.xgn.hhsoj.client.ClientInfo;
import com.hhs.xgn.hhsoj.type.Contest;
import com.hhs.xgn.hhsoj.util.Net;

public class ContestBoard extends JFrame {
	
	JFrame self=this;
	JList<Object> jlo;
	List<Contest> lc;
	public ContestBoard(){
		this.setTitle("Contests Online - "+ClientInfo.user);
		this.setLayout(new BorderLayout());
		
		//Contest getting
		long now=Net.currentTimeMillis();
		
		lc=Net.readAllContests();
		List<String> ls=new ArrayList<String>();
		for(Contest c:lc){
			ls.add(c.title+"["+(now>=c.start && now<=c.end ? "Running!!" : (now>c.end?"Ended":"Not started"))+"]");
		}
		
		//Upper Label Preparing
		JLabel jl=new JLabel("Now time:"+now+" .unix");
		
		//Middle JList Preparing
		jlo=new JList<Object>(ls.toArray());
		jlo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlo.setBorder(BorderFactory.createTitledBorder("Contests"));
		JScrollPane jspjlo=new JScrollPane(jlo);
        jspjlo.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        //Lower Refresh Preparing
        JButton jb=new JButton("Refresh");
        jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ContestBoard();
				self.dispose();
			}
		});
        
        //Left Practice Button Preparing
        JButton jb2=new JButton("Practice");
        jb2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jlo.getSelectedValue()==null){
					JOptionPane.showMessageDialog(null, "Please choose a contest to practice!","Warning",JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(((String)(jlo.getSelectedValue())).indexOf("Ended")==-1){
					JOptionPane.showMessageDialog(null, "This contest didn't end!\nYou can't practice it.","Warning",JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				new ContestPractice(lc.get(jlo.getSelectedIndex()));
				
			}
			
		});
        
        
        //Right Register Button Preparing
        JButton jb3=new JButton("Register");
        jb3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jlo.getSelectedValue()==null){
					JOptionPane.showMessageDialog(null, "Please choose a contest to register!","Warning",JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(((String)(jlo.getSelectedValue())).indexOf("Running")==-1){
					JOptionPane.showMessageDialog(null, "This contest aren't running!\nYou can't register it.","Warning",JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				//TODO Contest Register Board
				new RegisterBoard(lc.get(jlo.getSelectedIndex()));
			}
			
		});
        
        //Put all together!
        
        this.add("North",jl);
        this.add("Center",jspjlo);
        this.add("South", jb);
        this.add("West", jb2);
        this.add("East", jb3);
        this.setSize(500,500);
        this.setVisible(true);
	}
}
