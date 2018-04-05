package com.hhs.xgn.hhsoj.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.hhs.xgn.hhsoj.type.Contest;
import com.hhs.xgn.hhsoj.type.ContestResult;
import com.hhs.xgn.hhsoj.type.Problem;
import com.hhs.xgn.hhsoj.type.Submission;
import com.hhs.xgn.hhsoj.type.Test;
import com.hhs.xgn.hhsoj.type.Verdict;
import com.hhs.xgn.hhsoj.util.CmdUtil;
import com.hhs.xgn.hhsoj.util.CompilerUtil;
import com.hhs.xgn.hhsoj.util.ServerUtil;

public class ThreadTesting extends Thread {
	Submission s;
	Verdict v;
	boolean pra;
	
	public ThreadTesting(Submission s,boolean pra){
		this.s=s;
		this.pra=pra;
		
	}
	
	public void run(){
		try{
			System.out.println("[JUDGING]"+s.time+".txt");
			
			//Then create the verdict
			v=new Verdict();
			v.sub=s;
			v.verdict=0;
			
			//Add verdict basic information
			
			//Get the data
			Contest c=s.cont;
			Problem p=c.problems.get(s.prob);
			
			//Add a non-judged file
			v.tests.add("WJ");
			ServerUtil.writeReport(s.time+".txt", v);
			v.tests.clear();
			
			//Create a temp program file
			File folder=new File("runtime"+s.time);
			String fs="runtime"+s.time;
			folder.mkdirs();
			
			PrintWriter pw=new PrintWriter(new FileOutputStream(fs+"/__CLASS_33_"+(s.lang.equals("Java")?".java": (s.lang.equals("C++")?".cpp":".py") ) ));
			
			pw.println(s.prog);
			pw.close();
			
			//See if language supports
			if(s.lang.equals("Java") && !p.JavaOk){
				v.tests.add("FAIL:Java can't be used in this problem.");
				ServerUtil.writeReport(s.time+".txt",v);
				return;
			}
			if(s.lang.equals("C++") && !p.CppOk){
				v.tests.add("FAIL:C++ can't be used in this problem.");
				ServerUtil.writeReport(s.time+".txt",v);
				return;
			}
			if(s.lang.equals("Python") && !p.PythonOk){
				v.tests.add("FAIL:Python can't be used in this problem.");
				ServerUtil.writeReport(s.time+".txt",v);
				return;
			}
			
			//Create the Compile cmd
			PrintWriter pw2=new PrintWriter(new FileOutputStream(fs+"/compile.cmd"));
			pw2.println(CompilerUtil.getCompilerCommand(s.lang,folder.getAbsolutePath()));
			pw2.close();
			
			//Run compiler
			File ft=new File(fs+"/compile.cmd");
			String va=CmdUtil.runCompiler(ft.getAbsolutePath());
			if(va.startsWith("CE")){
				System.out.println("Compiler : "+va);
				v.tests.add(va);
				
				ServerUtil.writeReport(s.time+".txt",v);
				return;
			}
			
			//Create the run cmd
			//In fact useless these days
			PrintWriter pw3=new PrintWriter(new FileOutputStream(fs+"/run.cmd"));
			
			
			pw3.println(CompilerUtil.getRunCommand(s.lang,folder.getAbsolutePath()));
			pw3.close();
			
			//Run way
			File f=new File(fs+"/run.cmd");
			
			
			//For contest users. We need to set if it got AC.
			boolean ac=true;
			
			//Then test the program!!
			long mx=0; //the max time cost
			
			for(Test t:p.tests){
				
				//Putting text into in.txt
				PrintWriter in=new PrintWriter(new FileOutputStream(fs+"/in.txt"));
				in.println(t.input);
				in.close();
				
				
				//Process pro=Runtime.getRuntime().exec("\""+f.getAbsolutePath()+"\"");
				ProcessBuilder pb=new ProcessBuilder(CompilerUtil.getRunCommand(s.lang,folder.getAbsolutePath()));
				//ProcessBuilder pb=new ProcessBuilder("python.exe __CLASS_33_.py ");
				
				//ProcessBuilder pb=new ProcessBuilder("python.exe","__CLASS_33_.py");
				
				
				pb=pb.directory(folder);
				
				File outfile=new File(fs+"/out.txt");
				File infile=new File(fs+"/in.txt");
				
				pb=pb.redirectError(outfile);
				pb=pb.redirectInput(infile);
				pb=pb.redirectOutput(outfile);
				
				//System.out.println(pb.directory().getAbsolutePath());
				
				
				//pb.directory(directory)
				//Process pro=Runtime.getRuntime().exec(""+CompilerUtil.getRunCommand(s.lang,folder.getAbsolutePath()));
				Process pro=pb.start();
				
				
				long now=System.currentTimeMillis();
				
				//TLE FIGHTER
				boolean tle=pro.waitFor(p.tl, TimeUnit.MILLISECONDS);
				if(tle==false){
					pro.destroyForcibly();
					
					//System.out.println(pro.isAlive());
					
					v.tests.add("TLE");
					
					mx=p.tl;
					ac=false;
					if(p.mode==0){
						break;
					}
					
					continue;
				}
				
				
				//pro.destroyForcibly();
				//System.out.println(pro.exitValue());
				
				long now2=System.currentTimeMillis();
				
				//RE Checker
				if(pro.exitValue()!=0){
					v.tests.add("RE:"+pro.exitValue());
					
					mx=Math.max(now2-now, mx);
					ac=false;
					if(p.mode==0){
						break;
					}
					
					continue;
				}
				
				//Output
				
				BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(outfile)));
				
				String build="";
				int cj;
				while((cj=br.read())!=-1){
					build+=(char)cj;
				}
				
				br.close();
				
				
				
				
				//Checking
				if(p.customChecker){
					//Custom Checker
					Process proc=Runtime.getRuntime().exec("cmd /c "+p.customCheckerPos);
					proc.waitFor();
					if(proc.exitValue()==0){
						v.tests.add("AC:"+(now2-now)+"ms");
						
						mx=Math.max(now2-now, mx);
						
					}else{
						v.tests.add("WA");
						ac=false;
						mx=Math.max(now2-now, mx);
						if(p.mode==0){
							break;
						}
					}
				}else{
					//No custom checker
					build=build.trim();
					t.output=t.output.trim();
					if(build.equals(t.output)){
						v.tests.add("AC:"+(now2-now)+"ms");
						
						mx=Math.max(now2-now, mx);
					}else{
						v.tests.add("WA");
						ac=false;
						mx=Math.max(now2-now, mx);
						
						System.out.println(build+" ///// "+t.output);
						
						if(p.mode==0){
							break;
						}
					}
				}
				
			}
			
