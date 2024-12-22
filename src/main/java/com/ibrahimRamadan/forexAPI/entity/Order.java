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
    private double price;
    private LocalDateTime timeStamp;
}
