package com.ibrahimRamadan.forexAPI.service;

import com.ibrahimRamadan.forexAPI.DTO.OrderDto;
import com.ibrahimRamadan.forexAPI.entity.Order;
import com.ibrahimRamadan.forexAPI.exception.ResourceNotFoundException;
import com.ibrahimRamadan.forexAPI.repository.OrderRepository;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//@AllArgsConstructor
@Setter
@Getter
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ModelMapper modelMapper;

    private Timer timer;
    @Autowired
    private SimpleMeterRegistry simpleMeterRegistry;

    public OrderDto saveOrder (@Validated OrderDto orderDto)
    {
        // Time calculation thing
        timer = simpleMeterRegistry.timer("greetings.timer");
        Timer.Sample sample = Timer.start();

        Order order = modelMapper.map(orderDto, Order.class);
        orderRepository.save(order);

        // Time calculation thing
        double responseTimeInMilliSeconds = timer.record(() -> sample.stop(timer) / 1000);
        System.out.println("OrderService response time: " + responseTimeInMilliSeconds + " microsecond");

        return modelMapper.map(order,OrderDto.class);
    }

    public OrderDto findById(long orderId)
    {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty())
        {
            throw new ResourceNotFoundException("Order not found for this id :: " + orderId);
        }
        Order order = orderOptional.get();
        return modelMapper.map(order, OrderDto.class);
    }

    // Returning all the open orders made by a trader
    public List<OrderDto> getOpenOrders(long userId)
    {
         List<Order> openOrders = orderRepository.findByOrderStatusAndUserId("open", userId);
         List<OrderDto> openOrderDtos = openOrders.stream()
                 .map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
         return openOrderDtos;

    }
}
