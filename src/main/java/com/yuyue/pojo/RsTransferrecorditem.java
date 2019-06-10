package com.yuyue.pojo;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;


/**
 * The persistent class for the rs_transferrecorditem database table.
 * 
 */
@Entity
@Table(name="rs_transferrecorditem")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
@NamedQuery(name="RsTransferrecorditem.findAll", query="SELECT r FROM RsTransferrecorditem r")
public class RsTransferrecorditem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="transferitem_id")
	private int transferitemId;

	private float price;

	//bi-directional many-to-one association to BeLocation
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JoinColumn(name="inlocation_id")
	private BeLocation beLocation1;

	//bi-directional many-to-one association to BeLocation
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JoinColumn(name="outlocation_id")
	private BeLocation beLocation2;

	//bi-directional many-to-one association to BsBookinstore
	@ManyToOne
	@JsonBackReference("bsBookinstore")
	@ApiModelProperty(hidden = true) 
	@JoinColumn(name="book_id")
	private BsBookinstore bsBookinstore;

	//bi-directional many-to-one association to RsTransferrecord
	@ManyToOne
	@JoinColumn(name="transfer_id")
	@ApiModelProperty(hidden = true) 
	@JsonBackReference("rsTransferrecord")
	private RsTransferrecord rsTransferrecord;

	public RsTransferrecorditem() {
	}

	public int getTransferitemId() {
		return this.transferitemId;
	}

	public void setTransferitemId(int transferitemId) {
		this.transferitemId = transferitemId;
	}

	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public BeLocation getBeLocation1() {
		return this.beLocation1;
	}

	public void setBeLocation1(BeLocation beLocation1) {
		this.beLocation1 = beLocation1;
	}

	public BeLocation getBeLocation2() {
		return this.beLocation2;
	}

	public void setBeLocation2(BeLocation beLocation2) {
		this.beLocation2 = beLocation2;
	}

	public BsBookinstore getBsBookinstore() {
		return this.bsBookinstore;
	}

	public void setBsBookinstore(BsBookinstore bsBookinstore) {
		this.bsBookinstore = bsBookinstore;
	}

	public RsTransferrecord getRsTransferrecord() {
		return this.rsTransferrecord;
	}

	public void setRsTransferrecord(RsTransferrecord rsTransferrecord) {
		this.rsTransferrecord = rsTransferrecord;
	}

}