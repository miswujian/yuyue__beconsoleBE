package com.yuyue.pojo;

import java.io.Serializable;
import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;


/**
 * The persistent class for the rs_bookcaserecorditem database table.
 * 
 */
@Entity
@Table(name="rs_bookcaserecorditem")
@NamedQuery(name="RsBookcaserecorditem.findAll", query="SELECT r FROM RsBookcaserecorditem r")
public class RsBookcaserecorditem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="bookcaseitem_id")
	private Integer bookcaseitemId;

	private byte status;

	//bi-directional many-to-one association to BsBookinstore
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JoinColumn(name="book_id")
	private BsBookinstore bsBookinstore;

	//bi-directional many-to-one association to RsBookcaserecord
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JoinColumn(name="bookcase_id")
	private RsBookcaserecord rsBookcaserecord;

	//bi-directional many-to-one association to BsBookcellinfo
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JoinColumn(name="cell_id")
	private BsBookcellinfo bsBookcellinfo;

	//bi-directional many-to-one association to BeLocation
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JoinColumn(name="location_id")
	private BeLocation beLocation;
	
	@Transient
	private String bookName;
	
	@Transient
	private String rfid;
	
	@Transient
	private Byte isDelete;

	public RsBookcaserecorditem() {
	}

	public Byte getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Byte isDelete) {
		this.isDelete = isDelete;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getRfid() {
		return rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	public Integer getBookcaseitemId() {
		return this.bookcaseitemId;
	}

	public void setBookcaseitemId(Integer bookcaseitemId) {
		this.bookcaseitemId = bookcaseitemId;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public BsBookinstore getBsBookinstore() {
		return this.bsBookinstore;
	}

	public void setBsBookinstore(BsBookinstore bsBookinstore) {
		this.bsBookinstore = bsBookinstore;
	}

	public RsBookcaserecord getRsBookcaserecord() {
		return this.rsBookcaserecord;
	}

	public void setRsBookcaserecord(RsBookcaserecord rsBookcaserecord) {
		this.rsBookcaserecord = rsBookcaserecord;
	}

	public BsBookcellinfo getBsBookcellinfo() {
		return this.bsBookcellinfo;
	}

	public void setBsBookcellinfo(BsBookcellinfo bsBookcellinfo) {
		this.bsBookcellinfo = bsBookcellinfo;
	}

	public BeLocation getBeLocation() {
		return this.beLocation;
	}

	public void setBeLocation(BeLocation beLocation) {
		this.beLocation = beLocation;
	}

}