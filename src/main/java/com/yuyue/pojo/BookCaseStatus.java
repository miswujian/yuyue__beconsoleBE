package com.yuyue.pojo;

import java.io.Serializable;
import java.util.List;

public class BookCaseStatus implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer bookcaseId;
	
	private List<BookCaseitem> itemStatus;
	
	private Byte status;
	
	private String reason;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getBookcaseId() {
		return bookcaseId;
	}

	public void setBookcaseId(Integer bookcaseId) {
		this.bookcaseId = bookcaseId;
	}

	public List<BookCaseitem> getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(List<BookCaseitem> itemStatus) {
		this.itemStatus = itemStatus;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

}
