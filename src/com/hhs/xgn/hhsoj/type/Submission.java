package com.hhs.xgn.hhsoj.type;

public class Submission {

	public String user="";
	public String prog="";
	public String lang="";
	/**
	 * The problem id in the contest
	 */
	public int prob;
	public Contest cont=new Contest();
	public long time;
	@Override
	public String toString() {
		return "Submission [user=" + user + ", prog=" + prog + ", lang=" + lang + ", prob=" + prob + ", cont=" + cont
				+ ", time=" + time + "]";
	}
	public String toStringEasy() {
		return "Submission [user=" + user + ", lang=" + lang + ", cont="+cont.title+", prob="+cont.problems.get(prob).title +", time=" + time + "]";
	}
	
	
}
