package com.company.exchangerate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.company.exchangerate.model.ForexRate;
import com.company.exchangerate.model.Spread;
import com.company.exchangerate.repository.ForexRateRepository;
import com.company.exchangerate.repository.SpreadRepository;

@ExtendWith(MockitoExtension.class)
public class ServiceTests {
	
	@Mock
	ForexRateRepository forexRateRepository;
	
	@Mock
	SpreadRepository spreadRepository;
	
	ForexRate forexRate1;
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date date = null;
    
    @BeforeEach
    public void setUp() {
    	try {
			date = formatter.parse("2022-04-14");
		} catch (ParseException e) {
			e.printStackTrace();
		}
    }
    
	@Test
    public void testSaveFixerIoRate()
    {
		ForexRate forexRate = new ForexRate();
		forexRate.setRateDate(date);
		forexRate.setCurrency("TRY");
		forexRate.setRateValue(BigDecimal.valueOf(15.95));
		forexRateRepository.save(forexRate);
    }
	
	@Test
    public void testFindForexRate()
    {
		List<ForexRate> forexRates = new ArrayList<ForexRate>();
		ForexRate rate1 = new ForexRate(1, date, "JPY", BigDecimal.valueOf(136.234), 0);
		ForexRate rate2 = new ForexRate(2, date, "ZAR", BigDecimal.valueOf(15.123), 0);
		
		forexRates.add(rate1);
		forexRates.add(rate2);
		
        when(forexRateRepository.findAll()).thenReturn(forexRates);
        
        List<ForexRate> forexItems = forexRateRepository.findAll();
          
        assertEquals(2, forexItems.size());
    }
	
	@Test
    public void testFindSpread()
    {	
		List<Spread> spreads = new ArrayList<Spread>();
		Spread spread1 = new Spread("KRW", BigDecimal.valueOf(3.3));
		Spread spread2 = new Spread("MXN", BigDecimal.valueOf(4.4));
		
		spreads.add(spread1);
		spreads.add(spread2);
          
        when(spreadRepository.findAll()).thenReturn(spreads);
        
        List<Spread> spreadItems = spreadRepository.findAll();
          
        assertEquals(2, spreadItems.size());
    }
	
}
