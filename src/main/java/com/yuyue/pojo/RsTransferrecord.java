package com.yuyue.pojo;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the rs_transferrecord database table.
 * 
 */
@Entity
@Table(name="rs_transferrecord")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
@NamedQuery(name="RsTransferrecord.findAll", query="SELECT r FROM RsTransferrecord r")
public class RsTransferrecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="transfer_id")
	private int transferId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time", updatable = false)
	private Date createTime;

	private float fee;

	@Column(name="order_no")
	private String orderNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="review_time")
	private Date reviewTime;

	private byte status;

	//bi-directional many-to-one association to BeWarehouse
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JoinColumn(name="inwarehouse_id")
	private BeWarehouse beWarehouse1;

	//bi-directional many-to-one association to BeWarehouse
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JoinColumn(name="outwarehouse_id")
	private BeWarehouse beWarehouse2;

	//bi-directional many-to-one association to BeUser
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JsonBackReference("beUser1")
	@JoinColumn(name="transferuser_id")
	private BeUser beUser1;

	//bi-directional many-to-one association to BeUser
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JsonBackReference("beUser2")
	@JoinColumn(name="reviewuser_id")
	private BeUser beUser2;

	//bi-directional many-to-one association to RsTransferrecorditem
	@OneToMany(mappedBy="rsTransferrecord",cascade={CascadeType.REMOVE})
	private List<RsTransferrecorditem> rsTransferrecorditems;
	
	@Transient
	private User user1;
	
	@Transient
	private User user2;
	
	@Transient
	private List<Bookinstore> bookinstores;

	public RsTransferrecord() {
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

	public List<Bookinstore> getBookinstores() {
		return bookinstores;
	}

	public void setBookinstores(List<Bookinstore> bookinstores) {
		this.bookinstores = bookinstores;
	}

	public int getTransferId() {
		return this.transferId;
	}

	public void setTransferId(int transferId) {
		this.transferId = transferId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public float getFee() {
		return this.fee;
	}

	public void setFee(float fee) {
		this.fee = fee;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public BeWarehouse getBeWarehouse1() {
		return this.beWarehouse1;
	}

	public void setBeWarehouse1(BeWarehouse beWarehouse1) {
		this.beWarehouse1 = beWarehouse1;
	}

	public BeWarehouse getBeWarehouse2() {
		return this.beWarehouse2;
	}

	public void setBeWarehouse2(BeWarehouse beWarehouse2) {
		this.beWarehouse2 = beWarehouse2;
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

	public List<RsTransferrecorditem> getRsTransferrecorditems() {
		return this.rsTransferrecorditems;
	}

	public void setRsTransferrecorditems(List<RsTransferrecorditem> rsTransferrecorditems) {
		this.rsTransferrecorditems = rsTransferrecorditems;
	}

	public RsTransferrecorditem addRsTransferrecorditem(RsTransferrecorditem rsTransferrecorditem) {
		getRsTransferrecorditems().add(rsTransferrecorditem);
		rsTransferrecorditem.setRsTransferrecord(this);

		return rsTransferrecorditem;
	}

	public RsTransferrecorditem removeRsTransferrecorditem(RsTransferrecorditem rsTransferrecorditem) {
		getRsTransferrecorditems().remove(rsTransferrecorditem);
		rsTransferrecorditem.setRsTransferrecord(null);

		return rsTransferrecorditem;
	}

}