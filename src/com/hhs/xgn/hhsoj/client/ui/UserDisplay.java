package com.hhs.xgn.hhsoj.client.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.hhs.xgn.hhsoj.client.ClientInfo;
import com.hhs.xgn.hhsoj.server.community.rating.Rating;
import com.hhs.xgn.hhsoj.server.community.rating.RatingCalc;
import com.hhs.xgn.hhsoj.server.community.rating.RatingEntry;
import com.hhs.xgn.hhsoj.type.Blog;
import com.hhs.xgn.hhsoj.type.Contest;
import com.hhs.xgn.hhsoj.util.Net;
import com.hhs.xgn.hhsoj.util.RatingUtil;

public class UserDisplay extends JFrame {
	Rating r;
	UserDisplay self=this;
	String name;
	List<Blog> blog=new ArrayList<Blog>();
	
	public UserDisplay(String user){
		
		//Get his rating
		r=Net.getRating(user);
		blog=Net.getBlogs(user);
		
		
		//Basic information
		this.setTitle("User:"+user+" - "+ClientInfo.user);
		this.setLayout(new GridLayout(4, 1));
		
		//Add a name textarea
		JTextField name=new JTextField(user+"("+RatingUtil.getName(r.getRating())+")");
		name.setEditable(false);
		name.setForeground(RatingUtil.getColor(r.getRating()));
		
		//Blog textarea
		
		JList<Object> blogl=new JList<Object>(blog.toArray());
		blogl.setBorder(BorderFactory.createTitledBorder("Blogs"));
		
		blogl.addMouseListener(new MouseListener() {
			
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
				JList<?> jl=(JList<?>)arg0.getComponent();
				if(jl.getSelectedIndex()==-1) return;
				Blog b=blog.get(jl.getSelectedIndex());
				new ViewBlog(b);
			}
		});
		

		blogl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane jsp=new JScrollPane(blogl);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	
		//Rating Textarea
		JList<Object> ratingl=new JList<Object>(r.lre.toArray());
		ratingl.setBorder(BorderFactory.createTitledBorder("Contests"));


		ratingl.addMouseListener(new MouseListener() {
			
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
				
				Contest t=Net.readContestDetail(r.lre.get(jl.getSelectedIndex()).contestName);
				
				long now=Net.currentTimeMillis();
				
				
				if(now<t.end){
					JOptionPane.showMessageDialog(null, "Can't practise contest.The contest has not finished","Can't practise contest",JOptionPane.WARNING_MESSAGE);
				}else{
					new ContestPractice(t);
				}
			}
		});
		

		ratingl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane jsp2=new JScrollPane(ratingl);
		jsp2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
//		
		//Rating Graph
		JButton torating=new JButton("Show Rating Information");
		torating.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO to Rating
				if(self.r.lre.size()==0){
					JOptionPane.showMessageDialog(null, "This user is unrated yet.","No rating",JOptionPane.WARNING_MESSAGE);
					return;
				}
				new RatingShower(self.r);
			}
		});
		
		
		
	    this.add(name);
	    this.add(jsp);
	    this.add(jsp2);
	    this.add(torating);
	    
	    
	    this.setSize(500,500);
	    
	    this.setVisible(true);
	}
	
	
}
