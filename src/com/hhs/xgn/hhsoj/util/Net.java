package com.hhs.xgn.hhsoj.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JOptionPane;

import com.hhs.xgn.hhsoj.client.ClientInfo;
import com.hhs.xgn.hhsoj.client.task.TaskUtil;
import com.hhs.xgn.hhsoj.server.community.rating.Rating;
import com.hhs.xgn.hhsoj.server.community.rating.RatingCalc;
import com.hhs.xgn.hhsoj.server.community.rating.RatingEntry;
import com.hhs.xgn.hhsoj.type.Blog;
import com.hhs.xgn.hhsoj.type.Contest;
import com.hhs.xgn.hhsoj.type.ContestResult;
import com.hhs.xgn.hhsoj.type.Problem;
import com.hhs.xgn.hhsoj.type.Submission;
import com.hhs.xgn.hhsoj.type.Verdict;

/**
 * For net conversation between server and client.
 * @author XGN
 *
 */
public class Net {
	/**
	 * 0 - exception occurred
	 * 1 - bad
	 * 2 - ok
	 * @param user
	 * @param pass
	 * @return
	 */
	public static int checkUser(String user,String pass){
		try {
			Socket s=new Socket(ClientInfo.ip, ClientInfo.port);
			DataInputStream dis=new DataInputStream(s.getInputStream());
			DataOutputStream dos=new DataOutputStream(s.getOutputStream());
			
			dos.writeInt(Value.checkPassword);
			dos.writeUTF(user);
			dos.writeUTF(pass);
			
			int ans=dis.readInt();
			
			s.close();
			if(ans==0){
				return 1;
			}else{
				return 2;
			}
		} catch (Exception e){
			
			e.printStackTrace();
			return 0;
		}
		
	}

	public static String[] mySubmission(String user) {
		try {
			Socket s=new Socket(ClientInfo.ip,ClientInfo.port);
			DataInputStream dis=new DataInputStream(s.getInputStream());
			DataOutputStream dos=new DataOutputStream(s.getOutputStream());
			dos.writeInt(Value.mySubmission);
			dos.writeUTF(ClientInfo.user);
			
			int n=dis.readInt();
			String[] sa=new String[n];
			for(int i=0;i<n;i++){
				sa[i]=dis.readUTF();
			}
			
			s.close();
			return sa;
		} catch(Exception e){
			return new String[]{"Fail to get my submissions."};
		}
		
	}

	public static Verdict getSubmissionDetail(String toS) {
		try{
			Socket s=new Socket(ClientInfo.ip,ClientInfo.port);
			DataInputStream dis=new DataInputStream(s.getInputStream());
			DataOutputStream dos=new DataOutputStream(s.getOutputStream());
			dos.writeInt(Value.submissionDetail);
			dos.writeUTF(toS);
			
			Submission sb=new Submission();
			
			//User
			//Problem
			//Contest (name)
			//Language
			//Time
			//Code...
			//Verdict Lines
			//Verdict...
			
			sb.user=dis.readUTF();
			sb.prob=dis.readInt();
			String name=dis.readUTF();
			sb.lang=dis.readUTF();
			sb.time=dis.readLong();
			sb.prog=dis.readUTF();
			
			Verdict v=new Verdict();
			v.sub=sb;
			
			int n=dis.readInt();
			for(int i=0;i<n;i++){
				v.tests.add(dis.readUTF());
			}
			
			sb.cont=readContestDetail(name);
			
			s.close();
			return v;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Fail to get submission detail!\n"+e,"Error",JOptionPane.ERROR_MESSAGE);
			return new Verdict();
		}
	}

