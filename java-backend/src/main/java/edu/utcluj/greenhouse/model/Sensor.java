package edu.utcluj.greenhouse.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sensor_dht11")
public class Sensor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private int temperature;

	private int humidity;

	private Date date;

	public Sensor() {
		super();
	}

	public Sensor(int temperature, int humidity, Date date) {
		super();
		this.temperature = temperature;
		this.humidity = humidity;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
