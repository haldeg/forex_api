package com.company.exchangerate.controller;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.company.exchangerate.handler.ResponseHandler;
import com.company.exchangerate.model.Result;
import com.company.exchangerate.service.ForexRateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;

@RestController
public class ForexRateController {
	
	private final ForexRateService forexRateService;
	
	@Autowired
	public ForexRateController(ForexRateService forexRateService) {
		this.forexRateService = forexRateService;
	}
	
	@ApiOperation(value = "Calculate cross exchange rate from(currency) to(currency) for specified date")
	@GetMapping (value = "/exchange")
	public ResponseEntity<Object> getForexRate(@RequestParam String from, @RequestParam String to, 
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {

		BigDecimal forexRate = null;
		try {
			forexRate = forexRateService.getForexRate(date, from, to);
		} catch (Exception e){
			throw new ResponseStatusException( 
					HttpStatus.NOT_FOUND, "the page is not found" 
			); 
		}
		
		Result result = new Result();
		result = setResult(result, from, to, forexRate.toString());
		
		String resultStr = "";
		ObjectMapper mapper = new ObjectMapper();
        try {
        	resultStr = mapper.writeValueAsString(result);;
        } catch (JsonProcessingException e) {
        	System.out.println(e.getMessage());
        }
        
		return ResponseHandler.generateResponse(resultStr, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Receive latest forex rates from fixer.io and update them", httpMethod = "PUT")
	@RequestMapping(value = "/updateRates", method = {RequestMethod.GET, RequestMethod.PUT})
	public ResponseEntity<String> updateForexRate() {
		
		forexRateService.updateForexRate();
		return ResponseEntity.ok("Rates updated");
	}
	
	private Result setResult(Result result, String from, String to, String forexRate) {
		result.setFrom(from);
		result.setTo(to);
		result.setExchange(forexRate);
		return result;
	}
}
