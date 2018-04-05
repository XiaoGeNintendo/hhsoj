package com.hhs.xgn.hhsoj.client.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.hhs.xgn.hhsoj.client.ClientInfo;
import com.hhs.xgn.hhsoj.type.Contest;
import com.hhs.xgn.hhsoj.type.Verdict;
import com.hhs.xgn.hhsoj.util.Net;

public class ContestStatus extends JFrame {
	Contest c;
	ContestStatus self=this;
	List<Boolean> watchable=new ArrayList<Boolean>();
	JList<Object> jl;
	List<Verdict> v;
	boolean pra;
	
	public ContestStatus(Contest c,boolean pra){
		this.c=c;
		this.pra=pra;
		
		//Do some basic work
		v=Net.readContestSubmissions(c);
		
		List<String> ls=new ArrayList<String>();
		for(Verdict vs:v){
			ls.add(vs.sub.toStringEasy());
			
			if(!pra){
				watchable.add(vs.sub.user.equals(ClientInfo.user));
			}else{
				watchable.add(true);
			}
		}
		
		//Basic information
		this.setTitle("Contest Status:"+c.title+" - "+ClientInfo.user);
		this.setLayout(new BorderLayout());
		
		//Center
		jl=new JList<Object>(ls.toArray());
		jl.setBorder(BorderFactory.createTitledBorder("Submissions"));
		jl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane jsp=new JScrollPane(jl);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		//Down
		JButton jb=new JButton("Watch submission");
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jl.getSelectedValue()==null){
					JOptionPane.showMessageDialog(null, "Please choose a submission first!","Warning",JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if(!watchable.get(jl.getSelectedIndex())){
					JOptionPane.showMessageDialog(null, "This submission is not public for you. :(","Warning",JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				
				new WatchSubmission(v.get(jl.getSelectedIndex()));
				
			}
		});
		
		
		//Together
		
		this.add("Center",jsp);
		this.add("South",jb);
		this.setSize(500,500);
		this.setVisible(true);
	}
}
