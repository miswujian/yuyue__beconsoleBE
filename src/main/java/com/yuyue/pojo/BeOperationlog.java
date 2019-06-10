package com.yuyue.pojo;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;


/**
 * The persistent class for the be_operationlog database table.
 * 
 */
@Entity
@Table(name="be_operationlog")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
@NamedQuery(name="BeOperationlog.findAll", query="SELECT b FROM BeOperationlog b")
public class BeOperationlog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="log_id")
	private Integer logId;

	private byte crud;

	private String features;

	@Column(name="features_id")
	private int featuresId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="operation_time")
	private Date operationTime;

	private String remarks;

	//bi-directional many-to-one association to BeUser
	@ManyToOne
	@JoinColumn(name="operation_id")
	@ApiModelProperty(hidden = true)
	@JsonBackReference("beUser")
	private BeUser beUser;
	
	@Transient
	private User user;

	public BeOperationlog() {
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getLogId() {
		return this.logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public byte getCrud() {
		return this.crud;
	}

	public void setCrud(byte crud) {
		this.crud = crud;
	}

	public String getFeatures() {
		return this.features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public int getFeaturesId() {
		return this.featuresId;
	}

	public void setFeaturesId(int featuresId) {
		this.featuresId = featuresId;
	}

	public Date getOperationTime() {
		return this.operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BeUser getBeUser() {
		return this.beUser;
	}

	public void setBeUser(BeUser beUser) {
		this.beUser = beUser;
	}

}