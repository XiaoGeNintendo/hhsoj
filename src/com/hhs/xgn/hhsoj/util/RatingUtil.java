package com.hhs.xgn.hhsoj.util;

import java.awt.Color;

public class RatingUtil {

	public static Color getColor(int rating) {
		if(rating<=0){
			return Color.BLACK;
		}
		if(rating<=1200){
			return Color.gray;
		}
		if(rating<=1400){
			return Color.green;
		}
		if(rating<=1600){
			return Color.CYAN;
		}
		if(rating<=1900){
			return Color.blue;
		}
		if(rating<=2200){
			return new Color(128, 0,128); //purple
		}
		if(rating<=2400){
			return Color.YELLOW;
		}
		
		return Color.RED;
		
	}

	public static String getName(int rating) {
		if(rating<=0){
			return "Unrated";
		}
		if(rating<=1200){
			return "Newbie";
		}
		if(rating<=1400){
			return "Pupil";
		}
		if(rating<=1600){
			return "Specialist";
		}
		if(rating<=1900){
			return "Expert";
		}
		if(rating<=2200){
			return "Candidate Master";
		}
		if(rating<=2400){
			return "Master";
		}
		
		return "Grandmaster";
	}

}
