package com.ibrahimRamadan.forexAPI.controller;

import com.ibrahimRamadan.forexAPI.DTO.OrderDto;
import com.ibrahimRamadan.forexAPI.DTO.VariationDto;
import com.ibrahimRamadan.forexAPI.entity.UserEntity;
import com.ibrahimRamadan.forexAPI.repository.UserRepository;
import com.ibrahimRamadan.forexAPI.service.OrderService;
import com.ibrahimRamadan.forexAPI.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class CloseController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private VariationController variationController;
    @Autowired
    private UserService userService;


    @Transactional
    public HttpStatus closeTrade(Principal principal, long orderId)
    {
        OrderDto orderDto = orderService.findById(orderId);
        // making sure we are not closing a closed order
        if (orderDto.getOrderStatus() != "open")
        {
            return HttpStatus.BAD_REQUEST;
        }


        // Dealing with buy trades
        if (orderDto.getOrderType() == "buy")
        {
            VariationDto variationDto = variationController.getLastVariation();
            UserEntity user = userService.getUserByUsername(principal.getName());

        }


        return null;
    }
}
