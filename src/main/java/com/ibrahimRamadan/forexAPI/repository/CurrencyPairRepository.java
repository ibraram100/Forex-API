package com.ibrahimRamadan.forexAPI.repository;

import com.ibrahimRamadan.forexAPI.entity.CurrencyPair;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CurrencyPairRepository extends JpaRepository<CurrencyPair, Integer> {
    Optional<CurrencyPair> findByCurrencyPairId(int currencyPairId);
    CurrencyPair findBySymbol(String symbol);
}
