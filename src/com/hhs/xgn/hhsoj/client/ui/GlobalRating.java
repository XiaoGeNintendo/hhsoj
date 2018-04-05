package com.hhs.xgn.hhsoj.client.ui;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.hhs.xgn.hhsoj.client.ClientInfo;
import com.hhs.xgn.hhsoj.server.community.rating.Rating;
import com.hhs.xgn.hhsoj.util.Net;
import com.hhs.xgn.hhsoj.util.StringUtil;

public class GlobalRating extends JFrame {
	public GlobalRating(){
		//get information
		List<Rating> lr=Net.getGlobalRating();
		
		//Find where's me??
		int index_me=-1;
		for(int i=0;i<lr.size();i++){
			if(lr.get(i).user.equals(ClientInfo.user)){
				index_me=i;
			}
		}
		
		//Init Window
		this.setTitle("Global Rating - "+ClientInfo.user);
		this.setLayout(new BorderLayout());
		
		//Add my result
		JLabel my=new JLabel((index_me==-1?ClientInfo.user+" - ??th":ClientInfo.user+" - "+StringUtil.getEnglishEnding(index_me+1)));
		
		System.out.println(lr);
		
		//Add a list
		List<String> ls=new ArrayList<String>();
		
		for(int i=0;i<lr.size();i++){
			ls.add(StringUtil.getEnglishEnding(i+1)+" - "+lr.get(i).user+" "+lr.get(i).getRating()+" Contest Count:"+lr.get(i).lre.size());
		}
		
		//JList
		JList<Object> jl=new JList<Object>(ls.toArray());
		JScrollPane jsp=new JScrollPane(jl);
		
		jl.addMouseListener(new MouseListener() {
			
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
				// TODO Auto-generated method stub
				JList<?> jl=(JList<?>)arg0.getComponent();
				if(jl.getSelectedIndex()==-1) return;
				new UserDisplay(lr.get(jl.getSelectedIndex()).user);
			}
		});

		jl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
//		

		//add to window
		this.add("North",my);
		this.add("Center",jsp);
		this.setSize(500,500);
		this.setVisible(true);
	}
}
