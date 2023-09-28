package com.example.bookstoreApp.repository;

import com.example.bookstoreApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> { //Repo interfejs za User model

    User findByEmail(String email);
}
