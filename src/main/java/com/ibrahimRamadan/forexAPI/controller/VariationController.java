package com.ibrahimRamadan.forexAPI.controller;

import com.ibrahimRamadan.forexAPI.entity.CurrencyPair;
import com.ibrahimRamadan.forexAPI.entity.Variation;
import com.ibrahimRamadan.forexAPI.repository.CurrencyPairRepository;
import com.ibrahimRamadan.forexAPI.repository.VariationRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.math.BigDecimal;
import java.math.RoundingMode;

@EnableAsync
@RestController
public class VariationController {
    @Autowired
    private VariationRepository variationRepository;
    @Autowired
    private CurrencyPairRepository cpRepository;
    private static final Random RANDOM = new Random();

    @Async
    @Scheduled(fixedDelay = 1000) // Runs every second
    public void VariationGenerator() {
        String currencySymbol = "EUR/USD";
        CurrencyPair currencyPair = cpRepository.findBySymbol(currencySymbol);
        List<Variation> variations = new ArrayList<>();
        while(true) {
            for (int i = 0; i < 10; i++) {

                try {
                    Thread.sleep(1000); // Sleeping for 10 seconds before creating a new variation

                } catch (InterruptedException e) {
                    e.printStackTrace(); // Print exception stack trace for debugging
                    Thread.currentThread().interrupt(); // Preserve interrupt status
                }
                Variation variation = new Variation();
                variation.setCurrencyPair(currencyPair);
                double price = 1 + roundToSixDecimals(RANDOM.nextDouble());
                variation.setPrice(price);
                variation.setTimeStamp(LocalDateTime.now());
                variations.add(variation);

                System.out.println("Price: " + variation.getPrice());
                System.out.println("Time Stamp: " + variation.getTimeStamp());
                System.out.println("i = "+i);
                System.out.println("#################################");

            }
            // Saving all variations every 10 seconds, and we do this
            // So we can send all of our database queries at once, instead of sending them after
            // The creation of each variation

            variationRepository.saveAll(variations);
            // Resting the list
            variations = new ArrayList<>();
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXX");
        }

    }
    private static double roundToSixDecimals(double value)
    {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(6, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
