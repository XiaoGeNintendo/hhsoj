package com.hhs.xgn.hhsoj.util;

public class CompilerUtil {
	public static String getCompilerCommand(String lang,String abs){
		if(lang.equals("C++")){
			return "exit -1";
		}
		if(lang.equals("Java")){
			return "javac "+abs+"/__CLASS_33_.java";
		}
		if(lang.equals("Python")){
			return "exit";
		}
		return "exit -1";
	}

	public static String[] getRunCommand(String lang,String abs) {
		// TODO Auto-generated method stub
		if(lang.equals("C++")){
			return new String[]{"__CLASS_33_.exe"};
		}
		if(lang.equals("Java")){
			return new String[]{"java.exe ","__CLASS_33_"};
			
		}
		if(lang.equals("Python")){
			return new String[]{"python.exe","__CLASS_33_.py"};
			
		}
		return new String[]{"Unknown language"};
	}
}
