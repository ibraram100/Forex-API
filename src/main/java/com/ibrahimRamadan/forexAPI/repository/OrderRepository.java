package com.ibrahimRamadan.forexAPI.repository;

import com.ibrahimRamadan.forexAPI.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByOrderStatusAndUserId(String open, long userId);
}
