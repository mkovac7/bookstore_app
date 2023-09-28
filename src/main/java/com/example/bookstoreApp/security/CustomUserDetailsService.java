package com.example.bookstoreApp.security;

import com.example.bookstoreApp.model.User;
import com.example.bookstoreApp.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService { //UserDetailsService za odredjivanje uloge korisnika

    private UserRepository repository;
    public CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email);
        if (user != null && user.getName().equals("ADMIN")) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority("ADMIN"));
        }else if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority("USER"));
        } else {
            throw new UsernameNotFoundException("Invalid email or password");
        }
    }

    private List<GrantedAuthority> getAuthority(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}
