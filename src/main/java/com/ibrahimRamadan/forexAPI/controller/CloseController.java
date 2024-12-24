package com.ibrahimRamadan.forexAPI.controller;

import com.ibrahimRamadan.forexAPI.DTO.OrderDto;
import com.ibrahimRamadan.forexAPI.DTO.UserDto;
import com.ibrahimRamadan.forexAPI.DTO.VariationDto;
import com.ibrahimRamadan.forexAPI.entity.UserEntity;
import com.ibrahimRamadan.forexAPI.repository.UserRepository;
import com.ibrahimRamadan.forexAPI.service.OrderService;
import com.ibrahimRamadan.forexAPI.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class CloseController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private VariationController variationController;
    @Autowired
    private UserService userService;


    // This method is responsible for closing the trade
    @Transactional
    @PostMapping("/close")
    public ResponseEntity<String> closeTrade(Principal principal, @RequestParam long orderId)
    {
        OrderDto orderDto = orderService.findById(orderId);
        // making sure we are not closing a closed order
        if (!orderDto.getOrderStatus().equals("open"))
        {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order is not open");

        }


        // Dealing with buy trades
        if (!orderDto.getOrderType().equals("buy"))
        {
            VariationDto variationDto = variationController.getLastVariation();
            UserDto userDto = userService.getUserByUsername(principal.getName());
            // Calculating how much the trader made/lost from the sale
            double total = userDto.getCredit() + ( (variationDto.getBuyPrice()-orderDto.getPrice())* orderDto.getQuantity() );
            orderDto.setOrderStatus("closed");
            userDto.setCredit(total);
            orderService.saveOrder(orderDto);
            userService.saveUser(userDto);
        }


        return null;
    }
}
