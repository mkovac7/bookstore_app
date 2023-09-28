package com.example.bookstoreApp.configuration;

import com.example.bookstoreApp.model.User;
import com.example.bookstoreApp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class UserConfiguration { //konfiguracija za user model

    private static final Logger log = LoggerFactory.getLogger(UserConfiguration.class);
    private PasswordEncoder passwordEncoder;

    public UserConfiguration(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Bean //preload ADMIN usera
    CommandLineRunner initUserDatabase(UserRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new User("ADMIN", "ADMIN", passwordEncoder.encode("ADMIN"))));
        };
    }
}
