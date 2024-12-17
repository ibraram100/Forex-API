package com.ibrahimRamadan.forexAPI.controller;

import com.ibrahimRamadan.forexAPI.entity.CurrencyPair;
import com.ibrahimRamadan.forexAPI.entity.Variation;
import com.ibrahimRamadan.forexAPI.repository.CurrencyPairRepository;
import com.ibrahimRamadan.forexAPI.repository.VariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private CurrencyPairRepository cpRepository;

    @Autowired
    private VariationRepository variationRepository;

    private static final java.util.Random RANDOM = new java.util.Random();

    @GetMapping("/stream")
    public SseEmitter streamVariations() {
        SseEmitter emitter = new SseEmitter();
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            String currencySymbol = "EUR/USD";
            CurrencyPair currencyPair = cpRepository.findBySymbol(currencySymbol);
            List<Variation> variations = new ArrayList<>();

            while (true) {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(1000); // Sleeping for 1 second before creating a new variation

                        Variation variation = new Variation();
                        variation.setCurrencyPair(currencyPair);
                        double price = 1 + roundToSixDecimals(RANDOM.nextDouble());
                        variation.setPrice(price);
                        variation.setTimeStamp(LocalDateTime.now());
                        variations.add(variation);

                        emitter.send(variation);

                        System.out.println("Price: " + variation.getPrice());
                        System.out.println("Time Stamp: " + variation.getTimeStamp());
                        System.out.println("i = " + i);
                        System.out.println("#################################");

                    } catch (IOException | InterruptedException e) {
                        emitter.completeWithError(e);
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                // Saving all variations every 10 seconds, and we do this
                // So we can send all of our database queries at once, instead of sending them after
                // The creation of each variation
                variationRepository.saveAll(variations);

                // Resetting the list
                variations = new ArrayList<>();
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXX");
            }
        });

        return emitter;
    }

    private double roundToSixDecimals(double value) {
        return Math.round(value * 1_000_000.0) / 1_000_000.0;
    }
}
