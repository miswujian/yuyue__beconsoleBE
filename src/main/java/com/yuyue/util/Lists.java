package com.yuyue.util;

import java.io.Serializable;
import java.util.List;

public class Lists implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<String> dynamicIds;

	List<String> commentIds;
	
	List<Integer> addCaseIds;
	
	List<Integer> deleteCaseIds;
	
	int userId;

	public List<Integer> getAddCaseIds() {
		return addCaseIds;
	}

	public void setAddCaseIds(List<Integer> addCaseIds) {
		this.addCaseIds = addCaseIds;
	}

	public List<Integer> getDeleteCaseIds() {
		return deleteCaseIds;
	}

	public void setDeleteCaseIds(List<Integer> deleteCaseIds) {
		this.deleteCaseIds = deleteCaseIds;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<String> getDynamicIds() {
		return dynamicIds;
	}

	public void setDynamicIds(List<String> dynamicIds) {
		this.dynamicIds = dynamicIds;
	}

	public List<String> getCommentIds() {
		return commentIds;
	}

	public void setCommentIds(List<String> commentIds) {
		this.commentIds = commentIds;
	}
	
}
