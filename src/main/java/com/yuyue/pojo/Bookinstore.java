package com.yuyue.pojo;

import java.io.Serializable;
import java.math.BigInteger;

public class Bookinstore implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigInteger bookId;
	
	private String rfid;
	
	private String code;
	
	private String isbn;
	
	private Integer locationId;
	
	private String locationName;
	
	private Float price;
	
	private Integer locationId2;
	
	private String locationName2;
	
	private Integer storageitemId;
	
	private Integer transferitemId;
	
	private Byte isDelete;

	public Integer getTransferitemId() {
		return transferitemId;
	}

	public void setTransferitemId(Integer transferitemId) {
		this.transferitemId = transferitemId;
	}

	public Integer getStorageitemId() {
		return storageitemId;
	}

	public void setStorageitemId(Integer storageitemId) {
		this.storageitemId = storageitemId;
	}

	public Byte getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Byte isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public Integer getLocationId2() {
		return locationId2;
	}

	public void setLocationId2(Integer locationId2) {
		this.locationId2 = locationId2;
	}

	public String getLocationName2() {
		return locationName2;
	}

	public void setLocationName2(String locationName2) {
		this.locationName2 = locationName2;
	}

	public String getLocationName() {
		return locationName;
	}

	public BigInteger getBookId() {
		return bookId;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public void setBookId(BigInteger bookId) {
		this.bookId = bookId;
	}

	public String getRfid() {
		return rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

}
