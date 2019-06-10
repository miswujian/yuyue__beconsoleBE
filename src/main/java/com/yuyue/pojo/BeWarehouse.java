package com.yuyue.pojo;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the be_warehouse database table.
 * 
 */
@Entity
@Table(name="be_warehouse")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
@NamedQuery(name="BeWarehouse.findAll", query="SELECT b FROM BeWarehouse b")
public class BeWarehouse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="warehouse_id")
	private Integer warehouseId;

	private String contacts;

	private Double latitude;

	private Double longitude;

	@Column(name="operator_id")
	private int operatorId;

	private String remarks;

	private String telephone;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	@Column(name="warehouse_address")
	private String warehouseAddress;

	@Column(name="warehouse_code")
	private String warehouseCode;

	@Column(name="warehouse_name")
	private String warehouseName;
	
	@OneToMany(mappedBy="beWarehouse")
	private List<BeLocation> beLocations;

	//bi-directional many-to-one association to BeDepartment
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JoinColumn(name="department_id")
	private BeDepartment beDepartment;
	
	@OneToMany(mappedBy="beWarehouse" ,fetch=FetchType.LAZY)
	@JsonBackReference("bsBookcaseinfos")
	private List<BsBookcaseinfo> bsBookcaseinfos;
	
	@OneToMany(mappedBy="beWarehouse" ,fetch=FetchType.LAZY)
	@JsonBackReference("rsUserwarehouses")
	private List<RsUserwarehouse> rsUserwarehouses;
	
	@OneToMany(mappedBy="beWarehouse" ,fetch=FetchType.LAZY)
	@JsonBackReference("rsStoragerecords")
	private List<RsStoragerecord> rsStoragerecords;
	
	/*@OneToMany(mappedBy="beWarehouse1")
	@JsonBackReference("rsTransferrecords1")
	private List<RsTransferrecord> rsTransferrecords1;

	//bi-directional many-to-one association to RsTransferrecord
	@OneToMany(mappedBy="beWarehouse2")
	@JsonBackReference("rsTransferrecords2")
	private List<RsTransferrecord> rsTransferrecords2;*/
	
	@Transient
	private Integer departmentId;
	
	/*public List<RsTransferrecord> getRsTransferrecords1() {
		return rsTransferrecords1;
	}

	public void setRsTransferrecords1(List<RsTransferrecord> rsTransferrecords1) {
		this.rsTransferrecords1 = rsTransferrecords1;
	}

	public List<RsTransferrecord> getRsTransferrecords2() {
		return rsTransferrecords2;
	}

	public void setRsTransferrecords2(List<RsTransferrecord> rsTransferrecords2) {
		this.rsTransferrecords2 = rsTransferrecords2;
	}*/

	public List<RsStoragerecord> getRsStoragerecords() {
		return rsStoragerecords;
	}

	public void setRsStoragerecords(List<RsStoragerecord> rsStoragerecords) {
		this.rsStoragerecords = rsStoragerecords;
	}

	public List<RsUserwarehouse> getRsUserwarehouses() {
		return rsUserwarehouses;
	}

	public void setRsUserwarehouses(List<RsUserwarehouse> rsUserwarehouses) {
		this.rsUserwarehouses = rsUserwarehouses;
	}

	public List<BeLocation> getBeLocations() {
		return beLocations;
	}

	public void setBeLocations(List<BeLocation> beLocations) {
		this.beLocations = beLocations;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public List<BsBookcaseinfo> getBsBookcaseinfos() {
		return bsBookcaseinfos;
	}

	public void setBsBookcaseinfos(List<BsBookcaseinfo> bsBookcaseinfos) {
		this.bsBookcaseinfos = bsBookcaseinfos;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public BeWarehouse() {
	}

	public Integer getWarehouseId() {
		return this.warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getContacts() {
		return this.contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public int getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getWarehouseAddress() {
		return this.warehouseAddress;
	}

	public void setWarehouseAddress(String warehouseAddress) {
		this.warehouseAddress = warehouseAddress;
	}

	public String getWarehouseCode() {
		return this.warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getWarehouseName() {
		return this.warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public BeDepartment getBeDepartment() {
		return this.beDepartment;
	}

	public void setBeDepartment(BeDepartment beDepartment) {
		this.beDepartment = beDepartment;
	}

}