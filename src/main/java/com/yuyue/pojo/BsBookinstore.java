package com.yuyue.pojo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;


/**
 * The persistent class for the bs_bookinstore database table.
 * 
 */
/**
 * 书籍库存信息表
 *
 */
@Entity
@Table(name="bs_bookinstore")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
@NamedQuery(name="BsBookinstore.findAll", query="SELECT b FROM BsBookinstore b")
public class BsBookinstore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="book_id")
	private BigInteger bookId;

	private String index_id;

	@Column(name="is_donate")
	private byte isDonate;

	private String rfid;

	private byte status;
	
	private String code;

	//bi-directional many-to-one association to BsBookinfo
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JoinColumn(name="bookinfo_id")
	private BsBookinfo bsBookinfo;

	//bi-directional many-to-one association to BsBookcellinfo
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JoinColumn(name="cell_id")
	private BsBookcellinfo bsBookcellinfo;
	
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JoinColumn(name="location_id")
	private BeLocation beLocation;
	
	@OneToMany(mappedBy="bsBookinstore")
	@JsonBackReference("rsStoragerecorditems")
	private List<RsStoragerecorditem> rsStoragerecorditems;
	
	@Transient
	private String caseName;
	
	@Transient
	private Integer cellId;
	
	@Transient
	private String categoryName;

	//bi-directional many-to-one association to RsCurborrowrecord
	/*@OneToMany(mappedBy="bsBookinstore")
	private List<RsCurborrowrecord> rsCurborrowrecords;*/

	//bi-directional many-to-one association to RsHisborrowrecord
	/*@OneToMany(mappedBy="bsBookinstore")
	private List<RsHisborrowrecord> rsHisborrowrecords;*/

	public BsBookinstore() {
	}

	public List<RsStoragerecorditem> getRsStoragerecorditems() {
		return rsStoragerecorditems;
	}

	public void setRsStoragerecorditems(List<RsStoragerecorditem> rsStoragerecorditems) {
		this.rsStoragerecorditems = rsStoragerecorditems;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public Integer getCellId() {
		return cellId;
	}

	public void setCellId(Integer cellId) {
		this.cellId = cellId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BeLocation getBeLocation() {
		return beLocation;
	}

	public void setBeLocation(BeLocation beLocation) {
		this.beLocation = beLocation;
	}

	public BigInteger getBookId() {
		return this.bookId;
	}

	public void setBookId(BigInteger bookId) {
		this.bookId = bookId;
	}

	public String getIndex_id() {
		return this.index_id;
	}

	public void setIndex_id(String index_id) {
		this.index_id = index_id;
	}

	public byte getIsDonate() {
		return this.isDonate;
	}

	public void setIsDonate(byte isDonate) {
		this.isDonate = isDonate;
	}

	public String getRfid() {
		return this.rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public BsBookinfo getBsBookinfo() {
		return this.bsBookinfo;
	}

	public void setBsBookinfo(BsBookinfo bsBookinfo) {
		this.bsBookinfo = bsBookinfo;
	}

	public BsBookcellinfo getBsBookcellinfo() {
		return this.bsBookcellinfo;
	}

	public void setBsBookcellinfo(BsBookcellinfo bsBookcellinfo) {
		this.bsBookcellinfo = bsBookcellinfo;
	}

	/*public List<RsCurborrowrecord> getRsCurborrowrecords() {
		return this.rsCurborrowrecords;
	}

	public void setRsCurborrowrecords(List<RsCurborrowrecord> rsCurborrowrecords) {
		this.rsCurborrowrecords = rsCurborrowrecords;
	}

	public RsCurborrowrecord addRsCurborrowrecord(RsCurborrowrecord rsCurborrowrecord) {
		getRsCurborrowrecords().add(rsCurborrowrecord);
		rsCurborrowrecord.setBsBookinstore(this);

		return rsCurborrowrecord;
	}

	public RsCurborrowrecord removeRsCurborrowrecord(RsCurborrowrecord rsCurborrowrecord) {
		getRsCurborrowrecords().remove(rsCurborrowrecord);
		rsCurborrowrecord.setBsBookinstore(null);

		return rsCurborrowrecord;
	}

	public List<RsHisborrowrecord> getRsHisborrowrecords() {
		return this.rsHisborrowrecords;
	}

	public void setRsHisborrowrecords(List<RsHisborrowrecord> rsHisborrowrecords) {
		this.rsHisborrowrecords = rsHisborrowrecords;
	}

	public RsHisborrowrecord addRsHisborrowrecord(RsHisborrowrecord rsHisborrowrecord) {
		getRsHisborrowrecords().add(rsHisborrowrecord);
		rsHisborrowrecord.setBsBookinstore(this);

		return rsHisborrowrecord;
	}

	public RsHisborrowrecord removeRsHisborrowrecord(RsHisborrowrecord rsHisborrowrecord) {
		getRsHisborrowrecords().remove(rsHisborrowrecord);
		rsHisborrowrecord.setBsBookinstore(null);

		return rsHisborrowrecord;
	}*/

}