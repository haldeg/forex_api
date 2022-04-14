package com.company.exchangerate.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.company.exchangerate.model.ForexRate;

@Repository
public interface ForexRateRepository extends JpaRepository<ForexRate, Integer> {

	@Query(value = "select * from exchangerate.forex_rate f WHERE f.rate_date = :date and (f.currency = :from or f.currency = :to)" , nativeQuery = true)
	List<ForexRate> getForexRate(Date date, String from, String to);
	
	@Query(value = "select * from exchangerate.forex_rate f WHERE f.rate_date = (select max(rate_date) from exchangerate.forex_rate) and (f.currency = :from or f.currency = :to)" , nativeQuery = true)
	List<ForexRate> getForexRate(String from, String to);
	
	@Modifying
	@Query(value = "update exchangerate.forex_rate set counter = counter + 1 where rate_date = :date and (currency = :from or currency = :to)" , nativeQuery = true)
	void incrementCounter(Date date, String from, String to);
	
	@Modifying
	@Query(value = "insert into exchangerate.forex_rate (rate_date,currency,rate_value) values (:date,:currency,:rate) on conflict on constraint rate_unique do update set rate_value = :rate", nativeQuery = true)
	void updateRate(Date date, String currency, BigDecimal rate);
}
