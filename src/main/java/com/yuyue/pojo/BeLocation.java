package com.yuyue.pojo;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * The persistent class for the be_location database table.
 * 
 */
@Entity
@Table(name = "be_location")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
@NamedQuery(name = "BeLocation.findAll", query = "SELECT b FROM BeLocation b")
public class BeLocation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "location_id")
	private int locationId;

	@Column(name = "location_code")
	private String locationCode;

	@Column(name = "location_count")
	private int locationCount;

	@Column(name = "location_empty")
	private int locationEmpty;

	@Column(name = "location_name")
	private String locationName;

	private byte status;

	// bi-directional many-to-one association to BeCell
	@OneToMany(mappedBy = "beLocation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonBackReference("beCells")
	private List<BeCell> beCells;

	// bi-directional many-to-one association to BeWarehouse
	@ManyToOne
	@JoinColumn(name = "warehouse_id")
	@ApiModelProperty(hidden = true)
	@JsonBackReference("beWarehouse")
	private BeWarehouse beWarehouse;

	@OneToMany(mappedBy = "beLocation", fetch = FetchType.LAZY)
	@JsonBackReference("bsBookinstores")
	private List<BsBookinstore> bsBookinstores;

	@OneToMany(mappedBy = "beLocation", fetch = FetchType.LAZY)
	@JsonBackReference("rsStoragerecords")
	private List<RsStoragerecorditem> rsStoragerecorditems;

	@OneToMany(mappedBy = "beLocation", fetch = FetchType.LAZY)
	@JsonBackReference("rsBookcaserecorditems")
	private List<RsBookcaserecorditem> rsBookcaserecorditems;

	@OneToMany(mappedBy = "beLocation1", fetch = FetchType.LAZY)
	@JsonBackReference("rsTransferrecorditems1")
	private List<RsTransferrecorditem> rsTransferrecorditems1;

	@OneToMany(mappedBy = "beLocation2", fetch = FetchType.LAZY)
	@JsonBackReference("rsTransferrecorditems2")
	private List<RsTransferrecorditem> rsTransferrecorditems2;

	public BeLocation() {
	}

	public List<RsBookcaserecorditem> getRsBookcaserecorditems() {
		return rsBookcaserecorditems;
	}

	public void setRsBookcaserecorditems(List<RsBookcaserecorditem> rsBookcaserecorditems) {
		this.rsBookcaserecorditems = rsBookcaserecorditems;
	}

	public List<RsTransferrecorditem> getRsTransferrecorditems1() {
		return rsTransferrecorditems1;
	}

	public void setRsTransferrecorditems1(List<RsTransferrecorditem> rsTransferrecorditems1) {
		this.rsTransferrecorditems1 = rsTransferrecorditems1;
	}

	public List<RsTransferrecorditem> getRsTransferrecorditems2() {
		return rsTransferrecorditems2;
	}

	public void setRsTransferrecorditems2(List<RsTransferrecorditem> rsTransferrecorditems2) {
		this.rsTransferrecorditems2 = rsTransferrecorditems2;
	}

	public List<RsStoragerecorditem> getRsStoragerecorditems() {
		return rsStoragerecorditems;
	}

	public void setRsStoragerecorditems(List<RsStoragerecorditem> rsStoragerecorditems) {
		this.rsStoragerecorditems = rsStoragerecorditems;
	}

	public List<BsBookinstore> getBsBookinstores() {
		return bsBookinstores;
	}

	public void setBsBookinstores(List<BsBookinstore> bsBookinstores) {
		this.bsBookinstores = bsBookinstores;
	}

	public int getLocationId() {
		return this.locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getLocationCode() {
		return this.locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public int getLocationCount() {
		return this.locationCount;
	}

	public void setLocationCount(int locationCount) {
		this.locationCount = locationCount;
	}

	public int getLocationEmpty() {
		return this.locationEmpty;
	}

	public void setLocationEmpty(int locationEmpty) {
		this.locationEmpty = locationEmpty;
	}

	public String getLocationName() {
		return this.locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public List<BeCell> getBeCells() {
		return this.beCells;
	}

	public void setBeCells(List<BeCell> beCells) {
		this.beCells = beCells;
	}

	public BeCell addBeCell(BeCell beCell) {
		getBeCells().add(beCell);
		beCell.setBeLocation(this);

		return beCell;
	}

	public BeCell removeBeCell(BeCell beCell) {
		getBeCells().remove(beCell);
		beCell.setBeLocation(null);

		return beCell;
	}

	public BeWarehouse getBeWarehouse() {
		return this.beWarehouse;
	}

	public void setBeWarehouse(BeWarehouse beWarehouse) {
		this.beWarehouse = beWarehouse;
	}

}