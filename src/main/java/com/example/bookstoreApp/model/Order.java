package com.example.bookstoreApp.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "orders")
public class Order { //Model klasa za Order

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String userEmail;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderId")
    private List<Book> orderContents;

    private double totalPrice;

    private String deliveryAddress;


    private String paymentMethod;

    public Order() {
    }

    public Order(String userEmail, List<Book> orderContents, double totalPrice, String deliveryAddress) {
        this.userEmail = userEmail;
        this.orderContents = orderContents;
        this.totalPrice = totalPrice;
        this.deliveryAddress = deliveryAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<Book> getOrderContents() {
        return orderContents;
    }

    public void setOrderContents(List<Book> orderContents) {
        this.orderContents = orderContents;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userEmail='" + userEmail + '\'' +
                ", orderContents=" + orderContents +
                ", totalPrice=" + totalPrice +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                '}';
    }
}
