package com.yuyue.pojo;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;


/**
 * The persistent class for the be_cell database table.
 * 
 */
@Entity
@Table(name="be_cell")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
@NamedQuery(name="BeCell.findAll", query="SELECT b FROM BeCell b")
public class BeCell implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cell_id")
	private int cellId;

	private int capacity;

	@Column(name="cell_code")
	private String cellCode;

	@Column(name="cell_x")
	private byte cellX;

	@Column(name="cell_y")
	private byte cellY;

	@Column(name="is_busy")
	private byte isBusy;

	private byte status;

	//bi-directional many-to-one association to BeLocation
	@ManyToOne
	@ApiModelProperty(hidden = true) 
	@JsonBackReference("beLocation")
	@JoinColumn(name="location_id")
	private BeLocation beLocation;

	public BeCell() {
	}

	public int getCellId() {
		return this.cellId;
	}

	public void setCellId(int cellId) {
		this.cellId = cellId;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getCellCode() {
		return this.cellCode;
	}

	public void setCellCode(String cellCode) {
		this.cellCode = cellCode;
	}

	public byte getCellX() {
		return this.cellX;
	}

	public void setCellX(byte cellX) {
		this.cellX = cellX;
	}

	public byte getCellY() {
		return this.cellY;
	}

	public void setCellY(byte cellY) {
		this.cellY = cellY;
	}

	public byte getIsBusy() {
		return this.isBusy;
	}

	public void setIsBusy(byte isBusy) {
		this.isBusy = isBusy;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public BeLocation getBeLocation() {
		return this.beLocation;
	}

	public void setBeLocation(BeLocation beLocation) {
		this.beLocation = beLocation;
	}

}