package com.ibrahimRamadan.forexAPI.repository;

import com.ibrahimRamadan.forexAPI.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {

}