			v.tests.add("===");
			v.tests.add("Time Cost:"+mx+"ms");
			v.tests.add("Code length:"+v.sub.prog.length()+"b");
			
			if(!pra){
				//Contestant submit!!
				//Read contest result anyway
				ContestResult cr=ServerUtil.readContestResult(c.title, s.user+".txt");
				
				System.out.println("Now check contestant information");
				
				if(p.mode==1){
					//IOI
					
					System.out.println("[CONTEST]IOI indeed.");
					//First, count how many AC do we have??
					
					int acc=0;
					for(int i=0;i<v.tests.size();i++){
						if(v.tests.get(i).startsWith("AC")){
							acc++;
						}
					}
					
					//Then given variables
					int tot=v.tests.size()-3;
					int scr=c.score.get(p.title);
					int nows=Integer.parseInt((cr.results.get(p.title)=="" || cr.results.get(p.title)==null ?"0":cr.results.get(p.title)));
					int wes=(int) (scr*(acc/(double)tot));
					if(wes<nows){
						//Worse than last time. do nothing
						
					}else{
						
						System.out.println("[CONTEST]wes="+wes+"nows="+nows);
						
						//Better than last time!!
						cr.results.put(p.title, wes+"");
						cr.score+=wes-nows;
						
						//Put it back
						ServerUtil.writeContestResult(c.title, cr);
					}
					
					v.tests.add("Score:"+wes);
					
				}else{
					//ACM
					
					System.out.println("[CONTEST]ACM indeed.");
					//did this problem has been ACed.
					
					if(cr.results.get(p.title)!=null && cr.results.get(p.title).startsWith("+")){
						//Don't care about anything.
						
					}else{
					
						if(ac){
							System.out.println("[CONTEST]ac indeed.");
							
							//good job. ACed.
							String nowresult=cr.results.get(p.title);
							
							//how many times we did wrong??
							int count=(nowresult=="" || nowresult==null ?0:Integer.parseInt(nowresult.substring(1)));
							
							//The score equals to TOT-50*count
							int tot=c.score.get(p.title);
							int scr=Math.max(0, tot-50*count);
							
							cr.results.put(p.title,"+"+count);
							cr.score+=scr;
							
							//Write
							ServerUtil.writeContestResult(c.title, cr);
						}else{
							System.out.println("[CONTEST]not ac indeed.");
							//En?? not ac.
							
							//Count wa times
							String nowresult=cr.results.get(p.title);
							int count=(nowresult=="" || nowresult==null ?0:Integer.parseInt(nowresult.substring(1)));
							
							//Write it back
							cr.results.put(p.title, "-"+(count+1));
							
							ServerUtil.writeContestResult(c.title, cr);
						}
						
						v.tests.add("Accepted:"+ac);
					}
				}
			}
			
			//Close folder
			for(File ff:folder.listFiles()){
				ff.delete();
				System.out.println("[TESTING THREAD]Deleted: "+ff);
			}
			
			folder.delete();
			
			System.out.println("Finish excuting.");
			ServerUtil.writeReport(s.time+".txt",v);
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
