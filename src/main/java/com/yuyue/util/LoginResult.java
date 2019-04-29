package com.yuyue.util;

public class LoginResult {
	public static int SUCCESS_CODE = 0;
	public static int FAIL_CODE = 1;
	
	boolean isFetching;
	String message;
	Object data;
	String session;
	
	private LoginResult(boolean isFetching, String message, Object data, String session) {
		this.isFetching = isFetching;
		this.message = message;
		this.data = data;
		this.session=session;
	}

	public static LoginResult success() {
		return new LoginResult(false,null,null,null);
	}
	public static LoginResult success(Object data, String session) {
		return new LoginResult(false,"",data,session);
	}
	public static LoginResult fail(String message) {
		return new LoginResult(false,message,null,null);
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public boolean isFetching() {
		return isFetching;
	}

	public void setFetching(boolean isFetching) {
		this.isFetching = isFetching;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
	
}

