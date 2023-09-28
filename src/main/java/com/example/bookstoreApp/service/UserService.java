package com.example.bookstoreApp.service;

import com.example.bookstoreApp.model.Book;
import com.example.bookstoreApp.model.User;
import com.example.bookstoreApp.model.UserDto;
import com.example.bookstoreApp.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService { //Service klasa za User model

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(UserDto userDto) { //Metoda za snimanje novih User objekata
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        System.out.println("Saved user: " + user.toString());
    }

    public void updateUserCart(String email, List<Book> cart) { //Metoda za dodavanje Book objekata u cart
        User user = findUserByEmail(email);
        user.setCart(cart);
        userRepository.save(user);
    }

    public void emptyUserCart(String email) { //Metoda za praznjenje cart-a
        User user = findUserByEmail(email);
        user.setCart(null);
        userRepository.save(user);
    }

    public double totalCartPrice(List <Book> cart) { //Metoda za proracun ukupne cene
        double totalPrice = 0;
        for (Book book : cart)
            totalPrice+=book.getPrice();
        return (Math.round(totalPrice*100.0)/100.0);
    }

    public User getCurrentUser(HttpServletRequest request) { //Metoda za vracanje trenutnog korisnika iz requesta
        return  findUserByEmail(request.getUserPrincipal().getName());
    }

    public User findUserByEmail(String email) { //Metoda za vracanje korisnika na osnovu email-a
        return userRepository.findByEmail(email);
    }

    public List<User> getAll() { //metoda za vracanje svih korisnika
        return userRepository.findAll();
    }

}
