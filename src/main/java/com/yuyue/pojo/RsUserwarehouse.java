package com.yuyue.pojo;

import java.io.Serializable;
import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;


/**
 * The persistent class for the rs_userwarehouse database table.
 * 
 */
@Entity
@Table(name="rs_userwarehouse")
@NamedQuery(name="RsUserwarehouse.findAll", query="SELECT r FROM RsUserwarehouse r")
public class RsUserwarehouse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="uw_id")
	private int uwId;

	//bi-directional many-to-one association to BeUser
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JoinColumn(name="uid")
	private BeUser beUser;

	//bi-directional many-to-one association to BeWarehouse
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JoinColumn(name="warehouse_id")
	private BeWarehouse beWarehouse;

	//bi-directional many-to-one association to BsBookcaseinfo
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JoinColumn(name="case_id")
	private BsBookcaseinfo bsBookcaseinfo;
	
	private byte type;

	public RsUserwarehouse() {
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public int getUwId() {
		return this.uwId;
	}

	public void setUwId(int uwId) {
		this.uwId = uwId;
	}

	public BeUser getBeUser() {
		return this.beUser;
	}

	public void setBeUser(BeUser beUser) {
		this.beUser = beUser;
	}

	public BeWarehouse getBeWarehouse() {
		return this.beWarehouse;
	}

	public void setBeWarehouse(BeWarehouse beWarehouse) {
		this.beWarehouse = beWarehouse;
	}

	public BsBookcaseinfo getBsBookcaseinfo() {
		return this.bsBookcaseinfo;
	}

	public void setBsBookcaseinfo(BsBookcaseinfo bsBookcaseinfo) {
		this.bsBookcaseinfo = bsBookcaseinfo;
	}

}