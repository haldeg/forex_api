package com.company.exchangerate.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.exchangerate.model.FixerIoRate;
import com.company.exchangerate.model.ForexRate;
import com.company.exchangerate.model.Spread;
import com.company.exchangerate.provider.FixerIoRateProvider;
import com.company.exchangerate.repository.ForexRateRepository;
import com.company.exchangerate.repository.SpreadRepository;

@Service
@Transactional
public class ForexRateService {
	
	private ForexRateRepository forexRateRepository;
	private SpreadRepository spreadRepository;
	private FixerIoRateProvider fixerIoRateProvider;
	
	//use it for non-existent currencies at spread definitions
	private static final double DEFAULT_SPREAD = 2.75;
	
	@Autowired
	public ForexRateService(ForexRateRepository forexRateRepository, SpreadRepository spreadRepository, FixerIoRateProvider fixerIoRateProvider) {
		this.forexRateRepository = forexRateRepository;
		this.spreadRepository = spreadRepository;
		this.fixerIoRateProvider = fixerIoRateProvider;
	} 
	
	public BigDecimal getForexRate(Date date, String from, String to) {
		
		List<ForexRate> forexRates;
		
		if (date == null) {
			//get the latest day values
			forexRates = forexRateRepository.getForexRate(from, to);
		} else {
			forexRates = forexRateRepository.getForexRate(date, from, to);
		}
		
		BigDecimal fromRate = null;
		BigDecimal toRate = null;
		
		//get rate values of fromCurrency and toCurrency
		for (ForexRate forexRate : forexRates){
			if (from.equalsIgnoreCase(forexRate.getCurrency())) {
				fromRate = forexRate.getRateValue();
		    } else {
		    	toRate = forexRate.getRateValue();
		    	
		    	//if param date is not present then get date to use at counter incrementation
		    	if (date == null) {
		    		date = forexRate.getRateDate();
		    	}
		    }
		}
		
		//get spread value of fromCurrency
		Optional<Spread> fromSpread = spreadRepository.findById(from);
		BigDecimal fromSpreadValue = fromSpread.map(value->value.getSpreadPercentage()).orElse(BigDecimal.valueOf(DEFAULT_SPREAD));
		
		//get spread value of toCurrency
		Optional<Spread> toSpread = spreadRepository.findById(to);
		BigDecimal toSpreadValue = toSpread.map(value->value.getSpreadPercentage()).orElse(BigDecimal.valueOf(DEFAULT_SPREAD));
		
		//calculate rate
		BigDecimal result = calculateExchangeRate(fromRate, toRate, fromSpreadValue, toSpreadValue);
		
		//increment counter
		incrementCounter(date, from, to);
		
		return result;
	}
	
	/*
	 * Exchange rate calculation
	 */
	private BigDecimal calculateExchangeRate(BigDecimal fromRate, BigDecimal toRate, BigDecimal fromSpreadValue, BigDecimal toSpreadValue) {
		
		final BigDecimal ONE_HUNDRED = new BigDecimal(100);
		
		BigDecimal result = (toRate.divide(fromRate, MathContext.DECIMAL128)).multiply((ONE_HUNDRED.subtract(fromSpreadValue.max(toSpreadValue))).divide(ONE_HUNDRED, MathContext.DECIMAL128));
		
		return result;
	}
	
	/*
	 * Counter incrementation
	 */
	private synchronized void incrementCounter(Date date, String from, String to) {
		
		forexRateRepository.incrementCounter(date, from, to);
	}
	
	/*
	 * Receive latest exchange rates and update them
	 */
	public void updateForexRate() {
		
		FixerIoRate fixerIoRate = fixerIoRateProvider.getFixerIoRates();
		
		for (Entry<String, BigDecimal> pair : fixerIoRate.getRates().entrySet()) {
			forexRateRepository.updateRate(fixerIoRate.getDate(), pair.getKey(), pair.getValue());
		}
	}
}
