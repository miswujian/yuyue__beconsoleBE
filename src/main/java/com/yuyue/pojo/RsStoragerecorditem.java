package com.yuyue.pojo;

import java.io.Serializable;
import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;


/**
 * The persistent class for the rs_storagerecorditem database table.
 * 
 */
/**
 * 
 * 在添加之前 BsBookinstore已经存在了
 *
 */
@Entity
@Table(name="rs_storagerecorditem")
@NamedQuery(name="RsStoragerecorditem.findAll", query="SELECT r FROM RsStoragerecorditem r")
public class RsStoragerecorditem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="storageitem_id")
	private int storageitemId;

	//bi-directional many-to-one association to BsBookinstore
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JoinColumn(name="book_id")
	private BsBookinstore bsBookinstore;

	//bi-directional many-to-one association to RsStoragerecord
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JoinColumn(name="storage_id")
	private RsStoragerecord rsStoragerecord;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="location_id")
	@ApiModelProperty(hidden = true)
	//@JsonBackReference("beLocation")
	private BeLocation beLocation;
	
	private Float price;

	public RsStoragerecorditem() {
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public BeLocation getBeLocation() {
		return beLocation;
	}

	public void setBeLocation(BeLocation beLocation) {
		this.beLocation = beLocation;
	}

	public int getStorageitemId() {
		return this.storageitemId;
	}

	public void setStorageitemId(int storageitemId) {
		this.storageitemId = storageitemId;
	}

	public BsBookinstore getBsBookinstore() {
		return this.bsBookinstore;
	}

	public void setBsBookinstore(BsBookinstore bsBookinstore) {
		this.bsBookinstore = bsBookinstore;
	}

	public RsStoragerecord getRsStoragerecord() {
		return this.rsStoragerecord;
	}

	public void setRsStoragerecord(RsStoragerecord rsStoragerecord) {
		this.rsStoragerecord = rsStoragerecord;
	}

}