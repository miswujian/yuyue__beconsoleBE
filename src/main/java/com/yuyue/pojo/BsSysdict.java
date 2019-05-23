package com.yuyue.pojo;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;


/**
 * The persistent class for the bs_sysdict database table.
 * 
 */
@Entity
@Table(name="bs_sysdict")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
@NamedQuery(name="BsSysdict.findAll", query="SELECT b FROM BsSysdict b")
public class BsSysdict implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="dict_id")
	private int dictId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="dict_key")
	private String dictKey;

	@Column(name="dict_name")
	private String dictName;

	@Column(name="dict_type")
	private String dictType;

	@Column(name="dict_value")
	private String dictValue;

	private String remark;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	public BsSysdict() {
	}

	public int getDictId() {
		return this.dictId;
	}

	public void setDictId(int dictId) {
		this.dictId = dictId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDictKey() {
		return this.dictKey;
	}

	public void setDictKey(String dictKey) {
		this.dictKey = dictKey;
	}

	public String getDictName() {
		return this.dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getDictType() {
		return this.dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getDictValue() {
		return this.dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}