	public static Contest readContestDetail(String name) {
		try{
			Socket s=new Socket(ClientInfo.ip,ClientInfo.port);
			DataInputStream dis=new DataInputStream(s.getInputStream());
			DataOutputStream dos=new DataOutputStream(s.getOutputStream());
			dos.writeInt(Value.contestDetail);
			dos.writeUTF(name);
			//title
			//start
			//end
			//length
			//problem count
			//{name,score}
			//problem names
			
			Contest c=new Contest();
			c.title=name;
			
			c.start=dis.readLong();
			c.end=dis.readLong();
			c.length=dis.readLong();
			int n=dis.readInt();
			for(int i=0;i<n;i++){
				String a=dis.readUTF();
				int b=dis.readInt();
				c.score.put(a, b);
			}
			
			for(int i=0;i<n;i++){
				//title
				//tl
				//type
				//mode
				//Cppok
				//javaok
				//pythonok
				
				Problem p=new Problem();
				p.title=dis.readUTF();
				p.tl=dis.readInt();
				p.type=dis.readInt();
				p.mode=dis.readInt();
				p.CppOk=dis.readBoolean();
				p.JavaOk=dis.readBoolean();
				p.PythonOk=dis.readBoolean();
				
				c.problems.add(p);
			}
			
			s.close();
			return c;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Fail to read contest!\n"+e,"Error",JOptionPane.ERROR_MESSAGE);
			return new Contest();
		}

	}

	@Deprecated
	public static Problem readProblemDetail() {
		try{
			Socket s=new Socket(ClientInfo.ip,ClientInfo.port);
			DataInputStream dis=new DataInputStream(s.getInputStream());
			DataOutputStream dos=new DataOutputStream(s.getOutputStream());
			dos.writeInt(Value.contestDetail);
			dos.writeUTF("");
			
			//tl
			//type
			//mode
			//Cppok
			//javaok
			//pythonok
			
			Problem p=new Problem();
			p.tl=dis.readInt();
			p.type=dis.readInt();
			p.mode=dis.readInt();
			p.CppOk=dis.readBoolean();
			p.JavaOk=dis.readBoolean();
			p.PythonOk=dis.readBoolean();
			
			s.close();
			return p;
		}catch(Exception e){
			
			return new Problem();
		}
		
	}

	public static List<Contest> readAllContests() {
		try{
			Socket s=new Socket(ClientInfo.ip,ClientInfo.port);
			DataInputStream dis=new DataInputStream(s.getInputStream());
			DataOutputStream dos=new DataOutputStream(s.getOutputStream());
			dos.writeInt(Value.allContestInfo);
			
			int n=dis.readInt();
			List<String> ls=new ArrayList<String>();
			
			for(int i=0;i<n;i++){
				ls.add(dis.readUTF());
			}
			
			List<Contest> lc=new ArrayList<Contest>();
			for(int i=0;i<n;i++){
				lc.add(readContestDetail(ls.get(i)));
			}
			
			s.close();
			return lc;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Fail to get contest!\n"+e,"Error",JOptionPane.ERROR_MESSAGE);
			return new ArrayList<Contest>();
		}
		
	}

