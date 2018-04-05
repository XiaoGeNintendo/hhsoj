package com.hhs.xgn.hhsoj.type;

import java.util.ArrayList;
import java.util.List;

public class Verdict {

	public int verdict;
	public Submission sub;
	/**
	 * What verdict are the tests?
	 */
	public List<String> tests=new ArrayList<String>();
	
	public Verdict() {
		this.sub=new Submission();
		
	}

	@Override
	public String toString() {
		return "Verdict [verdict=" + verdict + ", sub=" + sub + ", tests=" + tests + "]";
	}

}
