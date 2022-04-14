package com.company.exchangerate.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * Db definition for fixer.io rates
 *
 */
@Entity
@Table(name = "forex_rate")
@JsonIgnoreProperties
public class ForexRate {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "rate_date")
	private Date rateDate;
	
	private String currency;
	
	@Column(name = "rate_value")
	private BigDecimal rateValue;
	
	private int counter;

	public ForexRate() {
	}
			
	public ForexRate(int id, Date rateDate, String currency, BigDecimal rateValue, int counter) {
		super();
		this.id = id;
		this.rateDate = rateDate;
		this.currency = currency;
		this.rateValue = rateValue;
		this.counter = counter;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getRateDate() {
		return rateDate;
	}

	public void setRateDate(Date rateDate) {
		this.rateDate = rateDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getRateValue() {
		return rateValue;
	}

	public void setRateValue(BigDecimal rateValue) {
		this.rateValue = rateValue;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	@Override
	public String toString() {
		return "ForexRate [id=" + id + ", rateDate=" + rateDate + ", currency=" + currency + ", rateValue=" + rateValue
				+ ", counter=" + counter + "]";
	}
}
