package com.company.exchangerate.model;

import java.math.BigDecimal;
import javax.persistence.*;

/**
 * 
 * Db definition for spreads
 *
 */
@Entity
@Table(name = "spread")
public class Spread {

	@Id
    private String currency;

	@Column(name = "spread_percentage")
    private BigDecimal spreadPercentage;
	
	public Spread() {
	}
	
	public Spread(String currency, BigDecimal spreadPercentage) {
		this.currency = currency;
		this.spreadPercentage = spreadPercentage;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getSpreadPercentage() {
		return spreadPercentage;
	}

	public void setSpreadPercentage(BigDecimal spreadPercentage) {
		this.spreadPercentage = spreadPercentage;
	}

	@Override
	public String toString() {
		return "Spread [currency=" + currency + ", spreadPercentage=" + spreadPercentage + "]";
	}
}

