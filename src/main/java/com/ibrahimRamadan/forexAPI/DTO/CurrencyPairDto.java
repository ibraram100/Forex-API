package com.ibrahimRamadan.forexAPI.DTO;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CurrencyPairDto {

    private int currencyPairId;
    private String baseCurrency;
    private String quote_currency;
    private String symbol;


}