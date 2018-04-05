package com.hhs.xgn.hhsoj.type;

import com.hhs.xgn.hhsoj.util.StringUtil;

public class Blog {
	public String title;
	public String user;
	public String content;
	
	public String toString(){
		return "Blog:\""+title+"\" by "+user+" -- "+StringUtil.shorten(content,30);
		
	}
}
