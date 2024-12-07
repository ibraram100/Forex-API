package com.ibrahimRamadan.forexAPI.startUp;

import com.ibrahimRamadan.forexAPI.DTO.CurrencyPairDto;
import com.ibrahimRamadan.forexAPI.entity.CurrencyPair;
import com.ibrahimRamadan.forexAPI.repository.CurrencyPairRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

// This class is responsible for initializing some basic currencies upon startup
// it checks if there are no available currencies first, and then it creates those currencies
@Component
public class CurrencyPairInit {
    @Autowired
    private CurrencyPairRepository cpRepository;
    @Autowired
    private ModelMapper modelMapper;
    @PostConstruct
    public void initCurrency()
    {
        System.out.println("fffffffff");

        try {


            List<CurrencyPair> currencyPairs = cpRepository.findAll(); // checking db for avilable currency pairs
            if (currencyPairs.isEmpty()) {
                CurrencyPairDto cpDto = new CurrencyPairDto();
                cpDto.setBaseCurrency("EUR");
                cpDto.setQuoteCurrency("USD");
                cpDto.setSymbol("EUR/USD");
                CurrencyPair currencyPair = modelMapper.map(cpDto, CurrencyPair.class);
                cpRepository.save(currencyPair);
            }
        } catch (Exception e) {
            System.err.println("Error fetching currency pairs: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
