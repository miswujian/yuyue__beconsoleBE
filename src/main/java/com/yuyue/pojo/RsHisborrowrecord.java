package com.yuyue.pojo;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;


/**
 * The persistent class for the rs_hisborrowrecord database table.
 * 
 */
/**
 * 历史借阅
 * @author 吴俭
 *
 */
@Entity
@Table(name="rs_hisborrowrecord")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
@NamedQuery(name="RsHisborrowrecord.findAll", query="SELECT r FROM RsHisborrowrecord r")
public class RsHisborrowrecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="borrow_id")
	private Integer borrowId;

	@Column(name="borrow_det")
	private String borrowDet;

	private String contact;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="deliver_address")
	private String deliverAddress;

	@Column(name="deliver_type")
	private byte deliverType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="expire_time")
	private Date expireTime;

	@Column(name="express_no")
	private String expressNo;

	private float fee;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="finish_time")
	private Date finishTime;

	@Column(name="has_comment")
	private byte hasComment;

	@Column(name="ng_fine")
	private float ngFine;

	@Column(name="ng_reason")
	private String ngReason;

	@Column(name="order_no")
	private String orderNo;

	@Column(name="overdue_days")
	private Integer overdueDays;

	private String phone;

	@Column(name="return_det")
	private String returnDet;

	@Column(name="return_way")
	private Byte returnWay;
	
	private Byte status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start_time")
	private Date startTime;

	//bi-directional many-to-one association to BsBookinstore
	@ManyToOne
	@JoinColumn(name="book_id")
	private BsBookinstore bsBookinstore;

	//bi-directional many-to-one association to BsUserinfo
	@ManyToOne
	@JoinColumn(name="user_id")
	private BsUserinfo bsUserinfo;

	//bi-directional many-to-one association to BsBookinfo
	@ManyToOne
	@JoinColumn(name="bookinfo_id")
	private BsBookinfo bsBookinfo;
	
	@Transient
	private String bookName;
	
	@Transient
	private String nikeName;
	
	@Transient
	private String vipNo;

	public RsHisborrowrecord() {
	}

	public String getNikeName() {
		return nikeName;
	}

	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}

	public String getVipNo() {
		return vipNo;
	}

	public void setVipNo(String vipNo) {
		this.vipNo = vipNo;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public Integer getBorrowId() {
		return this.borrowId;
	}

	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}

	public String getBorrowDet() {
		return this.borrowDet;
	}

	public void setBorrowDet(String borrowDet) {
		this.borrowDet = borrowDet;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDeliverAddress() {
		return this.deliverAddress;
	}

	public void setDeliverAddress(String deliverAddress) {
		this.deliverAddress = deliverAddress;
	}

	public byte getDeliverType() {
		return this.deliverType;
	}

	public void setDeliverType(byte deliverType) {
		this.deliverType = deliverType;
	}

	public Date getExpireTime() {
		return this.expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public String getExpressNo() {
		return this.expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public float getFee() {
		return this.fee;
	}

	public void setFee(float fee) {
		this.fee = fee;
	}

	public Date getFinishTime() {
		return this.finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public byte getHasComment() {
		return this.hasComment;
	}

	public void setHasComment(byte hasComment) {
		this.hasComment = hasComment;
	}

	public float getNgFine() {
		return this.ngFine;
	}

	public void setNgFine(float ngFine) {
		this.ngFine = ngFine;
	}

	public String getNgReason() {
		return this.ngReason;
	}

	public void setNgReason(String ngReason) {
		this.ngReason = ngReason;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getOverdueDays() {
		return this.overdueDays;
	}

	public void setOverdueDays(Integer overdueDays) {
		this.overdueDays = overdueDays;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getReturnDet() {
		return this.returnDet;
	}

	public void setReturnDet(String returnDet) {
		this.returnDet = returnDet;
	}

	public Byte getReturnWay() {
		return this.returnWay;
	}

	public void setReturnWay(Byte returnWay) {
		this.returnWay = returnWay;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public BsBookinstore getBsBookinstore() {
		return this.bsBookinstore;
	}

	public void setBsBookinstore(BsBookinstore bsBookinstore) {
		this.bsBookinstore = bsBookinstore;
	}

	public BsUserinfo getBsUserinfo() {
		return this.bsUserinfo;
	}

	public void setBsUserinfo(BsUserinfo bsUserinfo) {
		this.bsUserinfo = bsUserinfo;
	}

	public BsBookinfo getBsBookinfo() {
		return this.bsBookinfo;
	}

	public void setBsBookinfo(BsBookinfo bsBookinfo) {
		this.bsBookinfo = bsBookinfo;
	}

}