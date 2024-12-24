package com.ibrahimRamadan.forexAPI.DTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private long orderId;
    private String currencyPairName;
    private String orderStatus;
    private long userId;
    private double price;
    private LocalDateTime timeStamp;
    private LocalDateTime realTime;
    private double result;
    private LocalDateTime actualCloseTime;
    private LocalDateTime effectiveCloseTime;
    private int quantity;
}

