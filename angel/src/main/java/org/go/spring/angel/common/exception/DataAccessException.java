package org.go.spring.angel.common.exception;

@SuppressWarnings("serial")
public class DataAccessException extends RuntimeException{
	
	public DataAccessException(String msg){
		super(msg);
	}
}
