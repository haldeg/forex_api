package com.company.exchangerate.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {
	public static ResponseEntity<Object> generateResponse(String result, HttpStatus status) {
	    return new ResponseEntity<Object>(result, status);
    }
}