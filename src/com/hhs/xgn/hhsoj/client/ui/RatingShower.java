package com.hhs.xgn.hhsoj.client.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

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

public class RatingShower extends JFrame {
	Rating r;
	public RatingShower(Rating r){
		this.r=r;
		
		this.setTitle("Rating:"+r.user+" - "+ClientInfo.user);
		this.setLayout(new GridLayout(2, 1));
		
		//Chart
		JFreeChart jfc=ChartFactory.createXYLineChart("Rating Chart", "Time", "Rating", createDataset(),PlotOrientation.VERTICAL,true,true,false);
		
		ChartPanel cp = new ChartPanel( jfc );
		final XYPlot plot = jfc.getXYPlot( );
	    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
	    renderer.setSeriesPaint( 0 , Color.RED );
	    renderer.setSeriesPaint( 1 , Color.GREEN );
	    renderer.setSeriesStroke( 0 , new BasicStroke( 1.0f ) );
	    renderer.setSeriesStroke( 1 , new BasicStroke( 1.0f ) );
	    plot.setRenderer(renderer);
	    
	    //Table
	    List<String> ls=new ArrayList<String>();
	    
	    int ra=1500;
	    for(RatingEntry re:r.lre){
	    	int nr=RatingCalc.calc(ra, re);
	    	ls.add(re.contestName+": [Score]="+re.score+" [Rating]="+ra+"->"+nr+"("+(nr-ra)+")");
	    	ra=nr;
	    }
	    
	    JList<Object> jl=new JList<Object>(ls.toArray());
	    JScrollPane jsp=new JScrollPane(jl);
	    
	    this.add(cp);
	    this.add(jsp);
	    this.setSize(500,500);
	    this.setVisible(true);
	}
	
	private XYDataset createDataset(){
		XYSeries rating=new XYSeries("Rating");
		XYSeries score=new XYSeries("Score");
		
		score.add(1,1500);
		
		//System.out.println(r);
		
		int rn=1500;
		for(int i=0;i<r.lre.size();i++){
			rating.add(i+1, rn);
			
			rn=RatingCalc.calc(rn, r.lre.get(i));
			score.add(i+2, r.lre.get(i).score);
		}
		
		rn=RatingCalc.calc(rn, r.lre.get(r.lre.size()-1));
		rating.add(r.lre.size()+1,rn);
		
		XYSeriesCollection xysc=new XYSeriesCollection();
		xysc.addSeries(rating);
		xysc.addSeries(score);
		
		return xysc;
	}
}
