package com.ibrahimRamadan.forexAPI.controller;

import com.ibrahimRamadan.forexAPI.DTO.OrderDto;
import com.ibrahimRamadan.forexAPI.DTO.VariationDto;
import com.ibrahimRamadan.forexAPI.entity.UserEntity;
import com.ibrahimRamadan.forexAPI.entity.Variation;
import com.ibrahimRamadan.forexAPI.repository.UserRepository;
import com.ibrahimRamadan.forexAPI.service.OrderService;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;

@RestController
@RequestMapping("/api")
public class SellController {

    private Timer timer;
    @Autowired
    private SimpleMeterRegistry simpleMeterRegistry;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private VariationController variationController;

    @Autowired
    private BlockingQueue<VariationDto> latestVariationQueue;




    // Front would just send a request and the last sav
    @PostMapping("/sell")
    @Transactional
    public ResponseEntity<String> sellMarketOrder(Principal principal, @RequestParam int quantity)
    {
        // Time calculation thing
        timer = simpleMeterRegistry.timer("greetings.timer");
        Timer.Sample sample = Timer.start();


        // Creating the user who made the order
        Optional<UserEntity> userEntity = userRepository.findByUsername(principal.getName());
        if (userEntity.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found !");
        }
        UserEntity user = userEntity.get();


//        VariationDto variationDto = variationController.getLastVariation();
        VariationDto variationDto = latestVariationQueue.peek();
        if (user.getCredit() < (quantity * variationDto.getBuyPrice()))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient funds !");
        }

        OrderDto orderDto = new OrderDto();
        orderDto.setOrderStatus("open");
        orderDto.setPrice(variationDto.getSellPrice());
        orderDto.setUserId(user.getUserId());
        orderDto.setTimeStamp(variationDto.getTimeStamp());
        orderDto.setQuantity(quantity);
        orderDto.setOrderType("Sell Order");
        orderService.saveOrder(orderDto);

        // Time calculation thing
        double responseTimeInMilliSeconds = timer.record(() -> sample.stop(timer) / 1000000);
        System.out.println("Greetings API response time: " + responseTimeInMilliSeconds + " ms");

        return ResponseEntity.status(HttpStatus.OK).body("Sell Order placed successfully");

    }

//    @PostMapping("/sell")
    public void doNothing()
    {
        // Time calculation thing
        timer = simpleMeterRegistry.timer("greetings.timer");
        Timer.Sample sample = Timer.start();
        System.out.println("hello World !");

        // Time calculation thing
        double responseTimeInMilliSeconds = timer.record(() -> sample.stop(timer) / 1000);
        System.out.println("Greetings API response time: " + responseTimeInMilliSeconds + " ms");

    }
}
