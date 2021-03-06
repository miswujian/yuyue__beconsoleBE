package com.yuyue.pojo;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;


/**
 * The persistent class for the rs_vipplanorder database table.
 * 
 */
/**
 * vip充值表  trade_no 流水号
 *
 */
@Entity
@Table(name="rs_vipplanorder")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
@NamedQuery(name="RsVipplanorder.findAll", query="SELECT r FROM RsVipplanorder r")
public class RsVipplanorder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="planorder_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String planorderId;

	private String body;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="finish_time")
	private Date finishTime;

	@Column(name="order_no")
	private String orderNo;

	@Column(name="pay_data")
	private String payData;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="pay_expiretime")
	private Date payExpiretime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="pay_time")
	private Date payTime;

	@Column(name="pay_type")
	private Byte payType;

	private float price;

	@Column(name="real_price")
	private float realPrice;

	private String remak;

	private Byte status;

	private String title;

	@Column(name="trade_no")
	private String tradeNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	@Column(name="use_credit")
	private Byte useCredit;

	//bi-directional many-to-one association to BsVipplan
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JoinColumn(name="plan_id")
	private BsVipplan bsVipplan;

	//bi-directional many-to-one association to BsUserinfo
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JoinColumn(name="userId")
	private BsUserinfo bsUserinfo;

	//bi-directional many-to-one association to RsUsercredit
	/*@ManyToOne
	@JoinColumn(name="credit_id")
	private RsUsercredit rsUsercredit;*/
	
	@Column(name="credit_id")
	private String creditId;
	
	@Transient
	private String nikeName;
	
	@Transient
	private String vipNo;

	public RsVipplanorder() {
	}

	public String getCreditId() {
		return creditId;
	}

	public void setCreditId(String creditId) {
		this.creditId = creditId;
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

	public String getPlanorderId() {
		return this.planorderId;
	}

	public void setPlanorderId(String planorderId) {
		this.planorderId = planorderId;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getFinishTime() {
		return this.finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPayData() {
		return this.payData;
	}

	public void setPayData(String payData) {
		this.payData = payData;
	}

	public Date getPayExpiretime() {
		return this.payExpiretime;
	}

	public void setPayExpiretime(Date payExpiretime) {
		this.payExpiretime = payExpiretime;
	}

	public Date getPayTime() {
		return this.payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Byte getPayType() {
		return this.payType;
	}

	public void setPayType(Byte payType) {
		this.payType = payType;
	}

	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getRealPrice() {
		return this.realPrice;
	}

	public void setRealPrice(float realPrice) {
		this.realPrice = realPrice;
	}

	public String getRemak() {
		return this.remak;
	}

	public void setRemak(String remak) {
		this.remak = remak;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTradeNo() {
		return this.tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Byte getUseCredit() {
		return this.useCredit;
	}

	public void setUseCredit(Byte useCredit) {
		this.useCredit = useCredit;
	}

	public BsVipplan getBsVipplan() {
		return this.bsVipplan;
	}

	public void setBsVipplan(BsVipplan bsVipplan) {
		this.bsVipplan = bsVipplan;
	}

	public BsUserinfo getBsUserinfo() {
		return this.bsUserinfo;
	}

	public void setBsUserinfo(BsUserinfo bsUserinfo) {
		this.bsUserinfo = bsUserinfo;
	}

	/*public RsUsercredit getRsUsercredit() {
		return this.rsUsercredit;
	}

	public void setRsUsercredit(RsUsercredit rsUsercredit) {
		this.rsUsercredit = rsUsercredit;
	}*/

}