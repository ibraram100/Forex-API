package com.ibrahimRamadan.forexAPI.repository;

import com.ibrahimRamadan.forexAPI.entity.Variation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariationRepository extends JpaRepository<Variation, Long> {
    Variation findTopByOrderByTimeStampDesc();
}
