package com.yuyue.pojo;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.util.List;


/**
 * The persistent class for the be_department database table.
 * 
 */
/**
 * 部门表
 *
 */
@Entity
@Table(name="be_department")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
@NamedQuery(name="BeDepartment.findAll", query="SELECT b FROM BeDepartment b")
public class BeDepartment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	@JsonBackReference("beUsers")
	@OneToMany(mappedBy="beDepartment",fetch=FetchType.LAZY)
	private List<BeUser> beUsers;
	
	@JsonBackReference("beWarehouses")
	@OneToMany(mappedBy="beDepartment", cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	private List<BeWarehouse> beWarehouses;

	public BeDepartment() {
	}

	public List<BeWarehouse> getBeWarehouses() {
		return beWarehouses;
	}

	public void setBeWarehouses(List<BeWarehouse> beWarehouses) {
		this.beWarehouses = beWarehouses;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<BeUser> getBeUsers() {
		return this.beUsers;
	}

	public void setBeUsers(List<BeUser> beUsers) {
		this.beUsers = beUsers;
	}

	public BeUser addBeUser(BeUser beUser) {
		getBeUsers().add(beUser);
		beUser.setBeDepartment(this);

		return beUser;
	}

	public BeUser removeBeUser(BeUser beUser) {
		getBeUsers().remove(beUser);
		beUser.setBeDepartment(null);

		return beUser;
	}

}