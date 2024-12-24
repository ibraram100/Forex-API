package com.ibrahimRamadan.forexAPI.service;

import com.ibrahimRamadan.forexAPI.DTO.OrderDto;
import com.ibrahimRamadan.forexAPI.entity.Order;
import com.ibrahimRamadan.forexAPI.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@AllArgsConstructor
@Setter
@Getter
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public OrderDto saveOrder (@Validated OrderDto orderDto)
    {
        Order order = modelMapper.map(orderDto, Order.class);
        orderRepository.save(order);
        return modelMapper.map(order,OrderDto.class);
    }
}
