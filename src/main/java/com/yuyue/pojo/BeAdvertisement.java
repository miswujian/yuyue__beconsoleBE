package com.yuyue.pojo;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;


/**
 * The persistent class for the be_advertisement database table.
 * 
 */
@Entity
@Table(name="be_advertisement")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
@NamedQuery(name="BeAdvertisement.findAll", query="SELECT b FROM BeAdvertisement b")
public class BeAdvertisement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="adv_id")
	private Integer advId;

	@Column(name="adv_url")
	private String advUrl;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="end_time")
	private Date endTime;

	private String name;

	private String position;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start_time")
	private Date startTime;

	private byte status;

	//bi-directional many-to-one association to BsBookcaseinfo
	@ManyToOne
	@JoinColumn(name="case_id")
	private BsBookcaseinfo bsBookcaseinfo;

	public BeAdvertisement() {
	}

	public Integer getAdvId() {
		return this.advId;
	}

	public void setAdvId(Integer advId) {
		this.advId = advId;
	}

	public String getAdvUrl() {
		return this.advUrl;
	}

	public void setAdvUrl(String advUrl) {
		this.advUrl = advUrl;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public BsBookcaseinfo getBsBookcaseinfo() {
		return this.bsBookcaseinfo;
	}

	public void setBsBookcaseinfo(BsBookcaseinfo bsBookcaseinfo) {
		this.bsBookcaseinfo = bsBookcaseinfo;
	}

}