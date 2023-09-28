package com.example.bookstoreApp.service;


import com.example.bookstoreApp.model.Book;
import com.example.bookstoreApp.model.BookDto;
import com.example.bookstoreApp.model.Order;
import com.example.bookstoreApp.model.OrderDto;
import com.example.bookstoreApp.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService { //Service klasa za Order model

    private final OrderRepository orderRepository;

    @Autowired
    OrderService(OrderRepository repository) {
        this.orderRepository = repository;
    }

    public List<Order> getAll() { //Metoda za vracanje svih Order objekata
        return orderRepository.findAll();
    }

    @Transactional
    public void saveOrder(OrderDto orderDto, List<Book> cart, String userEmail) { //Metoda za snimanje novih Order-a
        Order order = new Order();
        double totalPrice = 0;
        for (Book book : cart)
            totalPrice+=book.getPrice();
        order.setUserEmail(userEmail);
        order.setOrderContents(cart);
        order.setTotalPrice((double)Math.round(totalPrice*100.0)/100.0);
        order.setDeliveryAddress(orderDto.getDeliveryAddress());
        if(orderDto.getPaymentMethod().equals("")) {
            order.setPaymentMethod("Pay on delivery");
        } else {
            order.setPaymentMethod(orderDto.getPaymentMethod());
        }
        orderRepository.save(order);
        System.out.println("Saved new order: " + order.toString());
    }
}
