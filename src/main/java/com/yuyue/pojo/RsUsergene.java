package com.yuyue.pojo;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the rs_usergene database table.
 * 
 */
@Entity
@Table(name="rs_usergene")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
@NamedQuery(name="RsUsergene.findAll", query="SELECT r FROM RsUsergene r")
public class RsUsergene implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="usergene_id")
	private Integer usergeneId;

	//bi-directional many-to-one association to BsGene
	@ManyToOne
	@JoinColumn(name="gene_id")
	private BsGene bsGene;

	//bi-directional many-to-one association to BsUserinfo
	@ManyToOne
	@JoinColumn(name="user_id")
	private BsUserinfo bsUserinfo;

	public RsUsergene() {
	}

	public Integer getUsergeneId() {
		return this.usergeneId;
	}

	public void setUsergeneId(Integer usergeneId) {
		this.usergeneId = usergeneId;
	}

	public BsGene getBsGene() {
		return this.bsGene;
	}

	public void setBsGene(BsGene bsGene) {
		this.bsGene = bsGene;
	}

	public BsUserinfo getBsUserinfo() {
		return this.bsUserinfo;
	}

	public void setBsUserinfo(BsUserinfo bsUserinfo) {
		this.bsUserinfo = bsUserinfo;
	}

}