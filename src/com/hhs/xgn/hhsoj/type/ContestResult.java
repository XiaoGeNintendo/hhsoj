package com.hhs.xgn.hhsoj.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contest Result class is for Contest Rank
 * @author XGN	
 *
 */
public class ContestResult {
	public String user;
	public int score;
	public Map<String,String> results=new HashMap<String,String>();
	@Override
	public String toString() {
		return "ContestResult [user=" + user + ", score=" + score + ", results=" + results + "]";
	}
	
}
