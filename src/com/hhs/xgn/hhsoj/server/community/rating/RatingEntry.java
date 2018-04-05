package com.hhs.xgn.hhsoj.server.community.rating;

public class RatingEntry {
	public String contestName;
	public int score;
	
	public RatingEntry(){}
	
	public RatingEntry(String arg0,int arg1){
		contestName=arg0;
		score=arg1;
	}

	@Override
	public String toString() {
		return "RatingEntry [contestName=" + contestName + ", score=" + score + "]";
	}
	
	
}
