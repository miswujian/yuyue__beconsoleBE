package com.yuyue.pojo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the be_franrank database table.
 * 
 */
@Entity
@Table(name="be_franrank")
@NamedQuery(name="BeFranrank.findAll", query="SELECT b FROM BeFranrank b")
public class BeFranrank implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String discount;

	private String dividend;

	@Column(name="rank_name")
	private String rankName;

	public BeFranrank() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDiscount() {
		return this.discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getDividend() {
		return this.dividend;
	}

	public void setDividend(String dividend) {
		this.dividend = dividend;
	}

	public String getRankName() {
		return this.rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

}