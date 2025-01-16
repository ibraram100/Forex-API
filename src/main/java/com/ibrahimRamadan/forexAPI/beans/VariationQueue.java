package com.ibrahimRamadan.forexAPI.beans;

import com.ibrahimRamadan.forexAPI.DTO.VariationDto;
import com.ibrahimRamadan.forexAPI.entity.Variation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// this class defines a bean that could store variations in a queue
// the reason behind it was so i don't have to call the variation generator in
// any market order so that i can improve performance.

@Configuration
public class VariationQueue {
    @Bean
    public BlockingQueue<VariationDto> latestVariationQueue()
    {
        return new LinkedBlockingQueue<>(1);
    }
}
