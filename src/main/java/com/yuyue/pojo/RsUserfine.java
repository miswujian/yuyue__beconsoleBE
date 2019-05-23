package com.yuyue.pojo;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;


/**
 * The persistent class for the rs_userfine database table.
 * 
 */
@Entity
@Table(name="rs_userfine")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
@NamedQuery(name="RsUserfine.findAll", query="SELECT r FROM RsUserfine r")
public class RsUserfine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="userfine_id")
	private String userfineId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="fine_money")
	private float fineMoney;

	@Column(name="fine_reason")
	private String fineReason;

	@Column(name="fine_type")
	private byte fineType;

	@Column(name="order_no")
	private String orderNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="pay_time")
	private Date payTime;

	@Column(name="pay_type")
	private byte payType;

	private byte status;

	@Column(name="trade_no")
	private String tradeNo;

	@Column(name="type_param")
	private String typeParam;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	//bi-directional many-to-one association to BsUserinfo
	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonBackReference("bsUserinfo")
	private BsUserinfo bsUserinfo;

	public RsUserfine() {
	}

	public String getUserfineId() {
		return this.userfineId;
	}

	public void setUserfineId(String userfineId) {
		this.userfineId = userfineId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public float getFineMoney() {
		return this.fineMoney;
	}

	public void setFineMoney(float fineMoney) {
		this.fineMoney = fineMoney;
	}

	public String getFineReason() {
		return this.fineReason;
	}

	public void setFineReason(String fineReason) {
		this.fineReason = fineReason;
	}

	public byte getFineType() {
		return this.fineType;
	}

	public void setFineType(byte fineType) {
		this.fineType = fineType;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getPayTime() {
		return this.payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public byte getPayType() {
		return this.payType;
	}

	public void setPayType(byte payType) {
		this.payType = payType;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getTradeNo() {
		return this.tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getTypeParam() {
		return this.typeParam;
	}

	public void setTypeParam(String typeParam) {
		this.typeParam = typeParam;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public BsUserinfo getBsUserinfo() {
		return this.bsUserinfo;
	}

	public void setBsUserinfo(BsUserinfo bsUserinfo) {
		this.bsUserinfo = bsUserinfo;
	}

}