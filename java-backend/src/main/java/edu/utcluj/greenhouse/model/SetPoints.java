package edu.utcluj.greenhouse.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class SetPoints {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Min(20)
	@Max(35)
	private int temperatureSetPoint;

	@NotNull
	@Min(40)
	@Max(70)
	private int humiditySetPoint;

	private Date date;

	public SetPoints() {
		super();
	}

	public SetPoints(int temperatureSetPoint, int humiditySetPoint, Date date) {
		super();
		this.temperatureSetPoint = temperatureSetPoint;
		this.humiditySetPoint = humiditySetPoint;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getTemperatureSetPoint() {
		return temperatureSetPoint;
	}

	public void setTemperatureSetPoint(int temperatureSetPoint) {
		this.temperatureSetPoint = temperatureSetPoint;
	}

	public int getHumiditySetPoint() {
		return humiditySetPoint;
	}

	public void setHumiditySetPoint(int humiditySetPoint) {
		this.humiditySetPoint = humiditySetPoint;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
