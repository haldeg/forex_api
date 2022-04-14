package com.company.exchangerate.service;

import java.math.BigDecimal;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.company.exchangerate.model.FixerIoRate;
import com.company.exchangerate.model.ForexRate;
import com.company.exchangerate.provider.FixerIoRateProvider;
import com.company.exchangerate.repository.ForexRateRepository;

@Service
@Transactional
public class FixerIoRateService {
	
	private FixerIoRateProvider fixerIoRateProvider;
	private ForexRateRepository forexRateRepository;
	
	@Autowired
	public FixerIoRateService(FixerIoRateProvider fixerIoRateProvider,ForexRateRepository forexRateRepository) {
		this.fixerIoRateProvider = fixerIoRateProvider;
		this.forexRateRepository = forexRateRepository;
	}
	
	/*
	 * Get forex rates from fixer.io at 12:05 AM GMT daily basis
	 */
	@Scheduled(cron = "0 5 0 * * *" , zone = "GMT")
	public FixerIoRate getFixerIoRates() {
		
		FixerIoRate fixerIoRate = fixerIoRateProvider.getFixerIoRates();
		
		for (Entry<String, BigDecimal> pair : fixerIoRate.getRates().entrySet()) {
			ForexRate forexRate = new ForexRate();
			forexRate.setRateDate(fixerIoRate.getDate());
			forexRate.setCurrency(pair.getKey());
			forexRate.setRateValue(pair.getValue());
			forexRateRepository.save(forexRate);
		}
		
		return fixerIoRate;
	}
}
