package com.company.exchangerate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ExchangerateApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ExchangerateApplication.class, args);
	}
}
