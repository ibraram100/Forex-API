package com.ibrahimRamadan.forexAPI.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class VariationDto {
    private long variationId;
    private double price;
    private LocalDateTime timeStamp;
    private double buyPrice;
    private double sellPrice;
}
