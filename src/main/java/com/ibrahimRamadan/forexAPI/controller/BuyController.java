package com.ibrahimRamadan.forexAPI.controller;


import com.ibrahimRamadan.forexAPI.DTO.OrderDto;
import com.ibrahimRamadan.forexAPI.DTO.VariationDto;
import com.ibrahimRamadan.forexAPI.entity.Order;
import com.ibrahimRamadan.forexAPI.entity.UserEntity;
import com.ibrahimRamadan.forexAPI.repository.OrderRepository;
import com.ibrahimRamadan.forexAPI.repository.UserRepository;
import com.ibrahimRamadan.forexAPI.service.OrderService;
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
    @Autowired
    private OrderService orderService;
    @Autowired
    private VariationController variationController;
   


    // Front would just send a request and the last sav
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

//        VariationDto variationDto = variationService.getLastVariation();
        VariationDto variationDto = variationController.getLastVariation();
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderStatus("open");
        orderDto.setPrice(variationDto.getBuyPrice());
        orderDto.setUserId(user.getUserId());
        orderDto.setTimeStamp(variationDto.getTimeStamp());
        orderDto.setQuantity(quantity);
        orderService.saveOrder(orderDto);
        return HttpStatus.OK;
    }
}
