package com.yuyue.pojo;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;


/**
 * 出入库单
 */
@Entity
@Table(name="rs_storagerecord")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
@NamedQuery(name="RsStoragerecord.findAll", query="SELECT r FROM RsStoragerecord r")
public class RsStoragerecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="storage_id")
	private int storageId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	private Float fee;

	@Column(name="order_no")
	private String orderNo;

	@Column(name="record_type")
	private byte recordType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="review_time")
	private Date reviewTime;

	private byte status;

	private byte type;

	//bi-directional many-to-one association to BeUser
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="reviewuser_id")
	@JsonBackReference("beUser1")
	@ApiModelProperty(hidden = true) 
	private BeUser beUser1;

	//bi-directional many-to-one association to BeUser
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="storageuser_id")
	@JsonBackReference("beUser2")
	@ApiModelProperty(hidden = true) 
	private BeUser beUser2;
	
	@OneToMany(mappedBy="rsStoragerecord",cascade={CascadeType.REMOVE})
	private List<RsStoragerecorditem> rsStoragerecorditems;

	@ManyToOne(fetch=FetchType.LAZY)
	@ApiModelProperty(hidden = true) 
	@JoinColumn(name="warehouse_id")
	//@JsonBackReference("beWarehouse")
	private BeWarehouse beWarehouse;
	
	@Transient
	private List<Bookinstore> bookinstores;
	
	@Transient
	private User user1;
	
	@Transient
	private User user2;
	
	public RsStoragerecord() {
	}

	public BeWarehouse getBeWarehouse() {
		return beWarehouse;
	}

	public void setBeWarehouse(BeWarehouse beWarehouse) {
		this.beWarehouse = beWarehouse;
	}

	public List<Bookinstore> getBookinstores() {
		return bookinstores;
	}

	public void setBookinstores(List<Bookinstore> bookinstores) {
		this.bookinstores = bookinstores;
	}

	public List<RsStoragerecorditem> getRsStoragerecorditems() {
		return rsStoragerecorditems;
	}

	public void setRsStoragerecorditems(List<RsStoragerecorditem> rsStoragerecorditems) {
		this.rsStoragerecorditems = rsStoragerecorditems;
	}

	public User getUser1() {
		return user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}

	public User getUser2() {
		return user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

	public int getStorageId() {
		return this.storageId;
	}

	public void setStorageId(int storageId) {
		this.storageId = storageId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Float getFee() {
		return this.fee;
	}

	public void setFee(Float fee) {
		this.fee = fee;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public byte getRecordType() {
		return this.recordType;
	}

	public void setRecordType(byte recordType) {
		this.recordType = recordType;
	}

	public Date getReviewTime() {
		return this.reviewTime;
	}

	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public byte getType() {
		return this.type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public BeUser getBeUser1() {
		return this.beUser1;
	}

	public void setBeUser1(BeUser beUser1) {
		this.beUser1 = beUser1;
	}

	public BeUser getBeUser2() {
		return this.beUser2;
	}

	public void setBeUser2(BeUser beUser2) {
		this.beUser2 = beUser2;
	}

}