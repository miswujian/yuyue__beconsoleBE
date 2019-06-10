package com.yuyue.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

/**
 * The persistent class for the be_user database table.
 * 
 */
/**
 * 用户表
 *
 */
@Entity
@Table(name="be_user")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
@NamedQuery(name="BeUser.findAll", query="SELECT b FROM BeUser b")
public class BeUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer uid;

	@Column(name = "name")
	private String userName;
	
	private String telephone;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date registrationtime;

	private byte status;
	
	private String password;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="auth")
	@JsonBackReference("beRole")
	@ApiModelProperty(hidden = true)
	private BeRole beRole;
	
	@ManyToOne
	@ApiModelProperty(hidden = true)
	@JoinColumn(name="departmentid")
	private BeDepartment beDepartment;

	@ManyToOne
	@ApiModelProperty(hidden = true)
	@JoinColumn(name="institutionid")
	private BeInstitution beInstitution;
	
	@OneToMany(mappedBy="beUser",fetch=FetchType.LAZY)
	@JsonBackReference(value = "bsBookcaseinfos")
	private List<BsBookcaseinfo> bsBookcaseinfos;
	
	@OneToMany(mappedBy="beUser" ,fetch=FetchType.LAZY)
	@JsonBackReference("rsUserwarehouses")
	private List<RsUserwarehouse> rsUserwarehouses;
	
	@OneToMany(mappedBy="beUser1",fetch=FetchType.LAZY)
	@JsonBackReference("rsStoragerecords1")
	private List<RsStoragerecord> rsStoragerecords1;

	//bi-directional many-to-one association to RsStoragerecord
	@OneToMany(mappedBy="beUser2",fetch=FetchType.LAZY)
	@JsonBackReference("rsStoragerecords2")
	private List<RsStoragerecord> rsStoragerecords2;
	
	/*@OneToMany(mappedBy="beUser1")
	@JsonBackReference("rsTransferrecords1")
	private List<RsTransferrecord> rsTransferrecords1;

	//bi-directional many-to-one association to RsTransferrecord
	@OneToMany(mappedBy="beUser2")
	@JsonBackReference("rsTransferrecords2")
	private List<RsTransferrecord> rsTransferrecords2;*/
	
	@Transient
	private String role;
	
	/*@Transient
	private Integer roleId;*/
	
	@Transient
	private List<String> permissions;
	
	@Transient
	private Integer roleType;

	/*public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}*/

	public List<BsBookcaseinfo> getBsBookcaseinfos() {
		return bsBookcaseinfos;
	}

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

	public List<RsStoragerecord> getRsStoragerecords1() {
		return rsStoragerecords1;
	}

	public void setRsStoragerecords1(List<RsStoragerecord> rsStoragerecords1) {
		this.rsStoragerecords1 = rsStoragerecords1;
	}

	public List<RsStoragerecord> getRsStoragerecords2() {
		return rsStoragerecords2;
	}

	public void setRsStoragerecords2(List<RsStoragerecord> rsStoragerecords2) {
		this.rsStoragerecords2 = rsStoragerecords2;
	}

	public List<RsUserwarehouse> getRsUserwarehouses() {
		return rsUserwarehouses;
	}

	public void setRsUserwarehouses(List<RsUserwarehouse> rsUserwarehouses) {
		this.rsUserwarehouses = rsUserwarehouses;
	}

	public void setBsBookcaseinfos(List<BsBookcaseinfo> bsBookcaseinfos) {
		this.bsBookcaseinfos = bsBookcaseinfos;
	}

	public Date getRegistrationtime() {
		return registrationtime;
	}

	public void setRegistrationtime(Date registrationtime) {
		this.registrationtime = registrationtime;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

	public BeUser() {
	}

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BeRole getBeRole() {
		return this.beRole;
	}

	public void setBeRole(BeRole beRole) {
		this.beRole = beRole;
	}

	public BeDepartment getBeDepartment() {
		return this.beDepartment;
	}

	public void setBeDepartment(BeDepartment beDepartment) {
		this.beDepartment = beDepartment;
	}

	public BeInstitution getBeInstitution() {
		return this.beInstitution;
	}

	public void setBeInstitution(BeInstitution beInstitution) {
		this.beInstitution = beInstitution;
	}
	

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
}