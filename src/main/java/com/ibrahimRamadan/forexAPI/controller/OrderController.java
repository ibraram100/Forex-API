package com.ibrahimRamadan.forexAPI.controller;

import com.ibrahimRamadan.forexAPI.DTO.OrderDto;
import com.ibrahimRamadan.forexAPI.DTO.UserDto;
import com.ibrahimRamadan.forexAPI.entity.Order;
import com.ibrahimRamadan.forexAPI.entity.UserEntity;
import com.ibrahimRamadan.forexAPI.service.OrderService;
import com.ibrahimRamadan.forexAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    // This method is responsible for returning all the open trades the trader have made
    @GetMapping("open")
    public List<OrderDto> getAllOpenOrders(Principal principal)
    {
        UserDto userDto = userService.getUserByUsername(principal.getName());
        return orderService.getOpenOrders(userDto.getUserId());
    }
}
