package com.hhs.xgn.hhsoj.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.text.html.CSS;

import com.hhs.xgn.hhsoj.server.community.rating.Rating;
import com.hhs.xgn.hhsoj.server.community.rating.RatingChanger;
import com.hhs.xgn.hhsoj.server.community.rating.RatingEntry;
import com.hhs.xgn.hhsoj.server.exception.BadClientRequestException;
import com.hhs.xgn.hhsoj.type.Blog;
import com.hhs.xgn.hhsoj.type.Contest;
import com.hhs.xgn.hhsoj.type.ContestResult;
import com.hhs.xgn.hhsoj.type.Problem;
import com.hhs.xgn.hhsoj.type.Submission;
import com.hhs.xgn.hhsoj.type.User;
import com.hhs.xgn.hhsoj.type.Verdict;
import com.hhs.xgn.hhsoj.util.ServerUtil;
import com.hhs.xgn.hhsoj.util.Value;

public class ServerThread extends Thread {
	Socket s;
	DataInputStream dis;
	DataOutputStream dos;
	
	public ServerThread(Socket s){
		this.s=s;
	}
	
	public void run(){
		try {
			System.out.println("Run client start");

			dis=new DataInputStream(s.getInputStream());
			dos=new DataOutputStream(s.getOutputStream());
			int mode=dis.readInt();
			
			switch(mode){
				case Value.checkPassword:
					checkPsd();
					break;
				case Value.mySubmission:
					mysub();
					break;
					
				case Value.submissionDetail:
					subdetail();
					break;
				
				case Value.contestDetail:
					contestDetail();
					break;
				case Value.allContestInfo:
					allcontest();
					break;
				case Value.downloadStatement:
					downloadStatement();
					break;
				
				case Value.submit:
					submit();
					break;
				
				case Value.contestSubmission:
					contestsub();
					break;
					
				case Value.contestStanding:
					conteststanding();
					break;
				
				case Value.registerContest:
					registerContest();
					break;
					
				case Value.pendRating:
					pendRatingChange();
					break;
				
				case Value.getRating:
					getRating();
					break;
					
				case Value.getAllUser:
					alluser();
					break;
					
				case Value.getBlogs:
					blogs();
					break;
				
				case Value.getAllBlogs:
					allblogs();
					break;
					
				case Value.submitBlog:
					submitBlog();
					break;
					
				case Value.time:
					time();
					break;
					
				default:
					throw new BadClientRequestException("Unknown command type:"+mode);
					
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void time() {
		try{
			dos.writeLong(System.currentTimeMillis());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	void submitBlog() {
		try{
			String title=dis.readUTF();
			String user=dis.readUTF();
			String content=dis.readUTF();
			
			File f=new File("blog/"+System.currentTimeMillis());
			PrintWriter pw=new PrintWriter(f);
			
			pw.println(title);
			pw.println(user);
			pw.println(content);
			
			pw.close();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	void allblogs() {
		try{
			File f=new File("blog");
			List<Blog> lb=new ArrayList<Blog>();
			
			for(String s:f.list()){
				lb.add(ServerUtil.readBlog(s));
			}
			
			dos.writeInt(lb.size());
			for(Blog b:lb){
				dos.writeUTF(b.title);
				dos.writeUTF(b.user);
				dos.writeUTF(b.content);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	void blogs() {
		try{
			String user=dis.readUTF();
			
			File blog=new File("blog");
			
			List<Blog> lb=new ArrayList<Blog>();
			
			for(String s:blog.list()){
				Blog b=ServerUtil.readBlog(s);
				
				if(b.user.equals(user)){
					lb.add(b);
				}
			}
			
			dos.writeInt(lb.size());
			
			
			for(Blog b:lb){
				dos.writeUTF(b.title);
				dos.writeUTF(b.user);
				dos.writeUTF(b.content);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}

	void alluser() {
		try{
			
			dos.writeInt(ServerInfo.users.size());
			for(User u:ServerInfo.users){
				dos.writeUTF(u.name);
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}

	void getRating() {
		try{
			String user=dis.readUTF();
			Rating r=ServerUtil.readRating(user);
			dos.writeInt(r.lre.size());
			for(RatingEntry re:r.lre){
				dos.writeUTF(re.contestName);
				dos.writeInt(re.score);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	void pendRatingChange() {
		try {
			String ct=dis.readUTF();
			String user=dis.readUTF();
			
			System.out.println("Waiting for judging all files");
			
			while(!ServerUtil.allSubmissionTested(user)){}
			
			System.out.println("Judged all files!");
			
			RatingChanger.changeRating(user,ct);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	void registerContest() {
		try{
			String con=dis.readUTF();
			String user=dis.readUTF();
			
			
			ContestResult cr=new ContestResult();
			cr.user=user;
			cr.score=0;
			File f=new File("standing/"+con+"/"+cr.user+".txt");
			if(f.exists()){
				dos.writeBoolean(false);
				return;
			}
			
			ServerUtil.writeContestResult(con,cr);
			
			dos.writeBoolean(true);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	void conteststanding() {
		try{
			String title=dis.readUTF();
			File f=new File("standing/"+title);
			
			List<ContestResult> lcr=new ArrayList<ContestResult>();
			
			for(String s:f.list()){
				ContestResult cr=ServerUtil.readContestResult(title,s);
				
				lcr.add(cr);
			}
			
			lcr.sort(new Comparator<ContestResult>() {

				@Override
				//-1 Smaller
				public int compare(ContestResult o1, ContestResult o2) {
					if(o1.score>o2.score){
						return -1;
					}
					
					if(o1.score<o2.score){
						return 1;
					}
					
					if(o1.user.compareTo(o2.user)<0){
						return -1;
					}else{
						return 1;
					}
				}
			});
			
			System.out.println(lcr);
			
			dos.writeInt(lcr.size());
			
			for(ContestResult cr:lcr){
				//user
				//score
				//m
				//p1 p2
				//p1 p2
				//p1 p2
				dos.writeUTF(cr.user);
				dos.writeInt(cr.score);
				dos.writeInt(cr.results.size());
				for(Entry<String,String> ess:cr.results.entrySet()){
					dos.writeUTF(ess.getKey());
					dos.writeUTF(ess.getValue());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	void contestsub() {
		try{
			String title=dis.readUTF();
			File f=new File("verdict");
			
			List<String> ls=new ArrayList<String>();
			
			for(String s:f.list()){
				Verdict v=ServerUtil.readVerdict(s);
				if(v.sub.cont.title.equals(title)){
					ls.add(v.sub.toStringEasy());
				}
			}
			
			dos.writeInt(ls.size());
			
			for(int i=0;i<ls.size();i++){
				dos.writeUTF(ls.get(i));
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	void submit(){
		//prog
		//cont_name
		//prob_id
		//user
		//pra
		try{
			String prog=dis.readUTF();
			String cont_name=dis.readUTF();
			int prob_id=dis.readInt();
			String user=dis.readUTF();
			boolean pra=dis.readBoolean();
			String lang=dis.readUTF();
			
			
			Submission s=new Submission();
			s.cont=ServerUtil.FindContestByName(cont_name);
			s.prob=prob_id;
			s.user=user;
			s.time=System.currentTimeMillis();
			s.prog=prog;
			s.lang=lang;
			
//			//First step, parse Language
//			
//			if(prog.indexOf("__CLASS_33_")!=-1){
//				//Java
//				//prog.replaceAll("__CLASS_33_", "//__CLASS_33_");
//				s.lang="Java";
//			}else{
//				if(prog.indexOf("#include")!=-1){
//					//C++
//					s.lang="C++";
//				}else{
//					s.lang="Python";
//				}
//			}
			
			System.out.println(s);
			
			//Then run!
			Thread t=new ThreadTesting(s,pra);
			t.start();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	void downloadStatement() {
		try{
			String title=dis.readUTF();
			File f=new File("contest/"+title);
			for(String s:f.list()){
				if(s.startsWith("statement")){
					
					dos.writeUTF(s);
					
					BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("contest/"+title+"/"+s)));
					List<Integer> li=new ArrayList<Integer>();
					int c;
					while((c=br.read())!=-1){
						li.add(c);
					}
					br.close();
					
					dos.writeInt(li.size());
					for(int i=0;i<li.size();i++){
						dos.writeByte(li.get(i));
					}
					
					return;
					
				}
			}
			
			throw new BadClientRequestException("Statement not found");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	void allcontest() {
		try{
			dos.writeInt(ServerInfo.contests.size());
			for(Contest c:ServerInfo.contests){
				dos.writeUTF(c.title);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	void contestDetail() {
		try{
			String name=dis.readUTF();
			for(Contest c:ServerInfo.contests){
				if(c.title.equals(name)){
					//Print time
					
					//title
					//start
					//end
					//length
					//problem count
					//{name,score}
					//problem names
					
					System.out.println("Sending:"+c);
					//dos.writeUTF(c.title);
					dos.writeLong(c.start);
					dos.writeLong(c.end);
					dos.writeLong(c.length);
					dos.writeInt(c.problems.size());
					for(Entry<String, Integer> ke:c.score.entrySet()){
						dos.writeUTF(ke.getKey());
						dos.writeInt(ke.getValue());
					}
					for(Problem p:c.problems){
						//title
						//tl
						//type
						//mode
						//Cppok
						//javaok
						//pythonok
						dos.writeUTF(p.title);
						dos.writeInt(p.tl);
						dos.writeInt(p.type);
						dos.writeInt(p.mode);
						dos.writeBoolean(p.CppOk);
						dos.writeBoolean(p.JavaOk);
						dos.writeBoolean(p.PythonOk);
					}
					
					return;
				}
			}
			
			throw new BadClientRequestException("Unknown contest name");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	void subdetail() {
		try {
			String tos=dis.readUTF();
			File f=new File("verdict");
			for(String s:f.list()){
				Verdict v=ServerUtil.readVerdict(s);
				if(v.sub.toStringEasy().equals(tos)){
					//time to print
					
					//User
					//Problem
					//Contest (name)
					//Language
					//Time
					//Code...
					//Verdict Lines
					//Verdict...
					dos.writeUTF(v.sub.user);
					dos.writeInt(v.sub.prob);
					dos.writeUTF(v.sub.cont.title);
					dos.writeUTF(v.sub.lang);
					dos.writeLong(v.sub.time);
					dos.writeUTF(v.sub.prog);
					dos.writeInt(v.tests.size());
					for(int i=0;i<v.tests.size();i++){
						dos.writeUTF(v.tests.get(i));
					}
					
					return;
				}
			}
			
			throw new BadClientRequestException("Unknown submission name");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	void p(String mes){
		System.out.println(mes);
	}
	
	void checkPsd(){
		try {
			String user=dis.readUTF();
			String psd=dis.readUTF();
			for(User s:ServerInfo.users){
				if(s.name.equals(user) && s.password.equals(psd)){
					dos.writeInt(1);
					return;
				}
			}
			
			dos.writeInt(0);
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
	}
	
	void mysub(){
		try{
			String user=dis.readUTF();
			
			List<String> ls=new ArrayList<String>();
			
			File f=new File("verdict");
			for(String s:f.list()){
				Verdict v=ServerUtil.readVerdict(s);
				if(v.sub.user.equals(user)){
					ls.add(v.sub.toStringEasy());
				}
			}
			
			dos.writeInt(ls.size());
			for(int i=0;i<ls.size();i++){
				dos.writeUTF(ls.get(i));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
