package com.hhs.xgn.hhsoj.type;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * What's in a problem folder?? <br/>
 * 
 * Problem root folder: <br/>
 *     	info.txt <br/>
 * 		checker.exe <br/>
 * 		tests: <br/>
 * 			xxx.in <br/>
 * 			xxx.out <br/>
 * @author think
 *
 */
public class Problem {
	public String title;
	
	/**
	 * 0 - Normal <br/>
	 * 1 - Submit answer <br/>
	 */
	public int type;
	/**
	 * Time Limit. In ms
	 */
	public int tl; 
	
	/**
	 * 0 - ACM-ICPC <br/>
	 * 1 - IOI <br/>
	 */
	public int mode;
	
	public boolean JavaOk=true;
	public boolean CppOk=true;
	public boolean PythonOk=true;
	
	public boolean customChecker=false;
	
	public List<Test> tests=new ArrayList<Test>();
	
	public File customCheckerPos;

	@Override
	public String toString() {
		return "Problem [title=" + title + ", type=" + type + ", tl=" + tl + ", mode=" + mode + ", JavaOk=" + JavaOk
				+ ", CppOk=" + CppOk + ", PythonOk=" + PythonOk + ", customChecker=" + customChecker + ", tests="
				+ tests + ", customCheckerPos=" + customCheckerPos + "]";
	}
	
	
}
