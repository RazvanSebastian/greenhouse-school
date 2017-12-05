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

	private String temperature;

	private String humidity;

	private Date date;

	public Sensor() {
		super();
	}

	public Sensor(String temperature, String humidity, Date date) {
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

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
