package com.hhs.xgn.hhsoj.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class CmdUtil {
	
	/**
	 * Run the given cmd command. with "cmd /c". <br/>
	 * You can set wait or not. If not wait. The exit status can only be 0.
	 * @param com
	 * @return the exit status. -1 if the JVM breaks down
	 */
	public static int runCmd(String com,boolean wait){
		try {
			Process pr=Runtime.getRuntime().exec("cmd /c \""+com+"\"");
			if(wait){
				pr.waitFor();
				return pr.exitValue();
			}else{
				return 0;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	public static String runCompiler(String pos) {
		try{
			Process pro=Runtime.getRuntime().exec("cmd /c "+pos);
			
			//System.out.println("cmd /c "+pos);
			
			boolean ans=pro.waitFor(30, TimeUnit.SECONDS);
			if(ans==false){
				pro.destroy();
				return "CE:Compile timeout.";
			}
			
			pro.destroy();
			if(pro.exitValue()==0){
				return "OK:Compile pass.";
			}else{
				return "CE:Compile failed.";
			}
		}catch(Exception e){
			e.printStackTrace();
			return "CE:Fail to compile";
		}
	}
}
