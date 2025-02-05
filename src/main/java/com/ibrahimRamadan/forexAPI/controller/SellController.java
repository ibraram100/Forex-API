package com.ibrahimRamadan.forexAPI.controller;

import com.ibrahimRamadan.forexAPI.DTO.OrderDto;
import com.ibrahimRamadan.forexAPI.DTO.UserDto;
import com.ibrahimRamadan.forexAPI.DTO.VariationDto;
import com.ibrahimRamadan.forexAPI.security.JWTAuthenticationFilter;
import com.ibrahimRamadan.forexAPI.service.OrderService;
import com.ibrahimRamadan.forexAPI.service.UserService;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api")
public class SellController {

    private Timer timer;
    @Autowired
    private SimpleMeterRegistry simpleMeterRegistry;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private VariationController variationController;
    @Autowired
    private BlockingQueue<VariationDto> latestVariationQueue;

    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private static CopyOnWriteArrayList<Double> responseTimes = new CopyOnWriteArrayList<>();





    // Front would just send a request and the last sav
    @PostMapping("/sell")
    @Transactional
    public ResponseEntity<String> sellMarketOrder(Principal principal, @RequestParam int quantity)
    {
        // Time calculation thing
        timer = simpleMeterRegistry.timer("greetings.timer");
        Timer.Sample sample = Timer.start();


        // Creating the user who made the order
        UserDto userDto = userService.getUserByUsername(principal.getName());


        VariationDto variationDto = latestVariationQueue.peek();
        if (userDto.getCredit() < (quantity * variationDto.getBuyPrice()))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient funds !");
        }

        OrderDto orderDto = new OrderDto();
        orderDto.setOrderStatus("open");
        orderDto.setPrice(variationDto.getSellPrice());
        orderDto.setUserId(userDto.getUserId());
        orderDto.setTimeStamp(variationDto.getTimeStamp());
        orderDto.setQuantity(quantity);
        orderDto.setOrderType("Sell Order");
        orderService.saveOrder(orderDto);

        // Time calculation thing
        double responseTimeInMilliSeconds = timer.record(() -> sample.stop(timer) / 1000);

        // Writing response times to a file
        // writeResponseTimeToFile(responseTimeInMilliSeconds);
        // Adding response times to an array
        responseTimes.add(responseTimeInMilliSeconds);

        return ResponseEntity.status(HttpStatus.OK).body("Sell Order placed successfully");
    }

    @GetMapping("get_results")
    public void getResult()
    {

        writeListToFile(responseTimes);

        userService.getResult();
        // Clearing the array
        responseTimes.clear();

        orderService.getResult();
        jwtAuthenticationFilter.getResult();
    }


    private static void writeResponseTimesToFile(double[] responseTimes) {
        try (FileWriter writer = new FileWriter("SellController_response_times.txt", true)) {
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



//    private static void writeResponseTimesToFile(List<Double> responseTimes) {
//        try (FileWriter writer = new FileWriter("SellController_response_times.txt", true)) {
//            for (double responseTime : responseTimes) {
//                writer.write(responseTime + "\n");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }




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
