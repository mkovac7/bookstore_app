package com.example.bookstoreApp.controller;

import com.example.bookstoreApp.model.User;
import com.example.bookstoreApp.model.UserDto;
import com.example.bookstoreApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AuthenticationController { //kontroler klasa za autentikaciju

    private UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login") //Get mapping metoda za login stranicu
    public String login(){
        return "login";
    }

    @GetMapping("/register") //Get mapping metoda za register stranicu
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/register/save") //Post mapping metoda za registraciju
    public String registration(@Validated @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "redirect:/register?error";
        } else {
            userService.saveUser(userDto);
            return "redirect:/register?success";
        }
    }


}
