package com.hhs.xgn.hhsoj.server.community.rating;

import com.hhs.xgn.hhsoj.util.ServerUtil;

public class RatingChanger {

	public static void changeRating(String user, String ct) {
		Rating r=ServerUtil.readRating(user);
		
		System.out.println("Now rating before="+r);
		
		r.lre.add(new RatingEntry(ct, ServerUtil.readContestResult(ct, user+".txt").score));
		
		System.out.println("Now rating after="+r);
		
		
		ServerUtil.writeRating(r);
		
	}
	 
}
