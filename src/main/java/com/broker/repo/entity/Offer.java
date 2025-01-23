package com.broker.repo.entity;

import jakarta.persistence.*;

@Entity
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price; // قیمت پیشنهاد
    private Double quantity; // تعداد پیشنهادی
    private String status; // وضعیت پیشنهاد (مثل Pending, Accepted, Rejected)

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; // ارجاع به اوردر مربوطه

    // Constructors, Getters, Setters
}
