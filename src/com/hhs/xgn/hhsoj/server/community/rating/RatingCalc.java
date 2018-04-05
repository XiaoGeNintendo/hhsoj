package com.hhs.xgn.hhsoj.server.community.rating;

public class RatingCalc {
	public static int calc(int rating,RatingEntry re){
		return (rating*6+re.score*7)/13;
	}
}
