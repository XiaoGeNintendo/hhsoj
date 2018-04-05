package com.hhs.xgn.hhsoj.util;

public class TimeUtil {
	public static String getTime(long unix){
		String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(unix));  
	    return date; 
	}

	public static String minusTime(long t1, long t2) {
		if(t1<t2){
			long t=t1;
			t1=t2;
			t2=t;
		}
		
		long minus=t1-t2;
		
		return (minus/3600000)+"h"+(minus/60000)+"m"+(minus/1000)+"s."+(minus%1000);
	}
}
