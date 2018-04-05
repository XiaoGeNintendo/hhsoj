package com.hhs.xgn.hhsoj.server.exception;

public class BadClientRequestException extends Exception {
	public BadClientRequestException(){
		super();
	}
	
	public BadClientRequestException(String s){
		super(s);
	}
}
