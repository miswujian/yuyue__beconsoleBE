package com.yuyue.pojo;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;


/**
 * The persistent class for the be_institution database table.
 * 
 */
/**
 * 机构表
 *
 */
@Entity
@Table(name="be_institution")
@NamedQuery(name="BeInstitution.findAll", query="SELECT b FROM BeInstitution b")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
public class BeInstitution implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer lever;

	private String name;

	//bi-directional many-to-one association to BeInstitution
	@ManyToOne(fetch=FetchType.LAZY)
	@ApiModelProperty(hidden = true) 
	@JoinColumn(name="parentid")
	private BeInstitution beInstitution;
	
	@Transient
	private List<BeInstitution> beInstitutions;
	
	@Transient
	private List<User> users;

	public BeInstitution() {
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLever() {
		return this.lever;
	}

	public void setLever(Integer lever) {
		this.lever = lever;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BeInstitution getBeInstitution() {
		return this.beInstitution;
	}

	public void setBeInstitution(BeInstitution beInstitution) {
		this.beInstitution = beInstitution;
	}

	public List<BeInstitution> getBeInstitutions() {
		return this.beInstitutions;
	}

	public void setBeInstitutions(List<BeInstitution> beInstitutions) {
		this.beInstitutions = beInstitutions;
	}

	public BeInstitution addBeInstitution(BeInstitution beInstitution) {
		getBeInstitutions().add(beInstitution);
		beInstitution.setBeInstitution(this);

		return beInstitution;
	}

	public BeInstitution removeBeInstitution(BeInstitution beInstitution) {
		getBeInstitutions().remove(beInstitution);
		beInstitution.setBeInstitution(null);

		return beInstitution;
	}

}