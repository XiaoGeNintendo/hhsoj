package com.hhs.xgn.hhsoj.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.hhs.xgn.hhsoj.server.ServerInfo;
import com.hhs.xgn.hhsoj.server.community.rating.Rating;
import com.hhs.xgn.hhsoj.server.community.rating.RatingEntry;
import com.hhs.xgn.hhsoj.type.Blog;
import com.hhs.xgn.hhsoj.type.Contest;
import com.hhs.xgn.hhsoj.type.ContestResult;
import com.hhs.xgn.hhsoj.type.Problem;
import com.hhs.xgn.hhsoj.type.Test;
import com.hhs.xgn.hhsoj.type.User;
import com.hhs.xgn.hhsoj.type.Verdict;

public class ServerUtil {
	public static void readUsers(){
		File f=new File("user");
		for(String s:f.list()){
			ServerInfo.users.add(readUser(s));
		}
	}
	
	public static User readUser(String s){
		try{
			
			User u=new User();
			u.name=s;
			
			File f=new File("user/"+s);
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			
			
			//File format: #1 - password
			u.password=br.readLine();
			
			//Read ok
			System.out.println("Read "+u);
			
			br.close();
			return u;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static void readContests(){
		File f=new File("contest");
		for(String s:f.list()){
			
			ServerInfo.contests.add(readContest(s));
		}
	}
	
	public static Contest readContest(String s){
		try{
			File f=new File("contest/"+s);
			Contest c=new Contest();
			
			//Read Information File First
			for(String fs:f.list()){
				if(fs.equals("info.txt")){
					c=readContestInfo(s,f.getAbsolutePath()+"/info.txt");
					break;
				}
			}
			
			
			for(String fs:f.list()){
				if(fs.equals("info.txt")){
					continue;
				}
				if(fs.length()>=9 && fs.substring(0, 9).equals("statement")){
					continue;
				}
				
				c.problems.add(readProblem(f.getAbsolutePath()+"/"+fs,fs));
			}
			
			System.out.println("Read: "+c);
			return c;
		}catch(Exception e){
			e.printStackTrace();
			return new Contest();
		}
	}
	
	
	public static Problem readProblem(String s,String na){
		Problem p=new Problem();
		File f=new File(s);
		File checker=new File(s+"/checker.exe");
		File info=new File(s+"/info.txt");
		p=readProblemInfo(info);
		
		p.title=na;
		
		if(checker.exists()){
			p.customChecker=true;
			p.customCheckerPos=checker;
		}
		
		File tests=new File(s+"/tests");
		for(String ts:tests.list()){
			if(ts.endsWith(".in")){
				p.tests.add(readTest(tests.getAbsolutePath()+"/"+ts));
			}
		}
		
		return p;
	}
	
	//i.in
	//0123
	public static Test readTest(String ip){
		
		try{
			File in=new File(ip);
			File out=new File(ip.substring(0,ip.length()-3)+".out");
			
			System.out.println(out.getName());
			
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(in)));
			
			
			List<Integer> l1=new ArrayList<Integer>();
			List<Integer> l2=new ArrayList<Integer>();
			
			int c1,c2;
			while((c1=br.read())!=-1){
				l1.add(c1);
			}
			
			br.close();
			
			BufferedReader br2=new BufferedReader(new InputStreamReader(new FileInputStream(out)));
			while((c2=br2.read())!=-1){
				l2.add(c2);
			}
			
			br2.close();
			
			//turn li -> string
			String s1="",s2="";
			for(int i=0;i<l1.size();i++){
				s1=s1+(char)(l1.get(i).intValue());
			}
			for(int i=0;i<l2.size();i++){
				s2=s2+(char)(l2.get(i).intValue());
			}
			
			Test t=new Test();
			t.input=s1;
			t.output=s2;
			return t;
			
		}catch(Exception e){
			e.printStackTrace();
			return new Test();
		}
	}
	//tl
	//type
	//mode
	//Cppok
	//javaok
	//pythonok
	
	public static Problem readProblemInfo(File f){
		try{
			Problem p=new Problem();
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			p.tl=Integer.parseInt(br.readLine());
			p.type=Integer.parseInt(br.readLine());
			p.mode=Integer.parseInt(br.readLine());
			p.CppOk=Integer.parseInt(br.readLine())==1;
			p.JavaOk=Integer.parseInt(br.readLine())==1;
			p.PythonOk=Integer.parseInt(br.readLine())==1;
			br.close();
			return p;
		}catch(Exception e){
			e.printStackTrace();
			return new Problem();
		}
	}
	//start
	//end
	//length
	//problem count
	//{name,score}
	
	public static Contest readContestInfo(String s,String p){
		try{
			Contest c=new Contest();
			c.title=s;
			
			File f=new File(p);
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			
			c.start=Long.parseLong(br.readLine());
			c.end=Long.parseLong(br.readLine());
			c.length=Long.parseLong(br.readLine());
			int n=Integer.parseInt(br.readLine());
			for(int i=0;i<n;i++){
				String sf=br.readLine();
				String[] ssf=sf.split(" ");
				c.score.put(ssf[0], Integer.parseInt(ssf[1]));
			}
			
			br.close();
			return c;
		}catch(Exception e){
			e.printStackTrace();
			return new Contest();
		}
		
	}

