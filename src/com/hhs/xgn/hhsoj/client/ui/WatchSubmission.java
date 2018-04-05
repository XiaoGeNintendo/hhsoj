package com.hhs.xgn.hhsoj.client.ui;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.hhs.xgn.hhsoj.type.*;
import com.hhs.xgn.hhsoj.util.TimeUtil;

public class WatchSubmission extends JFrame {
	Verdict v;
	JFrame self=this;
	
	/**
	 * Debug only!!
	 */
	@Deprecated
	public WatchSubmission(){
		todo();
	}
	
	public WatchSubmission(Verdict v){
		this.v=v;
		
		this.setTitle("Watch Submission - "+v.sub);
		
		
		todo();
	
	}
	
	void todo(){
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//-----------------------------
		MyTextPane mtp=new MyTextPane();
		mtp.setEditable(false);
		
		mtp.setText(v.sub.prog);
		mtp.syntaxParse();
		
		this.setLayout(new GridLayout(3, 1));
		//this.getContentPane().add(new JScrollPane(mtp));
		
		JScrollPane jsp=new JScrollPane(mtp);
		//默认的设置是超过文本框才会显示滚动条，以下设置让滚动条一直显示
        jsp.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(jsp);
		//-----------------------------
		
		
		JList<Object> jls=new JList<Object>(v.tests.toArray());
		jls.setBorder(BorderFactory.createTitledBorder("Submission Result"));
		
		JScrollPane jsps=new JScrollPane(jls);
		//默认的设置是超过文本框才会显示滚动条，以下设置让滚动条一直显示
        jsps.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(jsps);
		
		//-----------------------------
		JTextArea jl=new JTextArea();
		
		jl.setText("Solution By:"+v.sub.user+"\nProblem:"+v.sub.cont.problems.get(v.sub.prob).title+"\nContest:"+v.sub.cont.title+"\nTime:"+TimeUtil.getTime(v.sub.time));
		jl.setEditable(false);
		
		this.add(jl);
		
		this.setSize(500, 500);
		this.setVisible(true);
	}
}
