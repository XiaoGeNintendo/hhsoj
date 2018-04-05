package com.hhs.xgn.hhsoj.client.ui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.hhs.xgn.hhsoj.client.ClientInfo;
import com.hhs.xgn.hhsoj.type.Contest;
import com.hhs.xgn.hhsoj.type.ContestResult;
import com.hhs.xgn.hhsoj.type.Problem;
import com.hhs.xgn.hhsoj.util.Net;

public class ContestStanding extends JFrame {
	Contest c;
	ContestStanding self=this;
	public ContestStanding(Contest c){
		this.c=c;
		
		//Basic information
		this.setTitle("Contest Standing:"+c.title+" - "+ClientInfo.user);
		this.setLayout(new BorderLayout());
		
		//Getting data
		List<ContestResult> lcr=Net.getContestStanding(c);
		
		if(lcr.size()==0){
			JOptionPane.showMessageDialog(null, "Standing cannot be shown properly. :(\nError:No one took part in.","Error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		List<Object> cnl=new ArrayList<Object>();
		cnl.add("Rank");
		cnl.add("Name");
		cnl.add("¦²");
		
		for(Problem p:c.problems){
			cnl.add(p.title);
		}
		
		String[] cn=cnl.toArray(new String[0]);
		
		System.out.println(Arrays.asList(cn));
		
		//JTable
		MyTableModel dtm=new MyTableModel(new Object[][]{}, cn);
		
		JTable jt=new JTable(dtm);
		
		int i=1;
		for(ContestResult cr:lcr){
			List<String> om=new ArrayList<String>();
			
			om.add(String.valueOf(i));
			om.add(cr.user);
			om.add(cr.score+"");
			
			for(int j=3;j<cn.length;j++){
				om.add(cr.results.get(cn[j]));
			}
			
			String[] com=om.toArray(new String[0]);
			
			dtm.addRow(com);
			
			i++;
		}
		
		JScrollPane jsp=new JScrollPane(jt);
		
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		//Add
		this.add("Center",jsp);
		this.setSize(500,500);
		this.setVisible(true);
	}
}

class MyTableModel extends DefaultTableModel{

	
	public MyTableModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MyTableModel(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		// TODO Auto-generated constructor stub
	}

	public MyTableModel(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public MyTableModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}

	public MyTableModel(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public MyTableModel(Vector data, Vector columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		// TODO Auto-generated method stub
		return false;
	}
	
}