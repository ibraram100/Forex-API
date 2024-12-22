package com.ibrahimRamadan.forexAPI.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "variations")
public class Variation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "variation_id")
    private long variationId;
    private double price;
    private LocalDateTime timeStamp;
    @ManyToOne
    @JoinColumn(name = "currency_pair_id", nullable = false)
    private CurrencyPair currencyPair;
    @Column(name = "buy_price")
    private double buyPrice;
    @Column(name = "sell_price")
    private double sellPrice;
}
