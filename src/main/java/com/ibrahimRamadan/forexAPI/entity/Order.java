package com.ibrahimRamadan.forexAPI.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long orderId;
    @Column(name = "currency_pair")
    private String currencyPairName;
    @Column(name = "order_status")
    private String orderStatus;
    @Column(name = "user_id")
    private long userId;
    @Column(name = "order_type")
    private String orderType;
    // This one represents the open price
    private double price;
    // This one represents the close price
    @Column(name = "close_price")
    private double closePrice;
    private LocalDateTime timeStamp; // this one is the variation time stamp, not the actual time pair was bought/sold
    private double result;
    @Transient // it means that actualCloseTime won't be stored in te db
    private LocalDateTime actualCloseTime;
    @Transient
    private LocalDateTime effectiveCloseTime;
    @Transient
    private LocalDateTime realTime;
    private int quantity;
}
