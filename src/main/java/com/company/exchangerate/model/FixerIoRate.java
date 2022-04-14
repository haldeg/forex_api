package com.company.exchangerate.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 
 * Exchange rates received from fixer.io
 *
 */
public class FixerIoRate {

	private Date date;
	private Map<String, BigDecimal> rates;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Map<String, BigDecimal> getRates() {
		return rates;
	}

	public void setRates(Map<String, BigDecimal> rates) {
		this.rates = rates;
	}

	@Override
	public String toString() {
		return "FixerIoRate [date=" + date + ", rates=" + rates + "]";
	}
}
