package com.company.exchangerate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.company.exchangerate.controller.ForexRateController;
import com.company.exchangerate.service.FixerIoRateService;
import com.company.exchangerate.service.ForexRateService;

@SpringBootTest
public class ExchangerateApplicationTests {

	@Autowired
	ForexRateController forexRateConroller;
	
	@Autowired
	FixerIoRateService fixerIoRateService;
	
	@Autowired
	ForexRateService forexRateService;
	
	@Test
	public void contextLoads() {
		Assertions.assertThat(forexRateConroller).isNotNull();
		Assertions.assertThat(fixerIoRateService).isNotNull();
		Assertions.assertThat(forexRateService).isNotNull();
	}

}
