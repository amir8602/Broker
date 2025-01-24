package com.broker.repo.entity;

import com.broker.repo.enums.OfferStatus;
import com.broker.repo.enums.OrderAction;
import jakarta.persistence.*;

@Entity
@Table(name = "offer")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tofMali;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; // ارجاع به اوردر مربوطه

    private Long amount; // قیمت پیشنهاد
    private Long quantity; // تعداد پیشنهادی

    @Enumerated(EnumType.STRING)
    private OfferStatus status = OfferStatus.Pending;

    private String userId;

    private String cellNumber;

    private Long totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderAction action;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public OfferStatus getStatus() {
        return status;
    }

    public void setStatus(OfferStatus status) {
        this.status = status;
    }



    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Long getTofMali() {
        return tofMali;
    }

    public void setTofMali(Long toofMali) {
        this.tofMali = toofMali;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderAction getAction() {
        return action;
    }

    public void setAction(OrderAction action) {
        this.action = action;
    }
}
