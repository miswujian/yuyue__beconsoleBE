package com.yuyue.pojo;

import java.io.Serializable;

public class BookCaseitem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer bookcaseitemId;
	
	private Byte status;

	public Integer getBookcaseitemId() {
		return bookcaseitemId;
	}

	public void setBookcaseitemId(Integer bookcaseitemId) {
		this.bookcaseitemId = bookcaseitemId;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}
	
}
