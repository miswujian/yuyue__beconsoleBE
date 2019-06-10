package com.yuyue.pojo;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;


/**
 * The persistent class for the rs_rolepermission database table.
 * 
 */
/**
 * 角色权限关系表
 *
 */
@Entity
@Table(name="rs_rolepermission")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
@NamedQuery(name="RsRolepermission.findAll", query="SELECT r FROM RsRolepermission r")
public class RsRolepermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	//bi-directional many-to-one association to BePermission
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JoinColumn(name="pid")
	private BePermission bePermission;

	//bi-directional many-to-one association to BeRole
	@ManyToOne
	@JsonBackReference("beRole")
	@ApiModelProperty(hidden = true)
	@JoinColumn(name="rid")
	private BeRole beRole;
	
	private Integer curd;

	public Integer getCurd() {
		return curd;
	}

	public void setCurd(Integer curd) {
		this.curd = curd;
	}

	public RsRolepermission() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BePermission getBePermission() {
		return this.bePermission;
	}

	public void setBePermission(BePermission bePermission) {
		this.bePermission = bePermission;
	}

	public BeRole getBeRole() {
		return this.beRole;
	}

	public void setBeRole(BeRole beRole) {
		this.beRole = beRole;
	}

}