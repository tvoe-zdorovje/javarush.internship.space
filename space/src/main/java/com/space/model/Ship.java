package com.space.model;

import jdk.nashorn.internal.objects.annotations.Setter;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;


@Entity
//@Table(name = "ship")
public class Ship {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Basic
	private String name;

	@Basic
	private String planet;

		@Basic
	@Enumerated(EnumType.STRING)
	private ShipType shipType;

		@Basic
	private Date prodDate;

	@Basic
	private Boolean isUsed;

	@Basic
	private Double speed;

	@Basic
	private Integer crewSize;

	@Basic
	private Double rating;

	public Ship() {
	}

	public Ship(String name, String planet, ShipType shipType, Date prodDate, Boolean isUsed, Double speed, Integer crewSize) {
		this.name = name;
		this.planet = planet;
		this.shipType = shipType;
//		this.prodDate = new Date();
//		setProdDate(prodDate);
		this.prodDate = prodDate;
		this.isUsed = isUsed;
		this.speed = speed;
		this.crewSize = crewSize;
		createRating();
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlanet() {
		return planet;
	}

	public void setPlanet(String planet) {
		this.planet = planet;
	}

	public ShipType getShipType() {
		return shipType;
	}

	public void setShipType(ShipType shipType) {
		this.shipType = shipType;
	}

	public Date getProdDate() {
		return prodDate;
	}

	public void setProdDate(Date prodDate) {
//		try {
//			this.prodDate.setTime(new SimpleDateFormat("yyy-MM-dd").parse(prodDate).getTime());
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}

		this.prodDate = prodDate;
	}

	public Boolean isUsed() {
		return isUsed;
	}

	public void setUsed(Boolean used) {
		this.isUsed = used == null ? false : used;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Integer getCrewSize() {
		return crewSize;
	}

	public void setCrewSize(Integer crewSize) {
		this.crewSize = crewSize;
	}

	public Double getRating() {
		return rating;
	}

	@Setter(name = "rating")
	public void createRating(){
		float k = isUsed ? 0.5f : 1f;
		this.rating = (80f*speed*k)/((float)(1119-prodDate.getYear())+1);
		this.rating = Math.round(rating*100)/100d;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
