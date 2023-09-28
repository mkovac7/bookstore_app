package com.example.bookstoreApp.model;

import jakarta.annotation.Nullable;

import java.util.List;

public class OrderDto {

    private Long orderId;

    @Nullable
    private String userEmail;

    @Nullable
    private List<Book> orderContents;

    @Nullable
    private double totalPrice;

    private String deliveryAddress;

    private String paymentMethod;

    public OrderDto() {
    }

    public OrderDto(Long orderId, String userEmail, List<Book> orderContents, double totalPrice, String deliveryAddress) {
        this.orderId = orderId;
        this.userEmail = userEmail;
        this.orderContents = orderContents;
        this.totalPrice = totalPrice;
        this.deliveryAddress = deliveryAddress;
    }

    public OrderDto(String deliveryAddress, String paymentMethod) {
        this.deliveryAddress = deliveryAddress;
        this.paymentMethod = paymentMethod;
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
}
