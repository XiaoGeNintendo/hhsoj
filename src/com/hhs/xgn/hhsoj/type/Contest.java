package com.hhs.xgn.hhsoj.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Folder formatting of Contest:<br/>
 * Contest: <br/>
 * 		statement.* <br/>
 * 		info.txt <br/>
 * 		problemA: <br/>
 * 			... <br/>
 * 		problemB: <br/>
 * 			...
 * @author think
 *
 */
public class Contest {
	public List<Problem> problems=new ArrayList<Problem>();
	
	/**
	 * The start/end time of this contest. <br/>
	 * Like USACO <br/>
	 * The user can choose a part of time taking part of it. <br/>
	 * gen this by ms
	 * 
	 */
	public long start,end;
	
	/**
	 * The length of the contest. <br/>
	 * in ms. 
	 */
	public long length;
	
	public String title;
	
	/**
	 * The score of the problems. {name,score}
	 */
	public Map<String,Integer> score=new HashMap<String,Integer>();

	@Override
	public String toString() {
		return "Contest [problems=" + problems + ", start=" + start + ", end=" + end + ", length=" + length + ", title="
				+ title + ", score=" + score + "]";
	} 
	
	
}
