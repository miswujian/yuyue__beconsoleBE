package com.yuyue.pojo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the rs_bookgene database table.
 * 
 */
@Embeddable
public class RsBookgenePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="bookinfo_id")
	private Integer bookinfoId;

	@Column(name="gene_id")
	private Integer geneId;

	public RsBookgenePK() {
	}
	public Integer getBookinfoId() {
		return this.bookinfoId;
	}
	public void setBookinfoId(Integer bookinfoId) {
		this.bookinfoId = bookinfoId;
	}
	public Integer getGeneId() {
		return this.geneId;
	}
	public void setGeneId(Integer geneId) {
		this.geneId = geneId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RsBookgenePK)) {
			return false;
		}
		RsBookgenePK castOther = (RsBookgenePK)other;
		return 
			(this.bookinfoId == castOther.bookinfoId)
			&& (this.geneId == castOther.geneId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.bookinfoId;
		hash = hash * prime + this.geneId;
		
		return hash;
	}
}