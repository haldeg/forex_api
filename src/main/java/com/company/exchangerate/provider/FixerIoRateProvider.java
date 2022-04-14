package com.company.exchangerate.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.company.exchangerate.model.FixerIoRate;

@Component
public class FixerIoRateProvider {

	@Value("${fixerio.host}")
	private String host;
	
	@Value("${fixerio.accessKey}")
	private String accessKey;

	private RestTemplate restTemplate = new RestTemplate();

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public FixerIoRateProvider() {
	} 
 
	public FixerIoRate getFixerIoRates() {

		FixerIoRate fixerIoRate = null;
		
		StringBuilder url = new StringBuilder();
		url.append(host);
		url.append("/latest?access_key=");
		url.append(accessKey);
		
		try {
			fixerIoRate = restTemplate.getForObject(url.toString(), FixerIoRate.class);
		} catch (Exception e) {
			logger.error("Failed to receive rates", e);
			throw e;
		}

		logger.info("Rates received from " + url.toString());

		return fixerIoRate;
	}
}