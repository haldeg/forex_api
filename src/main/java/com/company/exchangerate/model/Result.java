package com.company.exchangerate.model;

/**
 * 
 * Response to user
 *
 */
public class Result {

	private String from;
	private String to;
	private String exchange;
	
	public Result(String from, String to, String exchange) {
		super();
		this.from = from;
		this.to = to;
		this.exchange = exchange;
	}

	public Result() {
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	@Override
	public String toString() {
		return "Result [from=" + from + ", to=" + to + ", exchange=" + exchange + "]";
	}
	
	
}
