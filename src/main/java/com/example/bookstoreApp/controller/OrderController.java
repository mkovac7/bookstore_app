package com.example.bookstoreApp.controller;

import com.example.bookstoreApp.model.Book;
import com.example.bookstoreApp.model.BookDto;
import com.example.bookstoreApp.model.OrderDto;
import com.example.bookstoreApp.model.User;
import com.example.bookstoreApp.service.OrderService;
import com.example.bookstoreApp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController { //kontroler klasa za Order model

    private final OrderService orderService;
    private final UserService userService;

    OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }


    @PostMapping("/cartPage/confirmOrder") //Post mapping metoda za snimanje Order-a
    public String confirmOrder(@ModelAttribute("order") OrderDto orderDto,
                             HttpServletRequest request,
                             BindingResult result,
                             Model model) {

        User currentUser = userService.getCurrentUser(request);
        List<Book> cart = currentUser.getCart();
        userService.emptyUserCart(currentUser.getEmail());

        if(result.hasErrors()) {
            model.addAttribute("order", orderDto);
            return "/cartPage";
        }
        orderService.saveOrder(orderDto, cart, currentUser.getEmail());
        return "redirect:/cartPage?success";
    }
}
