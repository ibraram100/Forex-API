package com.ibrahimRamadan.forexAPI.service;

import com.ibrahimRamadan.forexAPI.DTO.OrderDto;
import com.ibrahimRamadan.forexAPI.entity.Order;
import com.ibrahimRamadan.forexAPI.exception.ResourceNotFoundException;
import com.ibrahimRamadan.forexAPI.repository.OrderCrudRepository;
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
import org.springframework.web.bind.annotation.GetMapping;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
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

    @Autowired
    private OrderCrudRepository orderCrudRepository;

    private Timer timer;
    @Autowired
    private SimpleMeterRegistry simpleMeterRegistry;

    @Autowired
    private static CopyOnWriteArrayList<Double> responseTimes = new CopyOnWriteArrayList<>();



    public OrderDto saveOrder (@Validated OrderDto orderDto)
    {
        // Time calculation thing
        timer = simpleMeterRegistry.timer("greetings.timer");
        Timer.Sample sample = Timer.start();

        Order order = modelMapper.map(orderDto, Order.class);
        orderCrudRepository.save(order);

        // Time calculation thing
        double responseTimeInMilliSeconds = timer.record(() -> sample.stop(timer) / 1000);
//        System.out.println("OrderService response time: " + responseTimeInMilliSeconds + " microsecond");

        responseTimes.add(responseTimeInMilliSeconds);


        return modelMapper.map(order,OrderDto.class);

    }


    private static void writeResponseTimesToFile(double[] responseTimes) {
        try (FileWriter writer = new FileWriter("OrderService_response_times.txt", true)) {
            for (double responseTime : responseTimes) {
                writer.write(responseTime + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void writeListToFile(List<Double> responseTimes) {
        double[] responseTimesArray = responseTimes.stream().mapToDouble(Double::doubleValue).toArray();
        writeResponseTimesToFile(responseTimesArray);
    }

    public void getResult()
    {
        writeListToFile(responseTimes);
        // Clearing the array
        responseTimes.clear();

    }


//    public OrderDto saveOrder (@Validated OrderDto orderDto)
//    {
//        // Time calculation thing
//        timer = simpleMeterRegistry.timer("greetings.timer");
//        Timer.Sample sample = Timer.start();
//
//        Order order = modelMapper.map(orderDto, Order.class);
//        orderRepository.save(order);
//
//        // Time calculation thing
//        double responseTimeInMilliSeconds = timer.record(() -> sample.stop(timer) / 1000);
//        System.out.println("OrderService response time: " + responseTimeInMilliSeconds + " microsecond");
//
//        return modelMapper.map(order,OrderDto.class);
//    }

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
