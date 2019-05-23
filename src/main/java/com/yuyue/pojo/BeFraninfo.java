package com.yuyue.pojo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the be_franinfo database table.
 * 
 */
@Entity
@Table(name="be_franinfo")
@NamedQuery(name="BeFraninfo.findAll", query="SELECT b FROM BeFraninfo b")
public class BeFraninfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="account_name")
	private String accountName;

	@Column(name="account_number")
	private String accountNumber;

	private String bank;

	@Column(name="commu_address")
	private String commuAddress;

	@Column(name="contact_mailbox")
	private String contactMailbox;

	@Column(name="contact_name")
	private String contactName;

	@Column(name="contact_phone")
	private String contactPhone;

	@Column(name="founde_time")
	private String foundeTime;

	private String founder;

	@Column(name="fran_id")
	private String franId;

	@Column(name="id_number")
	private String idNumber;

	@Column(name="id_type")
	private String idType;

	@Column(name="legal_person")
	private String legalPerson;

	@Column(name="legal_person_mailbox")
	private String legalPersonMailbox;

	@Column(name="legal_person_phone")
	private String legalPersonPhone;

	@Column(name="modify_person")
	private String modifyPerson;

	@Column(name="modify_time")
	private String modifyTime;

	private String name;

	private String rank;

	private String remark;

	@Column(name="resginster_address")
	private String resginsterAddress;

	private String state;

	private String type;

	public BeFraninfo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBank() {
		return this.bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getCommuAddress() {
		return this.commuAddress;
	}

	public void setCommuAddress(String commuAddress) {
		this.commuAddress = commuAddress;
	}

	public String getContactMailbox() {
		return this.contactMailbox;
	}

	public void setContactMailbox(String contactMailbox) {
		this.contactMailbox = contactMailbox;
	}

	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getFoundeTime() {
		return this.foundeTime;
	}

	public void setFoundeTime(String foundeTime) {
		this.foundeTime = foundeTime;
	}

	public String getFounder() {
		return this.founder;
	}

	public void setFounder(String founder) {
		this.founder = founder;
	}

	public String getFranId() {
		return this.franId;
	}

	public void setFranId(String franId) {
		this.franId = franId;
	}

	public String getIdNumber() {
		return this.idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getIdType() {
		return this.idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getLegalPerson() {
		return this.legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getLegalPersonMailbox() {
		return this.legalPersonMailbox;
	}

	public void setLegalPersonMailbox(String legalPersonMailbox) {
		this.legalPersonMailbox = legalPersonMailbox;
	}

	public String getLegalPersonPhone() {
		return this.legalPersonPhone;
	}

	public void setLegalPersonPhone(String legalPersonPhone) {
		this.legalPersonPhone = legalPersonPhone;
	}

	public String getModifyPerson() {
		return this.modifyPerson;
	}

	public void setModifyPerson(String modifyPerson) {
		this.modifyPerson = modifyPerson;
	}

	public String getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRank() {
		return this.rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getResginsterAddress() {
		return this.resginsterAddress;
	}

	public void setResginsterAddress(String resginsterAddress) {
		this.resginsterAddress = resginsterAddress;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}