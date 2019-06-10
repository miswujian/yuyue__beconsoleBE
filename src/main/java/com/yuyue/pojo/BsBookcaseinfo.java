package com.yuyue.pojo;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;


/**
 * The persistent class for the bs_bookcaseinfo database table.
 * 
 */
/**
 * 书柜信息表
 *
 */
@Entity
@Table(name="bs_bookcaseinfo")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
@NamedQuery(name="BsBookcaseinfo.findAll", query="SELECT b FROM BsBookcaseinfo b")
public class BsBookcaseinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="case_id")
	private Integer caseId;

	@Column(name="case_address")
	private String caseAddress;

	@Column(name="case_code")
	private String caseCode;

	@Column(name="case_name")
	private String caseName;

	@Column(name="case_picture")
	private String casePicture;

	@Column(name="cell_count")
	private Integer cellCount;

	@Column(name="cell_empty")
	private Integer cellEmpty;

	private String deviceId;

	private double latitude;

	private double longitude;
	
	private Integer allocation;
	
	private byte status;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@ApiModelProperty(hidden = true) 
	@JoinColumn(name="warehouse_id")
	private BeWarehouse beWarehouse;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@ApiModelProperty(hidden = true) 
	@JoinColumn(name="yw_id")
	private BeUser beUser;
	
	@OneToMany(mappedBy="bsBookcaseinfo" ,fetch=FetchType.LAZY)
	@JsonBackReference("rsUserwarehouses")
	private List<RsUserwarehouse> rsUserwarehouses;
	
	@Transient
	private User user;
	
	@Transient
	private List<BsBookcellinfo> bsBookcellinfos;

	public BsBookcaseinfo() {
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<RsUserwarehouse> getRsUserwarehouses() {
		return rsUserwarehouses;
	}

	public void setRsUserwarehouses(List<RsUserwarehouse> rsUserwarehouses) {
		this.rsUserwarehouses = rsUserwarehouses;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public BeUser getBeUser() {
		return beUser;
	}

	public void setBeUser(BeUser beUser) {
		this.beUser = beUser;
	}

	public BeWarehouse getBeWarehouse() {
		return beWarehouse;
	}

	public void setBeWarehouse(BeWarehouse beWarehouse) {
		this.beWarehouse = beWarehouse;
	}

	public void setAllocation(Integer allocation) {
		this.allocation = allocation;
	}

	public Integer getCaseId() {
		return this.caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}

	public String getCaseAddress() {
		return this.caseAddress;
	}

	public void setCaseAddress(String caseAddress) {
		this.caseAddress = caseAddress;
	}

	public String getCaseCode() {
		return this.caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getCaseName() {
		return this.caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getCasePicture() {
		return this.casePicture;
	}

	public void setCasePicture(String casePicture) {
		this.casePicture = casePicture;
	}

	public Integer getCellCount() {
		return this.cellCount;
	}

	public void setCellCount(Integer cellCount) {
		this.cellCount = cellCount;
	}

	public Integer getCellEmpty() {
		return this.cellEmpty;
	}

	public void setCellEmpty(Integer cellEmpty) {
		this.cellEmpty = cellEmpty;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public List<BsBookcellinfo> getBsBookcellinfos() {
		return this.bsBookcellinfos;
	}

	public void setBsBookcellinfos(List<BsBookcellinfo> bsBookcellinfos) {
		this.bsBookcellinfos = bsBookcellinfos;
	}

	public BsBookcellinfo addBsBookcellinfo(BsBookcellinfo bsBookcellinfo) {
		getBsBookcellinfos().add(bsBookcellinfo);
		bsBookcellinfo.setBsBookcaseinfo(this);

		return bsBookcellinfo;
	}

	public BsBookcellinfo removeBsBookcellinfo(BsBookcellinfo bsBookcellinfo) {
		getBsBookcellinfos().remove(bsBookcellinfo);
		bsBookcellinfo.setBsBookcaseinfo(null);

		return bsBookcellinfo;
	}

	public int getAllocation() {
		return allocation;
	}

	public void setAllocation(int allocation) {
		this.allocation = allocation;
	}

}