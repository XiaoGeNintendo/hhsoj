package com.hhs.xgn.hhsoj.server.community.rating;

import java.util.ArrayList;
import java.util.List;

public class Rating {
	public String user;
	public List<RatingEntry> lre=new ArrayList<RatingEntry>();
	
	public Rating(){
		
	}
	
	public Rating(String s){
		user=s;
	}
	
	public int getRating(){
		int rating=1500;
		for(RatingEntry re:lre){
			rating=RatingCalc.calc(rating,re);
		}
		return rating;
	}
	
	public int getMaxRating(){
		int rating=1500;
		int mx=0;
		for(RatingEntry re:lre){
			rating=RatingCalc.calc(rating,re);
			mx=Math.max(mx, rating);
		}
		return mx;
	}

	@Override
	public String toString() {
		return "Rating [user=" + user + ", lre=" + lre + "]";
	}
	
}
