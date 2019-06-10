package com.yuyue.pojo;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


/**
 * The persistent class for the bs_gene database table.
 * 
 */
@Entity
@Table(name="bs_gene")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
@NamedQuery(name="BsGene.findAll", query="SELECT b FROM BsGene b")
public class BsGene implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="gene_id")
	private Integer geneId;

	private String name;
	
	private Integer sort;

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
		name="rs_bookgene"
		, joinColumns={
			@JoinColumn(name="gene_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="bookinfo_id")
			}
		)
	@JsonBackReference("bsBookinfos")
	private List<BsBookinfo> bsBookinfos;

	@OneToMany(mappedBy="bsGene" ,fetch=FetchType.LAZY)
	@JsonBackReference("rsUsergenes")
	private List<RsUsergene> rsUsergenes;

	public BsGene() {
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getGeneId() {
		return this.geneId;
	}

	public void setGeneId(Integer geneId) {
		this.geneId = geneId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<BsBookinfo> getBsBookinfos() {
		return this.bsBookinfos;
	}

	public void setBsBookinfos(List<BsBookinfo> bsBookinfos) {
		this.bsBookinfos = bsBookinfos;
	}

	public List<RsUsergene> getRsUsergenes() {
		return this.rsUsergenes;
	}

	public void setRsUsergenes(List<RsUsergene> rsUsergenes) {
		this.rsUsergenes = rsUsergenes;
	}

	public RsUsergene addRsUsergene(RsUsergene rsUsergene) {
		getRsUsergenes().add(rsUsergene);
		rsUsergene.setBsGene(this);

		return rsUsergene;
	}

	public RsUsergene removeRsUsergene(RsUsergene rsUsergene) {
		getRsUsergenes().remove(rsUsergene);
		rsUsergene.setBsGene(null);

		return rsUsergene;
	}

}