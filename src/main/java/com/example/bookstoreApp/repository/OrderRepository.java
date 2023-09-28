package com.example.bookstoreApp.repository;

import com.example.bookstoreApp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> { //Repo interfejs za Order model
}
