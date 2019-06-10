package com.yuyue.pojo;

import java.io.Serializable;

public class Case implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer caseId;
	
	private String caseName;

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	
}
