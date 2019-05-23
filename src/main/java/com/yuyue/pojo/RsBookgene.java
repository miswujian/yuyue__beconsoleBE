package com.yuyue.pojo;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the rs_bookgene database table.
 * 
 */
@Entity
@Table(name="rs_bookgene")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
@NamedQuery(name="RsBookgene.findAll", query="SELECT r FROM RsBookgene r")
public class RsBookgene implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RsBookgenePK id;

	public RsBookgene() {
	}

	public RsBookgenePK getId() {
		return this.id;
	}

	public void setId(RsBookgenePK id) {
		this.id = id;
	}

}