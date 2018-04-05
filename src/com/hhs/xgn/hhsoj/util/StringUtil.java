package com.hhs.xgn.hhsoj.util;

public class StringUtil {
	public static String getEnglishEnding(int x){
		    x %= 100;
		    if (x / 10 == 1)
		        return x+"th";
		    if (x % 10 == 1)
		        return x+"st";
		    if (x % 10 == 2)
		        return x+"nd";
		    if (x % 10 == 3)
		        return x+"rd";
		    return x+"th";
		
	}

	public static String shorten(String content,int length) {
		if(content.length()<=length){
			return content;
		}
		
		if(length<=3){
			throw new IllegalArgumentException("Length must be bigger than 3!! You foolish operator!!");
		}
		
		return content.substring(0, length-3)+"...";
	}
}
