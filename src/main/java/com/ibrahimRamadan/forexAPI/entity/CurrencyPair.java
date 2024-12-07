package com.ibrahimRamadan.forexAPI.entity;
// This class contains important information about currency pairs
// The exchange rate attribute is left out intentionally,
// as they will be used in the variation class

import com.ibrahimRamadan.forexAPI.repository.CurrencyPairRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "currency_pairs")
public class CurrencyPair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int currencyPairId;
    @Column(name = "base_currency")
    private String baseCurrency;
    @Column(name = "quote_currency")
    private String quoteCurrency;
    private String symbol; // EUR/USD for example

    @OneToMany(mappedBy = "currencyPair", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Variation> variations;
}
