package com.example.bookstoreApp.configuration;

import com.example.bookstoreApp.model.Book;
import com.example.bookstoreApp.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BookConfiguration { //konfiguracija za book model

    private static final Logger log = LoggerFactory.getLogger(BookConfiguration.class);

    //preload par primeraka knjiga
    @Bean
    CommandLineRunner initBookDatabase(BookRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Book("A Game of Thrones", "George Martin", "HarperCollins Publishers", 13.58)));
            log.info("Preloading " + repository.save(new Book("Dune", "Frank Herbert", "HODDER", 13.80)));
        };
    }
}