	public static void downloadStatement(String title) {
		try{
			Socket s=new Socket(ClientInfo.ip,ClientInfo.port);
			DataInputStream dis=new DataInputStream(s.getInputStream());
			DataOutputStream dos=new DataOutputStream(s.getOutputStream());
			dos.writeInt(Value.downloadStatement);
			dos.writeUTF(title);
			
			String name=dis.readUTF();
			int n=dis.readInt();
			
			System.out.println("Total size:"+n);
			
			DataOutputStream dos2f=new DataOutputStream(new FileOutputStream("local/"+title+"/"+name));
			
			for(int i=0;i<n;i++){
				dos2f.writeByte(dis.readByte());
			}
			
			
			dos2f.close();
			s.close();
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Fail to download statement!\n"+e,"Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
	}

	public static void submitCode(String text,String cont_name,int prob_id,boolean p,String lang) {
		try{
			Socket s=new Socket(ClientInfo.ip,ClientInfo.port);
			DataOutputStream dos=new DataOutputStream(s.getOutputStream());
			dos.writeInt(Value.submit);
			//prog
			//cont_name
			//prob_id
			//user
			//pra
			dos.writeUTF(text);
			dos.writeUTF(cont_name);
			dos.writeInt(prob_id);
			dos.writeUTF(ClientInfo.user);
			dos.writeBoolean(p);
			dos.writeUTF(lang);
			
			s.close();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Fail to submit code!\n"+e,"Error",JOptionPane.ERROR_MESSAGE);
		}
		
	}

	public static List<Verdict> readContestSubmissions(Contest c) {
		try{
			Socket s=new Socket(ClientInfo.ip,ClientInfo.port);
			DataInputStream dis=new DataInputStream(s.getInputStream());
			DataOutputStream dos=new DataOutputStream(s.getOutputStream());
			dos.writeInt(Value.contestSubmission);
			dos.writeUTF(c.title);
			
			//Not name!! It's toS
			List<String> ls=new ArrayList<String>();
			
			int n=dis.readInt();
			for(int i=0;i<n;i++){
				ls.add(dis.readUTF());
			}
			
			List<Verdict> lv=new ArrayList<Verdict>();
			
			for(int i=0;i<ls.size();i++){
				lv.add(getSubmissionDetail(ls.get(i)));
			}
			
			s.close();
			return lv;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Fail to get submission!\n"+e,"Error",JOptionPane.ERROR_MESSAGE);
			return new ArrayList<Verdict>();
		}
	}

	public static List<ContestResult> getContestStanding(Contest c) {
		try{
			Socket s=new Socket(ClientInfo.ip,ClientInfo.port);
			DataInputStream dis=new DataInputStream(s.getInputStream());
			DataOutputStream dos=new DataOutputStream(s.getOutputStream());
			dos.writeInt(Value.contestStanding);
			dos.writeUTF(c.title);
			
			List<ContestResult> lcr=new ArrayList<ContestResult>();
			
			int n=dis.readInt();
			for(int i=0;i<n;i++){
				
				//user
				//score
				//m
				//p1 p2
				//p1 p2
				//p1 p2
				
				ContestResult cr=new ContestResult();
				cr.user=dis.readUTF();
				cr.score=dis.readInt();
				int m=dis.readInt();
				for(int j=0;j<m;j++){
					String a,b;
					a=dis.readUTF();
					b=dis.readUTF();
					cr.results.put(a,b);
				}
				
				lcr.add(cr);
			}
			
			s.close();
			return lcr;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Fail to get contest standing!\n"+e,"Error",JOptionPane.ERROR_MESSAGE);
			return new ArrayList<ContestResult>();
		}
		
	}

	public static boolean registerContest(Contest c) {
		try{
			Socket s=new Socket(ClientInfo.ip,ClientInfo.port);
			DataInputStream dis=new DataInputStream(s.getInputStream());
			DataOutputStream dos=new DataOutputStream(s.getOutputStream());
			dos.writeInt(Value.registerContest);
			dos.writeUTF(c.title);
			dos.writeUTF(ClientInfo.user);
			
			boolean b=dis.readBoolean();
			
			s.close();
			return b;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Fail to register contest!\n"+e,"Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	public static void pendRatingChange(Contest c) {
		try{
			Socket s=new Socket(ClientInfo.ip,ClientInfo.port);
			
			DataOutputStream dos=new DataOutputStream(s.getOutputStream());
			dos.writeInt(Value.pendRating);
			
			dos.writeUTF(c.title);
			dos.writeUTF(ClientInfo.user);
			
			
			s.close();
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Fail to pend rating changes!!\n"+e,"Error",JOptionPane.ERROR_MESSAGE);
			System.out.println("Rating change failed. Add to user task list.");
			
			TaskUtil.addTask(1,c.title);
		}
		
	}

	public static Rating getRating(String user) {
		try{
			Socket s=new Socket(ClientInfo.ip,ClientInfo.port);
			DataInputStream dis=new DataInputStream(s.getInputStream());
			DataOutputStream dos=new DataOutputStream(s.getOutputStream());
			dos.writeInt(Value.getRating);
			
			dos.writeUTF(user);
			
			Rating r=new Rating(user);
			int p=dis.readInt();
			
			for(int i=0;i<p;i++){
				String a=dis.readUTF();
				int b=dis.readInt();
				r.lre.add(new RatingEntry(a,b));
			}
			
			
			s.close();
			
			System.out.println(r);
			
			return r;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Fail to get rating\n"+e,"Error",JOptionPane.ERROR_MESSAGE);
			return new Rating(user);
		}
	}

	public static List<Blog> getBlogs(String user) {
		try{
			Socket s=new Socket(ClientInfo.ip,ClientInfo.port);
			DataInputStream dis=new DataInputStream(s.getInputStream());
			DataOutputStream dos=new DataOutputStream(s.getOutputStream());
			dos.writeInt(Value.getBlogs);
			
			dos.writeUTF(user);
			
			int cnt=dis.readInt();
			
			List<Blog> lb=new ArrayList<Blog>();
			
			for(int i=0;i<cnt;i++){
				Blog b=new Blog();
				b.title=dis.readUTF();
				b.user=dis.readUTF();
				b.content=dis.readUTF();
				
				lb.add(b);
			}
			
			return lb;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Fail to get blogs\n"+e,"Error",JOptionPane.ERROR_MESSAGE);
			return new ArrayList<Blog>();
		}
		
	}

	public static List<String> getAllUser(){
		try{
			Socket s=new Socket(ClientInfo.ip,ClientInfo.port);
			DataInputStream dis=new DataInputStream(s.getInputStream());
			DataOutputStream dos=new DataOutputStream(s.getOutputStream());
			dos.writeInt(Value.getAllUser);
			
			int cnt=dis.readInt();
			
			List<String> ls=new ArrayList<String>();
			
			for(int i=0;i<cnt;i++){
				ls.add(dis.readUTF());
			}
			
			return ls;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Fail to get all users\n"+e,"Error",JOptionPane.ERROR_MESSAGE);
			return new ArrayList<String>();
		}
	}
	public static List<Rating> getGlobalRating() {
		try{
			List<String> ls=getAllUser();
			
			List<Rating> lr=new ArrayList<Rating>();
			
			for(String s:ls){
				lr.add(getRating(s));
			}
			
			//Sort
			lr.sort(new Comparator<Rating>() {

				@Override
				public int compare(Rating o1, Rating o2) {
					if(o1.getRating()>o2.getRating()){
						return -1;
					}
					if(o1.getRating()<o2.getRating()){
						return 1;
					}
					if(o1.getMaxRating()>o2.getMaxRating()){
						return -1;
					}
					if(o1.getMaxRating()<o2.getMaxRating()){
						return 1;
					}
					
					return -o1.user.compareTo(o2.user);
				}
			});
			
			return lr;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Fail to get global rating\n"+e,"Error",JOptionPane.ERROR_MESSAGE);
			return new ArrayList<Rating>();
		}
		
	}

	public static List<Blog> getAllBlogs() {
		try{
			Socket s=new Socket(ClientInfo.ip,ClientInfo.port);
			DataInputStream dis=new DataInputStream(s.getInputStream());
			DataOutputStream dos=new DataOutputStream(s.getOutputStream());
			dos.writeInt(Value.getAllBlogs);
			
			int cnt=dis.readInt();
			List<Blog> lb=new ArrayList<Blog>();
			
			for(int i=0;i<cnt;i++){
				Blog b=new Blog();
				b.title=dis.readUTF();
				b.user=dis.readUTF();
				b.content=dis.readUTF();
				
				lb.add(b);
			}
			
			s.close();
			return lb;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Fail to get global blogs\n"+e,"Error",JOptionPane.ERROR_MESSAGE);
			return new ArrayList<Blog>();
		}
	}

	public static boolean submitBlog(String title, String content) {
		try{
			Socket s=new Socket(ClientInfo.ip,ClientInfo.port);
			//DataInputStream dis=new DataInputStream(s.getInputStream());
			DataOutputStream dos=new DataOutputStream(s.getOutputStream());
			dos.writeInt(Value.submitBlog);
			dos.writeUTF(title);
			dos.writeUTF(ClientInfo.user);
			dos.writeUTF(content);
			
			s.close();
			
			return true;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Fail to submit blog\n"+e,"Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
	}
	
	
	public static long currentTimeMillis(){
		try{
			Socket s=new Socket(ClientInfo.ip,ClientInfo.port);
			DataInputStream dis=new DataInputStream(s.getInputStream());
			DataOutputStream dos=new DataOutputStream(s.getOutputStream());
			dos.writeInt(Value.time);
			
			long ans=dis.readLong();
			s.close();
			return ans;
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Fail to get system time\n"+e,"Error",JOptionPane.ERROR_MESSAGE);
			return -1;
		}
	}
	
}
