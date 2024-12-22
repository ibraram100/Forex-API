package com.ibrahimRamadan.forexAPI.controller;


import com.ibrahimRamadan.forexAPI.DTO.VariationDto;
import com.ibrahimRamadan.forexAPI.entity.Order;
import com.ibrahimRamadan.forexAPI.entity.UserEntity;
import com.ibrahimRamadan.forexAPI.repository.OrderRepository;
import com.ibrahimRamadan.forexAPI.repository.UserRepository;
import com.ibrahimRamadan.forexAPI.service.BuyOrderService;
import com.ibrahimRamadan.forexAPI.service.VariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BuyController {

    @Autowired
    private VariationService variationService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    private List<Order> orders = new ArrayList<>();


    // Front end should send a variation so we can store the order
    @PostMapping("/buy")
    public HttpStatus marketOrder(Principal principal, @RequestParam int quantity)
    {
        // Creating the user who made the order
        Optional<UserEntity> userEntity = userRepository.findByUsername(principal.getName());
        if (userEntity.isEmpty())
        {
            return HttpStatus.BAD_REQUEST;
        }
        UserEntity user = userEntity.get();

        VariationDto variationDto = variationService.getLastVariation();

        Order templateOrder = new Order();
        templateOrder.setOrderStatus("open");
        templateOrder.setPrice(variationDto.getBuyPrice());
        templateOrder.setUserId(user.getUserId());
        templateOrder.setTimeStamp(variationDto.getTimeStamp());
        for (int i=0;i<quantity;i++)
        {
            Order order = new Order();
            order.setOrderStatus(templateOrder.getOrderStatus());
            order.setPrice(templateOrder.getPrice());
            order.setUserId(templateOrder.getUserId());
            order.setTimeStamp(templateOrder.getTimeStamp());

            orders.add(order);
        }
        orderRepository.saveAll(orders);

        return HttpStatus.OK;
    }
}
