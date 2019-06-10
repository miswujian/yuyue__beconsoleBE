package com.yuyue.pojo;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;


/**
 * status 1-草稿 2-选书 3-待出库 4-配送中 5-已上柜 6-差异待审核 7-已完成
 * order 订单编号为T+日期+XXX，例如T20181012001
 * way 1-手动铺书 2-自动铺书
 * type 1-入柜 2-出柜
 * bookcaseType 1-铺新书/调回仓库（具体看是 入柜单还是 出柜单）2-其他
 */
@Entity
@Table(name="rs_bookcaserecord")
@NamedQuery(name="RsBookcaserecord.findAll", query="SELECT r FROM RsBookcaserecord r")
public class RsBookcaserecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="bookcase_id")
	private Integer bookcaseId;

	@Column(name="bookcase_type")
	private Byte bookcaseType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="order_no")
	private String orderNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="orders_time")
	private Date ordersTime;

	private Byte status;

	private Byte type;
	
	private String reason;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="uppercase_time")
	private Date uppercaseTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="review_time")
	private Date reviewTime;

	private Byte way;

	//bi-directional many-to-one association to BsBookcaseinfo
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JoinColumn(name="case_id")
	private BsBookcaseinfo bsBookcaseinfo;

	//bi-directional many-to-one association to BeUser
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JsonBackReference("beUser1")
	@JoinColumn(name="bookcaseuser_id")
	private BeUser beUser1;

	//bi-directional many-to-one association to BeWarehouse
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JoinColumn(name="warehouse_id")
	private BeWarehouse beWarehouse;

	//bi-directional many-to-one association to BeUser
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JsonBackReference("beUser2")
	@JoinColumn(name="yw_id")
	private BeUser beUser2;
	
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JsonBackReference("beUser3")
	@JoinColumn(name="reviewuser_id")
	private BeUser beUser3;

	//bi-directional many-to-one association to RsBookcaserecorditem
	@OneToMany(mappedBy="rsBookcaserecord",cascade={CascadeType.REMOVE})
	private List<RsBookcaserecorditem> rsBookcaserecorditems;
	
	@Transient
	private User user1;
	
	@Transient
	private User user2;
	
	@Transient
	private User user3;

	public RsBookcaserecord() {
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}

	public BeUser getBeUser3() {
		return beUser3;
	}

	public void setBeUser3(BeUser beUser3) {
		this.beUser3 = beUser3;
	}

	public User getUser3() {
		return user3;
	}

	public void setUser3(User user3) {
		this.user3 = user3;
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

	public Integer getBookcaseId() {
		return this.bookcaseId;
	}

	public void setBookcaseId(Integer bookcaseId) {
		this.bookcaseId = bookcaseId;
	}

	public Byte getBookcaseType() {
		return this.bookcaseType;
	}

	public void setBookcaseType(Byte bookcaseType) {
		this.bookcaseType = bookcaseType;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getOrdersTime() {
		return this.ordersTime;
	}

	public void setOrdersTime(Date ordersTime) {
		this.ordersTime = ordersTime;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Byte getType() {
		return this.type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Date getUppercaseTime() {
		return this.uppercaseTime;
	}

	public void setUppercaseTime(Date uppercaseTime) {
		this.uppercaseTime = uppercaseTime;
	}

	public Byte getWay() {
		return this.way;
	}

	public void setWay(Byte way) {
		this.way = way;
	}

	public BsBookcaseinfo getBsBookcaseinfo() {
		return this.bsBookcaseinfo;
	}

	public void setBsBookcaseinfo(BsBookcaseinfo bsBookcaseinfo) {
		this.bsBookcaseinfo = bsBookcaseinfo;
	}

	public BeUser getBeUser1() {
		return this.beUser1;
	}

	public void setBeUser1(BeUser beUser1) {
		this.beUser1 = beUser1;
	}

	public BeWarehouse getBeWarehouse() {
		return this.beWarehouse;
	}

	public void setBeWarehouse(BeWarehouse beWarehouse) {
		this.beWarehouse = beWarehouse;
	}

	public BeUser getBeUser2() {
		return this.beUser2;
	}

	public void setBeUser2(BeUser beUser2) {
		this.beUser2 = beUser2;
	}

	public List<RsBookcaserecorditem> getRsBookcaserecorditems() {
		return this.rsBookcaserecorditems;
	}

	public void setRsBookcaserecorditems(List<RsBookcaserecorditem> rsBookcaserecorditems) {
		this.rsBookcaserecorditems = rsBookcaserecorditems;
	}

	public RsBookcaserecorditem addRsBookcaserecorditem(RsBookcaserecorditem rsBookcaserecorditem) {
		getRsBookcaserecorditems().add(rsBookcaserecorditem);
		rsBookcaserecorditem.setRsBookcaserecord(this);

		return rsBookcaserecorditem;
	}

	public RsBookcaserecorditem removeRsBookcaserecorditem(RsBookcaserecorditem rsBookcaserecorditem) {
		getRsBookcaserecorditems().remove(rsBookcaserecorditem);
		rsBookcaserecorditem.setRsBookcaserecord(null);

		return rsBookcaserecorditem;
	}

}