	//User
	//Problem
	//Contest
	//Language
	//Time
	//Code Lines
	//Code...
	//Verdict Lines
	//Verdict...
	public static Verdict readVerdict(String s) {
		try{
			Verdict v=new Verdict();
			
			File f=new File("verdict/"+s);
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			
			v.sub.user=br.readLine();
			v.sub.prob=Integer.parseInt(br.readLine());
			v.sub.cont=FindContestByName(br.readLine());
			v.sub.lang=br.readLine();
			v.sub.time=Long.parseLong(br.readLine());
			int n=Integer.parseInt(br.readLine());
			v.sub.prog="";
			
			for(int i=0;i<n;i++){
				v.sub.prog+=br.readLine()+"\n";
			}
			n=Integer.parseInt(br.readLine());
			for(int i=0;i<n;i++){
				v.tests.add(br.readLine());
			}
			
			br.close();
			return v;
		}catch(Exception e){
			e.printStackTrace();
			return new Verdict();
		}
	}

	public static Contest FindContestByName(String name) {
		for(Contest c:ServerInfo.contests){
			if(c.title.equals(name)){
				return c;
			}
		}
		return null;
	}

	//User
		//Problem
		//Contest
		//Language
		//Time
		//Code Lines
		//Code...
		//Verdict Lines
		//Verdict...
	
	public static void writeReport(String filename, Verdict v) {
		try{
			File f=new File("verdict/"+filename);
			PrintWriter pw=new PrintWriter(new FileOutputStream(f));
			pw.println(v.sub.user);
			pw.println(v.sub.prob);
			pw.println(v.sub.cont.title);
			pw.println(v.sub.lang);
			pw.println(v.sub.time);
			
			int cnt=0;
			for(int i=0;i<v.sub.prog.length();i++){
				if(v.sub.prog.charAt(i)=='\n'){
					cnt++;
				}
			}
			pw.println(cnt+1);
			pw.println(v.sub.prog);
			pw.println(v.tests.size());
			for(int i=0;i<v.tests.size();i++){
				pw.println(v.tests.get(i));
			}
			
			pw.close();
		}catch(Exception e){
			return;
		}
	}

	public static void initStanding() {
		for(Contest c:ServerInfo.contests){
			File f=new File("standing/"+c.title);
			if(f.exists()==false){
				f.mkdirs();
				
				System.out.println("Created standing folder for "+c.title);
			}
		}
	}

	public static ContestResult readContestResult(String title, String file) {
		//user
		//score
		//m
		//p1 p2
		//p1 p2
		//p1 p2
		
		try{
			ContestResult cr=new ContestResult();
			
			File f=new File("standing/"+title+"/"+file);
			
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			
			cr.user=br.readLine();
			cr.score=Integer.parseInt(br.readLine());
			int n=Integer.parseInt(br.readLine());
			
			for(int i=0;i<n;i++){
				String[] str=br.readLine().split(" ");
				cr.results.put(str[0], str[1]);
			}
			
			br.close();
			return cr;
		}catch(Exception e){
			return new ContestResult();
		}
	}

	public static void writeContestResult(String con, ContestResult cr) {
		//user
		//score
		//m
		//p1 p2
		//p1 p2
		//p1 p2
		
		try{
			File f=new File("standing/"+con+"/"+cr.user+".txt");
			
			PrintWriter pw=new PrintWriter(new FileOutputStream(f));
			pw.println(cr.user);
			pw.println(cr.score);
			pw.println(cr.results.size());
			
			for(Entry<String,String> ess:cr.results.entrySet()){
				pw.print(ess.getKey()+" ");
				pw.println(ess.getValue());
			}
			
			pw.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static Rating readRating(String user) {
		
		try{
			Rating r=new Rating(user);
			
			File f=new File("rating/"+user);
			
			if(f.exists()){
				BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(f)));
				String s="";
				while((s=br.readLine())!=null){
					int score=Integer.parseInt(br.readLine().trim());
					r.lre.add(new RatingEntry(s,score));
				}
				
				br.close();
				
				return r;
			}else{
				r.user=user;
				return r;
			}
		}catch(Exception e){
			e.printStackTrace();
			return new Rating(user);
		}
		
	}
	
	public static void writeRating(Rating r){
		try{
			File f=new File("rating/"+r.user);
			PrintWriter pw=new PrintWriter(new FileOutputStream(f));
			
			for(RatingEntry re:r.lre){
				pw.println(re.contestName);
				pw.println(re.score);
			}
			pw.close();
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}

	public static boolean allSubmissionTested(String user) {
		File f=new File("verdict");
		for(String fs:f.list()){
			Verdict v=readVerdict(fs);
			if(v.sub.user.equals(user)){
				if(v.tests.isEmpty() || v.tests.get(0).startsWith("WJ")){
					return false;
				}
			}
		}
		return true;
	}

	public static Blog readBlog(String s) {
		
		try{
			File f=new File("blog/"+s);
			
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			
			Blog b=new Blog();
			b.title=br.readLine();
			b.user=br.readLine();
			
			List<Character> lc=new ArrayList<Character>();
			
			int c;
			while((c=br.read())!=-1){
				lc.add((char) c);
			}
			
			
			b.content="";
			
			for(Character cc:lc){
				b.content+=cc;
			}
			
			br.close();
			return b;
		}catch(Exception e){
			e.printStackTrace();
			return new Blog();
		}
		
	}
}
