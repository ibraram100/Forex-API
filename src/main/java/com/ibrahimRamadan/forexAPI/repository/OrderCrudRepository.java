package com.ibrahimRamadan.forexAPI.repository;

import com.ibrahimRamadan.forexAPI.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderCrudRepository extends CrudRepository<Order, Long> {

}
